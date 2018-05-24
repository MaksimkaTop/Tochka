package com.maks.hp.tochka.gitsearch

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.maks.hp.tochka.R
import com.maks.hp.tochka.gitsearch.entity.Item
import com.squareup.picasso.Picasso

class RecyclerAdapter(val data: ArrayList<Item>) : RecyclerView.Adapter<ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.name.text = data[position].login
        Picasso.get()
                .load(data[position].avatarUrl)
                .fit()
                .placeholder(R.drawable.batman)
                .into(holder.icon)
    }
}