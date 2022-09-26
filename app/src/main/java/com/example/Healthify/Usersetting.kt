package com.example.Healthify

import Healthify.databinding.UsersettingBinding
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.Healthify.databinding.UsersettingBinding

class Usersetting : AppCompatActivity() {
    private lateinit var binding: UsersettingBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        binding = UsersettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.buttonUpdate.setOnClickListener{update()}
        binding.button.setOnClickListener { logout() }
        showinfo()
        binding.backbtn.setOnClickListener{finish()}

    }


    private fun showinfo() {

        binding.settUsername.setText(intent.getStringExtra("username"))
        binding.setUsername.setText(intent.getStringExtra("username"))
        val db = DataBHelper(this)
        val getdata = db.getcontact(intent.getStringExtra("username"))
        binding.setUsercontact.setText(getdata)
        val getagedata = db.getage(intent.getStringExtra("username"))
        binding.settUserage.setText(getagedata)
        val getpassword = db.getpassword(intent.getStringExtra("username"))
        binding.setPassword.setText(getpassword)

    }


    private fun update(){
        val name = binding.settUsername.text.toString()
        val db = DataBHelper(this)
        val usercontact = binding.setUsercontact.text.toString()
        val userage = binding.settUserage.text.toString().toIntOrNull()
        val userpassword = binding.setPassword.text.toString()
        val update = db.updatename(name, usercontact, userage, userpassword)
        if (update == true) {
            Toast.makeText(this, "Updated Successful", Toast.LENGTH_SHORT).show()
        } else
            Toast.makeText(this, "Update Failed", Toast.LENGTH_SHORT).show()

        val context = binding.buttonUpdate.context
        val intent = Intent(context, Home::class.java)
    }







    private fun logout() {

        val context = binding.button.context
        val intent = Intent(context, FirstPage::class.java)
        startActivity(intent)
    }

}