package com.maks.hp.tochka

import android.content.Context
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
import android.provider.SyncStateContract.Helpers.update
import android.content.pm.PackageManager
import android.content.pm.PackageInfo
import android.util.Base64
import android.util.Log
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import android.provider.SyncStateContract.Helpers.update
import android.support.v4.app.FragmentActivity
import android.widget.Toast
import com.facebook.FacebookException
import com.facebook.FacebookSdk.getApplicationId
import com.facebook.login.LoginResult
import com.facebook.FacebookCallback
import com.facebook.login.LoginManager
import com.facebook.CallbackManager
import com.facebook.FacebookSdk.getApplicationContext




class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val drawer: DrawerLayout = findViewById(R.id.drawer_layout)
        val toggle: ActionBarDrawerToggle = ActionBarDrawerToggle(
                this, drawer, null, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()
        val navigationView: NavigationView = findViewById(R.id.nav_view)

        val parent1: ViewGroup = findViewById(R.id.content)
        parent1.removeAllViews()
        var newContent1: View = layoutInflater.inflate(R.layout.content_main
                , parent1, false)
        parent1.addView(newContent1)



        navigationView.setNavigationItemSelectedListener({
            val id = it.itemId
            var optionId = R.layout.content_main

            if (id == R.id.login) {

                val manager = supportFragmentManager
                val transaction = manager.beginTransaction()
                transaction.replace(R.id.content, FragmentButtonManage())
                transaction.addToBackStack(null)
                transaction.commit()


                //  optionId= R.layout.test_layout_buttons
            } else if (id == R.id.other_view) {
                optionId = R.layout.content_main
            }
            parent1.removeAllViews()
            newContent1 = layoutInflater.inflate(optionId, parent1, false)
            parent1.addView(newContent1)
//закрываем NavigationView
//параметр определяет анимацию закрытия
            drawer.closeDrawer(GravityCompat.START)

            true
        })

        //updateUserData()
        FacebookSdk.sdkInitialize(this)
        AppEventsLogger.activateApp(this)
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
}
