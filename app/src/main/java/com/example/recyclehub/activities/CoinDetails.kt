package com.example.recyclehub.activities

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.recyclehub.R
import com.example.recyclehub.activities.firebase.FirestoreClass
import com.example.recyclehub.activities.models.Users
import com.example.recyclehub.databinding.ActivityCoinDetailsBinding

class CoinDetails : BaseActivity() {
    lateinit var binding:ActivityCoinDetailsBinding
    lateinit var mUser : Users
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoinDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        FirestoreClass().loadUserData(this@CoinDetails)

        binding.btnGainCoins.setOnClickListener{
            startActivity(Intent(this@CoinDetails , MainActivity::class.java))
            finish()
        }
        binding.btnSendCoins.setOnClickListener {

            val dialog =  Dialog(this)



            dialog.setContentView(R.layout.dialog_send_coins)
            dialog.setCancelable(true)
            dialog.setCanceledOnTouchOutside(true)
            dialog.window?.setBackgroundDrawable(
                ColorDrawable(Color.TRANSPARENT))
            dialog.apply {
                findViewById<Button>(R.id.btn_dialog_send_coins).setOnClickListener {
                    val email = this.findViewById<EditText>(R.id.et_email_reciever).text.toString()
                    val coins = this.findViewById<EditText>(R.id.et_no_of_coins).text.toString()
                    if (email.isNotEmpty() && coins.isNotEmpty() && Integer.parseInt(coins) <= mUser.coins) {

                        dialog.dismiss()

                        // Show the progress dialog.
                        showProgressDialog(resources.getString(R.string.sending_coins))
                        FirestoreClass().sendCoins(this@CoinDetails, email, coins, mUser)
                    } else {

                        if(coins.isNotEmpty()){
                            if(Integer.parseInt(coins) > mUser.coins)
                                showErrorSnackBar("Insufficient Balance")
                        }

                        else if(email.isEmpty())
                            showErrorSnackBar("Please enter members email address.")
                        else
                            showErrorSnackBar("Please enter number of coins")



                    }

                }
            }


            dialog.show()


        }

    }

    fun setUserDataInUI(loggedInUser: Users) {

        mUser = loggedInUser
        binding.coinBalance.text = mUser.coins.toString()

    }

    fun coinsSentSuccess(){
        hideProgressDialog()
        Toast.makeText(this,"Coins sent successfully",Toast.LENGTH_SHORT).show()
    }

    fun coinUpdate() {
        FirestoreClass().loadUserData(this@CoinDetails)
    }
}