package com.example.speedwaymenago

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.speedwaymenago.ui.auth.MainActivity
import com.example.speedwaymenago.viewmodel.LoginRegisterViewModel
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.activity_table.*

class AdminActivity : AppCompatActivity() {

    private lateinit var loginRegisterViewModel: LoginRegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_table)

//       val recyclerView = findViewById<RecyclerView>(R.id.bets_recycler)
//        recyclerView.layoutManager = LinearLayoutManager(this)
//        recyclerView.adapter = BetsAdapter()
        loginRegisterViewModel = ViewModelProvider(this).get( LoginRegisterViewModel::class.java)

        btnLogoutAdmin.setOnClickListener {
            loginRegisterViewModel.logout()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

}