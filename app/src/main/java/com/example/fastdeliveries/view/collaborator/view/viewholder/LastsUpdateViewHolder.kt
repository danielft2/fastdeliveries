package com.example.fastdeliveries.view.collaborator.view.viewholder

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.fastdeliveries.R
import com.example.fastdeliveries.databinding.RowLastUpdateBinding
import com.example.fastdeliveries.view.collaborator.enums.DeliveryStatus
import com.example.fastdeliveries.view.collaborator.enums.DeliveryUpdate
import com.example.fastdeliveries.view.collaborator.models.LastUpdateDelivery

class LastsUpdateViewHolder(private val binding: RowLastUpdateBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(lastUpdateDelivery: LastUpdateDelivery) {
        binding.textLastUpdate.text = lastUpdateDelivery.type.value
        binding.textData.text = lastUpdateDelivery.data
        binding.imageIcon.setColorFilter(ContextCompat.getColor(itemView.context, getColorBind(lastUpdateDelivery.type)))
    }

    fun getColorBind(deliveryUpdate: DeliveryUpdate): Int {
        if (deliveryUpdate == DeliveryUpdate.ENTREGUE_AO_DESTINATARIO) return R.color.green_500
        return R.color.primary;
    }
}
