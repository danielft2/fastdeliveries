package com.example.fastdeliveries.view.collaborator.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fastdeliveries.databinding.RowLastUpdateBinding
import com.example.fastdeliveries.view.collaborator.models.LastUpdateDelivery
import com.example.fastdeliveries.view.collaborator.view.viewholder.LastsUpdateViewHolder

class LastsUpdatesAdpter: RecyclerView.Adapter<LastsUpdateViewHolder>() {
    private var lastsUpdatesList: List<LastUpdateDelivery> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LastsUpdateViewHolder {
        val item = RowLastUpdateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LastsUpdateViewHolder(item)
    }

    override fun getItemCount(): Int {
        return lastsUpdatesList.count()
    }

    override fun onBindViewHolder(holder: LastsUpdateViewHolder, position: Int) {
        holder.bind(lastsUpdatesList[position])
    }

    fun updateLastsUpdates(list: List<LastUpdateDelivery>) {
        lastsUpdatesList = list
        notifyDataSetChanged()
    }
}