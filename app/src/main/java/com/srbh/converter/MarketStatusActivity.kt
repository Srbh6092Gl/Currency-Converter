package com.srbh.converter

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.drawerlayout.widget.DrawerLayout

class MarketStatusActivity : NavigationPane() {

    private lateinit var baseCurrencyTextView: TextView
    private lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_market_status)

        val mDrawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        onCreateDrawer(mDrawerLayout)

        listView = findViewById(R.id.list)
        baseCurrencyTextView = findViewById(R.id.base_currency)
        baseCurrencyTextView.text = currency?.source

        val array = ArrayList<String>()
        val arrayAdapter: ArrayAdapter<*>

        for( i in currency?.quotes!!.keySet())
            array.add("${i.substring(3,i.length)}: ${currency!!.quotes[i]}")
//            array.add("${i}: ${currency!!.quotes[i]}")

        arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, array)
        listView.adapter = arrayAdapter

    }
}