package com.example.onlinevotingsystem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class superUserControl : AppCompatActivity() {
    val db=Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_super_user_control)

        findViewById<Button>(R.id.button).setOnClickListener {
            db.collection("superuser").document("voteStatus").update("s","Y")
                .addOnSuccessListener {
                    Toast.makeText(this, "vote started", Toast.LENGTH_SHORT).show()
                    startActivity(
                        Intent(this,MainActivity::class.java)
                    )
                    finish()
                    deleteCands()
                }
        }
        findViewById<Button>(R.id.button2).setOnClickListener {
            Toast.makeText(this,"vote ended",Toast.LENGTH_SHORT ).show()
            db.collection("superuser").document("voteStatus").update("s","N")
                .addOnSuccessListener {
                    Toast.makeText(this, "vote ended", Toast.LENGTH_SHORT).show()
                    startActivity(
                        Intent(this,MainActivity::class.java)
                    )
                    finish()
                }
        }
    }

    private fun deleteCands() {
        db.collection("candidates").get()
            .addOnSuccessListener { resultSet ->
                for (document in resultSet) {
                    db.collection("candidates").document(document.id).delete()
                }
            }

        db.collection("userProfile1").get()
            .addOnSuccessListener { resultSet ->
                for (document in resultSet) {
                    db.collection("userProfile1").document(document.id).update("status","N")
                }
            }

    }
}