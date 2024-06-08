package com.example.recyclehub.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recyclehub.R
import com.example.recyclehub.activities.adapters.PickUpCartAdapter
import com.example.recyclehub.activities.firebase.FirestoreClass
import com.example.recyclehub.activities.models.Qty
import com.example.recyclehub.activities.models.Users
import com.example.recyclehub.activities.utils.Constants
import com.example.recyclehub.databinding.ActivityPickUpCartBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder


var qtyList : MutableList<Qty> = ArrayList()

class PickUpCartActivity : BaseActivity() {

    lateinit var binding : ActivityPickUpCartBinding

    private lateinit var mUser : Users
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPickUpCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getUserDetails()
        setUpActionBar()
        setUpVisibilities()
        setUpRecyclerView()

        binding.btnPickUpRequest.setOnClickListener{
            showProgressDialog("Request in progress")
            var coins = qtyList.sumOf { qty -> qty.noOfCoins }
            FirestoreClass().updateUserProfileData(this@PickUpCartActivity , hashMapOf(Constants.COINS to (mUser.coins + coins)))
        }


    }

    private fun getUserDetails() {
        FirestoreClass().loadUserData(this@PickUpCartActivity)
    }

    private fun setUpVisibilities() {
        if (qtyList.isEmpty()){
            binding.tvItems.visibility = View.GONE
            binding.btnPickUpRequest.visibility = View.GONE
            binding.rvPickUpCart.visibility = View.GONE
            binding.tvEmptyCart.visibility = View.VISIBLE
        }
        else{
            binding.tvItems.visibility = View.VISIBLE
            binding.btnPickUpRequest.visibility = View.VISIBLE
            binding.rvPickUpCart.visibility = View.VISIBLE
            binding.tvEmptyCart.visibility = View.GONE
        }
    }

    private fun setUpRecyclerView() {
        binding.rvPickUpCart.apply {
            adapter = PickUpCartAdapter()
            layoutManager = LinearLayoutManager(context ,LinearLayoutManager.VERTICAL , false)
        }
    }

    private fun setUpActionBar() {
        setSupportActionBar(binding.toolbarPickUpCart)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
        binding.toolbarPickUpCart.setNavigationOnClickListener { onBackPressed() }


    }

    fun coinsAddedSuccess() {
        hideProgressDialog()
        MaterialAlertDialogBuilder(this)
            .setIcon(R.drawable.ic_done)
            .setTitle(resources.getString(R.string.title))
            .setMessage(resources.getString(R.string.supporting_text))
            .setNeutralButton(resources.getString(R.string.back_to_home)) { dialog, which ->

               startActivity(Intent(this@PickUpCartActivity , MainActivity::class.java))
                qtyList.clear()
               finish()

            }
            .setCancelable(false)

//                .setNegativeButton(resources.getString(R.string.decline)) { dialog, which ->
//                    // Respond to negative button press
//                }
//                .setPositiveButton(resources.getString(R.string.accept)) { dialog, which ->
//                    // Respond to positive button press
//                }
            .show()
    }

    fun setUser(loggedInUser: Users) {
        mUser = loggedInUser
    }
}