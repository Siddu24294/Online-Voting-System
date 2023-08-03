package com.example.onlinevotingsystem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class newVoterSignUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
// ...
// Initialize Firebase Aut
    private lateinit var signUpButton: Button
    private lateinit var uid:EditText
    private lateinit var pid:EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_voter_sign_up)

        auth = Firebase.auth
        signUpButton=findViewById(R.id.signUpButton)
        uid=findViewById(R.id.uid)
        pid=findViewById(R.id.pid)

        signUpButton.setOnClickListener {
            Toast.makeText(this, uid.text.toString(), Toast.LENGTH_SHORT).show()
            Toast.makeText(this, pid.text.toString(), Toast.LENGTH_SHORT).show()
            signUp(uid.text.toString().trim(), pid.text.toString())
        }
    }

    private fun updateUI(user: FirebaseUser?) {
        Toast.makeText(this, user!!.uid, Toast.LENGTH_SHORT).show()
        startActivity(
            Intent(this, VoterProfileRegister::class.java)
        )
        finish()
    }

    private fun signUp(uid:String,pid:String){
        Toast.makeText(this, "button pressed", Toast.LENGTH_SHORT).show()
        auth.createUserWithEmailAndPassword(uid, pid)
            .addOnCompleteListener(this) { task ->
                Toast.makeText(this, "task started", Toast.LENGTH_SHORT).show()
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("EmailPassword", "createUserWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("EmailPassword", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                    updateUI(null)
                }
            }
    }
}