package com.example.speedwaymenago

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_register.*

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()

        btnLogin.setOnClickListener {
            if (editEmailLogin.text.trim().toString().isNotEmpty() && editPasswordLogin.text.trim().toString().isNotEmpty()) {
                signInUser(editEmailLogin.text.trim().toString(), editPasswordLogin.text.trim().toString())

            } else {
                Toast.makeText(this, "input required", Toast.LENGTH_LONG).show()
            }

        }

        tvRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

    }

    private fun signInUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this, DashboardActivity::class.java)
                    startActivity(intent)

                } else {
                    Toast.makeText(this, "Erorr" + task.exception, Toast.LENGTH_LONG).show()
                }
            }
    }
    //check if user is already logged in
//    override fun onStart() {
//        super.onStart()
//        val user = auth.currentUser
//
//        if (user != null) {
//            var intent = Intent(this, DashboardActivity::class.java)
//            startActivity(intent)
//        }
//    }
}