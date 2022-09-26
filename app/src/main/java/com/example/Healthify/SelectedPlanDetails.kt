package com.example.Healthify

import Healthify.databinding.SelectedplandetailsBinding
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.Healthify.databinding.SelectedplandetailsBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.newfoodplan.*

class SelectedPlanDetails : AppCompatActivity() {

    private lateinit var selectedplandetailsBinding: SelectedplandetailsBinding
    private lateinit var db : FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        selectedplandetailsBinding = SelectedplandetailsBinding.inflate(layoutInflater)
        setContentView(selectedplandetailsBinding.root)

        supportActionBar?.hide()

        val bundle : Bundle? = intent.extras
        val image = bundle!!.getString("image")
        val detailsTitle = bundle.getString("detailsTitle")
        val breakfastName = bundle.getString("breakfastName")
        val breakfastCal = bundle.getString("breakfastCal")
        val breakfastDesc = bundle.getString("breakfastDesc")
        val lunchName = bundle.getString("lunchName")
        val lunchCal = bundle.getString("lunchCal")
        val lunchDesc = bundle.getString("lunchDesc")
        val dinnerName = bundle.getString("dinnerName")
        val dinnerCal = bundle.getString("dinnerCal")
        val dinnerDesc = bundle.getString("dinnerDesc")
        val id = bundle.getString("id")

        with(selectedplandetailsBinding){
            detailstitle.text = detailsTitle
            Picasso.get().load(image).into(detailsimage)
            breakfastname.text = breakfastName
            breakfastcal.text = breakfastCal
            breakfastdesc.text = breakfastDesc
            lunchname.text = lunchName
            lunchcal.text = lunchCal
            lunchdesc.text = lunchDesc
            dinnername.text = dinnerName
            dinnercal.text = dinnerCal
            dinnerdesc.text = dinnerDesc
        }

        selectedplandetailsBinding.backbtn.setOnClickListener {
            finish()
        }

        selectedplandetailsBinding.donebtn.setOnClickListener {
            donePlan(id.toString())
        }
    }

    private fun donePlan(id : String){

        db = FirebaseFirestore.getInstance()
        db.collection("selected").document(id)
            .delete()
            .addOnSuccessListener {
                Log.d("Firestore Delete", "Success")
                Toast.makeText(this, "Food Plan Done Successfully!", Toast.LENGTH_LONG).show()
                finish()

            }
            .addOnFailureListener {
                Log.w("Firestore Delete", "Failed")
            }


    }
}