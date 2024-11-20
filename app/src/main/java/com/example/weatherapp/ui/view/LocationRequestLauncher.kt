package com.example.weatherapp.ui.view

import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import androidx.fragment.app.FragmentActivity
import dagger.hilt.android.scopes.ActivityScoped

@ActivityScoped
class LocationRequestLauncher(activity:FragmentActivity): ActivityResultContract<String, Int>() {
    override fun createIntent(context: Context, input: String): Intent {
        TODO("Not yet implemented")
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Int {
        TODO("Not yet implemented")
    }

}