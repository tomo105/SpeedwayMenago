package com.example.speedwaymenago.ui.loggedIn

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.speedwaymenago.R
import com.example.speedwaymenago.db.Bet
import com.example.speedwaymenago.ui.MainActivity
import com.example.speedwaymenago.viewmodel.BetsViewModel
import com.example.speedwaymenago.viewmodel.LoginRegisterViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.add_bet.*

class DashboardActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var loginRegisterViewModel: LoginRegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_bet)

        loginRegisterViewModel = ViewModelProvider(this).get( LoginRegisterViewModel::class.java)

        auth = FirebaseAuth.getInstance()

//        fabLogout.setOnClickListener {
//            loginRegisterViewModel.logout()
//              val intent = Intent(this, MainActivity::class.java)
//             startActivity(intent)
//        }



    }
}