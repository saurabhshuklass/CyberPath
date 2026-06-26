package com.example.cyberpath.utils

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

object ProgressManager {

    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    fun markPracticalCompleted(
        practicalName: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {

        val uid = auth.currentUser?.uid
            ?: run {
                onFailure("User not logged in")
                return
            }

        val practicalRef = firestore
            .collection("practical_progress")
            .document(uid)

        practicalRef.get()

            .addOnSuccessListener { document ->

                if (document.getBoolean(practicalName) == true) {

                    onFailure("Already Completed")

                } else {

                    practicalRef.set(
                        mapOf(practicalName to true),
                        SetOptions.merge()
                    )

                        .addOnSuccessListener {

                            firestore.collection("users")
                                .document(uid)
                                .update(
                                    "completedPracticals",
                                    FieldValue.increment(1)
                                )

                                .addOnSuccessListener {

                                    onSuccess()

                                }

                                .addOnFailureListener {

                                    onFailure("Failed to update user progress")

                                }

                        }

                        .addOnFailureListener {

                            onFailure("Failed to save practical progress")

                        }

                }

            }

            .addOnFailureListener {

                onFailure("Failed to load progress")

            }

    }

}