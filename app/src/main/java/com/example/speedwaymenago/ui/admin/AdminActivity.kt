package com.example.speedwaymenago.ui.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import androidx.lifecycle.ViewModelProvider
import com.example.speedwaymenago.R
import com.example.speedwaymenago.db.Bet
import com.example.speedwaymenago.ui.MainActivity
import com.example.speedwaymenago.viewmodel.BetsViewModel
import com.example.speedwaymenago.viewmodel.LoginRegisterViewModel
import kotlinx.android.synthetic.main.activity_table.*
import kotlinx.android.synthetic.main.add_bet.*
import kotlinx.android.synthetic.main.match_bet.*

class AdminActivity : AppCompatActivity() {

    private lateinit var loginRegisterViewModel: LoginRegisterViewModel
    private lateinit var betsViewModel: BetsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_bet)
        betsViewModel = ViewModelProvider(this).get(BetsViewModel::class.java)

        if (!TextUtils.isEmpty(teamA.text.toString()) && !TextUtils.isEmpty(teamB.text.toString()) && !TextUtils.isEmpty( riderPointsMoreLess.text.toString ())
                && !TextUtils.isEmpty(riderPvP1.text.toString())&& !TextUtils.isEmpty(betsName.text.toString()) && !TextUtils.isEmpty(points.text.toString()) && !TextUtils.isEmpty(riderPvP2.text.toString())) {
                //todo send data to db
        }
        val teamA = teamA.text.toString()
        fabAddBet.setOnClickListener {
            var bet = Bet("leszno", "falubaz", 55, 35, "Kubera", "Dudek", "Chugunov", 7.5)
            betsViewModel.addBet(bet, "Round1", 1)
        }


        fabShowBet.setOnClickListener {
            betsViewModel.getBets("Round1")
        }
//       val recyclerView = findViewById<RecyclerView>(R.id.bets_recycler)
//        recyclerView.layoutManager = LinearLayoutManager(this)
////        recyclerView.adapter = BetsAdapter()
//        loginRegisterViewModel = ViewModelProvider(this).get( LoginRegisterViewModel::class.java)
//
//        btnLogoutAdmin.setOnClickListener {
//            loginRegisterViewModel.logout()
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
//

    }
}