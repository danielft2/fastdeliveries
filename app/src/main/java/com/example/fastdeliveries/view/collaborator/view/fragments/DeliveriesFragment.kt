package com.example.fastdeliveries.view.collaborator.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fastdeliveries.R
import com.example.fastdeliveries.databinding.FragmentDeliveriesBinding
import com.example.fastdeliveries.view.collaborator.constants.DeliveryConstants
import com.example.fastdeliveries.view.collaborator.view.DeliveryDetailsActivity
import com.example.fastdeliveries.view.collaborator.view.adapter.DeliveriesAdapter
import com.example.fastdeliveries.view.collaborator.view.listeners.IDeliveryListener
import com.example.fastdeliveries.view.collaborator.viewModel.DeliveriesViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning

class DeliveriesFragment : Fragment(), OnClickListener {
    private var _binding: FragmentDeliveriesBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: DeliveriesViewModel

    private val adpater: DeliveriesAdapter = DeliveriesAdapter()
    private var toast: Toast? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, b: Bundle?): View {
        viewModel = ViewModelProvider(this).get(DeliveriesViewModel::class.java)
        _binding = FragmentDeliveriesBinding.inflate(inflater, container, false)

        binding.recycleListDeliveries.layoutManager = LinearLayoutManager(context)
        binding.recycleListDeliveries.adapter = adpater
        binding.fab.setOnClickListener(this)

        val listener = object : IDeliveryListener {
            override fun onClick(id: Int) {
                val intent = Intent(context, DeliveryDetailsActivity::class.java)
                val bundle = Bundle()
                bundle.putInt(DeliveryConstants.PARAMS_DELIVERY_DETAILS.DELIVERY_ID, id)
                intent.putExtras(bundle)

                startActivity(intent)
            }
        }

        adpater.setDeliveryListerner(listener)
        viewModel.getAllDeliveries()
        observe()

        return binding.root
    }

    override fun onStart() {
        viewModel.getAllDeliveries()
        super.onStart()
    }

    override fun onResume() {
        viewModel.getAllDeliveries()
        super.onResume()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View) {
        if (v.id == R.id.fab) {
            qrCodeScanner()
        }
    }

    private fun qrCodeScanner() {
        val options = GmsBarcodeScannerOptions.Builder().setBarcodeFormats(Barcode.FORMAT_QR_CODE).build()
        val scanner = context?.let { GmsBarcodeScanning.getClient(it, options) }
        scanner?.startScan()
        ?.addOnSuccessListener { barcode ->
            val delivery: String? = barcode.rawValue
            createNewDelivery(delivery)
        }?.addOnFailureListener {
            showSnackbar(getString(R.string.FAILURE_SCANNER))
        }
    }

    private fun createNewDelivery(orderJSON: String?) {
        if (orderJSON != null) viewModel.createNewDelivery(orderJSON)
        else showSnackbar(getString(R.string.QR_CODE_INVALID))
    }

    private fun showSnackbar(message: String) { Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show() }

    private fun observe() {
        viewModel.deliveries.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                binding.recycleListDeliveries.visibility = View.VISIBLE
                binding.layoutDeliveriesEmpty.visibility = View.GONE
                adpater.updateDeliveries(it.reversed())
            } else {
                binding.recycleListDeliveries.visibility = View.GONE
                binding.layoutDeliveriesEmpty.visibility = View.VISIBLE
            }
        }

        viewModel.createDelivery.observe(viewLifecycleOwner) {
            if (it.status()) {
                viewModel.getAllDeliveries()
                showSnackbar(getString(R.string.CREATE_DELIVERY_SUCCESS))
            } else {
                showSnackbar(it.message())
            }
        }
    }
}