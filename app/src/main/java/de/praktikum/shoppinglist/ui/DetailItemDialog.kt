package de.praktikum.shoppinglist.ui

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import de.praktikum.shoppinglist.R
import de.praktikum.shoppinglist.model.ShoppingListItem

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