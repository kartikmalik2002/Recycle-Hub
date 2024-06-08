package com.example.recyclehub.activities.firebase

import android.app.Activity
import android.util.Log
import com.example.recyclehub.activities.CoinDetails
import com.example.recyclehub.activities.LogIn
import com.example.recyclehub.activities.MainActivity
import com.example.recyclehub.activities.PickUpCartActivity
import com.example.recyclehub.activities.SignUp
import com.example.recyclehub.activities.models.Users
import com.example.recyclehub.activities.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class FirestoreClass {
    private val mFireStore = FirebaseFirestore.getInstance()
    /**
     * A function to make an entry of the registered user in the firestore database.
     */
    fun registerUser(activity: SignUp, userInfo: Users) {

        mFireStore.collection(Constants.USERS)
            // Document ID for users fields. Here the document it is the User ID.
            .document(getCurrentUserID())
            // Here the userInfo are Field and the SetOption is set to merge. It is for if we wants to merge
            .set(userInfo, SetOptions.merge())
            .addOnSuccessListener {

                // Here call a function of base activity for transferring the result to it.
                activity.userRegisteredSuccess()
            }
            .addOnFailureListener { e ->
                activity.hideProgressDialog()
                Log.e(
                    activity.javaClass.simpleName,
                    "Error writing document",
                    e
                )
            }
    }

    /**
     * A function to SignIn using firebase and get the user details from Firestore Database.
     */
    fun loadUserData(activity: Activity) {

        // Here we pass the collection name from which we wants the data.
        mFireStore.collection(Constants.USERS)
            // The document id to get the Fields of user.
            .document(getCurrentUserID())
            .get()
            .addOnSuccessListener { document ->
                Log.e(activity.javaClass.simpleName, document.toString())

                // Here we have received the document snapshot which is converted into the User Data model object.
                val loggedInUser = document.toObject(Users::class.java)!!

                // Here call a function of base activity for transferring the result to it.
                when (activity) {
                    is LogIn -> {
                        activity.signInSuccess(loggedInUser)
                    }

                    is MainActivity -> {
                        activity.updateNavigationUserDetails(loggedInUser)
                    }


                    is PickUpCartActivity ->{
                        activity.setUser(loggedInUser)
                    }

                    is CoinDetails -> {
                        activity.setUserDataInUI(loggedInUser)
                    }
                }
            }
            .addOnFailureListener { e ->
                // Here call a function of base activity for transferring the result to it.
                when (activity) {
                    is LogIn -> {
                        activity.hideProgressDialog()
                    }

                    is MainActivity -> {
                        activity.hideProgressDialog()
                    }
//
//                    is MyProfileActivity -> {
//                        activity.hideProgressDialog()
//                    }
                }
                Log.e(
                    activity.javaClass.simpleName,
                    "Error while getting loggedIn user details",
                    e
                )
            }
    }


    fun updateUserProfileData(activity: Activity, userHashMap: HashMap<String, Any>) {
        mFireStore.collection(Constants.USERS) // Collection Name
            .document(getCurrentUserID()) // Document ID
            .update(userHashMap) // A hashmap of fields which are to be updated.
            .addOnSuccessListener {
                Log.e(activity.javaClass.simpleName, "Data updated successfully!")

                // Notify the success result.

                when (activity) {
                    is PickUpCartActivity -> {
                        activity.coinsAddedSuccess()
                    }

                    is CoinDetails -> {
                        activity.coinUpdate()
                    }
                }
            }
            .addOnFailureListener { e ->
                when (activity) {
                    is MainActivity -> {
                        activity.hideProgressDialog()
                    }

//                    is MyProfileActivity -> {
//                        activity.hideProgressDialog()
//                    }
                }

                Log.e(
                    activity.javaClass.simpleName,
                    "Error while creating a board.",
                    e
                )
            }
    }


    fun sendCoins(activity: CoinDetails, email: String, coins: String, user: Users) {
        mFireStore.collection(Constants.USERS).whereEqualTo("email" , email)
            .get()
            .addOnSuccessListener {document ->

                Log.e(activity.javaClass.simpleName, document.documents.toString())



                if (document.documents.size > 0) {
                    var curCoins = 0
                    mFireStore.collection(Constants.USERS)
                        .document(document.documents[0].id)
                        .get()
                        .addOnSuccessListener {document ->
                            curCoins = document.toObject(Users::class.java)!!.coins
                            val hashMap : HashMap<String , Any> = hashMapOf(Constants.COINS to (curCoins + Integer.parseInt(coins)))
                            mFireStore.collection(Constants.USERS)
                                .document(document.id)
                                .update(hashMap)
                                .addOnSuccessListener {
                                    activity.coinsSentSuccess()
                                }
                                .addOnFailureListener{
                                    activity.hideProgressDialog()
                                    activity.showErrorSnackBar("Error while sending coins")
                                }

                        }
                        .addOnFailureListener{e->
                            activity.hideProgressDialog()
                            activity.showErrorSnackBar("Error while getting user details")
                            Log.e(
                                activity.javaClass.simpleName,
                                "Error while getting user details",
                                e
                            )
                        }


                    val newHashMap : HashMap<String , Any> = hashMapOf(Constants.COINS to (user.coins - Integer.parseInt(coins)))
                    updateUserProfileData(activity , newHashMap)
                } else {
                    activity.hideProgressDialog()
                    activity.showErrorSnackBar("No such user found.")
                }
            }
            .addOnFailureListener { e ->
                activity.hideProgressDialog()
                Log.e(
                    activity.javaClass.simpleName,
                    "Error while getting user details",
                    e
                )
            }


    }


    fun getCurrentUserID(): String {
        // An Instance of currentUser using FirebaseAuth
        val currentUser = FirebaseAuth.getInstance().currentUser

        // A variable to assign the currentUserId if it is not null or else it will be blank.
        var currentUserID = ""
        if (currentUser != null) {
            currentUserID = currentUser.uid
        }

        return currentUserID
    }



}