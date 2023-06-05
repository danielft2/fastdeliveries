package com.example.fastdeliveries.view.collaborator.view.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.fastdeliveries.databinding.RowDeleveryBinding
import com.example.fastdeliveries.view.collaborator.models.Delivery

class DeleveriesViewHolder(private val binding: RowDeleveryBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(delivery: Delivery) {
        binding.nameProduct.text = delivery.name_product
        binding.textLocation.text = delivery.adress
        binding.textLastUpdate.text = delivery.lastUpdate
        binding.textCodDelevery.text = delivery.id.toString()
    }
}