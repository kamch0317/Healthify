package com.example.Healthify

import Healthify.databinding.SelectedpageBinding
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.Healthify.databinding.SelectedpageBinding
import com.google.firebase.firestore.*
import kotlinx.android.synthetic.main.selectedpage.*

class SelectedPage : AppCompatActivity() {

    private lateinit var selectedPage: SelectedpageBinding
    private lateinit var Foodplanrvadapter: Foodplanrvadapter
    private var foodList = ArrayList<FoodData>()
    private  lateinit var db : FirebaseFirestore
    private var userName=" "

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        selectedPage = SelectedpageBinding.inflate(layoutInflater)
        setContentView(selectedPage.root)

        supportActionBar?.hide()
        val bundle : Bundle? = intent.extras
        val userNameGet = bundle!!.getString("username")
        userName = userNameGet.toString()
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        selectedPage.foodplanrv.layoutManager = layoutManager
        Foodplanrvadapter = Foodplanrvadapter(this, foodList, object : Foodplanrvadapter.onFoodItemClickListener {
            override fun onItemClick(position: Int) {
                enterPlanDetails(position)
            }
        })
        selectedPage.foodplanrv.adapter = Foodplanrvadapter

        selectedPage.addBtn.setOnClickListener {
            val intent = Intent(this, FoodPlanSelectnigList::class.java)
            intent.putExtra("userName", userName)
            startActivity(intent)
        }

        selectedPage.backbtn.setOnClickListener {
            finish()
        }

        bottom_nav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    home()

                }

                R.id.tools -> {
                    tools()


                }

                R.id.workout -> {
                    workout()

                }

                R.id.foodplan -> {
                    foodplan()

                }

                R.id.usernav -> {
                    usersetting()

                }


            }
            true
        }

    }

    override fun onResume() {
        super.onResume()

        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        selectedPage.foodplanrv.layoutManager = layoutManager
        Foodplanrvadapter = Foodplanrvadapter(this, foodList, object : Foodplanrvadapter.onFoodItemClickListener {
            override fun onItemClick(position: Int) {
                enterPlanDetails(position)
                foodList.clear()
            }
        })
        selectedPage.foodplanrv.adapter = Foodplanrvadapter

        loadSelectedList(userName)
    }

    private fun enterPlanDetails(position : Int){
        val intent = Intent(this,SelectedPlanDetails::class.java)
        intent.putExtra("detailsTitle", foodList[position].setName)
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

    private fun loadSelectedList(username : String){

        foodList.clear()
        db = FirebaseFirestore.getInstance()
        db.collection("selected").whereEqualTo("username", userName).addSnapshotListener(object : EventListener<QuerySnapshot> {
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

    private fun usersetting() {
        val name = intent.getStringExtra("username")
        val context = selectedPage.bottomNav.context
        val intent = Intent(context, Usersetting::class.java)
        intent.putExtra("username", name);
        startActivity(intent)

    }


    private fun home() {
        val name = intent.getStringExtra("username")
        val context = selectedPage.bottomNav.context
        val intent = Intent(context, Home::class.java)
        intent.putExtra("username", name);
        startActivity(intent)

    }

    private fun tools() {
        val name = intent.getStringExtra("username")
        val context = selectedPage.bottomNav.context
        val intent = Intent(context, BMICalculator::class.java)
        intent.putExtra("username", name);
        startActivity(intent)
    }


    private fun workout() {
        val name = intent.getStringExtra("username")
        val context = selectedPage.bottomNav.context
        val intent = Intent(context, WorkoutMain::class.java)
        intent.putExtra("username", name);
        startActivity(intent)
    }

    private fun foodplan() {
        val name = intent.getStringExtra("username")
        val context = selectedPage.bottomNav.context
        val intent = Intent(context, SelectedPage::class.java)
        intent.putExtra("username", name);
        startActivity(intent)

    }
}

