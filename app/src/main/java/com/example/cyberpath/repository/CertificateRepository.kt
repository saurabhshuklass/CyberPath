package com.example.cyberpath.repository

import com.example.cyberpath.model.Certificate
import com.example.cyberpath.model.CertificateHistory
import com.google.firebase.firestore.FirebaseFirestore

class CertificateRepository {

    private val firestore = FirebaseFirestore.getInstance()

    fun saveCertificate(
        uid: String,
        certificate: Certificate,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {

        firestore.collection("certificates")
            .document(uid)
            .set(certificate)
            .addOnSuccessListener {

                onSuccess()

            }
            .addOnFailureListener {

                onFailure(it)

            }

    }

    fun getCertificate(
        uid: String,
        onSuccess: (Certificate?) -> Unit,
        onFailure: (Exception) -> Unit
    ) {

        firestore.collection("certificates")
            .document(uid)
            .get()
            .addOnSuccessListener { document ->

                if (document.exists()) {

                    val certificate =
                        document.toObject(Certificate::class.java)

                    onSuccess(certificate)

                } else {

                    onSuccess(null)

                }

            }
            .addOnFailureListener {

                onFailure(it)

            }

    }

    fun getCertificateHistory(
        uid: String,
        onSuccess: (List<CertificateHistory>) -> Unit,
        onFailure: (Exception) -> Unit
    ) {

        firestore.collection("certificates")
            .document(uid)
            .collection("history")
            .get()
            .addOnSuccessListener { snapshot ->

                val list = ArrayList<CertificateHistory>()

                for (doc in snapshot.documents) {

                    val item = doc.toObject(CertificateHistory::class.java)

                    if (item != null) {
                        list.add(item)
                    }

                }

                onSuccess(list)

            }
            .addOnFailureListener {

                onFailure(it)

            }

    }

}