package com.example.recyclehub.activities

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recyclehub.R
import com.example.recyclehub.activities.adapters.PickUpCartAdapter
import com.example.recyclehub.activities.models.Qty
import com.example.recyclehub.databinding.ActivityPickUpCartBinding


var qtyList : MutableList<Qty> = ArrayList()

class PickUpCartActivity : AppCompatActivity() {

    lateinit var binding : ActivityPickUpCartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPickUpCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpActionBar()
        setUpVisibilities()
        setUpRecyclerView()
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
}