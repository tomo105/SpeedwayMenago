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


//class AuthAppRepository(private val application: Application) {
//    private val firebaseAuth: FirebaseAuth by lazy {
//        FirebaseAuth.getInstance()
//    }
//    private lateinit var userLiveData: MutableLiveData<FirebaseUser>
//    private lateinit var loggedOutLiveData: MutableLiveData<Boolean>
//
//    fun register(email: String, password: String) {
//        firebaseAuth.createUserWithEmailAndPassword(email, password)
//            .addOnCompleteListener() { task ->
//                if (task.isSuccessful) {
//                    userLiveData.value = firebaseAuth.currentUser
//
//                } else {                                                                            // not successful
//                    Log.e("custom", "failed" + task.exception)
//                    Toast.makeText(application, "Failed in registration", Toast.LENGTH_LONG).show()
//                }
//            }
//    }
//
//    fun login(email: String, password: String) {
//        firebaseAuth.signInWithEmailAndPassword(email, password)
//            .addOnCompleteListener() { task ->
//                if (task.isSuccessful) {
//                    userLiveData.value= firebaseAuth.currentUser
//                } else {
//                    Toast.makeText(application, "Login Failure: " + task.exception!!.message, Toast.LENGTH_SHORT).show()
//                }
//            }
//    }
//
//    fun logout() {
//        firebaseAuth.signOut()
//        loggedOutLiveData.value = false
//    }
//
//    fun isLoggedOutLiveData() = loggedOutLiveData
//
//    fun currentUser() = userLiveData
//
//
//}
class AuthAppRepository(private val application: Application) {
    val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    var userDbRef: DatabaseReference = FirebaseDatabase.getInstance().getReference("User")

    var userLiveData: MutableLiveData<FirebaseUser> = MutableLiveData()
    private val loggedOutLiveData: MutableLiveData<Boolean> = MutableLiveData()

    fun login(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener()
            { task ->
                if (task.isSuccessful) {
                    //   userLiveData.postValue(firebaseAuth.currentUser)
                    userLiveData.postValue(firebaseAuth.currentUser)
//                    Log.d("custom", "Logged user with email= " + userLiveData.value!!.email.toString())
//                    Log.d("custom", "Logged user with name= " + userLiveData.value!!.displayName)
//                    val firebase = FirebaseDatabase.getInstance()
//                    userDbRef = firebase.getReference("User")
//
//                    userDbRef.addValueEventListener(object : ValueEventListener {
//                        override fun onCancelled(p0: DatabaseError) {
//                            Log.d("custom", "failed to log in ")
//                            Toast.makeText(
//                                application.applicationContext,
//                                "failed to login check, email or password",
//                                Toast.LENGTH_LONG
//                            ).show()
//                        }
//
//                        override fun onDataChange(p0: DataSnapshot) {
//                            val role = p0.child(registeredUserId.toString()).child("role").value
////                            if (role != null) {
////                                if (role == "admin") {
////                                  //  val intent =
////                                    startActivity(application.applicationContext,Intent(application.applicationContext, AdminActivity::class.java))
////                                } else {
////                                   // val intent = Intent(, DashboardActivity::class.java)
////                                    //startActivity(application.applicationContext,intent)
////                                }
////                            }

                    //        }
                    //    })
                    //
                } else {
                    Log.d("custom", "log in error: " + task.exception!!.message )
                    Toast.makeText(
                        application.applicationContext,
                        "Login Failure: " + task.exception!!.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    fun register(username: String, email: String, password: String) {

        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    userLiveData.postValue(firebaseAuth.currentUser)
                    updateUserNameInProfile(username)
                    addUserInfoToDb(username, email)
                } else {                                                                            // not successful
                    Log.e("custom", "failed" + task.exception)
                    Toast.makeText(application.applicationContext, "Register Failure: " + task.exception!!.message, Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun addUserInfoToDb(username: String, email: String) {
        val userRole: String = if (username == "admin") {
            "admin"
        } else {
            "user"
        }


        val user = User(username, email, userRole)
        userDbRef.child(firebaseAuth.uid.toString()).setValue(user)

        userDbRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.d("custom", "failed to add to User db")
                Log.d("custom", p0.message + " \n " + p0.details)
            }

            override fun onDataChange(p0: DataSnapshot) {
                Log.d("custom", "added user to db User")
            }
        })
    }

    private fun updateUserNameInProfile(username: String) {
        val profileUpdates =
            UserProfileChangeRequest.Builder()
                .setDisplayName(username).build()

        firebaseAuth.currentUser!!.updateProfile(profileUpdates)
    }

    fun logOut() {
        firebaseAuth.signOut()
        userLiveData.postValue(null)
        loggedOutLiveData.postValue(true)
    }

    init {
        if (firebaseAuth.currentUser != null) {
            userLiveData.postValue(firebaseAuth.currentUser)
            loggedOutLiveData.postValue(false)
        }
    }
}
