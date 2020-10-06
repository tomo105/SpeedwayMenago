package com.example.speedwaymenago

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_register.*
import java.util.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var ref: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()


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
                createUser(username, email, password)
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

        val intent = Intent(this, DashboardActivity::class.java)
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->

                if (task.isSuccessful) {            //create successful
                    val firebase = FirebaseDatabase.getInstance()
                    ref = firebase.getReference("Role")
                    Log.e("Task", "successful added a user.")
                    var firebaseInput = Role()

                    if (username.equals("admin", ignoreCase = true)) {
                        firebaseInput = Role("admin")
                    } else {
                        firebaseInput = Role("user")
                    }

                    ref.child(auth.uid.toString()).setValue(firebaseInput)

                    ref.addValueEventListener(object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError) {
                            Log.e("error", "error")
                            Log.e("error", p0.message)
                        }

                        override fun onDataChange(p0: DataSnapshot) {
                            startActivity(intent)
                            //change UI
                        }
                    })

                } else {                                                                            // not successful
                    Log.e("Task", "failed" + task.exception)
                    Toast.makeText(this, "Failed in registration", Toast.LENGTH_LONG).show()
                }
            }
    }

}