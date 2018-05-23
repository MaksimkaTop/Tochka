package com.maks.hp.tochka.gitserch

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.maks.hp.tochka.R
import kotlinx.android.synthetic.main.recycler.*

class SearchFragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.recycler, parent, false)


     fun initRecycler(data: GitInfo) {
        my_recycler_view.layoutManager = LinearLayoutManager(context)
        my_recycler_view.adapter = RecyclerAdapter(data)
    }

}