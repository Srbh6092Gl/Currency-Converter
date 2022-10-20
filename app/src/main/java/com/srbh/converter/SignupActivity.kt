package com.srbh.converter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.Objects

class SignupActivity : AppCompatActivity() {

    private lateinit var mFirstName: EditText
    private lateinit var mLastName: EditText
    private lateinit var mEmail: EditText
    private lateinit var mDOB: EditText
    private lateinit var mPassword: EditText
    private lateinit var mConfirmPassword: EditText
    private lateinit var mSignup: Button
    private lateinit var mLogInText: TextView

    private lateinit var auth: FirebaseAuth
    private lateinit var user: FirebaseUser
    private lateinit var db: FirebaseDatabase
    private lateinit var map: HashMap<String,Any>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        mFirstName = findViewById(R.id.first_name)
        mLastName = findViewById(R.id.last_name)
        mEmail = findViewById(R.id.email)
        mDOB = findViewById(R.id.dob)
        mPassword = findViewById(R.id.password)
        mConfirmPassword = findViewById(R.id.confirm_password)
        mSignup = findViewById(R.id.signup_button)
        mLogInText = findViewById(R.id.login_text)

        mLogInText.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        mSignup.setOnClickListener{

            val firstName = mFirstName.text.toString()
            val lastName = mLastName.text.toString()
            val email = mEmail.text.toString()
            val dob = mDOB.text.toString()
            val password = mPassword.text.toString()
            val confirmPassword = mConfirmPassword.text.toString()

            if(firstName == "")
                Toast.makeText(this, "Enter first name",Toast.LENGTH_SHORT).show()
            else if(lastName == "")
                Toast.makeText(this, "Enter last name",Toast.LENGTH_SHORT).show()
            else if(email == "")
                Toast.makeText(this, "Enter email",Toast.LENGTH_SHORT).show()
            else if(dob == "")
                Toast.makeText(this, "Enter Date of Birth",Toast.LENGTH_SHORT).show()
            else if(password == "")
                Toast.makeText(this, "Enter password",Toast.LENGTH_SHORT).show()
            else if(confirmPassword == "")
                Toast.makeText(this, "Re enter password",Toast.LENGTH_SHORT).show()
            else if(password != confirmPassword)
                Toast.makeText(this, "Both password shout be same",Toast.LENGTH_SHORT).show()
            else {

                //Creating auth
                auth = Firebase.auth

                //Creating user
                auth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(this){
                        user = auth.currentUser!!
                        map = hashMapOf(
                            "firstName" to firstName,
                            "lastName" to lastName,
                            "dob" to dob,
                            "email" to email
                        )
                        addUserToDatabase(user)
                    }
            }
        }
    }

    private fun addUserToDatabase(user: FirebaseUser) {

        db = Firebase.database
        val userDB = db.getReference("user")
        userDB.child(user.uid).updateChildren(map)

        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)

    }
}