package com.example.practice_fitbit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageButton
import androidx.cardview.widget.CardView
import com.example.practice_fitbit.databinding.ActivityMainBinding

const val Rotation_Time = 4000L

class HomeScreen : AppCompatActivity() {




    private lateinit var logout_Button : ImageButton
    private lateinit var dietimg : ImageButton
    private lateinit var cardno1 : CardView
    private lateinit var cardno2 : CardView
    private lateinit var cardno3 : CardView
    private lateinit var cardno4 : CardView
    private lateinit var cardno5 : CardView
    private lateinit var cardno6 : CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)


        logout_Button = findViewById(R.id.logoutbtn)
        dietimg = findViewById(R.id.dietlogo)
        cardno1 = findViewById(R.id.card1)
        cardno2 = findViewById(R.id.card2)
        cardno3 = findViewById(R.id.card3)
        cardno4 = findViewById(R.id.card4)
        cardno5 = findViewById(R.id.card5)
        cardno6 = findViewById(R.id.card6)


        logout_Button.setOnClickListener {
            startActivity(Intent(this,LoginScreen::class.java))
            finish()
        }


        dietimg.setOnClickListener {
            startActivity(Intent(this,DietPlan::class.java))
            finish()
        }


        cardno1.setOnClickListener {
            startActivity(Intent(this,Card1::class.java))
        }

        cardno2.setOnClickListener {
            startActivity(Intent(this,Card2::class.java))
        }

        cardno3.setOnClickListener {
            startActivity(Intent(this,Card3::class.java))
        }

        cardno4.setOnClickListener {
            startActivity(Intent(this,Card4::class.java))
        }

        cardno5.setOnClickListener {
            startActivity(Intent(this,Card5::class.java))
        }

        cardno6.setOnClickListener {
            startActivity(Intent(this,Card6::class.java))
        }

        val Rotate = AnimationUtils.loadAnimation(this,R.anim.clockwise)



    }
}