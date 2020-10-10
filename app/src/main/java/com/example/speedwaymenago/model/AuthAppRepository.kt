package com.example.speedwaymenago.model

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.speedwaymenago.db.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.*


class AuthAppRepository(private val application: Application) {
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    var userDbRef: DatabaseReference = FirebaseDatabase.getInstance().getReference("User")

    var currentUserLiveData: MutableLiveData<FirebaseUser> = MutableLiveData()
    private val loggedOutLiveData: MutableLiveData<Boolean> = MutableLiveData()

    fun login(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener()
            { task ->
                if (task.isSuccessful) {
                    Log.d("custom", "token : " + firebaseAuth.currentUser!!.getIdToken(true).toString())
                    Log.d("custom", "Logged user with email= " + firebaseAuth.currentUser!!.email)
                    Log.d("custom", "Logged user with name= " + firebaseAuth.currentUser!!.displayName)
                    currentUserLiveData.postValue(firebaseAuth.currentUser)
                } else {
                    Log.d("custom", "log in error: " + task.exception!!.message)
                    Toast.makeText(application.applicationContext, "Login Failure: " + task.exception!!.message, Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun register(username: String, email: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    currentUserLiveData.postValue(firebaseAuth.currentUser)
                    firebaseAuth.getAccessToken(true)
                    updateUserDisplayNameInProfile(username)
                    addUserInfoToDb(username, email)
                } else {                                                                            // not successful
                    Log.e("custom", "failed" + task.exception)
                    Toast.makeText(application.applicationContext, "Register Failure: " + task.exception!!.message, Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun addUserInfoToDb(username: String, email: String) {
        val user = User(username, email)
        userDbRef.child(firebaseAuth.uid.toString()).setValue(user)

        userDbRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.d("custom", "failed to add to User db")
                Log.d("custom", p0.message + " \n " + p0.details)
            }

            override fun onDataChange(p0: DataSnapshot) {
                Log.d("custom", "User added to db User")
            }
        })
    }

    private fun updateUserDisplayNameInProfile(username: String) {
        val profileUpdates =
            UserProfileChangeRequest.Builder()
                .setDisplayName(username)
                .build()

        firebaseAuth.currentUser!!.updateProfile(profileUpdates)

    }

    fun logOut() {
        firebaseAuth.signOut()
        //currentUserLiveData.postValue(null)   //???
        loggedOutLiveData.postValue(true)
    }

    init {
        if (firebaseAuth.currentUser != null) {
            currentUserLiveData.postValue(firebaseAuth.currentUser)
            loggedOutLiveData.postValue(false)
        }
    }
}
