package com.maks.hp.tochka.gitsearch

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.maks.hp.tochka.R
import com.maks.hp.tochka.api.SearchServers
import com.maks.hp.tochka.api.SearchServersProvider
import com.maks.hp.tochka.extend.addAfterTextChangedListener
import com.maks.hp.tochka.gitsearch.entity.Entity
import com.maks.hp.tochka.gitsearch.entity.Item
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.recycler.*

const val tag: String = "users"

class SearchFragment : Fragment() {

    val cmp: CompositeDisposable = CompositeDisposable()
    val repo: SearchServers = SearchServersProvider.provideSearchServers()
    val list: ArrayList<Item> = arrayListOf()

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.recycler, parent, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchView.addAfterTextChangedListener {
            Log.wtf("qwe", searchView.text.toString())
        }
        butn()
    }

    fun butn() {
        cmp.add(
                repo.searchUsers("q", "desc", "followers")
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe({ result ->
                            result.items.forEach {
                                list.add(it)
                                Log.wtf("qwe", it.toString())
                                initRecycler(list)
                            }
                        }, {
                            //TODO eerrror
                            Log.wtf("qwe", it.localizedMessage)
                        })
        )
    }

    private fun initRecycler(data: ArrayList<Item>
    ) {
        my_recycler_view.layoutManager = LinearLayoutManager(context)
        my_recycler_view.adapter = RecyclerAdapter(data)
    }

}