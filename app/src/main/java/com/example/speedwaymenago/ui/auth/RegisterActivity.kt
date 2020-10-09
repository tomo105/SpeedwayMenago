package com.example.speedwaymenago.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.speedwaymenago.AdminActivity
import com.example.speedwaymenago.R
import com.example.speedwaymenago.db.User
import com.example.speedwaymenago.viewmodel.LoginRegisterViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var userRef: DatabaseReference
    private lateinit var loginRegisterViewModel: LoginRegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()
        loginRegisterViewModel = ViewModelProvider(this).get( LoginRegisterViewModel::class.java)

        tvLogin.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        btnRegister.setOnClickListener {
            val username = editUsername.text.trim().toString()
            val email = editEmail.text.trim().toString()
            val password = editPassword.text.trim().toString()

            if (username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                Toast.makeText(this, "Input Provided", Toast.LENGTH_LONG).show()
                loginRegisterViewModel.register(username, email, password)
                // createUser(username, email, password)
            } else {
                Toast.makeText(this, "Input Required", Toast.LENGTH_LONG).show()
            }

        }


        tvLogin.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

    }


    private fun createUser(username: String, email: String, password: String) {

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->

                if (task.isSuccessful) {            //create successful
                    val firebaseInstance = FirebaseDatabase.getInstance()
                    userRef = firebaseInstance.getReference("User")

                    val userRole: String = if (username == "admin") {
                        "admin"
                    } else {
                        "user"
                    }

                    val user = User(username, email, userRole)
                    userRef.child(auth.uid.toString()).setValue(user)

                    userRef.addValueEventListener(object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError) {
                            Log.d("custom", "failed to add to User db")
                            Log.d("custom", p0.message + " \n " + p0.details)
                        }

                        override fun onDataChange(p0: DataSnapshot) {
                            Log.d("custom", "added user to db User")
                            val intent = Intent(this@RegisterActivity, MainActivity::class.java)
                            startActivity(intent)
                            //change UI
                        }
                    })


                } else {                                                                            // not successful
                    Log.e("custom", "failed" + task.exception)
                    Toast.makeText(this, "Failed in registration", Toast.LENGTH_LONG).show()
                }
            }
    }

}