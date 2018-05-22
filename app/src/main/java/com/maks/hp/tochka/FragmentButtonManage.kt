package com.maks.hp.tochka

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.Profile
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.livinglifetechway.k4kotlin.onClick
import kotlinx.android.synthetic.main.fragment_buttons_manager_login.*


class FragmentButtonManage : Fragment() {
    private var callbackManager: CallbackManager? = null


    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_buttons_manager_login, parent, false)
        return v
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        callbackManager = CallbackManager.Factory.create()


//        callbackManager = CallbackManager.Factory.create()
//
//        LoginManager.getInstance().registerCallback(callbackManager,
//                object : FacebookCallback<LoginResult> {
//                    override fun onSuccess(loginResult: LoginResult) {
//                        // App code
//                        Log.wtf("qwe", "scs")
//
//                    }
//
//                    override fun onCancel() {
//                        // App code
//                        Log.wtf("qwe", "cncl")
//
//                    }
//
//                    override fun onError(exception: FacebookException) {
//                        // App code
//                        Log.wtf("qwe", "err")
//
//                    }
//                })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        callbackManager = CallbackManager.Factory.create()
        sign_in_button.onClick {
            fragmentManager!!.beginTransaction()
                    .replace(R.id.buttons_manager_fragment_container, GoogleLogin()) // give your fragment container id in first parameter
                    .addToBackStack(null)  // if written, this transaction will be added to backstack
                    .commit()
        }

        val loginButton: LoginButton = login_button
        loginButton.setReadPermissions("email")
        // If using in a fragment
        loginButton.fragment = this

        // Callback registration
        loginButton.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                Log.wtf("qwe", "scs")
                val icon = Profile.getCurrentProfile().getProfilePictureUri(60, 60).toString()
                val name = Profile.getCurrentProfile().name
                Log.wtf("qwe", name)
                (activity as MainActivity).updateUserData(icon, name)
            }

            override fun onCancel() {
                Log.wtf("qwe", "cncl")
            }

            override fun onError(exception: FacebookException) {
                // App code
                Log.wtf("qwe", "err")
            }
        })


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager!!.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }
}
