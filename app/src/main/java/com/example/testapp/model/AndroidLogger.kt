package com.example.testapp.model

import android.util.Log

class AndroidLogger : Logger {
    override fun e(
        tag: String,
        message: String,
    ) {
        Log.e(tag, message)
    }
}
