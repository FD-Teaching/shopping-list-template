package de.praktikum.shoppinglist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.praktikum.shoppinglist.model.ShoppingListItem
import de.praktikum.shoppinglist.model.ShoppingListViewModel
import de.praktikum.shoppinglist.ui.AddItemFAB
import de.praktikum.shoppinglist.ui.ShoppingList
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