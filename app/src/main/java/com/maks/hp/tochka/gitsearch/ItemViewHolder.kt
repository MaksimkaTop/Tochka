package com.maks.hp.tochka.gitsearch

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.maks.hp.tochka.R

class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)  {
    val name : TextView = itemView.findViewById(R.id.tv_git_name)
    val icon : ImageView = itemView.findViewById(R.id.iv_git_icon)
}