package com.maks.hp.tochka

import android.app.ProgressDialog
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
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.tasks.Task


class GoogleLogin : Fragment(), GoogleApiClient.OnConnectionFailedListener {
    private val RC_SIGN_IN = 9001
    private var mGoogleApiClient: GoogleApiClient? = null
    private var mGoogleSignInClient: GoogleSignInClient? = null
    private var account: GoogleSignInAccount? = null
    private var signOutButton: Button? = null
    private val disconnectButton: Button? = null
    private val signOutView: LinearLayout? = null
    private var mStatusTextView: TextView? = null
    private var mProgressDialog: ProgressDialog? = null
    private var imgProfilePic: ImageView? = null
    var image: String = ""
    var name: String? = ""


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()

//        mGoogleApiClient = GoogleApiClient.Builder(activity!!)
//                .enableAutoManage(activity!!, this)
//                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
//                .build()

        mGoogleSignInClient = GoogleSignIn.getClient(activity!!, gso)

    }


    override fun onStart() {
        super.onStart()

        account = GoogleSignIn.getLastSignedInAccount(context)
    }


    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.google_login_layout, parent, false)


        //   sign_in_button.setOnClickListener {
        val signInIntent = mGoogleSignInClient?.signInIntent
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

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.



            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)

        }
    }


    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            account = completedTask.getResult(ApiException::class.java)
            // Signed in successfully, show authenticated UI.
            Log.w("qwe", "signInResult:= success")
            image = account?.photoUrl.toString()
            name = account?.displayName

            Log.wtf("asd", account?.displayName)
            Log.wtf("asd", account?.photoUrl.toString())
            updateUI(true)
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("qwe", "signInResult:failed code=" + e.statusCode)
            updateUI(false)
        }
    }


    private fun updateUI(signedIn: Boolean) {


        if (signedIn) {
            if (activity is MainActivity)
                (activity as MainActivity).updateUserData(image, name)
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
