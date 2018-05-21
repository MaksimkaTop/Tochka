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
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.nav_header_main.view.*


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
