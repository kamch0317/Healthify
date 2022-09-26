package com.example.Healthify

import Healthify.databinding.SignupBinding
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import com.example.Healthify.databinding.SignupBinding

class Signup : AppCompatActivity() {
    private lateinit var binding: SignupBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        checkfield()
        binding.submitButton.setOnClickListener { insertdata() }

    }

    private fun checkfield() {
        binding.loginUsername.doOnTextChanged { text, start, before, count ->
            if (text!!.length > 20) {
                binding.username.error = "Meet the maximum number of name"
            } else if (text.length < 20)
                binding.username.error = null
        }

        binding.contact.doOnTextChanged { text, start, before, count ->
            if (text!!.length > 10) {
                binding.usercontact.error = "Phone number should not more than 10 digit "
            } else if (text!!.length < 10) {
                binding.usercontact.error = null
            }
        }

        binding.age.doOnTextChanged { text, start, before, count ->
            if (text!!.length > 2) {
                binding.userage.error = "Please enter a valid age"
            } else if (text!!.length < 2) {
                binding.userage.error = null
            }
        }

        binding.password.doOnTextChanged { text, start, before, count ->
            if (text!!.isNotBlank()) {
                binding.userpassword.error = null
            }
        }

        binding.confirmpassword.doOnTextChanged { text, start, before, count ->
            if (text!!.isNotBlank()) {
                binding.confirmpass.error = null
            }


        }
    }




        private fun insertdata() {

            val db = DataBHelper(this)
            val username = binding.loginUsername.text.toString()
            val usercontact = binding.contact.text.toString()
            val userage = binding.age.text.toString().toIntOrNull()
            val password = binding.password.text.toString()
            val repassword = binding.confirmpassword.text.toString()
            val checkuser = db.checkusername(username)


            if (username.isNullOrBlank()) {
                binding.username.error = "This field is required"

            } else if (usercontact.isNullOrBlank()) {
                binding.usercontact.error = "This field is required"

            } else if (binding.age.text.isNullOrBlank()) {
                binding.userage.error = "This field is required"

            } else if (binding.password.text.isNullOrBlank()) {
                binding.userpassword.error = "This field is required"

            } else if (binding.confirmpassword.text.isNullOrBlank()) {
                binding.confirmpass.error = "This field is required"


        } else (
            if (password.equals(repassword)) {
            if (checkuser == false) {
                val insert = db.insertuserdata(username, usercontact, userage, password)
                if (insert == true) {
                    Toast.makeText(this, "WELCOME TO KIAH", Toast.LENGTH_SHORT).show()
                } else
                    Toast.makeText(this, "Submission failed", Toast.LENGTH_SHORT).show()
            } else (
                    Toast.makeText(this, "Username exits Please try again", Toast.LENGTH_SHORT)
                        .show()
                    )

        } else (
                Toast.makeText(this, "Password not matching Please try again", Toast.LENGTH_SHORT)
                    .show()
                )
                    )

            val context = binding.submitButton.context
            val intent = Intent(context, FirstPage::class.java)
            startActivity(intent)
        }

}


