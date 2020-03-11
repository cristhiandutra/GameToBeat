package com.cristhian.gametobeat.util

import android.text.TextUtils
import android.util.Patterns

object LoginUtil {

    private const val MIN_CHARACTERS_PASSWORD = 6

    fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isPasswordValid(password: String): Boolean { // Check if password is valid and length is bigger than 3 characters
        return (!TextUtils.isEmpty(password) && password.length >= MIN_CHARACTERS_PASSWORD)
    }

    fun isPasswordMatch(password: String, passwordRepeat: String): Boolean {
        return password == passwordRepeat
    }
}