package com.weatherapp.utils.display

import android.content.Context
import android.graphics.PorterDuff
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.weatherapp.R


object Toaster {
    fun show(context: Context, text: CharSequence) {
        val toast = android.widget.Toast.makeText(context, text, android.widget.Toast.LENGTH_SHORT)
        toast.view.background.setColorFilter(
            ContextCompat.getColor(context, R.color.colorAccent), PorterDuff.Mode.SRC_IN
        )
        val textView = toast.view.findViewById(android.R.id.message) as TextView
        textView.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary))
        toast.show()
    }
}