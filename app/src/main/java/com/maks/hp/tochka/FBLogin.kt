package com.maks.hp.tochka

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import java.util.*


class FBLogin : Fragment() {
    private var callbackManager: CallbackManager? = null
    var icon = ""
    var name = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.wtf("qwe", "onCreate")
        super.onCreate(savedInstanceState)

        callbackManager = CallbackManager.Factory.create()
        Log.wtf("qwe", "onCreate2")
        ;
        LoginManager.getInstance().registerCallback(callbackManager,
                object : FacebookCallback<LoginResult> {
                    override fun onSuccess(loginResult: LoginResult) {
                        Log.wtf("qwe", "onCreate3")
                        Log.wtf("qwe",
                                "User ID: "
                                        + loginResult.accessToken.userId
                                        + "\n" +
                                        "Auth Token: "
                                        + loginResult.accessToken.token
                        )
                        // App code
                        Log.wtf("qwe", loginResult.toString())
                        LoginManager.getInstance().logInWithReadPermissions(activity, Arrays.asList("public_profile"))
                        icon = Profile.getCurrentProfile().getProfilePictureUri(60, 60).toString()
                        name = Profile.getCurrentProfile().name
                        Log.wtf("qwe", name)

                        GraphRequest(

                                AccessToken.getCurrentAccessToken(),
                                "/{maks.topychkanov.1}/picture",
                                null,
                                HttpMethod.GET,

                                GraphRequest.Callback { /* handle the result */ }
                        ).executeAsync()

                        updateUI(true)
                    }

                    override fun onCancel() {
                        // App code
                        Log.wtf("qwe", "ffff")

                    }

                    override fun onError(exception: FacebookException) {
                        // App code
                        Log.wtf("qwe", "errror")

                    }
                })
    }

    private fun updateUI(signedIn: Boolean) {


        if (signedIn) {
            if (activity is MainActivity)
                (activity as MainActivity).updateUserData(icon, name)
        } else {

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager!!.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
        Log.wtf("qwe", AccessToken.getCurrentAccessToken().toString())

    }

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.google_login_layout, parent, false)
        return v
    }

}