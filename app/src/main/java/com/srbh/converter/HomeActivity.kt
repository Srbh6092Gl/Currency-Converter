package com.srbh.converter

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.SpinnerAdapter
import androidx.drawerlayout.widget.DrawerLayout
import com.google.gson.GsonBuilder
import okhttp3.*
import pl.utkala.searchablespinner.SearchableSpinner
import java.io.IOException

class HomeActivity : NavigationPane() {

    private lateinit var currSpinnerAdapter: SpinnerAdapter

    private lateinit var fromCurrSpinner: SearchableSpinner
    private lateinit var toCurrSpinner: SearchableSpinner

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val mDrawerLayout:DrawerLayout = findViewById(R.id.drawer_layout)
        onCreateDrawer(mDrawerLayout)

        val amountField: EditText = findViewById(R.id.amount)

        val convertButton: Button = findViewById(R.id.convert)
        convertButton.setOnClickListener{

            val resultIntent = Intent(this, ResultActivity::class.java)
            if(amountField.text != null ){
                val amount = amountField.text.toString()
                val finalValue = conversion(fromCurrSpinner.selectedItem.toString(),toCurrSpinner.selectedItem.toString(),amount)
                resultIntent.putExtra("value", finalValue)
                startActivity(resultIntent)
            }

        }

        currSpinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, currency!!.quotes!!.keySet().toList())
        fromCurrSpinner = findViewById(R.id.fromCurr)
        fromCurrSpinner.adapter = currSpinnerAdapter
        fromCurrSpinner.setSelection(0,true)
        fromCurrSpinner.gravity = android.view.Gravity.CENTER

        toCurrSpinner = findViewById(R.id.toCurr)
        toCurrSpinner.adapter = currSpinnerAdapter
        toCurrSpinner.setSelection(0,true)
        toCurrSpinner.gravity = android.view.Gravity.CENTER


    }

    private fun conversion(fromCurr: String, toCurr: String, amount: String): Float {

        val fromCurrRate = currency!!.quotes[fromCurr].toString()
        val toCurrRate = currency!!.quotes[toCurr].toString()
        return (amount.toFloat() * toCurrRate.toFloat() / fromCurrRate.toFloat())

    }

}