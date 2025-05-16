package de.praktikum.shoppinglist.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import de.praktikum.shoppinglist.model.ShoppingListItem

@Composable
fun ShoppingItemView(item: ShoppingListItem,
                     onItemClick: (ShoppingListItem) -> Unit) {

    Row(modifier = Modifier
        .padding(all = 8.dp)
        .clickable { onItemClick(item) }) {
        // Image display using files or online resources
        // You can use an online URL instead of the file:/// notation
        AsyncImage(
            model = "file:///android_asset/images/${item.image}",
            contentDescription = null,
            modifier = Modifier.size(50.dp)
        )
        Column(modifier = Modifier.padding(start = 8.dp)) {
            Text(text = item.name,
                fontWeight = FontWeight.Bold)
            Text(text = item.unit)
        }
    }
}

// Based on https://medium.com/@ryhn151/swipe-to-delete-with-jetpack-compose-f32c0dd39eca
@Composable
fun SwipeToDismissListItem(
    modifier: Modifier = Modifier,
    onEndToStart: () -> Unit = {},
    content: @Composable () -> Unit
) {
    // 1. State is hoisted here
    val dismissState = rememberSwipeToDismissBoxState()

    SwipeToDismissBox(
        modifier = modifier
            .fillMaxWidth(),
        state = dismissState,
        backgroundContent = {

            // 2. Animate the swipe by changing the color
            val color by animateColorAsState(
                targetValue = when (dismissState.targetValue) {
                    SwipeToDismissBoxValue.Settled -> Color.Transparent
                    // We don't have any action for the start-to-end swipe for now, so we keep it transparent
                    SwipeToDismissBoxValue.StartToEnd -> Color.Transparent
                    SwipeToDismissBoxValue.EndToStart -> Color.Red
                },
                label = "swipe"
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color) // 3. Set the animated color here
            ) {

                // 4. Show the correct icon
                when (dismissState.targetValue) {

                    SwipeToDismissBoxValue.EndToStart -> {
                        Icon(
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .padding(end = 16.dp),
                            imageVector = Icons.Default.Delete,
                            contentDescription = "delete"
                        )
                    }

                    // Nothing to do
                    SwipeToDismissBoxValue.StartToEnd -> { }
                    SwipeToDismissBoxValue.Settled -> { }
                }

            }
        }
    ) {
        content()
    }

    // 5. Trigger the callbacks
    when (dismissState.currentValue) {
        SwipeToDismissBoxValue.EndToStart -> {
            LaunchedEffect(dismissState.currentValue) {
                onEndToStart()
                // Resetting the state of the box
                dismissState.snapTo(SwipeToDismissBoxValue.Settled)
            }

        }

        SwipeToDismissBoxValue.StartToEnd -> {
            LaunchedEffect(dismissState.currentValue) {
                // nothing to do in our case except resetting the state
                dismissState.snapTo(SwipeToDismissBoxValue.Settled)
            }
        }

        SwipeToDismissBoxValue.Settled -> {
            // Nothing to do
        }
    }
}