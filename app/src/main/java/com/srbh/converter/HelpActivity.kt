package com.srbh.converter

import android.os.Bundle
import android.widget.TextView
import androidx.drawerlayout.widget.DrawerLayout

class HelpActivity : NavigationPane() {

    private lateinit var trackedTextView: TextView
    private lateinit var baseTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)

        val mDrawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        onCreateDrawer(mDrawerLayout)

        trackedTextView = findViewById(R.id.tracked_ans)
        baseTextView  = findViewById(R.id.base_ans)

        trackedTextView.text = currency?.quotes?.keySet()?.size.toString()
        baseTextView.text = currency?.source

    }
}