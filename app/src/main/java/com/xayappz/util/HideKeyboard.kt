package com.xayappz.util

import android.view.inputmethod.InputMethodManager

import android.view.View

import android.app.Activity
import android.content.Context
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService





class HideKeyboard {
   companion object{
    fun hideKeyboard(activity: Activity,view: View) {
        val manager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        manager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}}