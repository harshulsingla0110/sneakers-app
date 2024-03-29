package com.harshul.shoesapp.utils

import android.app.Activity
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.TextView
import com.harshul.shoesapp.R

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun Activity.orangeTheme() {
    window.apply {
        statusBarColor = getColor(R.color.main_orange)
        navigationBarColor = getColor(R.color.main_orange)
    }
}
fun Activity.lightTheme() {
    window.apply {
        statusBarColor = getColor(R.color.white)
        navigationBarColor = getColor(R.color.white)
    }
}

fun TextView.setSpannableTxt(
    text: String,
    textColor: Int,
    start: Int,
    end: Int
) {
    val color = ForegroundColorSpan(context.getColor(textColor))
    val spannableString = SpannableString(text)
    spannableString.setSpan(color, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    setText(spannableString)
}

fun Int.formatToIndianCurrency(): String {
    val formatter = java.text.DecimalFormat("##,##,###")
    return "₹${formatter.format(this)}"
}