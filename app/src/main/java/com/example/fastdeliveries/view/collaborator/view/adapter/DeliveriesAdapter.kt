package com.example.fastdeliveries.view.collaborator.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fastdeliveries.databinding.RowDeleveryBinding
import com.example.fastdeliveries.view.collaborator.models.Delivery
import com.example.fastdeliveries.view.collaborator.view.listeners.IDeliveryListener
import com.example.fastdeliveries.view.collaborator.view.viewholder.DeliveriesViewHolder

class DeliveriesAdapter : RecyclerView.Adapter<DeliveriesViewHolder>() {
    private var deliveriesList: List<Delivery> = listOf()
    private lateinit var listener: IDeliveryListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeliveriesViewHolder {
        val item = RowDeleveryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DeliveriesViewHolder(item, listener)
    }

    override fun getItemCount(): Int {
        return deliveriesList.count()
    }

    override fun onBindViewHolder(holder: DeliveriesViewHolder, position: Int) {
        holder.bind(deliveriesList[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateDeliveries(list: List<Delivery>) {
        deliveriesList = list
        notifyDataSetChanged()
    }

    fun setDeliveryListener(deliveryListener: IDeliveryListener) {
        listener = deliveryListener
    }
}