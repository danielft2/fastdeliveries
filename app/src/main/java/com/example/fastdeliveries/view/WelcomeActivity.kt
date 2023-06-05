package com.example.fastdeliveries.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.fastdeliveries.R
import com.example.fastdeliveries.databinding.ActivityWelcomeBinding
import com.example.fastdeliveries.view.collaborator.view.SigninActivity

class WelcomeActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()

        binding = ActivityWelcomeBinding.inflate(layoutInflater);
        setContentView(binding.root)

        setOnClickEvents()
    }

    override fun onClick(v: View) {
        if (v.id === R.id.button_track_delivery) {
            startActivity(Intent(this, SigninActivity::class.java))
            finish()
        }
    }


    private fun setOnClickEvents() {
        binding.buttonTrackDelivery.setOnClickListener(this)
    }
}