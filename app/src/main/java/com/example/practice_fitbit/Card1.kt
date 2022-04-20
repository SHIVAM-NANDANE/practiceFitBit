package com.example.practice_fitbit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.ImageButton
import android.widget.TextView

class Card1 : AppCompatActivity() {

    private lateinit var pullupscroll: TextView
    private lateinit var logout_Button : ImageButton
    private lateinit var acclogo : ImageButton




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card1)

        logout_Button = findViewById(R.id.logoutbtn)
        acclogo = findViewById(R.id.accountlogo)

        logout_Button.setOnClickListener {
            startActivity(Intent(this,LoginScreen::class.java))
            finish()

        }


        acclogo.setOnClickListener {
            startActivity(Intent(this,Profile::class.java))

        }
        pullupscroll = findViewById(R.id.pullupscroll)
        pullupscroll.movementMethod = ScrollingMovementMethod()


    }
}