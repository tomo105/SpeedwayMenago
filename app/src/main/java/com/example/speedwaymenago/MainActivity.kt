package com.example.speedwaymenago

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_register.*

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var ref: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()

        btnLogin.setOnClickListener {
            if (editEmailLogin.text.trim().toString().isNotEmpty() && editPasswordLogin.text.trim()
                    .toString().isNotEmpty()
            ) {
                signInUser(
                    editEmailLogin.text.trim().toString(),
                    editPasswordLogin.text.trim().toString()
                )

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
                    checkUser()
                    val intent = Intent(this, DashboardActivity::class.java)
                    startActivity(intent)

                } else {
                    Toast.makeText(this, "Erorr" + task.exception, Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun checkUser() {
        val user = auth.currentUser
        val registeredUserId = user?.uid
        Log.d("check", registeredUserId + "reistered")

        val firebase = FirebaseDatabase.getInstance()
        ref = firebase.getReference("Role")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                val name = p0.value
                val name2 = p0.child(registeredUserId.toString()).child("role").value
                //  val value = p0.child()
                Log.d("check", name.toString())
                 Log.d("check", name2.toString())
            }

        })


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