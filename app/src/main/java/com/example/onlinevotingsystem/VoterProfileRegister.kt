package com.example.onlinevotingsystem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class VoterProfileRegister : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    val db=Firebase.firestore


    private lateinit var uName:TextView
    private lateinit var uAge:TextView
    private lateinit var uPhone:TextView
    private lateinit var uCity:TextView
    private lateinit var updateProfile:Button
// ...

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_voter_profile_register)
        auth = Firebase.auth
        val user=auth.currentUser
        Toast.makeText(this, "$user", Toast.LENGTH_SHORT).show()

        uName=findViewById(R.id.uname)
        uPhone=findViewById(R.id.uPhone)
        uCity=findViewById(R.id.uCity)
        uAge=findViewById(R.id.uAge)
        updateProfile=findViewById(R.id.updateProf)

        updateProfile.setOnClickListener {
            ProfileUpdater(
                user!!,
                uName.text.toString(),
                uPhone.text.toString(),
                uAge.text.toString().toInt(),
                uCity.text.toString()

            )
        }

    }

    private fun ProfileUpdater(user: FirebaseUser, Name:String,  Phone: String,Age:Int, City:String){
        Toast.makeText(this, user.uid, Toast.LENGTH_SHORT).show()
        Toast.makeText(this, "${Age::class.simpleName}", Toast.LENGTH_SHORT).show()

        val userDetCard=VoterCard(Name,Phone,Age,City,"N");//Could not serialize object. Serializing Arrays is not supported, please use Lists instead (found in field 'age.first.filters'
        db.collection("userProfile1").document(user.uid).set(userDetCard)
            .addOnSuccessListener {
                toVotingPage()
            } //check for error
//        cleanUp()
    }

    private fun toVotingPage() {
        Toast.makeText(this, "working", Toast.LENGTH_SHORT).show()
        startActivity(
            Intent(this,VotingPage::class.java)
        )
        finish()
    }

/*    private fun cleanUp() {
        auth.signOut()
        startActivity(
            Intent(this,MainActivity::class.java)
        )
    }*/

    private fun uploadProfile(user:FirebaseUser,map:HashMap<String,String>){

//        val sfDocRef = db.collection("userProfile").document(user!!.uid.toString())

/*
        db.runTransaction { transaction ->
            val snapshot = transaction.get(sfDocRef)

            // Note: this could be done without a transaction
            //       by updating the population using FieldValue.increment()
            val newPopulation = snapshot.getDouble("population")!! + 1
            transaction.update(sfDocRef, "population", newPopulation)

            // Success
            null
        }.addOnSuccessListener { Log.d(TAG, "Transaction success!") }
            .addOnFailureListener { e -> Log.w(TAG, "Transaction failure.", e) }
*/

        db.collection("userProfile").document(user.uid).set(map)
            .addOnSuccessListener {
                Toast.makeText(this, "profile successfully updated", Toast.LENGTH_SHORT).show()
                startActivity(
                    Intent(this,VotingPage::class.java)
                )
                finish()
            }
            .addOnFailureListener{
                Toast.makeText(this, "server error", Toast.LENGTH_SHORT).show()
            }
    }
}