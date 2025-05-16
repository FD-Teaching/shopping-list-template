package de.praktikum.shoppinglist.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Composable
fun AddItemFAB(addShoppingListItem: (name: String, quantity: String) -> Unit) {
    val openEditDialog = remember { mutableStateOf(false) }

    FloatingActionButton(onClick = {
        openEditDialog.value = true
    }) {

        Icon(Icons.Filled.Add, "Button for adding an item")

        when {
            openEditDialog.value -> {
                AddItemDialog(
                    onConfirmation = { name, quantity ->
                        openEditDialog.value = false
                        addShoppingListItem(name, quantity)
                    },
                    onDismissRequest = {
                        openEditDialog.value = false
                    }
                )
            }
        }
    }
}