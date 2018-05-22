package com.maks.hp.tochka

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
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


    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_buttons_manager_login, parent, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        sign_in_button.onClick {
            fragmentManager!!.beginTransaction()
                    .replace(R.id.buttons_manager_fragment_container, GoogleLogin())
                    .addToBackStack(null)
                    .commit()
        }
        login_button.onClick {
            initFbLogin()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager!!.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun initFbLogin() {
        callbackManager = CallbackManager.Factory.create()
        val loginButton: LoginButton = login_button
        loginButton.setReadPermissions("email")
        loginButton.fragment = this
        loginButton.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                Helper().snack(sign_in_button, getString(R.string.success))
                val icon = Profile.getCurrentProfile().getProfilePictureUri(60, 60).toString()
                val name = Profile.getCurrentProfile().name
                (activity as MainActivity).updateUserData(icon, name)
            }

            override fun onCancel() {
               Helper().snack(sign_in_button, getString(R.string.cancel))
            }

            override fun onError(exception: FacebookException) {
                Helper().snack(sign_in_button, getString(R.string.error))
            }
        })
    }
}
