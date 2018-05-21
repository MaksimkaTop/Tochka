package com.maks.hp.tochka

import android.app.ProgressDialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.GoogleApiClient
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.nav_header_main.*


class GoogleLogin : Fragment(), GoogleApiClient.OnConnectionFailedListener {
    private val RC_SIGN_IN = 9001
    private var mGoogleApiClient: GoogleApiClient? = null
    private var signInButton: SignInButton? = null
    private var signOutButton: Button? = null
    private val disconnectButton: Button? = null
    private val signOutView: LinearLayout? = null
    private var mStatusTextView: TextView? = null
    private var mProgressDialog: ProgressDialog? = null
    private var imgProfilePic: ImageView? = null
    var image : String = ""


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestServerAuthCode(getString(R.string.server_client_id))
                .requestEmail()
                .build()

        mGoogleApiClient = GoogleApiClient.Builder(activity!!)
                .enableAutoManage(activity!!, this )
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build()




    }


    override fun onStart() {
        super.onStart()

        val opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient)
        if (opr.isDone) {
            Log.wtf("qwe", "Got cached sign-in")
            val result = opr.get()
            handleSignInResult(result)
        } else {

            showProgressDialog()
            opr.setResultCallback { googleSignInResult ->
                hideProgressDialog()
                handleSignInResult(googleSignInResult)
            }
        }
    }


    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.google_login_layout, parent, false)


        //   sign_in_button.setOnClickListener {
        val signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
        startActivityForResult(signInIntent, RC_SIGN_IN)
        // }


        //sign_in_button.setOnClickListener {
        //Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback {
       //     updateUI(false)
            //  }
        //}

        return v
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {

            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            handleSignInResult(result)
        }
    }


    private fun handleSignInResult(result: GoogleSignInResult) {
        Log.wtf("qwe", "handleSignInResult:" + result.isSuccess)
        if (result.isSuccess) {
            // Signed in successfully, show authenticated UI.
            val acct = result.signInAccount
            //Similarly you can get the email and photourl using acct.getEmail() and  acct.getPhotoUrl()
            image = acct?.photoUrl.toString()
            if (acct?.photoUrl != null)

                updateUI(true)
        } else {
            // Signed out, show unauthenticated UI.
            updateUI(false)
        }
    }


    private fun updateUI(signedIn: Boolean) {
        if (signedIn) {

            // sign_in_button!!.visibility = View.GONE
        } else {

            //sign_in_button!!.visibility = View.VISIBLE
        }
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        Log.wtf("qwe", "onConnectionFailed:$connectionResult")
    }

    private fun showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = ProgressDialog(activity)
            mProgressDialog!!.isIndeterminate = true
        }

        mProgressDialog!!.show()
    }

    private fun hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog!!.isShowing) {
            mProgressDialog!!.hide()
        }

    }
}
