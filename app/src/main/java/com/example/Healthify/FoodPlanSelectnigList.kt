package com.example.Healthify

import Healthify.databinding.FoodplanmainBinding
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.Healthify.databinding.FoodplanmainBinding
import com.google.firebase.firestore.*
import com.google.firebase.firestore.EventListener
import kotlinx.android.synthetic.main.foodplanmain.*
import java.util.*
import kotlin.collections.ArrayList

class FoodPlanSelectnigList : AppCompatActivity() {

    private lateinit var foodplanmainBinding: FoodplanmainBinding
    private lateinit var Foodplanrvadapter: Foodplanrvadapter
    private var foodList2 = ArrayList<FoodData>()
    private  lateinit var db : FirebaseFirestore
    private var userNameGet : String? = null
    private var userName : String = " "

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        foodList2.clear()
        foodplanmainBinding = FoodplanmainBinding.inflate(layoutInflater)
        setContentView(foodplanmainBinding.root)

        supportActionBar?.hide()

        val bundle : Bundle? = intent.extras
        userNameGet = bundle!!.getString("userName")
        userName = userNameGet.toString()

        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        foodplanmainBinding.foodplanrv.layoutManager = layoutManager
        Foodplanrvadapter = Foodplanrvadapter(this, foodList2, object : Foodplanrvadapter.onFoodItemClickListener {
            override fun onItemClick(position: Int) {
                enterPlanDetails(position)
            }
        })
        foodplanmainBinding.foodplanrv.adapter = Foodplanrvadapter

        foodplanmainBinding.addNewFoodPlan.setOnClickListener {
            addNewFoodPlan()
        }

        foodplanmainBinding.backbtn.setOnClickListener {
            finish()
        }

        foodplanmainBinding.deletebtn.setOnClickListener {
            deletePlan()
        }


    }

    override fun onResume() {
        super.onResume()

        foodList2.clear()

        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        foodplanmainBinding.foodplanrv.layoutManager = layoutManager
        Foodplanrvadapter = Foodplanrvadapter(this, foodList2, object : Foodplanrvadapter.onFoodItemClickListener {
            override fun onItemClick(position: Int) {
                enterPlanDetails(position)
            }
        })
        foodplanmainBinding.foodplanrv.adapter = Foodplanrvadapter

        loadFoodList2()
    }


    private fun deletePlan(){
        val intent = Intent(this@FoodPlanSelectnigList, DeletePage::class.java)
        intent.putExtra("userName", userName)
        startActivity(intent)

    }

    private fun enterPlanDetails(position : Int){
        val intent = Intent(this@FoodPlanSelectnigList, FoodDetailsActivity::class.java)
        intent.putExtra("detailsTitle", foodList2[position].setName)
        intent.putExtra("detailsDesc", foodList2[position].setDesc)
        intent.putExtra("image", foodList2[position].image)
        intent.putExtra("breakfastName", foodList2[position].breakfastName)
        intent.putExtra("breakfastCal", foodList2[position].breakfastCal)
        intent.putExtra("breakfastDesc", foodList2[position].breakfastDesc)
        intent.putExtra("lunchName", foodList2[position].lunchName)
        intent.putExtra("lunchCal", foodList2[position].lunchCal)
        intent.putExtra("lunchDesc", foodList2[position].lunchDesc)
        intent.putExtra("dinnerName", foodList2[position].dinnerName)
        intent.putExtra("dinnerCal", foodList2[position].dinnerCal)
        intent.putExtra("dinnerDesc", foodList2[position].dinnerDesc)
        intent.putExtra("userName", userName)
        startActivity(intent)
    }

    private fun addNewFoodPlan(){
        val intent = Intent(this@FoodPlanSelectnigList, AddNewFoodPlan::class.java)
        intent.putExtra("userName", userName)
        startActivity(intent)
    }

    private fun loadFoodList2(){

        foodList2.clear()
        db = FirebaseFirestore.getInstance()
        db.collection("foodPlan").addSnapshotListener(object : EventListener<QuerySnapshot>{
            override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                if (error!=null){
                    Log.e("Firestore error", error.message.toString())
                    return
                }

                for (dc : DocumentChange in value?.documentChanges!!){
                    if(dc.type == DocumentChange.Type.ADDED){
                        val food = dc.document.toObject(FoodData::class.java)
                        foodList2.add(food)
                    }
                }
                Foodplanrvadapter.notifyDataSetChanged()

            }

        })

    }


}




