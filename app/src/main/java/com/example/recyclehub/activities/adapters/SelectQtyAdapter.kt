package com.example.recyclehub.activities.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclehub.R
import com.example.recyclehub.activities.categoryList
import com.google.android.material.card.MaterialCardView

class SelectQtyAdapter(val id : Int): RecyclerView.Adapter<SelectQtyAdapter.SelectQtyViewHolder>() {

    private val selectQtyList = categoryList[id].selectQtyList
    class SelectQtyViewHolder( itemView: View) : RecyclerView.ViewHolder(itemView) {
        val qty = itemView.findViewById<TextView>(R.id.item_select_qty)
        val coins = itemView.findViewById<TextView>(R.id.item_select_coins)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectQtyViewHolder {
        val itemLayout = LayoutInflater.from(parent.context).
                          inflate(R.layout.item_select_choice,parent,false)
        return SelectQtyViewHolder(itemLayout)
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: SelectQtyViewHolder, position: Int) {



        holder.qty.text = selectQtyList[position].qty
        holder.coins.text = selectQtyList[position].noOfCoins.toString()

        holder.itemView.setOnClickListener{
            if(!selectQtyList[position].isSelected) {

                selectQtyList.forEach {
                    it.isSelected = false
                }

                selectQtyList[position].isSelected = true

                notifyDataSetChanged()
            }
        }

        if(selectQtyList[position].isSelected){
            holder.itemView.findViewById<MaterialCardView>(R.id.cv_item_select).strokeColor =
                R.color.md_theme_light_outline
        }
        else{
            holder.itemView.findViewById<MaterialCardView>(R.id.cv_item_select).strokeColor =
                0
        }
    }

    override fun getItemCount(): Int {
        return selectQtyList.size
    }



}