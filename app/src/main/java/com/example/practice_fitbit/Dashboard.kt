package com.example.practice_fitbit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.cardview.widget.CardView

class Dashboard : AppCompatActivity() {

    private lateinit var exercises: CardView
    private lateinit var dietplan: CardView
    private lateinit var video: CardView
    private lateinit var logout_Button : ImageButton
    private lateinit var acclogo : ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        logout_Button = findViewById(R.id.logoutbtn)
        acclogo = findViewById(R.id.accountlogo)

        logout_Button.setOnClickListener {
            startActivity(Intent(this,LoginScreen::class.java))
            finish()

        }


        acclogo.setOnClickListener {
            startActivity(Intent(this,Profile::class.java))

        }

        exercises = findViewById(R.id.excercises)
        dietplan = findViewById(R.id.diet)
        video =findViewById(R.id.video)

        exercises.setOnClickListener {
            startActivity(Intent(this,HomeScreen::class.java))
        }

        dietplan.setOnClickListener {
            startActivity(Intent(this,DietPlan::class.java))
        }

        video.setOnClickListener {
            startActivity(Intent(this,VideoActivity::class.java))
        }
    }
}