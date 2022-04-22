package com.example.practice_fitbit.Cards

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.ImageButton
import android.widget.TextView
import com.example.practice_fitbit.MainScreens.LoginScreen
import com.example.practice_fitbit.Dashboard.Profile
import com.example.practice_fitbit.MainScreens.HomeScreen
import com.example.practice_fitbit.R

class Card4 : AppCompatActivity() {
    private lateinit var bicepscroll: TextView
    private lateinit var logout_Button : ImageButton
    private lateinit var acclogo : ImageButton
    private lateinit var homeimg : ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card4)

        logout_Button = findViewById(R.id.logoutbtn)
        acclogo = findViewById(R.id.profilelogo)
        homeimg = findViewById(R.id.homelogo)

        homeimg.setOnClickListener {
            startActivity(Intent(this, HomeScreen::class.java))

        }

        logout_Button.setOnClickListener {
            startActivity(Intent(this, LoginScreen::class.java))
            finish()

        }


        acclogo.setOnClickListener {
            startActivity(Intent(this, Profile::class.java))

        }

        bicepscroll = findViewById(R.id.bicepscroll)
        bicepscroll.movementMethod = ScrollingMovementMethod()
    }
}