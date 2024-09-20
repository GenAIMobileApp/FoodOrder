import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun FoodFilterChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    AssistChip(
        onClick = onClick,
        label = { Text(text) },
        modifier = modifier.padding(4.dp),
        colors = AssistChipDefaults.assistChipColors(
            containerColor = if (selected)
                Color.LightGray.copy(alpha = 0.6f)
            else
                Color.White
        ),
        border = AssistChipDefaults.assistChipBorder(enabled = false)
    )
}

@Preview(showBackground = true)
@Composable
fun FilterChipPreview() {
    FoodFilterChip(
        text = "Chicken",
        selected = true,
        onClick = { /* Handle click */ }
    )
}