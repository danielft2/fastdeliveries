package com.example.fastdeliveries.view.collaborator.view.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.fastdeliveries.databinding.RowLastUpdateBinding
import com.example.fastdeliveries.view.collaborator.models.LastUpdateDelivery

class LastsUpdateViewHolder(private val binding: RowLastUpdateBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(lastUpdateDelivery: LastUpdateDelivery) {
        binding.textLastUpdate.text = lastUpdateDelivery.type.toString()
        binding.textData.text = lastUpdateDelivery.data.toString()
    }
}
