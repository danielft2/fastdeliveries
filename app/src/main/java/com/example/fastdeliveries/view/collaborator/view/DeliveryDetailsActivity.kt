package com.example.fastdeliveries.view.collaborator.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.view.WindowManager
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fastdeliveries.R
import com.example.fastdeliveries.databinding.ActivityDeliveryDetailsBinding
import com.example.fastdeliveries.databinding.ActivityUpdateDeliveryStatusBinding
import com.example.fastdeliveries.view.collaborator.constants.DeliveryConstants
import com.example.fastdeliveries.view.collaborator.enums.DeliveryStatus
import com.example.fastdeliveries.view.collaborator.enums.DeliveryUpdate
import com.example.fastdeliveries.view.collaborator.models.LastUpdateDelivery
import com.example.fastdeliveries.view.collaborator.view.adapter.LastsUpdatesAdpter
import com.example.fastdeliveries.view.collaborator.viewModel.DeliveryDetailsViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DeliveryDetailsActivity : AppCompatActivity(), OnClickListener {
    private lateinit var binding: ActivityDeliveryDetailsBinding
    private lateinit var sheetUpdateBiding: ActivityUpdateDeliveryStatusBinding
    private lateinit var viewModel: DeliveryDetailsViewModel
    private lateinit var dialog: BottomSheetDialog

    private val adpter = LastsUpdatesAdpter()
    private var lastDeliveryClicked: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeliveryDetailsBinding.inflate(layoutInflater)
        sheetUpdateBiding = ActivityUpdateDeliveryStatusBinding.inflate(layoutInflater, null, false)
        viewModel = ViewModelProvider(this).get(DeliveryDetailsViewModel::class.java)

        dialog = BottomSheetDialog(this, R.style.BottomSheetDialog).apply {
            window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_MODE_CHANGED)
        }

        setContentView(binding.root)
        initSetup()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.button_back -> { finish() }
            R.id.button_update_delivery -> { showSheetUpdateDelivery() }
            R.id.button_delete_delivery -> {
                AlertDialog.Builder(this)
                    .setTitle("Confirmar exclusão.")
                    .setMessage("Tem certeza que deseja excluir a entrega?")
                    .setPositiveButton("Sim") { dialog, wich ->
                        viewModel.deleteDelivery(lastDeliveryClicked)
                    }
                    .create()
                    .show()
            }
            R.id.button_confirm_update -> {
                viewModel.updateStatusDelivery(
                    lastDeliveryClicked,
                    sheetUpdateBiding.editPassword.text.toString(),
                    getStatusOfUpdate(sheetUpdateBiding.spinnerStatusDelivery.selectedItem.toString())
                )
                dialog.dismiss()
            }
        }
    }

    private fun initSetup() {
        // List of updates
        supportActionBar?.hide()
        binding.listUpdates.layoutManager = LinearLayoutManager(this)
        binding.listUpdates.adapter = adpter

        loadDataOfDelivery()
        observer()
        listeners()
    }

    private fun loadDataOfDelivery() {
        val bundle = intent.extras
        if (bundle != null) {
            val deliveryId = bundle.getInt(DeliveryConstants.PARAMS_DELIVERY_DETAILS.DELIVERY_ID)
            lastDeliveryClicked = deliveryId
            viewModel.getAllLastsUpdates(deliveryId)
            viewModel.getDataDelivery(deliveryId)
        }
    }

    private fun loadDataSpinnerStatus() {
        val range = DeliveryUpdate.values().count() - 1;
        val list = DeliveryUpdate.values().slice(1..range) .map { it.value  }
        val adapter = ArrayAdapter(this, R.layout.spinner_item, list)
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        sheetUpdateBiding.spinnerStatusDelivery.adapter = adapter
    }

    private fun listeners() {
        binding.buttonBack.setOnClickListener(this)
        binding.buttonUpdateDelivery.setOnClickListener(this)
        binding.buttonDeleteDelivery.setOnClickListener(this)
    }

    private fun observer() {
        viewModel.lastsUpdates.observe(this) {
            adpter.updateLastsUpdates(it.reversed())
        }

        viewModel.deliveryData.observe(this) {
            if (it != null) {
                binding.textCodDelevery.text = it.code
                binding.textCityDelivery.text = it.order.city
                binding.textAdressDelivery.text = it.order.adress
                binding.textRecipientDelivery.text = it.order.recipient_name
                binding.textNameProduct.text = it.order.name_product
                isDeliveryCompleted(it.status)
            }
        }

        viewModel.deleteDelivery.observe(this) {
            if (it.status()) {
                showSnackbar(getString(R.string.DELETE_DELIVERY_SUCCESS))
                finish()
            } else {
                showSnackbar(it.message())
            }
        }

        viewModel.updateStatus.observe(this) {
            if (it.status()) {
                showSnackbar(getString(R.string.UPDATE_DELIVERY_SUCCESS))
                viewModel.getAllLastsUpdates(lastDeliveryClicked)
                viewModel.getDataDelivery(lastDeliveryClicked)
            } else {
                showSnackbar(it.message())
            }
        }
    }

    private fun showSnackbar(message: String) { Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show() }

    private fun showSheetUpdateDelivery() {
        sheetUpdateBiding = ActivityUpdateDeliveryStatusBinding.inflate(layoutInflater, null, false)
        sheetUpdateBiding.buttonConfirmUpdate.setOnClickListener(this)

        loadDataSpinnerStatus()
        dialog.setContentView(sheetUpdateBiding.root)
        dialog.show()
    }

    private fun getStatusOfUpdate(value: String): LastUpdateDelivery {
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy, hh:mm:ss a")

        val type = DeliveryUpdate.values().find { it.value === value }
        return LastUpdateDelivery(type!!, LocalDateTime.now().format(formatter).toString())
    }

    private fun isDeliveryCompleted(deliveryStatus: DeliveryStatus) {
        if (deliveryStatus == DeliveryStatus.ENTREGUE) {
            binding.imageIcBox.setColorFilter(ContextCompat.getColor(this, R.color.green_500))
            binding.textCodDelevery.setTextColor(ContextCompat.getColor(this, R.color.green_500))
            binding.buttonUpdateDelivery.visibility = View.GONE
            binding.buttonDeleteDelivery.visibility = View.GONE
        }
    }
}