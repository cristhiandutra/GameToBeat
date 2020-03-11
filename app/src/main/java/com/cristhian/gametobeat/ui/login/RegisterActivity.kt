package com.cristhian.gametobeat.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cristhian.gametobeat.R
import com.cristhian.gametobeat.ui.list.GameListActivity
import com.cristhian.gametobeat.util.LoginUtil
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    lateinit var mFirebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        configureFirebaseAuth()

        bt_register.setOnClickListener { register() }
    }

    private fun configureFirebaseAuth() {
        mFirebaseAuth = FirebaseAuth.getInstance()
    }

    fun register() {

        // Reset errors
        et_email.error = null
        et_password.error = null
        et_password_repeat.error = null

        val email: String = et_email.text.toString()
        val password: String = et_password.text.toString()
        val passwordRepeat: String = et_password_repeat.text.toString()
        var cancel = false
        var focusView: View? = null
        if (!LoginUtil.isPasswordValid(password)) {
            et_password.error = getString(R.string.password_invalid)
            cancel = true
            focusView = et_password
        }
        if (!LoginUtil.isPasswordValid(passwordRepeat)) {
            et_password_repeat.error = getString(R.string.password_invalid)
            cancel = true
            focusView = et_password_repeat
        }
        if (!LoginUtil.isEmailValid(email)) {
            et_email.setError(getString(R.string.email_invalid))
            cancel = true
            focusView = et_email
        }
        if (cancel) {
            focusView!!.requestFocus()
        } else if (!LoginUtil.isPasswordMatch(password, passwordRepeat)) {
            Toast.makeText(this@RegisterActivity, "Passwords must be the same", Toast.LENGTH_LONG)
                    .show()
        } else {
            mFirebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (!task.isSuccessful) {
                            Toast.makeText(
                                    this@RegisterActivity,
                                    getString(R.string.register_error),
                                    Toast.LENGTH_LONG
                            ).show()
                        } else {
                            Toast.makeText(
                                    this@RegisterActivity,
                                    getString(R.string.register_success),
                                    Toast.LENGTH_LONG
                            ).show()
                            val intent =
                                    Intent(this@RegisterActivity, GameListActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }
        }
    }
}