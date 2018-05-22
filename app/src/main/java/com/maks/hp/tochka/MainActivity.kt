package com.maks.hp.tochka

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.nav_header_main.view.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializationDrawer()
        fbFirstLaunch()
    }

    fun updateUserData(photo: String, credentials: String?) {
        val icon: ImageView = nav_view.getHeaderView(0).imageView
        Picasso.get()
                .load(photo)
                .fit()
                .into(icon)
        val name: TextView = nav_view.getHeaderView(0).textView2
        name.text = credentials
    }

    private fun fbFirstLaunch() {
        FacebookSdk.sdkInitialize(this)
        AppEventsLogger.activateApp(this)
    }

    private fun initializationDrawer() {
        val navigationView: NavigationView = nav_view
        val parentView: ViewGroup = drawer_content
        val drawer: DrawerLayout = drawer_layout
        val toggle = ActionBarDrawerToggle(
                this, drawer, null, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()
        parentView.removeAllViews()
        var newContent: View = layoutInflater.inflate(R.layout.content_main, parentView, false)
        parentView.addView(newContent)
        navigationView.setNavigationItemSelectedListener({
            val id = it.itemId
            var optionId = R.layout.content_main
            if (id == R.id.login) {
                val manager = supportFragmentManager
                val transaction = manager.beginTransaction()
                transaction.replace(R.id.drawer_content, FragmentButtonManage())
                transaction.addToBackStack(null)
                transaction.commit()
            } else if (id == R.id.other_view) {
                optionId = R.layout.content_main
            }
            parentView.removeAllViews()
            newContent = layoutInflater.inflate(optionId, parentView, false)
            parentView.addView(newContent)
            drawer.closeDrawer(GravityCompat.START)
            true
        })
    }
}
