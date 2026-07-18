package com.example.cyberpath

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.example.cyberpath.utils.AvatarManager

class AccountActivity : AppCompatActivity() {

    // Firebase
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    // Toolbar
    private lateinit var btnBack: ImageButton

    // Profile
    private lateinit var imgProfile: ImageView
    private lateinit var btnEditImage: ImageButton

    // User Data
    private lateinit var edtName: EditText
    private lateinit var edtEmail: EditText
    private lateinit var spinnerCourse: Spinner

    // Save
    private lateinit var btnSave: Button

    private val courses = arrayOf(
        "MCA Cyber Security",
        "MCA AI",
        "MCA Data Science",
        "BCA",
        "B.Tech CSE",
        "B.Tech Cyber Security"
    )

    private var selectedAvatar = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_account)

        initFirebase()

        initViews()

        setupSpinner()

        loadUserData()

        setupClickListeners()

    }

    private fun initFirebase() {

        auth = FirebaseAuth.getInstance()

        firestore = FirebaseFirestore.getInstance()

    }

    private fun initViews() {

        btnBack = findViewById(R.id.btnBack)

        imgProfile = findViewById(R.id.imgProfile)

        btnEditImage = findViewById(R.id.btnEditImage)

        edtName = findViewById(R.id.edtName)

        edtEmail = findViewById(R.id.edtEmail)

        spinnerCourse = findViewById(R.id.spinnerCourse)

        btnSave = findViewById(R.id.btnSave)

    }

    private fun setupSpinner() {

        val adapter = ArrayAdapter(

            this,

            android.R.layout.simple_spinner_dropdown_item,

            courses

        )

        spinnerCourse.adapter = adapter

    }

    private fun loadUserData() {

        val uid = auth.currentUser?.uid ?: return

        edtEmail.setText(auth.currentUser?.email)

        firestore.collection("users")

            .document(uid)

            .get()

            .addOnSuccessListener { document ->

                if (!document.exists()) return@addOnSuccessListener

                edtName.setText(

                    document.getString("name") ?: ""

                )

                selectedAvatar =
                    document.getLong("avatar")?.toInt() ?: 1

                showAvatar(selectedAvatar)

                val course =

                    document.getString("course")

                        ?: "MCA Cyber Security"

                val position =

                    courses.indexOf(course)

                if (position != -1) {

                    spinnerCourse.setSelection(position)

                }

            }

    }

    private fun showAvatar(number: Int) {

        imgProfile.setImageResource(

            AvatarManager.getAvatar(number)

        )

    }

    private fun setupClickListeners() {

        btnBack.setOnClickListener {
            finish()
        }

        btnSave.setOnClickListener {

            saveUserData()

        }

        btnEditImage.setOnClickListener {

            showAvatarPicker()

        }

    }

    private fun saveUserData() {

        val uid = auth.currentUser?.uid ?: return

        val name = edtName.text.toString().trim()

        val course = spinnerCourse.selectedItem.toString()

        if (name.isEmpty()) {

            edtName.error = "Enter your name"

            edtName.requestFocus()

            return

        }

        val updates = hashMapOf<String, Any>()

        updates["avatar"] = selectedAvatar
        updates["name"] = name
        updates["course"] = course

        firestore.collection("users")
            .document(uid)
            .update(updates)

            .addOnSuccessListener {

                Toast.makeText(
                    this,
                    "Profile Updated Successfully",
                    Toast.LENGTH_SHORT
                ).show()

                finish()

            }

            .addOnFailureListener {

                Toast.makeText(
                    this,
                    "Failed to update profile",
                    Toast.LENGTH_SHORT
                ).show()

            }

    }

    private fun showAvatarPicker() {

        val view = LayoutInflater.from(this)
            .inflate(R.layout.dialog_avatar_picker, null)

        val dialog = AlertDialog.Builder(this)
            .setTitle("Choose Avatar")
            .setView(view)
            .create()

        val avatars = listOf(

            view.findViewById<ImageView>(R.id.avatar1),
            view.findViewById<ImageView>(R.id.avatar2),
            view.findViewById<ImageView>(R.id.avatar3),
            view.findViewById<ImageView>(R.id.avatar4),
            view.findViewById<ImageView>(R.id.avatar5),
            view.findViewById<ImageView>(R.id.avatar6)

        )

        avatars.forEachIndexed { index, imageView ->

            imageView.setOnClickListener {

                selectedAvatar = index + 1

                showAvatar(selectedAvatar)

                dialog.dismiss()

            }

        }

        dialog.show()

    }
}