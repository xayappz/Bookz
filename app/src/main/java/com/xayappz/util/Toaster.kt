package com.xayappz.util

import android.content.Context
import android.widget.Toast




class Toaster {
    companion object {
        fun showToast(msg: String?, ctx: Context?) {
            Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show()
        }
    }
}