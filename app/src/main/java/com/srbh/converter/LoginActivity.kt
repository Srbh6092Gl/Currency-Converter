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
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var mEmail: EditText
    private lateinit var mPassword: EditText
    private lateinit var mSignUpText: TextView
    private lateinit var mLogin: Button

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        mEmail = findViewById(R.id.email)
        mPassword = findViewById(R.id.password)
        mSignUpText = findViewById(R.id.sign_up_text)
        mLogin = findViewById(R.id.login_button)

        mSignUpText.setOnClickListener{
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }

        mLogin.setOnClickListener{

            val email = mEmail.text.toString()
            val password = mPassword.text.toString()
            if(email == "")
                Toast.makeText(this, "Enter email", Toast.LENGTH_SHORT).show()
            else if(password == "")
                Toast.makeText(this, "Enter password",Toast.LENGTH_SHORT).show()
            else {
                auth = Firebase.auth
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) {
                        val intent = Intent(this, HomeActivity::class.java)
                        startActivity(intent)
                    }
            }
        }
    }
}