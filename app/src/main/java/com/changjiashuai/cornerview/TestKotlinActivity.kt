package com.changjiashuai.cornerview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.sample_corner_view.*

class TestKotlinActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sample_corner_view)

        cornerView.triangleColor = resources.getColor(R.color.colorPrimaryDark)
        cornerView.triangleSideA = cornerView.dp2px(20f)
        cornerView.triangleSideB = cornerView.dp2px(30f)

        cornerView.borderColor = resources.getColor(R.color.colorPrimaryDark)
        cornerView.borderWidth = cornerView.dp2px(8f)
    }
}
