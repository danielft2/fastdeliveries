package com.example.fastdeliveries.view.collaborator.view.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fastdeliveries.databinding.FragmentDeliveriesBinding
import com.example.fastdeliveries.view.collaborator.view.adapter.DeliveriesAdapter
import com.example.fastdeliveries.view.collaborator.viewModel.DeliveriesViewModel


class DeliveriesFragment : Fragment() {
    private var _binding: FragmentDeliveriesBinding? = null
    private val binding get() = _binding!!
    private val adpater: DeliveriesAdapter = DeliveriesAdapter();

    private lateinit var deliveriesViewModel: DeliveriesViewModel;

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, b: Bundle?): View {
        deliveriesViewModel = ViewModelProvider(this).get(DeliveriesViewModel::class.java)
        _binding = FragmentDeliveriesBinding.inflate(inflater, container, false)

        binding.recycleListDeliveries.layoutManager = LinearLayoutManager(context)
        binding.recycleListDeliveries.adapter = adpater;

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
            adpater.updateDeliveries(it)
        }
    }
}