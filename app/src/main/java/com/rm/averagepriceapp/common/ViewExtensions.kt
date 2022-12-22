package com.rm.averagepriceapp.common

import android.view.View

fun View.hide() {
    visibility = View.GONE
}

fun View.show() {
    visibility = View.VISIBLE
}

fun showViews(vararg views: View) {
    for (v in views) v.show()
}