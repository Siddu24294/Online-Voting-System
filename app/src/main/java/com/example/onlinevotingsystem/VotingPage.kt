package com.example.onlinevotingsystem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase


class VotingPage : AppCompatActivity() {
    val db=Firebase.firestore
    var currentPos:Int = -1

    private lateinit var auth:FirebaseAuth

    private lateinit var VoteButton:Button
    private lateinit var confirmView:TextView
    private lateinit var recyclerview: RecyclerView
    private lateinit var voterName:TextView

    val candList = ArrayList<Candidate>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_voting_page)

        auth=Firebase.auth
        val user=auth.currentUser
        if(user==null){
            Toast.makeText(this, "user null redirecting to main menu", Toast.LENGTH_SHORT).show()
            startActivity(
                Intent(this,MainActivity::class.java)
            )
            finish()
//            TTODO("Redirect to main menu")

        }

        voterName=findViewById(R.id.voterName)

        val docRef =db.collection("userProfile1").document(user!!.uid)
        docRef.get().addOnSuccessListener { documentSnapshot ->
            val city = documentSnapshot.toObject<VoterCard>()
            voterName.text= city!!.name
        }
/*
        db.collection("userProfile1").document(user.uid).get()
            .addOnSuccessListener {doc->
                voterName.text=doc.data?.get("name").toString()
            }
*/

        VoteButton=findViewById(R.id.VoteButton)
        confirmView=findViewById(R.id.confirmView)
        recyclerview = findViewById<RecyclerView>(R.id.recyclerview)


        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this)

        VoteButton.setOnClickListener{
            val currentCand=candList[currentPos]
            Toast.makeText(this, "$currentCand", Toast.LENGTH_SHORT).show()
            val docId=currentCand.docId
            val votes=currentCand.votes
            db.collection("candidates").document(docId).update("vote_count",votes.inc())
            db.collection("userProfile1").document(user.uid).update("status","Y")
                .addOnSuccessListener {
                    Toast.makeText(this, "voting succesful", Toast.LENGTH_SHORT).show()
                    auth.signOut()
                    startActivity(
                        Intent(this,MainActivity::class.java)
                    )
                    finish()
                }

        }

        getData()

    }
    fun getData(){
        Toast.makeText(this,"starting",Toast.LENGTH_SHORT).show()
        db.collection("candidates").get()
            .addOnSuccessListener { resultSet->
            for(document in resultSet){
                val data =document.data
                val cid=data.get("candId").toString()
                val cname=data.get("candname").toString()
                val docId=document.id
                val vote_count:Long= data.get("vote_count") as Long
                candList.add(Candidate(candId = cid, candname = cname, docId = docId, votes = vote_count))
            }
            restOfWork()
        }.addOnFailureListener {
                Toast.makeText(this, "fialed", Toast.LENGTH_SHORT).show()
            }
    }

    fun restOfWork(){
        val adapter = CustomAdapter(candList)

        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter

        adapter.setOnItemClickListener(object :CustomAdapter.onItemCLickListener{
            override fun onItemClick(position: Int) {
                currentPos=position
                confirmView.text=candList[currentPos].candname
            }
        })
    }
}