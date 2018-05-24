package com.maks.hp.tochka.gitsearch

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.maks.hp.tochka.R
import com.maks.hp.tochka.gitsearch.entity.Item
import com.squareup.picasso.Picasso
import com.makeramen.roundedimageview.RoundedTransformationBuilder



class RecyclerAdapter : RecyclerView.Adapter<ItemViewHolder>() {
    var data = ArrayList<Item>()

    fun updateData(newDataSet: ArrayList<Item>) {
        data.clear()
        data.addAll(newDataSet)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val transformation = RoundedTransformationBuilder()
                .cornerRadiusDp(30f)
                .oval(false)
                .build()

        holder.name.text = data[position].login
        Picasso.get()
                .load(data[position].avatarUrl)
                .fit()
                .transform(transformation)
                .placeholder(R.drawable.user_placeholder)
                .into(holder.icon)
    }
}