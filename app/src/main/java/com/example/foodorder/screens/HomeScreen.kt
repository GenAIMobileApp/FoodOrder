package com.example.foodorder.screens

import FoodItemAdd
import NearbyRestaurantCard
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.foodorder.R
import com.example.foodorder.components.FoodChipComponent

@Composable
fun HomeScreen(data: HomeScreenData) {
    var searchQuery by remember { mutableStateOf("") }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        // Address Picker
        item {
            AddressPicker(
                address = "1901 Thornridge Cir. Shiloh...",
                onAddressClick = { /* Handle address click */ }
            )
        }

        // Search Input
        item {
            SearchBar(
                query = searchQuery,
                onQueryChange = { searchQuery = it },
                onSearch = { /* Handle search */ },
                modifier = Modifier.padding(16.dp)
            )
        }

        // Food Categories
        item {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(data.foodCategories) { category ->
                    FoodChipComponent(
                        imageResId = category.imageResId,
                        name = category.name
                    )
                }
            }
        }

        // Nearby Hotels
        item {
            Text(
                "Nearby hotels",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(16.dp)
            )
        }

        item {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(data.nearbyRestaurants) { restaurant ->
                    NearbyRestaurantCard(
                        imageResId = restaurant.imageResId,
                        name = restaurant.name,
                        distance = restaurant.distance,
                        rating = restaurant.rating,
                        reviews = restaurant.reviews
                    )
                }
            }
        }

        // Recommended Food Items
        item {
            Text(
                "Recommended for you",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(16.dp)
            )
        }

        items(data.recommendedFoodItems.chunked(2)) { rowItems ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                rowItems.forEach { foodItem ->
                    FoodItemAdd(
                        imageRes = foodItem.imageRes,
                        title = foodItem.title,
                        description = foodItem.description,
                        rating = foodItem.rating,
                        reviews = foodItem.reviews,
                        price = foodItem.price,
                        distance = foodItem.distance,
                        onFavoriteClick = { /* Handle favorite click */ },
                        onAddClick = { /* Handle add click */ },
                        modifier = Modifier.weight(1f)
                    )
                    if (rowItems.size == 2 && rowItems.last() != foodItem) {
                        Spacer(modifier = Modifier.width(16.dp))
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
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
        Icon(
            painter = painterResource(id = android.R.drawable.ic_menu_mylocation),
            contentDescription = "Location"
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = address,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )
        IconButton(onClick = onAddressClick) {
            Icon(
                painter = painterResource(id = android.R.drawable.arrow_down_float),
                contentDescription = "Change address"
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier.fillMaxWidth(),
        placeholder = { Text("Food, groceries, drinks, etc.") },
        leadingIcon = {
            Icon(
                painter = painterResource(id = android.R.drawable.ic_menu_search),
                contentDescription = "Search"
            )
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = { onSearch(query) })
    )
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val sampleData = HomeScreenData(
        foodCategories = listOf(
            FoodCategory(R.drawable.burger, "Burger"),
            FoodCategory(R.drawable.pizza, "Pizza"),
            FoodCategory(R.drawable.taco, "Taco"),
            FoodCategory(R.drawable.chicken_fingers, "Chicken")
        ),
        nearbyRestaurants = listOf(
            Restaurant(R.drawable.restaurant_1, "Egg Benedict", "2.2 away from you", 4.9f, 1100, listOf("American", "Breakfast"), "Delicious egg dishes"),
            Restaurant(R.drawable.restaurant_2, "Kashmiri Biryani", "2.2 away from you", 4.9f, 1100, listOf("Indian", "Rice"), "Aromatic rice dish")
        ),
        recommendedFoodItems = listOf(
            FoodItem(R.drawable.food_item_1, "Naked Jackfruit Burrito Bowl", "Vegan bowl", 4.9f, 1100, "\$20.00", "2.2 away from you"),
            FoodItem(R.drawable.food_item_2, "NY Chicken Roll", "Large size", 4.9f, 1100, "\$20.00", "2.2 away from you"),
            FoodItem(R.drawable.food_item_3, "Kochchi Prawn Spaghetti", "Spicy seafood pasta", 4.9f, 1100, "\$22.00", "2.2 away from you"),
            FoodItem(R.drawable.food_item_4, "Double Chicken & Cheese Fiesta", "Pizza - Large", 4.9f, 1100, "\$25.00", "2.2 away from you")
        )
    )

    MaterialTheme {
        HomeScreen(data = sampleData)
    }
}

// Spanish sample data
val spanishSampleData = HomeScreenData(
    foodCategories = listOf(
        FoodCategory(R.drawable.burger, "Hamburguesa"),
        FoodCategory(R.drawable.pizza, "Pizza"),
        FoodCategory(R.drawable.taco, "Taco"),
        FoodCategory(R.drawable.chicken_fingers, "Pollo")
    ),
    nearbyRestaurants = listOf(
        Restaurant(R.drawable.restaurant_1, "Huevos Benedictinos", "A 2,2 km de ti", 4.9f, 1100, listOf("Americano", "Desayuno"), "Deliciosos platos de huevo"),
        Restaurant(R.drawable.restaurant_2, "Paella Valenciana", "A 2,2 km de ti", 4.9f, 1100, listOf("Español", "Arroz"), "Auténtica paella española")
    ),
    recommendedFoodItems = listOf(
        FoodItem(R.drawable.food_item_1, "Burrito de Jackfruit", "Bowl vegano", 4.9f, 1100, "20,00 €", "A 2,2 km de ti"),
        FoodItem(R.drawable.food_item_2, "Rollito de Pollo NY", "Tamaño grande", 4.9f, 1100, "20,00 €", "A 2,2 km de ti"),
        FoodItem(R.drawable.food_item_3, "Espaguetis con Gambas Picantes", "Pasta de mariscos picante", 4.9f, 1100, "22,00 €", "A 2,2 km de ti"),
        FoodItem(R.drawable.food_item_4, "Fiesta de Pollo y Queso Doble", "Pizza - Grande", 4.9f, 1100, "25,00 €", "A 2,2 km de ti")
    )
)

// Japanese sample data
val japaneseSampleData = HomeScreenData(
    foodCategories = listOf(
        FoodCategory(R.drawable.burger, "ハンバーガー"),
        FoodCategory(R.drawable.pizza, "ピザ"),
        FoodCategory(R.drawable.taco, "タコス"),
        FoodCategory(R.drawable.chicken_fingers, "チキン")
    ),
    nearbyRestaurants = listOf(
        Restaurant(R.drawable.restaurant_1, "エッグベネディクト", "2.2km先", 4.9f, 1100, listOf("アメリカン", "朝食"), "美味しい卵料理"),
        Restaurant(R.drawable.restaurant_2, "カレーライス", "2.2km先", 4.9f, 1100, listOf("日本", "カレー"), "本格的な日本のカレー")
    ),
    recommendedFoodItems = listOf(
        FoodItem(R.drawable.food_item_1, "ジャックフルーツのブリトーボウル", "ビーガン向け", 4.9f, 1100, "2,000円", "2.2km先"),
        FoodItem(R.drawable.food_item_2, "NYチキンロール", "大サイズ", 4.9f, 1100, "2,000円", "2.2km先"),
        FoodItem(R.drawable.food_item_3, "海老のスパイシーパスタ", "スパイシーシーフードパスタ", 4.9f, 1100, "2,200円", "2.2km先"),
        FoodItem(R.drawable.food_item_4, "ダブルチキン＆チーズフィエスタ", "ピザ - ラージ", 4.9f, 1100, "2,500円", "2.2km先")
    )
)

@Preview(showBackground = true, name = "Home Screen (Spanish)")
@Composable
fun HomeScreenPreviewSpanish() {
    MaterialTheme {
        HomeScreen(data = spanishSampleData)
    }
}

@Preview(showBackground = true, name = "Home Screen (Japanese)")
@Composable
fun HomeScreenPreviewJapanese() {
    MaterialTheme {
        HomeScreen(data = japaneseSampleData)
    }
}