package com.srbh.converter

import android.os.Bundle
import android.widget.TextView
import androidx.drawerlayout.widget.DrawerLayout

class ResultActivity : NavigationPane() {

    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val mDrawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        onCreateDrawer(mDrawerLayout)

        textView = findViewById(R.id.result)
        textView.text = intent.getFloatExtra("value",0.0F).toString()

    }
}