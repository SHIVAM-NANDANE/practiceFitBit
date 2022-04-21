package com.example.practice_fitbit.Cards

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.ImageButton
import android.widget.TextView
import com.example.practice_fitbit.MainScreens.LoginScreen
import com.example.practice_fitbit.Dashboard.Profile
import com.example.practice_fitbit.R

class Card5 : AppCompatActivity() {

    private lateinit var absscroll: TextView
    private lateinit var logout_Button : ImageButton
    private lateinit var acclogo : ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card5)

        logout_Button = findViewById(R.id.logoutbtn)
        acclogo = findViewById(R.id.accountlogo)

        logout_Button.setOnClickListener {
            startActivity(Intent(this, LoginScreen::class.java))
            finish()

        }


        acclogo.setOnClickListener {
            startActivity(Intent(this, Profile::class.java))

        }

        absscroll = findViewById(R.id.absscroll)
        absscroll.movementMethod = ScrollingMovementMethod()
    }
}