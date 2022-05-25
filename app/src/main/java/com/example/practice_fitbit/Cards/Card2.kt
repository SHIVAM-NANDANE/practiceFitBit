package com.example.practice_fitbit.Cards

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import com.example.practice_fitbit.MainScreens.LoginScreen
import com.example.practice_fitbit.MainScreens.HomeScreen
import com.example.practice_fitbit.R
import com.example.practice_fitbit.UserProfileActivity

class Card2 : AppCompatActivity() {
    private lateinit var squatsscroll : TextView
    private lateinit var logout_Button : ImageButton
    private lateinit var acclogo : ImageButton
    private lateinit var homeimg : ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card2)

        homeimg = findViewById(R.id.homelogo)
        logout_Button = findViewById(R.id.logoutbtn)
        acclogo = findViewById(R.id.profilelogo)

        homeimg.setOnClickListener {
            startActivity(Intent(this, HomeScreen::class.java))
            finish()
        }

        logout_Button.setOnClickListener {
            startActivity(Intent(this, LoginScreen::class.java))
            finish()

        }


        acclogo.setOnClickListener {
            startActivity(Intent(this, UserProfileActivity::class.java))

        }

        squatsscroll = findViewById(R.id.squatsscroll)
        squatsscroll.movementMethod = ScrollingMovementMethod()
    }

    fun startTimeCounter(view: View) {

        val countTime: TextView = findViewById(R.id.countTime)
        object : CountDownTimer(180000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                countTime.text =
                    "Time remaining: " + (millisUntilFinished / 1000) / 60 + " Min " + (millisUntilFinished / 1000) % 60 + " Sec"

            }

            override fun onFinish() {
                countTime.text = "Done!"
            }
        }.start()
    }
}