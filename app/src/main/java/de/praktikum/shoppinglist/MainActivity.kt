package de.praktikum.shoppinglist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.praktikum.shoppinglist.ui.theme.ShoppingListTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShoppingListTheme {

                val items = listOf(
                    ShoppingListItem(0,"Äpfel", "1kg", R.drawable.apples),
                    ShoppingListItem(1,"Paprika", "3 Stück", R.drawable.bellpeppers),
                    ShoppingListItem(2,"Kaffee", "500g", R.drawable.coffee),
                    ShoppingListItem(3,"Nudeln", "500g", R.drawable.pasta)
                )

                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(stringResource(R.string.appbar_title))
                            }
                        )
                    }) { innerPadding ->
                    ShoppingList(items, Modifier.padding(innerPadding))
                }
            }
        }
    }
}

data class ShoppingListItem(val id: Int, val name: String, val unit: String, val image: Int)

@Composable
fun ShoppingList(items: List<ShoppingListItem>, modifier: Modifier) {
    val openAlertDialog = remember { mutableStateOf(false) }
    val currentItemId = remember { mutableIntStateOf(0) }

    LazyColumn(modifier) {
        itemsIndexed(items){ index, item ->
            ShoppingItemView(item, onItemClick = {
                currentItemId.intValue = index
                openAlertDialog.value = true
            })
        }
    }

    when {
        openAlertDialog.value -> {
            DetailItemDialog(items[currentItemId.intValue], onConfirmation = {
                openAlertDialog.value = false
            })
        }
    }
}

@Composable
fun ShoppingItemView(item: ShoppingListItem, onItemClick: (ShoppingListItem) -> Unit) {
    Row(modifier = Modifier
        .padding(all = 8.dp)
        .clickable { onItemClick(item) }) {
        Image(painter = painterResource(item.image),
            contentDescription = null,
            modifier = Modifier.size(50.dp))
        Column(modifier = Modifier.padding(start = 8.dp)) {
            Text(text = item.name,
                fontWeight = FontWeight.Bold)
            Text(text = item.unit)
        }
    }
}

@Composable
fun DetailItemDialog(item: ShoppingListItem, onConfirmation: () -> Unit) {
    AlertDialog(
        onDismissRequest = { },
        title = { Text(text = item.name) },
        text = { Text(text = item.unit) },
        confirmButton = {
            TextButton(
                onClick = { onConfirmation() }
            ) {
                Text(stringResource(R.string.back))
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun ShoppingListPreview() {
    ShoppingListTheme {
        val items = listOf(
            ShoppingListItem(0,"Äpfel", "1kg", R.drawable.apples),
            ShoppingListItem(1,"Paprika", "3 Stück", R.drawable.bellpeppers),
            ShoppingListItem(2,"Kaffee", "500g", R.drawable.coffee)
        )
        ShoppingList(items, modifier = Modifier.padding(all = 8.dp))
    }
}