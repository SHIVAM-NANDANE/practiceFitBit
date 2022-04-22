package com.example.practice_fitbit.MainScreens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import android.widget.ImageView
import androidx.cardview.widget.CardView
import com.example.practice_fitbit.Cards.*
import com.example.practice_fitbit.Dashboard.Dashboard
import com.example.practice_fitbit.Dashboard.DietPlan
import com.example.practice_fitbit.R

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
    private lateinit var rotateimage : ImageView
    private lateinit var homeimg : ImageButton



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
        rotateimage = findViewById(R.id.rotatingimage)
        homeimg = findViewById(R.id.homelogo)

        homeimg.setOnClickListener {
            startActivity(Intent(this, Dashboard::class.java))

        }


        logout_Button.setOnClickListener {
            startActivity(Intent(this, LoginScreen::class.java))
            finish()

        }


        dietimg.setOnClickListener {
            startActivity(Intent(this, DietPlan::class.java))

        }


        cardno1.setOnClickListener {
            startActivity(Intent(this, Card1::class.java))
        }

        cardno2.setOnClickListener {
            startActivity(Intent(this, Card2::class.java))
        }

        cardno3.setOnClickListener {
            startActivity(Intent(this, Card3::class.java))
        }

        cardno4.setOnClickListener {
            startActivity(Intent(this, Card4::class.java))
        }

        cardno5.setOnClickListener {
            startActivity(Intent(this, Card5::class.java))
        }

        cardno6.setOnClickListener {
            startActivity(Intent(this, Card6::class.java))
        }

        // loading the animation of
        // clockwise.xml file into a variable
        val rotate = AnimationUtils.loadAnimation(
            this,
            R.anim.clockwise
        )

        // assigning that animation to
        // the image and start animation
        rotateimage.startAnimation(rotate)
    }



    }
