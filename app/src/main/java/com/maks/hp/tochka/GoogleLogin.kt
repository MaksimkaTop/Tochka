package com.maks.hp.tochka

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
    private var mGoogleSignInClient: GoogleSignInClient? = null
    private var account: GoogleSignInAccount? = null
    var image: String = ""
    var name: String? = "No name"

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
        mGoogleSignInClient = GoogleSignIn.getClient(activity!!, gso)
    }

    override fun onStart() {
        super.onStart()
        account = GoogleSignIn.getLastSignedInAccount(context)
    }

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.google_login_layout, parent, false)
        val signInIntent = mGoogleSignInClient?.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
        return v
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            account = completedTask.getResult(ApiException::class.java)
            image = account?.photoUrl.toString()
            name = account?.displayName
            updateUI(true)
            Snackbar.make(activity!!.findViewById(R.id.nav_view), "Success", Snackbar.LENGTH_LONG)
                    .setAction("Dismiss", {})
                    .show()
        } catch (e: ApiException) {
            Log.w("qwe", "signInResult:failed code=" + e.statusCode)
            Snackbar.make(activity!!.findViewById(R.id.nav_view), e.statusCode, Snackbar.LENGTH_LONG)
            updateUI(false)
        }
    }


    private fun updateUI(signedIn: Boolean) {
        if (signedIn) {
            (activity as MainActivity).updateUserData(image, name)
        } else {
        }
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        Log.wtf("qwe", "onConnectionFailed:$connectionResult")
    }
}
