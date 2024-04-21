package com.example.recyclehub.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.recyclehub.R
import com.example.recyclehub.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    // A global variable for User Name
    private lateinit var mUserName: String
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        // This is used to align the xml view to this class
        setContentView(binding.root)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }


        setupActionBar()

        // Assign the NavigationView.OnNavigationItemSelectedListener to navigation view.
        binding.navView.setNavigationItemSelectedListener(this)
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
                    Intent(this@MainActivity, CoinDetails::class.java),

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

                // TODO (Step 2: Start activity for result.)
                // START
                startActivity(intent)
                return true
                // END
            }
        }
        return super.onOptionsItemSelected(item)
    }

}