package com.example.Healthify

import Healthify.databinding.DeletepageBinding
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.Healthify.databinding.DeletepageBinding
import com.google.firebase.firestore.*

class DeletePage : AppCompatActivity() {

    private lateinit var deletepageBinding : DeletepageBinding
    private lateinit var Foodplanrvadapter: Foodplanrvadapter
    private var foodList = ArrayList<FoodData>()
    private  lateinit var db : FirebaseFirestore
    private var userNameGet : String? = null
    private var userName : String = " "

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        deletepageBinding = DeletepageBinding.inflate(layoutInflater)
        setContentView(deletepageBinding.root)

        supportActionBar?.hide()

        val bundle : Bundle? = intent.extras
        userNameGet = bundle!!.getString("userName")
        userName = userNameGet.toString()

        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        deletepageBinding.foodplanrv.layoutManager = layoutManager
        Foodplanrvadapter = Foodplanrvadapter(this,foodList, object : Foodplanrvadapter.onFoodItemClickListener {
            override fun onItemClick(position: Int) {
                enterPlanDetails(position)
            }
        })
        deletepageBinding.foodplanrv.adapter = Foodplanrvadapter

        deletepageBinding.backbtn.setOnClickListener {
            finish()
        }


    }

    override fun onResume() {
        super.onResume()

        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        deletepageBinding.foodplanrv.layoutManager = layoutManager
        Foodplanrvadapter = Foodplanrvadapter(this, foodList, object : Foodplanrvadapter.onFoodItemClickListener {
            override fun onItemClick(position: Int) {
                enterPlanDetails(position)
            }
        })
        deletepageBinding.foodplanrv.adapter = Foodplanrvadapter

        loadFoodList()
    }

    private fun enterPlanDetails(position : Int){
        val intent = Intent(this, DeletePageDetails::class.java)
        intent.putExtra("detailsTitle", foodList[position].setName)
        intent.putExtra("detailsDesc", foodList[position].setDesc)
        intent.putExtra("image", foodList[position].image)
        intent.putExtra("breakfastName", foodList[position].breakfastName)
        intent.putExtra("breakfastCal", foodList[position].breakfastCal)
        intent.putExtra("breakfastDesc", foodList[position].breakfastDesc)
        intent.putExtra("lunchName", foodList[position].lunchName)
        intent.putExtra("lunchCal", foodList[position].lunchCal)
        intent.putExtra("lunchDesc", foodList[position].lunchDesc)
        intent.putExtra("dinnerName", foodList[position].dinnerName)
        intent.putExtra("dinnerCal", foodList[position].dinnerCal)
        intent.putExtra("dinnerDesc", foodList[position].dinnerDesc)
        intent.putExtra("id", foodList[position].id)
        startActivity(intent)
    }

    private fun loadFoodList(){

        foodList.clear()
        db = FirebaseFirestore.getInstance()
        db.collection("foodPlan").whereEqualTo("username", userName).addSnapshotListener(object : EventListener<QuerySnapshot> {
            override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                if (error!=null){
                    Log.e("Firestore error", error.message.toString())
                    return
                }

                for (dc : DocumentChange in value?.documentChanges!!){
                    if(dc.type == DocumentChange.Type.ADDED){
                        val food = dc.document.toObject(FoodData::class.java)
                        foodList.add(food)
                    }
                }
                Foodplanrvadapter.notifyDataSetChanged()

            }

        })
    }

}