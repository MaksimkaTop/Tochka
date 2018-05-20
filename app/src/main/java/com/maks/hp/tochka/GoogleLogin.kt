package com.maks.hp.tochka

import android.app.ProgressDialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.provider.MediaStore
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
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.OptionalPendingResult
import com.google.android.gms.common.api.ResultCallback
import com.google.android.gms.common.api.Status
import kotlinx.android.synthetic.main.test_layout_buttons.*

import java.io.InputStream
import java.net.URL


class GoogleLogin : Fragment(), GoogleApiClient.OnConnectionFailedListener {
    private val RC_SIGN_IN = 0
    private var mGoogleApiClient: GoogleApiClient? = null
    private var signInButton: SignInButton? = null
    private var signOutButton: Button? = null
    private val disconnectButton: Button? = null
    private val signOutView: LinearLayout? = null
    private var mStatusTextView: TextView? = null
    private var mProgressDialog: ProgressDialog? = null
    private var imgProfilePic: ImageView? = null


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()

        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        mGoogleApiClient = GoogleApiClient.Builder(activity!!)
                .enableAutoManage(activity!! /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build()


    }


    override fun onStart() {
        super.onStart()

        val opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient)
        if (opr.isDone) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            Log.d(TAG, "Got cached sign-in")
            val result = opr.get()
            handleSignInResult(result)
        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
            showProgressDialog()
            opr.setResultCallback { googleSignInResult ->
                hideProgressDialog()
                handleSignInResult(googleSignInResult)
            }
        }
    }


    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.test_layout_buttons, parent, false)



        sign_in_button!!.setOnClickListener {
            val signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }


        sign_in_button!!.setOnClickListener { Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback { updateUI(false) } }

        return v
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            handleSignInResult(result)
        }
    }


    private fun handleSignInResult(result: GoogleSignInResult) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess)
        if (result.isSuccess) {
            // Signed in successfully, show authenticated UI.
            val acct = result.signInAccount
            //Similarly you can get the email and photourl using acct.getEmail() and  acct.getPhotoUrl()

            if (acct?.photoUrl != null)

            updateUI(true)
        } else {
            // Signed out, show unauthenticated UI.
            updateUI(false)
        }
    }


    private fun updateUI(signedIn: Boolean) {
        if (signedIn) {
            sign_in_button!!.visibility = View.GONE
        } else {
            sign_in_button!!.visibility = View.VISIBLE
        }
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:$connectionResult")
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


    /**
     * Background Async task to load user profile picture from url
     */







}
