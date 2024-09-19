package com.example.foodorder.screens

import FilterChip
import FoodItemAdd
import ItemCounter
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text

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


// Sample data
val sampleRestaurant = Restaurant(
    imageResId = R.drawable.restaurant_1,
    name = "Egg Benedict with Capsicum",
    distance = "2.2 miles away",
    rating = 4.9f,
    reviews = 1100,
    cuisines = listOf("American", "Breakfast", "Brunch"),
    description = "Indulge in our signature Egg Benedict, perfectly poached eggs atop a toasted English muffin, layered with Canadian bacon and drizzled with creamy hollandaise sauce. A sprinkle of fresh capsicum adds a delightful crunch and flavor to this classic breakfast dish."
)

@Composable
fun RestaurantDetailsScreen(restaurant: Restaurant, foodItems: List<Food>) {
    var quantity by remember { mutableStateOf(1) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Cover Image
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                Image(
                    painter = painterResource(id = restaurant.imageResId),
                    contentDescription = "Restaurant cover image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                IconButton(
                    onClick = { /* Handle back navigation */ },
                    modifier = Modifier.align(Alignment.TopStart).padding(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            }

            // Cuisine Chips
            LazyRow(
                modifier = Modifier.padding(vertical = 8.dp),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                items(restaurant.cuisines) { cuisine ->

                    FilterChip(
                        text = cuisine,
                        selected = true,
                        onClick = { /* Handle click */ }
                    )
                }
            }

            // Restaurant Details
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = restaurant.name,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "${restaurant.rating}",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = " (${restaurant.reviews} reviews)",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = restaurant.distance,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = restaurant.description,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Divider()

            // Food Items Grid
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(2.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.height(300.dp)
            ) {
                items(foodItems.size) { index ->
                    val foodItem = foodItems[index]
                    FoodItemAdd(
                        title = foodItem.name,
                        description = foodItem.description,
                        rating = foodItem.rating,
                        reviews = foodItem.reviews,
                        price = foodItem.price,
                        distance = foodItem.distance,
                        onAddClick = { /* Handle add click */ },
                        onFavoriteClick = {},
                        imageRes = foodItem.imageRes
                    )
                }
            }

            Spacer(modifier = Modifier.height(80.dp)) // Space for the footer
        }

        // Fixed Footer
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                var count by remember { mutableIntStateOf(0) }
                ItemCounter(
                    count = count,
                    onIncrement = { count++ },
                    onDecrement = { if (count > 0) count-- },
                    modifier = Modifier.padding(start = 16.dp)
                )
                Button(
                    onClick = { /* Handle add to cart */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA500))
                ) {
                    Text("Add to Cart")
                }
            }
        }
    }
}

@Composable
fun FoodItem(
    name: String,
    distance: String,
    rating: Float,
    reviews: Int,
    price: String,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(text = name, style = MaterialTheme.typography.titleMedium)
            Text(text = price, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "$rating",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = " ($reviews reviews)",
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = onFavoriteClick) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Favorite"
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun RestaurantDetailsScreenPreview() {
    MaterialTheme {
        RestaurantDetailsScreen(sampleRestaurant, sampleFoodItems)
    }
}