package com.example.fastdeliveries.view.collaborator.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.fastdeliveries.R
import com.example.fastdeliveries.databinding.ActivitySigninBinding
import com.example.fastdeliveries.view.collaborator.viewModel.SigninViewModel
import com.example.fastdeliveries.view.constants.ErrorMessages
import com.google.android.gms.maps.SupportMapFragment

class SigninActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivitySigninBinding
    private lateinit var viewModel: SigninViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(SigninViewModel::class.java)

        setOnClickEvents()
    }

    override fun onClick(v: View) {
        if (v.id === R.id.button_enter) {
            var response  = viewModel.signin(
                binding.editCpf.text.toString(), binding.editPassword.text.toString()
            )

            if (response) {
                startActivity(Intent(this, MainCollaboratorActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, ErrorMessages.MESSAGES.AUTH, Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun setOnClickEvents() {
        binding.buttonEnter.setOnClickListener(this)
    }
}