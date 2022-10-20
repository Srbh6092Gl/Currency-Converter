package com.srbh.converter

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Handler
import android.util.Log
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

var userID: String ?= "a@a.com"
var name: String ?= "Aaaaaa"
open class NavigationPane : AppCompatActivity() {

    private lateinit var mToggle: ActionBarDrawerToggle

    private lateinit var auth: FirebaseAuth
    private lateinit var user: FirebaseUser
    private lateinit var mDelayHandler: Handler
    private val delay = 2000L

    fun onCreateDrawer(mDrawerLayout: DrawerLayout) {

        val navigationView:NavigationView = findViewById(R.id.nav_view)
        val headerView = navigationView.getHeaderView(0)
        val email: TextView = headerView.findViewById(R.id.email_ID)
        val nameID: TextView = headerView.findViewById(R.id.name_ID)

        val runnable = Runnable{
            auth = Firebase.auth
            user = auth.currentUser!!
            val userID = user.uid
            val userDB = FirebaseDatabase.getInstance().getReference("user").child(userID)
            userDB.addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    email.text = snapshot.child("email").value.toString()
                    nameID.text =snapshot.child("firstName").value.toString()+" "+snapshot.child("lastName").value.toString()
                }

                override fun onCancelled(error: DatabaseError) {
                    println(error.message)
                    Log.d(TAG, "onCancelled: ${error.toString()}")
                }

            })
        }


        mDelayHandler = Handler()
        mDelayHandler!!.postDelayed(runnable, delay)

        mToggle = ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close)
        mDrawerLayout.addDrawerListener(mToggle)
        mToggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navigationView.setNavigationItemSelectedListener { menuItem ->
            menuItem.isChecked = true
            mDrawerLayout.closeDrawers()
            when(menuItem.itemId){
                R.id.home -> {
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                }
                R.id.market_status -> {
                    val intent = Intent(this, MarketStatusActivity::class.java)
                    startActivity(intent)
                }
                R.id.help -> {
                    val intent = Intent(this, HelpActivity::class.java)
                    startActivity(intent)
                }
                R.id.contact -> {
                    Toast.makeText(this, "Visit: www.loonycorn.com",Toast.LENGTH_SHORT).show()
                }
                R.id.logout -> {
                    Firebase.auth.signOut()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                }
            }
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(mToggle.onOptionsItemSelected(item))
            return true
        return super.onOptionsItemSelected(item)
    }
}
