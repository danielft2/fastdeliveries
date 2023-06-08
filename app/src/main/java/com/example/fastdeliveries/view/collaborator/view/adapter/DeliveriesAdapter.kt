package com.example.fastdeliveries.view.collaborator.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fastdeliveries.databinding.RowDeleveryBinding
import com.example.fastdeliveries.view.collaborator.models.Delivery
import com.example.fastdeliveries.view.collaborator.view.listeners.IDeliveryListener
import com.example.fastdeliveries.view.collaborator.view.viewholder.DeleveriesViewHolder

class DeliveriesAdapter : RecyclerView.Adapter<DeleveriesViewHolder>() {
    private var deliveriesList: List<Delivery> = listOf();
    private lateinit var listener: IDeliveryListener;

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeleveriesViewHolder {
        val item = RowDeleveryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DeleveriesViewHolder(item, listener)
    }

    override fun getItemCount(): Int {
        return deliveriesList.count()
    }

    override fun onBindViewHolder(holder: DeleveriesViewHolder, position: Int) {
        holder.bind(deliveriesList[position])
    }

    fun updateDeliveries(list: List<Delivery>) {
        deliveriesList = list;
        notifyDataSetChanged();
    }

    fun setDeliveryListerner(deliveryListerner: IDeliveryListener) {
        listener = deliveryListerner
    }
}