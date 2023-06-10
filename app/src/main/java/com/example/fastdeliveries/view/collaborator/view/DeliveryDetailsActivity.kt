package com.example.fastdeliveries.view.collaborator.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fastdeliveries.R
import com.example.fastdeliveries.databinding.ActivityDeliveryDetailsBinding
import com.example.fastdeliveries.view.collaborator.constants.DeliveryConstants
import com.example.fastdeliveries.view.collaborator.view.adapter.LastsUpdatesAdpter
import com.example.fastdeliveries.view.collaborator.viewModel.DeliveryDetailsViewModel

class DeliveryDetailsActivity : AppCompatActivity(), OnClickListener {
    private lateinit var binding: ActivityDeliveryDetailsBinding
    private lateinit var viewModel: DeliveryDetailsViewModel
    private val adpter = LastsUpdatesAdpter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeliveryDetailsBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(DeliveryDetailsViewModel::class.java)

        setContentView(binding.root)

        binding.listUpdates.layoutManager = LinearLayoutManager(this)
        binding.listUpdates.adapter = adpter

        loadData()
        observer()
        listeners()
        supportActionBar?.hide()
    }

    override fun onClick(v: View) {
        if (v.id == R.id.button_back) {
            finish()
        }
    }

    private fun listeners() {
        binding.buttonBack.setOnClickListener(this)
    }

    private fun loadData() {
        val bundle = intent.extras
        if (bundle != null) {
            val deliveryId = bundle.getInt(DeliveryConstants.PARAMS_DELIVERY_DETAILS.DELIVERY_ID)
            viewModel.getAllLastsUpdates(deliveryId)
            viewModel.getDataDelivery(deliveryId)
        }
    }

    private fun observer() {
        viewModel.lastsUpdates.observe(this) {
            adpter.updateLastsUpdates(it)
        }

        viewModel.deliveryData.observe(this) {
            if (it != null) {
                binding.textCodDelevery.text = it.code
                binding.textAdressDelivery.text = it.order.city
                binding.textRecipientDelivery.text = it.order.recipient_name
                binding.textNameProduct.text = it.order.name_product
            }
        }
    }
}