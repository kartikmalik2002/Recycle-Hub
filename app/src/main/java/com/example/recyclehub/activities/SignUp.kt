package com.example.recyclehub.activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.recyclehub.R
import com.example.recyclehub.activities.firebase.FirestoreClass
import com.example.recyclehub.activities.models.Users
import com.example.recyclehub.databinding.ActivitySignUpBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class SignUp : BaseActivity() {
    private lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        binding = ActivitySignUpBinding.inflate(layoutInflater)

        val view = binding.root
        setContentView(view)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
        )

        binding.btnSignUp.setOnClickListener {
            //FirebaseApp.initializeApp(this)
            registerUser()
        }

        binding.tvGoToLogin.setOnClickListener {
            startActivity(Intent(this@SignUp, LogIn::class.java))
            finish()
        }

    }
    private fun registerUser() {
    // Here we get the text from editText and trim the space
    val name: String = binding.etNameSignUp.text.toString().trim { it <= ' ' }
    val email: String = binding.etEmailSignUp.text.toString().trim { it <= ' ' }
    val password: String = binding.etPasswordSignUp.text.toString().trim { it <= ' ' }
    val confirmPassword: String = binding.etConfirmPasswordSignUp.text.toString().trim { it <= ' ' }

    if (validateForm(name, email, password,confirmPassword)) {
        // Show the progress dialog.

        showProgressDialog(resources.getString(R.string.please_wait))

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(
                OnCompleteListener<AuthResult> { task ->

                    // Hide the progress dialog
                    hideProgressDialog()

                    // If the registration is successfully done
                    if (task.isSuccessful) {

                        // Firebase registered user
                        val firebaseUser: FirebaseUser = task.result!!.user!!
                        // Registered Email
                        val registeredEmail = firebaseUser.email!!

                        val user = Users(
                            firebaseUser.uid, name, registeredEmail
                        )

                        // call the registerUser function of FirestoreClass to make an entry in the database.
                        FirestoreClass().registerUser(this@SignUp, user)
                    } else {
                        Toast.makeText(
                            this@SignUp,
                            task.exception!!.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
    }
}
// END

private fun validateForm(name: String, email: String, password: String,confirmPassword: String): Boolean {
    return when {
        TextUtils.isEmpty(name) -> {
            showErrorSnackBar("Please enter name.")
            false
        }

        TextUtils.isEmpty(email) -> {
            showErrorSnackBar("Please enter email.")
            false
        }

        TextUtils.isEmpty(password) -> {
            showErrorSnackBar("Please enter password.")
            false
        }
        TextUtils.isEmpty(confirmPassword)->{
            showErrorSnackBar("Please confirm Password.")
            false
        }
        !(password.equals(confirmPassword)) -> {
            showErrorSnackBar("confirm password in not matching")
            false
        }
        else -> {
            true
        }
    }
}
    fun userRegisteredSuccess() {

        Toast.makeText(
            this@SignUp,
            "You have successfully registered.",
            Toast.LENGTH_SHORT
        ).show()

        // Hide the progress dialog
        hideProgressDialog()

        /**
         * Here the new user registered is automatically signed-in so we just sign-out the user from firebase
         * and send him to Intro Screen for Sign-In
         */
        startActivity(Intent(this,MainActivity::class.java))
        // Finish the Sign-Up Screen
        this.finish()
    }


}