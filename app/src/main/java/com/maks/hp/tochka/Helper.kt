package com.maks.hp.tochka

import android.support.design.widget.Snackbar
import android.view.View

class Helper {

    fun snack(view: View, text : String){
        Snackbar.make(view, text, Snackbar.LENGTH_LONG)
                .setAction("Dismiss", {})
                .show()
    }
}