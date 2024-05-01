package com.example.mealmate.services

import com.example.mealmate.domain.model.User
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

class UserServices {


// CRUD - Create, Read, Update, Delete

    suspend fun createUser(user: User) {
        Firebase.firestore.collection("users").document(user.id).set(user).await()

    }

    suspend fun loadUser(id: String): DocumentSnapshot {
        val output = Firebase.firestore.collection("users").document(id).get().await()
        return output
    }

    suspend fun loadUserList(): QuerySnapshot {
        val output = Firebase.firestore.collection("users").get().await()
        return output
    }

    fun observeUser(id: String, callback: (DocumentSnapshot?) -> Unit) {
        Firebase.firestore.collection("users").document(id)
            .addSnapshotListener { snapshot, error ->
                callback(snapshot)
            }
    }

    suspend fun uploadUserPreference(field: String, userId: String, items: List<String>): Boolean {
        return try {

            Firebase.firestore.collection("users").document(userId).collection(field)
                .add(items).await()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }


}