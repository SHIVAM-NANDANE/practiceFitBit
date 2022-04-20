package com.example.practice_fitbit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast

import androidx.appcompat.widget.SwitchCompat

class DietPlan : AppCompatActivity() {

    private lateinit var switch: Switch


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diet_plan)



        switch = findViewById(R.id.switchButton)

        // switch button checked change listener
        switch.setOnCheckedChangeListener { compoundButton, b ->
            if (b){
                // when switch button is checked

            }else{
                // if switch button is unchecked
                startActivity(Intent(this,Nonvegdiet::class.java))


            }
        }

    }
}