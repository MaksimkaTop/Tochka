package com.maks.hp.tochka.gitserch

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.maks.hp.tochka.R

class SearchFragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.recycler, parent, false)

}