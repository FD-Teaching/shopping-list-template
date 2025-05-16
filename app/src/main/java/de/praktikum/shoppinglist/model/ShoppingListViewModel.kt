package de.praktikum.shoppinglist.model

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import de.praktikum.shoppinglist.model.ShoppingListItem

class ShoppingListViewModel : ViewModel() {

    private val db = Firebase.firestore
    private var listener: ListenerRegistration? = null

    val shoppingListItems = mutableStateListOf<ShoppingListItem>()

    init {
        getItems()
    }

    // In this simple app, the viewmodel directly fetches the objects from the database
    fun getItems() {
        val query = db.collection("products")

        // with a listener, we also get updates when something changes
        listener = query
            .addSnapshotListener { products, error ->
                if (error != null) {
                    return@addSnapshotListener
                }
                else {
                    Log.w("FirestoreAction", "Error getting documents.", error)
                }

                if (products != null) {
                    shoppingListItems.clear()
                    for (product in products) {
                        try {
                            // Creating "Product" objects from the serialized objects saved in the database.
                            val item = product.toObject(ShoppingListItem::class.java)
                            item.id = product.id
                            shoppingListItems.add(item)
                            Log.d("FirestoreAction", "${product.id} => ${product.data}")
                        }
                        catch (_: Exception) {
                            Log.w("FirestoreAction", "Could not deserialize object ${product.data}")
                        }
                    }
                }
            }

    }

    fun addShoppingListItem(name: String, unit: String) {
        // leaving the "image" attribute blank -> sets it to a default image
        // leaving the "id" attribute empty -> set when retrieving items from database
        db.collection("products")
            .add(ShoppingListItem(name, unit))
    }

    fun deleteShoppingListItem(id: String) {
        db.collection("products")
            .document(id)
            .delete()
    }

    // called when the app is paused
    fun removeListener() {
        listener?.remove()
    }

}