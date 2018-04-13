package com.changjiashuai.cornerview

import android.view.View

/**
 * Email: changjiashuai@gmail.com
 *
 * Created by CJS on 2018/4/13 13:46.
 */
fun View.dp2px(dpValue: Float): Float {
    val scale = resources.displayMetrics.density
    return (dpValue * scale + 0.5f)
}