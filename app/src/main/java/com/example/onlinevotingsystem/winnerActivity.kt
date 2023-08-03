package com.example.onlinevotingsystem

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class winnerActivity : AppCompatActivity() {
    val db=Firebase.firestore
    private lateinit var disp:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_winner)

        var cand:String
        var voteCount=0

        disp=findViewById(R.id.textView9)

        db.collection("candidates").get()
            .addOnSuccessListener { resultSet ->
                for (document in resultSet) {
                    val data = document.data
                    val curc = data.get("vote_count").toString().toInt()
                    if (curc > voteCount) {
                        cand = data.get("candname").toString()
                        disp.text=cand
                    }
                }
            }
    }
}