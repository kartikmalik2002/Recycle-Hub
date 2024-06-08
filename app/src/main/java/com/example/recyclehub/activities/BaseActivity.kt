package com.example.recyclehub.activities

import android.app.Dialog
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.recyclehub.R
import com.example.recyclehub.databinding.DialogProgressBinding
import com.google.android.material.snackbar.Snackbar

open class BaseActivity : AppCompatActivity() {

    private lateinit var tv_progress_text: TextView
    private lateinit var mProgressDialog: Dialog
    private lateinit var binding: DialogProgressBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DialogProgressBinding.inflate(layoutInflater)
        setContentView(binding.root)
        super.onCreate(savedInstanceState)
    }

    fun showProgressDialog(text: String) {

        mProgressDialog = Dialog(this)

        /*Set the screen content from a layout resource.
        The resource will be inflated, adding all top-level views to the screen.*/
        mProgressDialog.setContentView(R.layout.dialog_progress)


        binding.tvProgressText.text = text
        //mProgressDialog.tv_progress_text.text = text

        //Start the dialog and display it on screen.
        mProgressDialog.show()
    }

    fun hideProgressDialog() {
        mProgressDialog.dismiss()
    }

    fun showErrorSnackBar(message: String) {
        val snackBar =
            Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
        val snackBarView = snackBar.view
        snackBar.setBackgroundTint(ContextCompat.getColor(
            this@BaseActivity,
            R.color.md_theme_light_error
        ))
        snackBar.show()
    }
}