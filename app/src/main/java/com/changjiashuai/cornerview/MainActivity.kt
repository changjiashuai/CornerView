package com.changjiashuai.cornerview

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun testKotlin(view: View) {
        startActivity(Intent(this, TestKotlinActivity::class.java))
    }

    fun testJava(view: View) {
        startActivity(Intent(this, TestJavaActivity::class.java))
    }
}
