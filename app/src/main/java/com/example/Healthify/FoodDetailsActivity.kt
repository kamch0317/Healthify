package com.example.Healthify

import Healthify.databinding.FoodplandetailsBinding
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.Healthify.databinding.FoodplandetailsBinding
import com.google.firebase.firestore.*
import com.squareup.picasso.Picasso

class FoodDetailsActivity : AppCompatActivity() {

    private lateinit var foodplandetailsBinding: FoodplandetailsBinding
    private lateinit var db : FirebaseFirestore
    private var selectedList = ArrayList<SelectedData>()
    private var valid = 0
    private var image : String? = null
    private var detailsTitle : String = " "
    private var detailsDesc : String = " "
    private var breakfastName : String = " "
    private var breakfastCal : String = " "
    private var breakfastDesc : String = " "
    private var lunchName : String = " "
    private var lunchCal : String = " "
    private var lunchDesc : String = " "
    private var dinnerName : String = " "
    private var dinnerCal : String = " "
    private var dinnerDesc : String = " "
    private var userNameGet : String? = null
    private var userName : String = " "
    private var newImage : String = " "



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        foodplandetailsBinding = FoodplandetailsBinding.inflate(layoutInflater)
        setContentView(foodplandetailsBinding.root)

        supportActionBar?.hide()

        val bundle : Bundle? = intent.extras
        image = bundle!!.getString("image")
        newImage = image.toString()
        detailsTitle = bundle.getString("detailsTitle").toString()
        detailsDesc = bundle.getString("detailsTitle").toString()
        breakfastName = bundle.getString("breakfastName").toString()
        breakfastCal = bundle.getString("breakfastCal").toString()
        breakfastDesc = bundle.getString("breakfastDesc").toString()
        lunchName = bundle.getString("lunchName").toString()
        lunchCal = bundle.getString("lunchCal").toString()
        lunchDesc = bundle.getString("lunchDesc").toString()
        dinnerName = bundle.getString("dinnerName").toString()
        dinnerCal = bundle.getString("dinnerCal").toString()
        dinnerDesc = bundle.getString("dinnerDesc").toString()
        userNameGet = bundle!!.getString("userName")
        userName = userNameGet.toString()

        with(foodplandetailsBinding){
            detailstitle.text = detailsTitle
            Picasso.get().load(image).into(detailsimage)
            //Glide.with(foodplandetailsBinding.root).load(image).into(detailsimage)
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

        foodplandetailsBinding.selectbtn.setOnClickListener {
            checkSelected(detailsTitle)
        }

        foodplandetailsBinding.backbtn.setOnClickListener {
            finish()
        }

    }

    private fun selectFoodPlan(){

        db = FirebaseFirestore.getInstance()
        val selected : MutableMap<String, Any> = HashMap()
        selected["setName"] = detailsTitle
        selected["setDesc"] = detailsDesc
        selected["image"] = newImage
        selected["breakfastName"] = breakfastName
        selected["breakfastCal"] = breakfastCal
        selected["breakfastDesc"] = breakfastDesc
        selected["lunchName"] = lunchName
        selected["lunchCal"] = lunchCal
        selected["lunchDesc"] = lunchDesc
        selected["dinnerName"] = dinnerName
        selected["dinnerCal"] = dinnerCal
        selected["dinnerDesc"] = dinnerDesc
        selected["username"] = userName

        db.collection("selected").add(selected)
            .addOnSuccessListener {
                Toast.makeText(this, "You Selected The Meal Plan Successfully!", Toast.LENGTH_LONG).show()
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Firestore data write error", Toast.LENGTH_LONG).show()
            }

    }

    private fun checkSelected(setName : String) {


        db = FirebaseFirestore.getInstance()
        db.collection("selected").whereEqualTo("username",userName).addSnapshotListener(object : EventListener<QuerySnapshot> {
            override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                if (error != null) {
                    Log.e("Firestore error", error.message.toString())
                    return
                }

                for (dc: DocumentChange in value?.documentChanges!!) {
                    if (dc.type == DocumentChange.Type.ADDED) {
                        val select = dc.document.toObject(SelectedData::class.java)
                        selectedList.add(select)
                    }
                }

            }

        })

        for (data in selectedList) {
            if (data.setName == setName) {
                valid = 1
            } else {
                valid = 0
            }
        }
        if (valid == 1) {
            Toast.makeText(
                this,
                "You Had Selected This Meal Plan Before! Please Select Another Meal Plan! ",
                Toast.LENGTH_LONG
            ).show()
            return
        }else{
            selectFoodPlan()
        }
    }


}