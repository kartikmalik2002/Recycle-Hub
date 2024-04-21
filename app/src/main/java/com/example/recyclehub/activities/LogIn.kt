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
import com.example.recyclehub.activities.models.Users
import com.example.recyclehub.databinding.ActivityLogInBinding
import com.google.firebase.auth.FirebaseAuth
import com.example.recyclehub.activities.firebase.FirestoreClass

class LogIn : BaseActivity() {
    private lateinit var binding: ActivityLogInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)

        setContentView(binding.root)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
        )

//        enableEdgeToEdge()
//        setContentView(R.layout.activity_log_in)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        binding.btnLonIn.setOnClickListener {
            signInRegisteredUser()
        }
        binding.tvGoToSignUp.setOnClickListener {
            startActivity(Intent(this@LogIn, SignUp::class.java))
        }
    }


    private fun signInRegisteredUser() {
        // Here we get the text from editText and trim the space
        val email: String = binding.etEmailLogIn.text.toString().trim { it <= ' ' }
        val password: String = binding.etPasswordLogIn.text.toString().trim { it <= ' ' }

        if (validateForm(email, password)) {
            // Show the progress dialog.
            showProgressDialog(resources.getString(R.string.please_wait))

            // FirebaseApp.initializeApp(this)

            // Sign-In using FirebaseAuth
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    hideProgressDialog()
                    if (task.isSuccessful) {

                        FirestoreClass().loadUserData(this@LogIn)

                    } else {
                        Toast.makeText(
                            this@LogIn,
                            task.exception!!.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }
    }
    // END
    private fun validateForm(email: String, password: String): Boolean {
        return if (TextUtils.isEmpty(email)) {
            showErrorSnackBar("Please enter email.")
            false
        } else if (TextUtils.isEmpty(password)) {
            showErrorSnackBar("Please enter password.")
            false
        } else {
            true
        }
    }

    fun signInSuccess(user: Users) {

        hideProgressDialog()

        startActivity(Intent(this@LogIn, MainActivity::class.java))
        this.finish()
    }

}