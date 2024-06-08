package com.example.recyclehub.activities.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclehub.R
import com.example.recyclehub.activities.qtyList

class PickUpCartAdapter(): RecyclerView.Adapter<PickUpCartAdapter.PickUpCartAdapterViewHolder>() {
    class PickUpCartAdapterViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val item = itemView.findViewById<TextView>(R.id.tv_item_title)
        val qty = itemView.findViewById<TextView>(R.id.tv_item_qty)
        val remove = itemView.findViewById<TextView>(R.id.tv_item_remove)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PickUpCartAdapterViewHolder {
        return PickUpCartAdapterViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_pick_up_cart,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return qtyList.size
    }

    override fun onBindViewHolder(holder: PickUpCartAdapterViewHolder, position: Int) {
        holder.item.text = qtyList[position].item
        holder.qty.text = qtyList[position].qty
        holder.remove.setOnClickListener {
            qtyList.remove(qtyList[position])
            notifyDataSetChanged()
        }

    }
}