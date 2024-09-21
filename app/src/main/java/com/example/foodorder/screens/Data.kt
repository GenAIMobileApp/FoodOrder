package com.example.foodorder.screens

data class FoodCategory(
    val imageResId: Int,
    val name: String
)

data class Restaurant(
    val imageResId: Int,
    val name: String,
    val distance: String,
    val rating: Float,
    val reviews: Int,
    val cuisines: List<String>,
    val description: String
)

data class FoodItem(
    val imageRes: Int,
    val title: String,
    val description: String,
    val rating: Float,
    val reviews: Int,
    val price: String,
    val distance: String
)

data class HomeScreenData(
    val foodCategories: List<FoodCategory>,
    val nearbyRestaurants: List<Restaurant>,
    val recommendedFoodItems: List<FoodItem>
)

data class RestaurantDetailsData(
    val restaurant: Restaurant,
    val menuItems: List<FoodItem>
)

// Assuming Restaurant and FoodItem classes are already defined from screen 1