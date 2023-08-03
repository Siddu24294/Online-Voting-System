package com.example.onlinevotingsystem

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class activity_candidate_register : AppCompatActivity() {
    lateinit var candname : EditText
    lateinit var candage : EditText
    lateinit var candemail : EditText
    lateinit var candphone : EditText
    lateinit var candcity : EditText
    lateinit var candregbtn : Button

    var db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_candidate_register)

        candname = findViewById(R.id.candname)
        candage = findViewById(R.id.candage)
        candemail =findViewById(R.id.candemail)
        candphone = findViewById(R.id.candphone)
        candcity = findViewById(R.id.candcity)
        candregbtn = findViewById(R.id.candregbtn)

        candregbtn.setOnClickListener{
            val cname = candname.text.toString().trim()
            val cage = candage.text.toString().trim()
            val cemail = candemail.text.toString().trim()
            val cphone = candphone.text.toString().trim()
            val ccity = candcity.text.toString().trim()



            //val candid = FirebaseAuth.getInstance().currentUser!!.uid

            val dbcount =FirebaseFirestore.getInstance().collection("candidates")
            dbcount.get().addOnSuccessListener {
                    querySnapshot->
                val candcount = querySnapshot.size()
                val candId=candcount+1

                if(candcount ==0 || candcount<3)
                {

                    val candmap = hashMapOf(
                        "candId" to candId,
                        "candname" to cname,
                        "candage" to cage,
                        "candemail" to cemail,
                        "candphone" to cphone,
                        "candcity" to ccity,
                        "vote_count" to 0
                    )
                    db.collection("candidates").document().set(candmap)
                        .addOnSuccessListener {
                            Toast.makeText(this, "candidate added", Toast.LENGTH_SHORT).show()
                            candname.text.clear()
                            candage.text.clear()
                            candemail.text.clear()
                            candphone.text.clear()
                            candcity.text.clear()
                        }
                        .addOnFailureListener{
                            Toast.makeText(this, "failed to insert", Toast.LENGTH_SHORT).show()
                        }
                }
                else
                {
                    Toast.makeText(this, "maximum reached", Toast.LENGTH_SHORT).show()
                    candname.text.clear()
                    candage.text.clear()
                    candemail.text.clear()
                    candphone.text.clear()
                    candcity.text.clear()
                    finish()
                }
            }.addOnFailureListener {
                Toast.makeText(this, "count is not done", Toast.LENGTH_SHORT).show()
            }
        }
    }
}