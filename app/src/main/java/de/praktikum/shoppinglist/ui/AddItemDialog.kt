package de.praktikum.shoppinglist.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import de.praktikum.shoppinglist.R

@Composable
fun AddItemDialog(onConfirmation: (name: String, quantity: String) -> Unit, onDismissRequest: () -> Unit) {
    Dialog(
        onDismissRequest = { onDismissRequest() }
    ) {

        var itemNameText by remember { mutableStateOf("")}
        var itemQuantityText by remember { mutableStateOf("")}

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column {
                TextField(
                    value = itemNameText,
                    onValueChange = { itemNameText = it },
                    label = { Text(stringResource(R.string.productname))}
                )
                TextField(
                    value = itemQuantityText,
                    onValueChange = { itemQuantityText = it },
                    label = { Text(stringResource(R.string.quantity))}
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    TextButton(
                        onClick = { onDismissRequest() },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text(stringResource(R.string.cancel))
                    }
                    TextButton(
                        onClick = { onConfirmation(itemNameText, itemQuantityText) },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text(stringResource(R.string.save))
                    }
                }
            }
        }
    }
}