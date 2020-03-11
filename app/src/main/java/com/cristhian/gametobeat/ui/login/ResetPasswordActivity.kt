package com.cristhian.gametobeat.ui.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cristhian.gametobeat.R
import com.cristhian.gametobeat.util.LoginUtil
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_reset_password.*

class ResetPasswordActivity : AppCompatActivity() {

    lateinit var mFirebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)

        configureFirebaseAuth()

        bt_reset.setOnClickListener { resetPassword() }
    }

    private fun configureFirebaseAuth() {
        mFirebaseAuth = FirebaseAuth.getInstance()
    }

    fun resetPassword() {
        // Reset errors
        et_email.error = null

        val email: String = et_email.text.toString()
        var cancel = false
        var focusView: View? = null
        if (!LoginUtil.isEmailValid(email)) {
            et_email.error = getString(R.string.email_invalid)
            cancel = true
            focusView = et_email
        }
        if (cancel) {
            focusView!!.requestFocus()
        } else {
            mFirebaseAuth.sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        if (!task.isSuccessful) {
                            Toast.makeText(
                                    this@ResetPasswordActivity,
                                    getString(R.string.email_reset_invalid),
                                    Toast.LENGTH_LONG
                            ).show()
                        } else {
                            Toast.makeText(
                                    this@ResetPasswordActivity,
                                    getString(R.string.reset_success),
                                    Toast.LENGTH_LONG
                            ).show()
                            finish()
                        }
                    }
        }
    }
}