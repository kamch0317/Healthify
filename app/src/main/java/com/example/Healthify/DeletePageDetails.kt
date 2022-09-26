package com.example.Healthify

import Healthify.databinding.DeletepagedetailsBinding
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.Healthify.databinding.DeletepagedetailsBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso

class DeletePageDetails : AppCompatActivity() {

    private lateinit var db : FirebaseFirestore
    private lateinit var deletepagedetailsBinding: DeletepagedetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        deletepagedetailsBinding = DeletepagedetailsBinding.inflate(layoutInflater)
        setContentView(deletepagedetailsBinding.root)

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
        val docid = bundle.getString("id")

        with(deletepagedetailsBinding){
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

        deletepagedetailsBinding.backbtn.setOnClickListener {
            finish()
        }

        deletepagedetailsBinding.deletebtn.setOnClickListener {
            var builder = AlertDialog.Builder(this)
            builder.setTitle("Confirm Delete")
            builder.setMessage("Are you sure you want to delete this item?")
            builder.setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, id ->
                deletePlan(docid.toString())
                dialog.cancel()
            })
            builder.setNegativeButton("No", DialogInterface.OnClickListener { dialog, id ->
                dialog.cancel()
            })
            var alert = builder.create()
            alert.show()

        }
    }

    private fun deletePlan(id : String){

        db = FirebaseFirestore.getInstance()
        db.collection("foodPlan").document(id)
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