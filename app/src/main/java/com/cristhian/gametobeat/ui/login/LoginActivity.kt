package com.cristhian.gametobeat.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cristhian.gametobeat.R
import com.cristhian.gametobeat.ui.list.GameListActivity
import com.cristhian.gametobeat.util.LoginUtil
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    lateinit var mFirebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        configureFirebaseAuth()

        if (::mFirebaseAuth.isInitialized && mFirebaseAuth.currentUser != null) {
            val intent = Intent(this@LoginActivity, GameListActivity::class.java)
            startActivity(intent)
            finish()
        }
        configureOnClicks()
    }

    private fun configureFirebaseAuth() {
        mFirebaseAuth = FirebaseAuth.getInstance()
    }

    private fun configureLogin() {

        // Reset errors
        et_email.error = null
        et_password.error = null

        if (!LoginUtil.isEmailValid(et_email.text.toString())) {
            et_email.error = getString(R.string.email_invalid)
            et_email.requestFocus()
            return
        }

        if (!LoginUtil.isPasswordValid(et_password.text.toString())) {
            et_password.error = getString(R.string.password_invalid)
            et_password.requestFocus()
            return
        }

        //logar
        mFirebaseAuth.signInWithEmailAndPassword(et_email.text.toString(), et_password.text.toString())
                .addOnCompleteListener {
                    if (!it.isSuccessful) {
                        Toast.makeText(
                                this@LoginActivity,
                                getString(R.string.login_error),
                                Toast.LENGTH_LONG
                        ).show()
                    } else {
                        val intent = Intent(this@LoginActivity, GameListActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }

    }

    private fun configureOnClicks() {

        bt_login.setOnClickListener {
            configureLogin()
        }

        tv_forgot_password.setOnClickListener {
            val intent = Intent(this, ResetPasswordActivity::class.java)
            startActivity(intent)
        }

        tv_register_now.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}