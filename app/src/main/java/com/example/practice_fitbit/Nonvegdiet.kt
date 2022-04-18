package com.example.practice_fitbit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Switch
import android.widget.Toast

class Nonvegdiet : AppCompatActivity() {

    private lateinit var switch: Switch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nonvegdiet)

        switch = findViewById(R.id.switchButton)

        // switch button checked change listener
        switch.setOnCheckedChangeListener { compoundButton, b ->
            if (b){
                // when switch button is checked

                startActivity(Intent(this,DietPlan::class.java))

            }else{
                // if switch button is unchecked

                Toast.makeText(this, "Non veg diet", Toast.LENGTH_SHORT).show()

            }
        }
    }
}