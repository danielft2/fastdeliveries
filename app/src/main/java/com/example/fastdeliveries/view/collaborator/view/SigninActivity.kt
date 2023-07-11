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
import com.google.android.material.snackbar.Snackbar

class SigninActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivitySigninBinding
    private lateinit var viewModel: SigninViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[SigninViewModel::class.java]

        binding.buttonEnter.setOnClickListener(this)
        observers();
    }

    override fun onClick(v: View) {
        if (v.id === R.id.button_enter) {
            viewModel.signin(
                binding.editCpf.text.toString(), binding.editPassword.text.toString()
            )


        } else {
            Toast.makeText(this, getString(R.string.CREDENCIALS_INVALID), Toast.LENGTH_SHORT).show()
        }
    }

    private fun observers() {
        viewModel.signinResponse.observe(this) {
            if (it.status()) {
                startActivity(Intent(this, MainCollaboratorActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, it.message(), Toast.LENGTH_SHORT).show()
            }
        }
    }
}