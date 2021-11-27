package com.example.wheeloffortune

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Listens to when button is pressed
        val rollButton: Button = findViewById(R.id.button)
        rollButton.setOnClickListener {


            spinWheel()

        }


    }


    var life: Int = 5
    var point: Int = 0


/*  // Skal nok ikke bruges, men kun hvis
    fun lifeRoller() {
        val roll = NumberGenerator(2).roll()

        when(roll) {
            1 -> life++
            2 -> life--
        }
    }
    */


    fun gainLife() {
        life++
        Toast.makeText(this, "You gained a life", Toast.LENGTH_SHORT).show()
        val healthOnScreen = findViewById(R.id.healthBar_View) as TextView
        healthOnScreen.text = life.toString()
    }

    fun loseLife() {
        life--
        Toast.makeText(this, "You lost a life", Toast.LENGTH_SHORT).show()
        val healthOnScreen = findViewById(R.id.healthBar_View) as TextView
        healthOnScreen.text = life.toString()
    }


    fun spinWheel() {
        val wheel = NumberGenerator(2).roll()

        when (wheel) {

            1 -> { gainLife() }
            2 -> { loseLife() }


        }
    }


    class NumberGenerator(val numberOfOptions: Int) {

        fun roll(): Int {
            return (1..numberOfOptions).random()
        }
    }

    class GuessWord(pointsToGamble: Int) {

    }
}

