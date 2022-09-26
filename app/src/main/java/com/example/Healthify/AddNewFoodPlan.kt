package com.example.Healthify

import Healthify.databinding.NewfoodplanBinding
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.Healthify.databinding.NewfoodplanBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class AddNewFoodPlan : AppCompatActivity() {

    private lateinit var  binding: NewfoodplanBinding
    private lateinit var selectedPhoto : Uri
    private var photoUrl : String = " "
    private lateinit var db : FirebaseFirestore
    private var userNameGet : String? = null
    private var userName : String = " "

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = NewfoodplanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val loadimage = registerForActivityResult(ActivityResultContracts.GetContent(),
            ActivityResultCallback{
                if (it != null) {
                    selectedPhoto = it
                }
                binding.uploadedImage.setImageURI(it)
            })

        supportActionBar?.hide()

        val bundle : Bundle? = intent.extras
        userNameGet = bundle!!.getString("userName")
        userName = userNameGet.toString()

        binding.uploadimgBtn.setOnClickListener {
            loadimage.launch("image/*")

            binding.uploadimgBtn.alpha = 0f

        }

        binding.addBtn.setOnClickListener {
            var photoUri = selectedPhoto
            uploadImageToFirebaseStorage(photoUri)
        }

        binding.backbtn.setOnClickListener {
            finish()
        }


    }

    private fun addDatatoFirebase(photoUri: String){
        val setName = binding.setName.text.toString()
        val setDesc = binding.breakfastName.text.toString()
        val breakfastName = binding.breakfastName.text.toString()
        val breakfastCal = binding.breakfastCal.text.toString()
        val breakfastDesc = binding.breakfastDesc.text.toString()
        val lunchName = binding.lunchName.text.toString()
        val lunchCal = binding.lunchCal.text.toString()
        val lunchDesc = binding.lunchDesc.text.toString()
        val dinnerName = binding.dinnerName.text.toString()
        val dinnerCal = binding.dinnerCal.text.toString()
        val dinnerDesc = binding.dinnerDesc.text.toString()


        if (setName.isNullOrEmpty() || setName == " " || setDesc.isNullOrEmpty() || setDesc == " " || breakfastName.isNullOrEmpty() || breakfastName == " "
            || breakfastCal.isNullOrEmpty() || breakfastCal == " " || breakfastDesc.isNullOrEmpty() || breakfastDesc == " "
            || lunchName.isNullOrEmpty() || lunchName == " " || lunchCal.isNullOrEmpty() || lunchCal == " "|| lunchDesc.isNullOrEmpty() || lunchDesc == " "
            || dinnerName.isNullOrEmpty() || dinnerName == " " || dinnerCal.isNullOrEmpty() || dinnerCal == " " || dinnerDesc.isNullOrEmpty() || dinnerDesc == " "){

            Toast.makeText(this, "Please make sure all the field is filled!", Toast.LENGTH_LONG)
                .show()
            return
        }

        db = FirebaseFirestore.getInstance()
        val newFoodPlan : MutableMap<String, Any> = HashMap()
        newFoodPlan["image"] = photoUri
        newFoodPlan["setName"] = setName
        newFoodPlan["setDesc"] = setDesc
        newFoodPlan["breakfastName"] = breakfastName
        newFoodPlan["breakfastCal"] = breakfastCal
        newFoodPlan["breakfastDesc"] = breakfastDesc
        newFoodPlan["lunchName"] = lunchName
        newFoodPlan["lunchCal"] = lunchCal
        newFoodPlan["lunchDesc"] = lunchDesc
        newFoodPlan["dinnerName"] = dinnerName
        newFoodPlan["dinnerCal"] = dinnerCal
        newFoodPlan["dinnerDesc"] = dinnerDesc
        newFoodPlan["username"] = userName

        db.collection("foodPlan").add(newFoodPlan)
            .addOnSuccessListener {
                Toast.makeText(this, "You Added The Meal Plan Successfully!", Toast.LENGTH_LONG).show()
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Firestore data write error", Toast.LENGTH_LONG).show()
            }

    }

    private fun uploadImageToFirebaseStorage(photoUri : Uri){

        if (photoUri == null) {
            Toast.makeText(this, "Photo is not uploaded! Please upload photo!", Toast.LENGTH_LONG)
                .show()
            return
        }
        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().reference.child("images/$filename")

        ref.putFile(photoUri)
            .addOnSuccessListener {
                ref.downloadUrl.addOnSuccessListener {
                    addDatatoFirebase(it.toString())
                }
                Log.d("Firebase Storage", "Successfully Uploaded Image")
            }
    }

}