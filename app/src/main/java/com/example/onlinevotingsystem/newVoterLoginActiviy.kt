package com.example.onlinevotingsystem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class newVoterLoginActiviy : AppCompatActivity() {

    private lateinit var auth:FirebaseAuth
    val db=Firebase.firestore

    private lateinit var uid:TextView
    private lateinit var pid:TextView
    private lateinit var logInButton: Button
    private lateinit var signUpButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_voter_login_activiy)
        auth= Firebase.auth
        auth.signOut()

        uid=findViewById(R.id.uid)
        pid=findViewById(R.id.pid)
        logInButton=findViewById(R.id.logInButton)
        signUpButton=findViewById(R.id.signUpButton)


        logInButton.setOnClickListener {
            logIn(uid.text.toString().trim(),pid.text!!.toString())
        }

        signUpButton.setOnClickListener {
            startActivity(
                Intent(this,newVoterSignUpActivity::class.java)
            )
        }


    }

    private fun logIn(email:String,password:String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("EmailPassword", "signInWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("EmailPassword", "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                    updateUI(null)
                }
            }
    }

    private fun updateUI(user: FirebaseUser?) {
        if(user!=null){
            Toast.makeText(this, "$user", Toast.LENGTH_SHORT).show()
            var status="0"
            db.collection("userProfile1").document(user.uid).get().addOnSuccessListener {
                if(it.data?.get("status").toString()=="N"){
                    startActivity(
                        Intent(this,VotingPage::class.java)
                    )
                    finish()
                }
                else if(it.data?.get("status").toString()=="Y"){
                    startActivity(
                        Intent(this,alreadyVotedActivity::class.java)
                    )
                    finish()
                }
            }
        }
        else{
            Toast.makeText(this, "log in failed...try again", Toast.LENGTH_SHORT).show()
            auth.signOut()
            finish()
        }
    }

}