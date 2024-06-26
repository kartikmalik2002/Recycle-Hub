package com.example.recyclehub.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.recyclehub.R
import com.example.recyclehub.activities.adapters.CategoryItemAdapter
import com.example.recyclehub.activities.firebase.FirestoreClass
import com.example.recyclehub.activities.models.ItemsCategory
import com.example.recyclehub.activities.models.Qty
import com.example.recyclehub.activities.models.Users
import com.example.recyclehub.activities.utils.Constants
import com.example.recyclehub.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

val categoryList = mutableListOf(
    ItemsCategory("Paper",R.drawable.pic_paper ,arrayListOf(Qty("Paper","5 - 10",20,true), Qty("Paper","10 - 50", 50), Qty("Paper","50 +",150))),
    ItemsCategory("Glass Bottles",R.drawable.pic_glass_bottles, arrayListOf(Qty("Glass Bottles","5 - 10",30, true),Qty("Glass Bottles","10 - 50", 40),Qty("Glass Bottles","50 +",100))),
    ItemsCategory("Plastic Bottles",R.drawable.pic_plastic_bottles, arrayListOf(Qty("Plastic Bottles","5 - 10",10, true),Qty("Plastic Bottles","10 - 50", 30),Qty("Plastic Bottles","50 +",80))),
    ItemsCategory("Used Batteries", R.drawable.pic_used_batteries,arrayListOf(Qty("Used Batteries","5 - 10",30, true),Qty("Used Batteries","10 - 50", 70),Qty("Used Batteries","50 +",150))),
    ItemsCategory("Electronic Waste", R.drawable.pic_electronic_waste,arrayListOf(Qty("Electronic Waste","5 - 10",40, true),Qty("Electronic Waste","10 - 50", 70),Qty("Electronic Waste","50 +",150))),
    ItemsCategory("Solid Waste",R.drawable.pic_solid_waste, arrayListOf(Qty("Solid Waste","5 - 10",10, true),Qty("Solid Waste","10 - 50", 30),Qty("Solid Waste","50 +",80))),

)

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    // A global variable for User Name
    private lateinit var mUserName: String
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        // This is used to align the xml view to this class
        setContentView(binding.root)

        setupActionBar()

        // Assign the NavigationView.OnNavigationItemSelectedListener to navigation view.
        binding.navView.setNavigationItemSelectedListener(this)

        showProgressDialog(resources.getString(R.string.please_wait))
        FirestoreClass().loadUserData(this@MainActivity)

        binding.appBarMain.fabCart.setOnClickListener {
            startActivity(Intent(this@MainActivity , PickUpCartActivity::class.java))
        }

    }

    private fun setupActionBar() {

        setSupportActionBar(binding.appBarMain.toolbarMainActivity)
        binding.appBarMain.toolbarMainActivity.setNavigationIcon(R.drawable.ic_action_navigation_menu)

        binding.appBarMain.toolbarMainActivity.setNavigationOnClickListener {
            toggleDrawer()
        }
    }

    /**
     * A function for opening and closing the Navigation Drawer.
     */
    private fun toggleDrawer() {

        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.nav_coins -> {

                startActivity(
                    Intent(this@MainActivity, CoinDetails::class.java)

                )
            }

            R.id.nav_sign_out -> {
                // Here sign outs the user from firebase in this device.
                FirebaseAuth.getInstance().signOut()
                // Send the user to the intro screen of the application.
                val intent = Intent(this, LogIn::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
            }
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu to use in the action bar
        menuInflater.inflate(R.menu.menu_home, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle presses on the action bar menu items
        when (item.itemId) {

            R.id.menu_coins -> {

                val intent = Intent(this@MainActivity, CoinDetails::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun updateNavigationUserDetails(user: Users) {

        hideProgressDialog()

        mUserName = user.name
        // The instance of the header view of the navigation view.
        val headerView = binding.navView.getHeaderView(0)
        // The instance of the user image of the navigation view.
        val navUserImage = headerView.findViewById<ImageView>(R.id.iv_user_image)
        // The instance of the user name TextView of the navigation view.
        val navUsername = headerView.findViewById<TextView>(R.id.tv_username)
        // Set the user name
        navUsername.text = user.name
    }


}