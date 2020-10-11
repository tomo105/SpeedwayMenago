package com.example.speedwaymenago.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.speedwaymenago.db.Bet
import com.example.speedwaymenago.model.DbAppRepository

class BetsViewModel(application: Application) : AndroidViewModel(application) {
    private val betsRepository = DbAppRepository(application)

    fun addBet(bet: Bet, title: String, num: Int) {
        betsRepository.addBetToDb(bet, title, num)
    }

    fun getBets(title: String) {
        betsRepository.getBetsFromSpecificRound(title)
    }

}