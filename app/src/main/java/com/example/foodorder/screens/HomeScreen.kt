package com.example.foodorder.screens

import NearbyRestaurantCard
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.example.foodorder.R
import com.example.foodorder.components.FoodComponent
import com.example.foodorder.components.FoodItem

// Data classes
data class FoodType(val imageResId: Int, val name: String)
data class Restaurant(val imageResId: Int, val name: String, val distance: String, val rating: Float, val reviews: Int)
data class Food(val name: String, val distance: String, val rating: Float, val reviews: Int, val price: String, val isFavorite: Boolean = false)

// Sample data
val sampleFoodTypes = listOf(
    FoodType(R.drawable.burger, "Burger"),
    FoodType(R.drawable.pizza, "Pizza"),
    FoodType(R.drawable.taco, "Mexican"),
    FoodType(R.drawable.chicken_fingers, "Chicken"),
    FoodType(R.drawable.burger, "Indian"),
    FoodType(R.drawable.pizza, "Italian"),
    FoodType(R.drawable.taco, "Japanese")
)

val sampleRestaurants = listOf(
    Restaurant(R.drawable.restaurant_1, "Egg Benedict with Capsicum", "2.2 away from you", 4.9f, 1100),
    Restaurant(R.drawable.restaurant_2, "Kashmiri Biryani and Ka", "2.2 away from you", 4.9f, 1100),
    Restaurant(R.drawable.restaurant_1, "Spicy Thai", "1.5 away from you", 4.7f, 950),
    Restaurant(R.drawable.restaurant_2, "Italian Delight", "3.0 away from you", 4.8f, 1200),
    Restaurant(R.drawable.restaurant_1, "Burger Palace", "2.8 away from you", 4.6f, 800)
)

val sampleFoodItems = listOf(
    Food("Naked Jackfruit Burrito Bowl", "2.2 away from you", 4.9f, 1100, "\$20.00"),
    Food("NY Chicken Roll - Large", "2.2 away from you", 4.9f, 1100, "\$20.00"),
    Food("Kochchi Prawn Spaghetti", "2.2 away from you", 4.9f, 1100, "\$20.00"),
    Food("Double Chicken & Cheese Fiesta - Pizza", "2.2 away from you", 4.9f, 1100, "\$20.00"),
    Food("Veggie Supreme", "2.5 away from you", 4.7f, 950, "\$18.00"),
    Food("Spicy Tofu Stir Fry", "3.0 away from you", 4.8f, 1000, "\$15.00"),
    Food("Classic Margherita", "1.8 away from you", 4.6f, 800, "\$16.00"),
    Food("Grilled Salmon Salad", "2.7 away from you", 4.8f, 1050, "\$22.00")
)

@Composable
fun HomeScreen(
    foodTypes: List<FoodType>,
    restaurants: List<Restaurant>,
    foodItems: List<Food>
) {
    var searchQuery by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        AddressPicker(
            address = "1901 Thornridge Cir. Shiloh...",
            onAddressClick = { /* Handle address click */ }
        )

        SearchBar(
            query = searchQuery,
            onQueryChange = { searchQuery = it },
            onSearch = { /* Handle search */ },
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        LazyRow(
            modifier = Modifier.padding(vertical = 8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(foodTypes) { foodType ->
                FoodComponent(imageResId = foodType.imageResId, name = foodType.name)
                Spacer(modifier = Modifier.width(8.dp))
            }
        }

        Text(
            "Nearby hotels",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
        LazyRow(
            modifier = Modifier.padding(bottom = 8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(restaurants) { restaurant ->
                NearbyRestaurantCard(
                    imageResId = restaurant.imageResId,
                    name = restaurant.name,
                    distance = restaurant.distance,
                    rating = restaurant.rating,
                    reviews = restaurant.reviews
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
        }

        Text(
            "Recommended for you",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.height(400.dp) // Adjust height as needed
        ) {
            items(foodItems) { foodItem ->
                FoodItem(
                    name = foodItem.name,
                    distance = foodItem.distance,
                    rating = foodItem.rating,
                    reviews = foodItem.reviews,
                    price = foodItem.price,
                    isFavorite = foodItem.isFavorite,
                    onFavoriteClick = { /* Handle favorite click */ }
                )
            }
        }
    }
}

@Composable
fun AddressPicker(address: String, onAddressClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = address,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.weight(1f)
        )
        IconButton(onClick = onAddressClick) {
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Change address"
            )
        }
    }
}

@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = query,
        onValueChange = onQueryChange,
        placeholder = { Text("Food, groceries, drinks, etc.") },
        modifier = modifier.fillMaxWidth(),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search"
            )
        },
        trailingIcon = {
            IconButton(onClick = onSearch) {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = "Search"
                )
            }
        },
        singleLine = true
    )
}

val sampleRestaurantsEn = listOf(
    Restaurant(R.drawable.restaurant_1, "Egg Benedict with Capsicum", "2.2 miles away", 4.9f, 1100),
    Restaurant(R.drawable.restaurant_2, "Kashmiri Biryani and Ka", "2.2 miles away", 4.9f, 1100),
    Restaurant(R.drawable.restaurant_1, "Spicy Thai", "1.5 miles away", 4.7f, 950)
)

val sampleFoodItemsEn = listOf(
    Food("Naked Jackfruit Burrito Bowl", "2.2 miles away", 4.9f, 1100, "\$20.00"),
    Food("NY Chicken Roll - Large", "2.2 miles away", 4.9f, 1100, "\$20.00"),
    Food("Kochchi Prawn Spaghetti", "2.2 miles away", 4.9f, 1100, "\$20.00"),
    Food("Double Chicken & Cheese Fiesta - Pizza", "2.2 miles away", 4.9f, 1100, "\$20.00")
)

// Sample data in Spanish
val sampleFoodTypesEs = listOf(
    FoodType(R.drawable.burger, "Hamburguesa"),
    FoodType(R.drawable.pizza, "Pizza"),
    FoodType(R.drawable.taco, "Mexicano"),
    FoodType(R.drawable.chicken_fingers, "Pollo"),
    FoodType(R.drawable.burger, "Indio")
)

val sampleRestaurantsEs = listOf(
    Restaurant(R.drawable.restaurant_1, "Huevos Benedictinos con Pimiento", "3,5 km de distancia", 4.9f, 1100),
    Restaurant(R.drawable.restaurant_2, "Biryani de Cachemira y Ka", "3,5 km de distancia", 4.9f, 1100),
    Restaurant(R.drawable.restaurant_1, "Tailandés Picante", "2,4 km de distancia", 4.7f, 950)
)

val sampleFoodItemsEs = listOf(
    Food("Burrito de Jaca Desnuda", "3,5 km de distancia", 4.9f, 1100, "18,00 €"),
    Food("Rollo de Pollo NY - Grande", "3,5 km de distancia", 4.9f, 1100, "18,00 €"),
    Food("Espaguetis de Gambas Kochchi", "3,5 km de distancia", 4.9f, 1100, "18,00 €"),
    Food("Fiesta de Pollo y Queso Doble - Pizza", "3,5 km de distancia", 4.9f, 1100, "18,00 €")
)

// Sample data in Arabic
val sampleFoodTypesAr = listOf(
    FoodType(R.drawable.burger, "برغر"),
    FoodType(R.drawable.pizza, "بيتزا"),
    FoodType(R.drawable.taco, "مكسيكي"),
    FoodType(R.drawable.chicken_fingers, "دجاج"),
    FoodType(R.drawable.burger, "هندي")
)

val sampleRestaurantsAr = listOf(
    Restaurant(R.drawable.restaurant_1, "بيض بينديكت مع الفلفل", "على بعد 3.5 كم", 4.9f, 1100),
    Restaurant(R.drawable.restaurant_2, "برياني كشميري و كا", "على بعد 3.5 كم", 4.9f, 1100),
    Restaurant(R.drawable.restaurant_1, "تايلندي حار", "على بعد 2.4 كم", 4.7f, 950)
)

val sampleFoodItemsAr = listOf(
    Food("بوريتو جاك فروت المكشوف", "على بعد 3.5 كم", 4.9f, 1100, "75.00 ريال"),
    Food("لفة دجاج نيويورك - كبيرة", "على بعد 3.5 كم", 4.9f, 1100, "75.00 ريال"),
    Food("سباغيتي الروبيان كوتشي", "على بعد 3.5 كم", 4.9f, 1100, "75.00 ريال"),
    Food("بيتزا - مهرجان الدجاج والجبن المزدوج", "على بعد 3.5 كم", 4.9f, 1100, "75.00 ريال")
)

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun HomeScreenPreview() {
    MaterialTheme {
        HomeScreen(
            foodTypes = sampleFoodTypes,
            restaurants = sampleRestaurants,
            foodItems = sampleFoodItems
        )
    }
}
@Preview(name = "English", showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun HomeScreenPreviewEnglish() {
    MaterialTheme {
        HomeScreen(
            foodTypes = sampleFoodTypes,
            restaurants = sampleRestaurantsEn,
            foodItems = sampleFoodItemsEn
        )
    }
}

@Preview(name = "Spanish", showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun HomeScreenPreviewSpanish() {
    MaterialTheme {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
            HomeScreen(
                foodTypes = sampleFoodTypesEs,
                restaurants = sampleRestaurantsEs,
                foodItems = sampleFoodItemsEs
            )
        }
    }
}

@Preview(name = "Arabic", showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun HomeScreenPreviewArabic() {
    MaterialTheme {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            HomeScreen(
                foodTypes = sampleFoodTypesAr,
                restaurants = sampleRestaurantsAr,
                foodItems = sampleFoodItemsAr
            )
        }
    }
}