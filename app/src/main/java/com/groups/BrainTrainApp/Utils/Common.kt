package com.groups.BrainTrainApp.Utils

import android.content.Context
import java.util.Locale

fun getResourceId(context: Context, name: String): Int {
    var name = name.lowercase(Locale.getDefault())
    return context.resources.getIdentifier(name, "drawable", context.packageName)
}