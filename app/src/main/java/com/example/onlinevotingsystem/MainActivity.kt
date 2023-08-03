package com.example.onlinevotingsystem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {

    private lateinit var superuserbtn :  Button
    private lateinit var candbtn : Button
    private lateinit var voterbtn : Button
    private lateinit var winner : Button
    private lateinit var auth:FirebaseAuth

    val db=Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        superuserbtn = findViewById(R.id.superuserbtn)
        candbtn = findViewById(R.id.candbtn)
        voterbtn = findViewById(R.id.voterbtn)
        winner=findViewById(R.id.winner)
        auth=Firebase.auth
        auth.signOut()

        superuserbtn.setOnClickListener{
            val intent = Intent(this,superuser_login::class.java)
            startActivity(intent)
            finish()
        }

        candbtn.setOnClickListener{
            val intent = Intent(this,activity_candidate_register::class.java)
            startActivity(intent)
            finish()
        }

        voterbtn.setOnClickListener{
            val intent = Intent(this,newVoterLoginActiviy::class.java)
            startActivity(intent)
            finish()
        }

        winner.setOnClickListener {
            startActivity(
                Intent(this,winnerActivity::class.java)
            )
            finish()
        }

    }

    override fun onStart() {
        super.onStart()
        if(auth.currentUser!=null){
            auth.signOut()
        }
        db.collection("superuser").document("voteStatus").get()
            .addOnSuccessListener {it->
                val status=it.data!!.get("s")
                if (status=="Y"){
                    candbtn.isEnabled=true
                    voterbtn.isEnabled=true
                    winner.isEnabled=false
                }
                else{
                    candbtn.isEnabled=false
                    voterbtn.isEnabled=false
                    winner.isEnabled=true
                }
            }
    }
}