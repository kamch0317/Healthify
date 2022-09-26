package com.example.Healthify

import com.google.firebase.firestore.DocumentId

data class FoodData(var image : String? = null,
                    var setName : String? = null,
                    var setDesc : String? = null,
                    var breakfastName : String? = null,
                    var breakfastCal : String? = null,
                    var breakfastDesc : String? = null,
                    var lunchName : String? = null,
                    var lunchCal : String? = null,
                    var lunchDesc : String? = null,
                    var dinnerName : String? = null,
                    var dinnerCal : String? = null,
                    var dinnerDesc : String? = null,
                    var userName : String? = null,
                    @DocumentId
                    var id : String? = null)
