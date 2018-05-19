package com.maks.hp.tochka

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toolbar
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.view.View
import android.view.ViewGroup


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
        val newContent1: View = layoutInflater.inflate(R.layout.test_layout_buttons
                , parent1, false)
        parent1.addView(newContent1)



        navigationView.setNavigationItemSelectedListener({
            val id = it.itemId
            var optionId = R.layout.content_main
            if (id == R.id.nav_camera) {
                optionId= R.layout.test_layout_buttons
// Обработка нажатия на кнопку камеры
            } else if (id == R.id.nav_gallery) {
                optionId = R.layout.nav_gallery
            } else if (id == R.id.nav_slideshow) {
            } else if (id == R.id.nav_manage) {
            } else if (id == R.id.nav_share) {
            } else if (id == R.id.nav_send) {
            }
            val parent: ViewGroup = findViewById(R.id.content)
            parent.removeAllViews()
            val newContent: View = layoutInflater.inflate(optionId, parent, false)
            parent.addView(newContent)
//закрываем NavigationView
//параметр определяет анимацию закрытия
            drawer.closeDrawer(GravityCompat.START)

            true
        })


    }
}
