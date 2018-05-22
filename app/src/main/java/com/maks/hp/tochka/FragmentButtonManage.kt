package com.maks.hp.tochka

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.livinglifetechway.k4kotlin.onClick
import kotlinx.android.synthetic.main.fragment_buttons_manager_login.*


class FragmentButtonManage : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_buttons_manager_login, parent, false)

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sign_in_button.onClick {
            fragmentManager!!.beginTransaction()
                    .replace(R.id.buttons_manager_fragment_container,GoogleLogin()) // give your fragment container id in first parameter
                    .addToBackStack(null)  // if written, this transaction will be added to backstack
                    .commit()
        }

        login_button.onClick {
            login_button.setReadPermissions("email")
            fragmentManager!!.beginTransaction()
                    .replace(R.id.buttons_manager_fragment_container,FBLogin()) // give your fragment container id in first parameter
                    .addToBackStack(null)  // if written, this transaction will be added to backstack
                    .commit()
        }
    }


}
