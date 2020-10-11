package com.example.speedwaymenago.model

import android.app.Application
import android.util.Log
import android.widget.Toast
import com.example.speedwaymenago.db.Bet
import com.google.firebase.database.*

class DbAppRepository(private val application: Application) {
    //TODO save data to db or get idk yet

    var firebaseDb: FirebaseDatabase = FirebaseDatabase.getInstance()
  //  val currentBetsLiveData: LiveData<MutableList<Bet>> //= LiveData<MutableList<Bet>>()

    fun addBetToDb(bet: Bet, title: String, numberOfBet: Int) { //numberofbets is a int to refer which match is added
        val refDb = firebaseDb.getReference(title)
        refDb.child(numberOfBet.toString()).setValue(bet)
        refDb.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(
                    application.applicationContext,
                    "Error in adding bet to database" + error.message,
                    Toast.LENGTH_LONG
                ).show()
                Log.d("custom", "error in adding bet to db")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                //    currentBetsLiveData.postValue(listOf(bet))
                Toast.makeText(
                    application.applicationContext,
                    "Error in adding bet to database",
                    Toast.LENGTH_LONG
                ).show()
                Log.d("custom", "added bet to db")
            }

        })
    }
        //: MutableList<Bet>
        fun getBetsFromSpecificRound(roundNumber: String) { //numberofbets is a int to refer which match is added
            val refDb = firebaseDb.getReference(roundNumber)
          //  refDb.child(numberOfBet.toString()).setValue(bet)
            refDb.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(application.applicationContext,"Error in adding bet to database" + error.message,Toast.LENGTH_LONG).show()
                    Log.d("custom", "error in adding bet to db" + error.details + "  "+ error.message)
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    //iterowac i potem else return czy cos a reszte jako livedata do zakladów !!!
                  //  while(snapshot.child())
                   Log.d("custom", snapshot.child("1").value.toString())
                    Toast.makeText(application.applicationContext,"Error in adding bet to database",Toast.LENGTH_LONG).show()
                    Log.d("custom", "retrrieve bet data and all info was :")
                    Log.d("custom",snapshot.toString() )
                    Log.d("custom",snapshot.key )
                    Log.d("custom",snapshot.child("5").exists().toString() )
                }

            })
    }

}