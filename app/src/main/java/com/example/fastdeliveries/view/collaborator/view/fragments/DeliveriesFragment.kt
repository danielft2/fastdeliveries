package com.example.fastdeliveries.view.collaborator.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fastdeliveries.databinding.FragmentDeliveriesBinding
import com.example.fastdeliveries.view.collaborator.constants.DeliveryConstants
import com.example.fastdeliveries.view.collaborator.view.DeliveryDetailsActivity
import com.example.fastdeliveries.view.collaborator.view.adapter.DeliveriesAdapter
import com.example.fastdeliveries.view.collaborator.view.listeners.IDeliveryListener
import com.example.fastdeliveries.view.collaborator.viewModel.DeliveriesViewModel

class DeliveriesFragment : Fragment() {
    private var _binding: FragmentDeliveriesBinding? = null
    private val binding get() = _binding!!
    private val adpater: DeliveriesAdapter = DeliveriesAdapter()

    private lateinit var deliveriesViewModel: DeliveriesViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, b: Bundle?): View {
        deliveriesViewModel = ViewModelProvider(this).get(DeliveriesViewModel::class.java)
        _binding = FragmentDeliveriesBinding.inflate(inflater, container, false)

        binding.recycleListDeliveries.layoutManager = LinearLayoutManager(context)
        binding.recycleListDeliveries.adapter = adpater

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
        deliveriesViewModel.getAllDeliveries()
        observe()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observe() {
        deliveriesViewModel.deliveries.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                binding.recycleListDeliveries.visibility = View.VISIBLE
                binding.layoutDeliveriesEmpty.visibility = View.GONE
                adpater.updateDeliveries(it)
            }
        }
    }
}