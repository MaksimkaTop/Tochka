package com.maks.hp.tochka

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import com.maks.hp.tochka.gitsearch.SearchFragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.nav_header_main.view.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initDrawer()
        fbFirstLaunch()
    }

    fun updateUserData(photo: String, credentials: String?) {
        val icon: ImageView = nav_view.getHeaderView(0).iv_drawer_icon
        Picasso.get()
                .load(photo)
                .fit()
                .into(icon)
        val name: TextView = nav_view.getHeaderView(0).tv_drawer_name
        name.text = credentials
    }

    private fun fbFirstLaunch() {
        FacebookSdk.sdkInitialize(this)
        AppEventsLogger.activateApp(this)
    }

    private fun initDrawer() {
        val navigationView: NavigationView = nav_view
        val parentView: ViewGroup = drawer_content
        val drawer: DrawerLayout = drawer_layout
        val toggle = ActionBarDrawerToggle(
                this, drawer, null, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        navigationView.setNavigationItemSelectedListener({
            val id = it.itemId
            if (id == R.id.login) {
                val manager = supportFragmentManager
                val transaction = manager.beginTransaction()
               transaction.replace(R.id.drawer_content, FragmentButtonManage())
                transaction.addToBackStack(null)
                transaction.commit()


            } else if (id == R.id.other_view) {
                val manager = supportFragmentManager
                val transaction = manager.beginTransaction()
                transaction.replace(R.id.drawer_content, SearchFragment())
                transaction.addToBackStack(null)
                transaction.commit()

            }
            parentView.removeAllViews()
            drawer.closeDrawer(GravityCompat.START)
            true
        })
    }
}
