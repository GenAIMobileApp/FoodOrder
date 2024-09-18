package com.example.foodorder

import NearbyRestaurantCard
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.foodorder.ui.theme.FoodOrderTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.foodorder.components.FoodComponent
import com.example.foodorder.components.FoodItem

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FoodOrderTheme  {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 16.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FoodOrderTheme {
        Greeting("Android")
    }
}


@Preview(showBackground = true)
@Composable
fun FoodAppComponentsPreview() {
    MaterialTheme {
        Surface {
            Column {
                Row (horizontalArrangement = Arrangement.SpaceEvenly) {
                    FoodComponent(R.drawable.burger, "Burger")
                    FoodComponent(R.drawable.chicken_fingers, "Roasted ")
                }

                Spacer(modifier = Modifier.height(16.dp))

                NearbyRestaurantCard(
                    imageResId = R.drawable.taco,
                    name = "Egg Benedict with Capsicum",
                    distance = "2.2 away from you",
                    rating = 4.9f,
                    reviews = 1100
                )

                Spacer(modifier = Modifier.height(16.dp))

                FoodItem(
                    name = "Eggs Benedict with Capsicum",
                    distance = "2.2",
                    rating = 4.9f,
                    reviews = 1100,
                    price = "\$20.00",
                    isFavorite = true
                )
            }
        }
    }
}



