package de.praktikum.shoppinglist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil3.compose.AsyncImage
import de.praktikum.shoppinglist.model.ShoppingListItem
import de.praktikum.shoppinglist.model.ShoppingListViewModel
import de.praktikum.shoppinglist.ui.AddItemDialog
import de.praktikum.shoppinglist.ui.AddItemFAB
import de.praktikum.shoppinglist.ui.ShoppingItemView
import de.praktikum.shoppinglist.ui.ShoppingList
import de.praktikum.shoppinglist.ui.SwipeToDismissListItem
import de.praktikum.shoppinglist.ui.theme.ShoppingListTheme

class MainActivity : ComponentActivity() {

    private val viewModel: ShoppingListViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            ShoppingListTheme {

                // The scaffold composable makes it easy to add an app bar, FAB, and main content
                Scaffold(
                    // Showing the app name in the app bar
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(stringResource(R.string.appbar_title))
                            }
                        )
                    },
                    // The floating action button for adding new products
                    floatingActionButton = {
                        AddItemFAB(addShoppingListItem = { name, quantity ->
                            // passing the viewmodel function as a callback here so we don't need to expose the viewModel to the component
                            viewModel.addShoppingListItem(name, quantity)
                        })
                    }) { innerPadding ->
                        ShoppingList(
                            viewModel.shoppingListItems,
                            Modifier.padding(innerPadding),
                            removeShoppingListItem = { id -> viewModel.deleteShoppingListItem(id) },
                        )
                    }
            }
        }
    }

    override fun onDestroy() {
        // detaching the database listener
        viewModel.removeListener()
        super.onDestroy()
    }
}


@Preview(showBackground = true)
@Composable
fun ShoppingListPreview() {
    ShoppingListTheme {
        val items = listOf(
            ShoppingListItem("id0", "Äpfel", "1kg", "apples.jpg"),
            ShoppingListItem("id1", "Paprika", "3 Stück", "bellpepers.jpg"),
            ShoppingListItem("id2", "Kaffee", "500g", "coffee.jpg")
        )
        ShoppingList(
            items,
            modifier = Modifier.padding(all = 8.dp),
            removeShoppingListItem = { },
        )
    }
}