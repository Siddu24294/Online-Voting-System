package com.example.onlinevotingsystem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class superuser_login : AppCompatActivity() {

    private lateinit var superuid :EditText
    private lateinit var superpwd :EditText
    private lateinit var superloginbtn : Button
    var db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_superuser_login)

        superuid = findViewById(R.id.superuid)
        superpwd = findViewById(R.id.superpwd)
        superloginbtn = findViewById(R.id.superloginbtn)

        superloginbtn.setOnClickListener{
            val suid = superuid.text.toString().trim()
            val spwd = superpwd.text.toString().trim()

            if(suid.isNotEmpty() && spwd.isNotEmpty())
            {
                val users = FirebaseFirestore.getInstance().collection("superuser")
                users.get()
                    .addOnSuccessListener { querySnapshot ->
                        for (documentSnapshot in querySnapshot.documents) {
                            val uname = documentSnapshot.getString("superuid")
                            if(uname == suid)
                            {
                                val upwd = documentSnapshot.getString("superpwd")
                                if(upwd == spwd)
                                {

                                    startActivity(
                                        Intent(this,superUserControl::class.java)
                                    )

                                    Toast.makeText(this, "Logged in Successfully", Toast.LENGTH_SHORT).show()
                                    finish()
    //                                val intent = Intent(this,activity_voting::class.java)
      //                              startActivity(intent)
                                }
                            }
                        }
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "username not found", Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }
}