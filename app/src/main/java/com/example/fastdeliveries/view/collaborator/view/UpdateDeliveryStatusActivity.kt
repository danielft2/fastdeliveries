package com.example.fastdeliveries.view.collaborator.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.fastdeliveries.R
import com.example.fastdeliveries.databinding.ActivityUpdateDeliveryStatusBinding
import com.example.fastdeliveries.view.collaborator.constants.DeliveryConstants
import com.example.fastdeliveries.view.collaborator.enums.DeliveryUpdate
import com.example.fastdeliveries.view.collaborator.models.LastUpdateDelivery
import com.example.fastdeliveries.view.collaborator.viewModel.UpdateDeliveryStatusViewModel
import java.util.Date

class UpdateDeliveryStatusActivity : AppCompatActivity(), OnClickListener {
    private lateinit var binding: ActivityUpdateDeliveryStatusBinding
    private lateinit var viewModel: UpdateDeliveryStatusViewModel
    private var delivery_id: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateDeliveryStatusBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(UpdateDeliveryStatusViewModel::class.java)

        setContentView(binding.root)
        supportActionBar?.hide()

        listener()
        observer()
        loadDataIntent()
        loadDataSpinner()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.button_confirm_update -> {
                viewModel.updateStatusDelivery(
                    delivery_id,
                    binding.editPassword.text.toString(),
                    newLastUpdate(binding.spinnerStatusDelivery.selectedItemPosition)
                )
            }
        }
    }

    private fun loadDataSpinner() {
        val list = listOf<String>(
            DeliveryUpdate.MOVIMENTANDO_ENTRE_AS_FILIAIS.value,
            DeliveryUpdate.SAINDO_PARA_ENTREGA.value,
            DeliveryUpdate.EM_MOVIMENTO.value,
            DeliveryUpdate.CHEGOU_NO_CIDADE_DE_DESTINO.value,
            DeliveryUpdate.ENTREGUE_AO_DESTINATARIO.value
        )

        val adpter = ArrayAdapter(this, R.layout.spinner_item, list)
        adpter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        binding.spinnerStatusDelivery.adapter = adpter
    }

    private fun loadDataIntent() {
        val bundle = intent.extras
        if (bundle != null) {
            val deliveryId = bundle.getInt(DeliveryConstants.PARAMS_DELIVERY_DETAILS.DELIVERY_ID)
            delivery_id = deliveryId
        }
    }

    private fun newLastUpdate(position: Int): LastUpdateDelivery {
        var type = DeliveryUpdate.AGUARDANDO_MOVIMENTACAO

        when(position) {
            0 -> { type = DeliveryUpdate.MOVIMENTANDO_ENTRE_AS_FILIAIS }
            1 -> { type = DeliveryUpdate.SAINDO_PARA_ENTREGA }
            2 -> { type = DeliveryUpdate.EM_MOVIMENTO }
            3 -> { type = DeliveryUpdate.CHEGOU_NO_CIDADE_DE_DESTINO }
            4 -> { type = DeliveryUpdate.ENTREGUE_AO_DESTINATARIO }
        }

        return LastUpdateDelivery(type, Date().toString())
    }

    private fun listener() {
        binding.buttonConfirmUpdate.setOnClickListener(this)
    }

    private fun observer() {
        viewModel.updateStatus.observe(this, Observer {
            if (it.status()) {
                Toast.makeText(this, getString(R.string.UPDATE_DELIVERY_SUCCESS), Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, it.message(), Toast.LENGTH_SHORT).show()
            }
        })
    }
}