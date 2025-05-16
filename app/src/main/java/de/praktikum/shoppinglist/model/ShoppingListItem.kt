package de.praktikum.shoppinglist.model

import com.google.firebase.firestore.Exclude
import kotlinx.serialization.Serializable

@Serializable
data class ShoppingListItem(var name: String = "",
                            var unit: String = "",
                            var image: String = "default.jpg",
                            @Exclude @set:Exclude @get:Exclude var id: String = "")