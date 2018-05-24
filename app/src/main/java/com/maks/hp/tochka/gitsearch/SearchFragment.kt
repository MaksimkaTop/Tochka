package com.maks.hp.tochka.gitsearch

import android.content.res.Configuration
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.maks.hp.tochka.R
import com.maks.hp.tochka.api.SearchServers
import com.maks.hp.tochka.api.SearchServersProvider
import com.maks.hp.tochka.extend.addAfterTextChangedListener
import com.maks.hp.tochka.gitsearch.entity.Item
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.recycler_holder.*


class SearchFragment : Fragment() {

    private val compositDisp: CompositeDisposable = CompositeDisposable()
    private val repo: SearchServers = SearchServersProvider.provideSearchServers()

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.recycler_holder, parent, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        searchView.addAfterTextChangedListener {
            runSearch(it.toString())
        }
    }

    private fun runSearch(name: String) {
        showProgressBar(true)
        compositDisp.add(
                repo.searchUsers(name)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe({ result ->
                            val listItems: ArrayList<Item> = arrayListOf()
                            result.items.forEach {
                                listItems.add(it)
                                showProgressBar(false)
                                Log.wtf("qwe", it.toString())
                            }
                            updateRecycler(listItems)
                        }, {
                            //TODO errror
                            Log.wtf("qwe", it.localizedMessage)
                        })
        )
    }

    private fun updateRecycler(data: ArrayList<Item>) {
        (my_recycler_view.adapter as RecyclerAdapter).updateData(data)
    }
    fun initRecycler(){
        val orientationPos = this.resources.configuration.orientation
        if (orientationPos == Configuration.ORIENTATION_PORTRAIT) my_recycler_view.layoutManager = GridLayoutManager(context, 1)
        else my_recycler_view.layoutManager = GridLayoutManager(context, 2)
        my_recycler_view.adapter = RecyclerAdapter()
    }

     fun showProgressBar(flag: Boolean) {
        pb_git.visibility = if (flag) View.VISIBLE else View.INVISIBLE
    }
}