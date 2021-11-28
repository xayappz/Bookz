package com.xayappz.util

import android.app.Activity
import android.content.Intent
import android.net.Uri


class Browser {
    companion object {
        fun navigateToChrome(url: String, activity: Activity) {
            if (url == "") {
                Toaster.showToast("No viewable version available.", activity)
                return
            }
            val defaultBrowser =
                Intent.makeMainSelectorActivity(Intent.ACTION_MAIN, Intent.CATEGORY_APP_BROWSER)
            defaultBrowser.data = Uri.parse(url)
            activity.startActivity(defaultBrowser)

        }
    }
}