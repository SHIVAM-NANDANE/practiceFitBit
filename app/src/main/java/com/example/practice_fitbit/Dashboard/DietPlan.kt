package com.example.practice_fitbit.Dashboard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Switch
import com.example.practice_fitbit.MainScreens.HomeScreen
import com.example.practice_fitbit.MainScreens.LoginScreen

import com.example.practice_fitbit.R

class DietPlan : AppCompatActivity() {

    private lateinit var switch: Switch
    private lateinit var logout_Button : ImageButton
    private lateinit var acclogo : ImageButton
    private lateinit var homeimg : ImageButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diet_plan)



        switch = findViewById(R.id.switchButton)
        homeimg = findViewById(R.id.homelogo)
        logout_Button = findViewById(R.id.logoutbtn)
        acclogo = findViewById(R.id.profilelogo)


        homeimg.setOnClickListener {
            startActivity(Intent(this, Dashboard::class.java))

        }

        logout_Button.setOnClickListener {
            startActivity(Intent(this, LoginScreen::class.java))
            finish()

        }


        acclogo.setOnClickListener {
            startActivity(Intent(this, Profile::class.java))

        }

        // switch button checked change listener
        switch.setOnCheckedChangeListener { compoundButton, b ->
            if (b){
                // when switch button is checked

            }else{
                // if switch button is unchecked
                startActivity(Intent(this, Nonvegdiet::class.java))


            }
        }

    }
}