package com.example.fastdeliveries.view.collaborator.view.viewholder

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.fastdeliveries.R
import com.example.fastdeliveries.databinding.RowDeleveryBinding
import com.example.fastdeliveries.view.collaborator.enums.DeliveryStatus
import com.example.fastdeliveries.view.collaborator.models.Delivery
import com.example.fastdeliveries.view.collaborator.view.listeners.IDeliveryListener

class DeleveriesViewHolder(
    private val binding: RowDeleveryBinding,
    private val listener: IDeliveryListener) : RecyclerView.ViewHolder(binding.root
) {
    fun bind(delivery: Delivery) {
        binding.codeProduct.text = delivery.code
        binding.textLocation.text = delivery.order.city
        binding.textLastUpdate.text = delivery.getLastUpdate()
        binding.textCodDelevery.text = delivery.id.toString()
        binding.imageIcon.setColorFilter(ContextCompat.getColor(itemView.context, getColorBind(delivery.status)))

        binding.cardDelivery.setOnClickListener() {
            listener.onClick(delivery.id)
        }
    }

    fun getColorBind(deliveryStatus: DeliveryStatus): Int {
        if (deliveryStatus == DeliveryStatus.PENDENTE) return R.color.primary
        return R.color.green_500;
    }
}