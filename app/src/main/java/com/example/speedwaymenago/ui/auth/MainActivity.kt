package com.example.speedwaymenago.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.speedwaymenago.AdminActivity
import com.example.speedwaymenago.ui.DashboardActivity
import com.example.speedwaymenago.R
import com.example.speedwaymenago.viewmodel.LoginRegisterViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var ref: DatabaseReference
    private lateinit var loginRegisterViewModel: LoginRegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loginRegisterViewModel = ViewModelProvider(this).get( LoginRegisterViewModel::class.java)

        auth = FirebaseAuth.getInstance()

        btnLogin.setOnClickListener {
            if (editEmailLogin.text.trim().toString().isNotEmpty() && editPasswordLogin.text.trim()
                    .toString().isNotEmpty()
            ) {
                //  signInUser(editEmailLogin.text.trim().toString(), editPasswordLogin.text.trim().toString())
                loginRegisterViewModel.login(
                    editEmailLogin.text.trim().toString(),
                    editPasswordLogin.text.trim().toString()
                )
//                loginRegisterViewModel.userLiveData.observe(this, Observer { user ->
//                    user.uid
//                } )

//                val intent = Intent(this, DashboardActivity::class.java)
//                startActivity(intent)
            } else {
                Toast.makeText(this, "input required", Toast.LENGTH_LONG).show()
            }

            loginRegisterViewModel.userLiveData.observe(this, Observer { user ->
                if( user.email =="tomo105@wp.pl") {
                    val intent = Intent(this, AdminActivity::class.java)
                    startActivity(intent)
                }
                else {
                    val intent = Intent(this, DashboardActivity::class.java)
                    startActivity(intent)
                }
                Log.d("custom", "Logged user with email= " + user.email.toString())
                Log.d("custom", "Logged user with name= " + user.displayName)
            })

        }


        tvRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

    }
 ///---------------------------------------------------------------------------------
    private fun signInUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    checkUser()
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
        ref = firebase.getReference("User")

        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.d("custom", "failed to log in ")
                Toast.makeText(
                    this@MainActivity,
                    "failed to login check, email or password",
                    Toast.LENGTH_LONG
                ).show()
            }

            override fun onDataChange(p0: DataSnapshot) {
                val role = p0.child(registeredUserId.toString()).child("role").value

                if (role != null) {
                    if (role == "admin") {
                        val intent = Intent(this@MainActivity, AdminActivity::class.java)
                        startActivity(intent)
                    } else {
                        val intent = Intent(this@MainActivity, DashboardActivity::class.java)
                        startActivity(intent)
                    }
                }

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