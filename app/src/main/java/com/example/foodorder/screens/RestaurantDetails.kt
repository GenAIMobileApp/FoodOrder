package com.example.foodorder.screens

import FoodFilterChip
import FoodItemAdd
import ItemCounter
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.foodorder.R
import com.example.foodorder.components.FoodItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestaurantDetails(data: RestaurantDetailsData, onBackClick: () -> Unit) {
    var selectedCuisine by remember { mutableStateOf("") }
    var itemCount by remember { mutableStateOf(1) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { /* TODO */ }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "More options")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ItemCounter(
                        count = itemCount,
                        onIncrement = { itemCount++ },
                        onDecrement = { if (itemCount > 1) itemCount-- },
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Button(
                        onClick = { /* TODO: Add to cart */ },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF460A))
                    ) {
                        Text("Add to cart")
                    }
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Cover Image
            item {
                Image(
                    painter = painterResource(id = data.restaurant.imageResId),
                    contentDescription = "Restaurant cover",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Crop
                )
            }

            // Cuisine Filter Chips
            item {
                LazyRow(
                    modifier = Modifier.padding(vertical = 16.dp, horizontal = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(data.restaurant.cuisines) { cuisine ->
                        FoodFilterChip(
                            text = cuisine,
                            selected = selectedCuisine == cuisine,
                            onClick = { selectedCuisine = cuisine }
                        )
                    }
                }
            }

            // Restaurant Details
            item {
                FoodItem(
                    name = data.restaurant.name,
                    distance = data.restaurant.distance,
                    rating = data.restaurant.rating,
                    reviews = data.restaurant.reviews,
                    price = "", // Assuming no price for restaurant
                    isFavorite = false,
                    onFavoriteClick = { /* TODO */ }
                )
            }

            // Description
            item {
                Text(
                    text = data.restaurant.description,
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            item {
                Divider(modifier = Modifier.padding(horizontal = 16.dp))
            }

            // Menu Items Title
            item {
                Text(
                    text = "Menu",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }

            // Menu Items Grid
            items(data.menuItems.chunked(2)) { rowItems ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    rowItems.forEach { item ->
                        FoodItemAdd(
                            imageRes = item.imageRes,
                            title = item.title,
                            description = item.description,
                            rating = item.rating,
                            reviews = item.reviews,
                            price = item.price,
                            distance = item.distance,
                            onFavoriteClick = { /* TODO */ },
                            onAddClick = { /* TODO */ },
                            modifier = Modifier.weight(1f)
                        )
                    }
                    // If there's only one item in the row, add an empty space
                    if (rowItems.size == 1) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RestaurantDetailsPreview() {
    val sampleData = RestaurantDetailsData(
        restaurant = Restaurant(
            imageResId = R.drawable.restaurant_1,
            name = "Delicious Bites",
            distance = "2.2 km away",
            rating = 4.5f,
            reviews = 1234,
            cuisines = listOf("Italian", "Mexican", "American", "Chinese"),
            description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
        ),
        menuItems = listOf(
            FoodItem(R.drawable.food_item_1, "Margherita Pizza", "Classic cheese pizza", 4.7f, 523, "\$12.99", "2.2 km"),
            FoodItem(R.drawable.food_item_2, "Chicken Tacos", "Spicy chicken tacos", 4.5f, 342, "\$9.99", "2.2 km"),
            FoodItem(R.drawable.food_item_3, "Cheeseburger", "Juicy beef patty with cheese", 4.6f, 789, "\$8.99", "2.2 km"),
            FoodItem(R.drawable.food_item_4, "Vegetable Stir Fry", "Assorted veggies in soy sauce", 4.4f, 256, "\$11.99", "2.2 km"),
            FoodItem(R.drawable.burger, "Double Cheeseburger", "Two patties with extra cheese", 4.8f, 678, "\$13.99", "2.2 km"),
            FoodItem(R.drawable.pizza, "Pepperoni Pizza", "Classic pepperoni pizza", 4.6f, 890, "\$14.99", "2.2 km"),
            FoodItem(R.drawable.taco, "Fish Tacos", "Crispy fish tacos with slaw", 4.5f, 432, "\$10.99", "2.2 km"),
            FoodItem(R.drawable.chicken_fingers, "Chicken Fingers", "Crispy chicken tenders", 4.3f, 345, "\$7.99", "2.2 km")
        )
    )

    MaterialTheme {
        RestaurantDetails(data = sampleData, onBackClick = {})
    }
}