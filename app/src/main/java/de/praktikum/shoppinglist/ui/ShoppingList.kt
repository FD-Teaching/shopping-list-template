package de.praktikum.shoppinglist.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import de.praktikum.shoppinglist.model.ShoppingListItem

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ShoppingList(items: List<ShoppingListItem>,
                 modifier: Modifier,
                 removeShoppingListItem: (id: String) -> Unit) {
    val openAlertDialog = remember { mutableStateOf(false) }
    val currentItemId = remember { mutableIntStateOf(0) }

    LazyColumn(modifier) {
        itemsIndexed(items){ index, item ->
            // Each item is packed into a SwipeToDismissBox, implemented in the SwipeToDismissListItem composable
            SwipeToDismissListItem(
                modifier = Modifier.animateItemPlacement(),
                onEndToStart = {
                    removeShoppingListItem(item.id)
                }
            ) {
                ShoppingItemView(item, onItemClick = {
                    currentItemId.intValue = index
                    openAlertDialog.value = true
                })
            }
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