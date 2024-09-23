package com.acidriian.wallet.common

import android.app.Activity
import android.widget.Toast
import androidx.fragment.app.Fragment

internal fun Fragment.makeToast(value: String){
    Toast.makeText(activity, value, Toast.LENGTH_SHORT).show()
}

internal fun Activity.makeToast(value: String){
    Toast.makeText(this, value, Toast.LENGTH_SHORT).show()
}