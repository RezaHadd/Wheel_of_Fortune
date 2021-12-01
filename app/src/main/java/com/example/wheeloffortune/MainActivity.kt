package com.example.wheeloffortune

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import org.apache.commons.lang3.StringUtils

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       startNewGame()

        //Listens to when button is pressed and executes wheel spinning
        val rollButton: Button = findViewById(R.id.button)
        rollButton.setOnClickListener {
            spinWheel()

        }

    }



    var life: Int = 5
    var pointsOnLine = 1 //startingpoint is 1, otherwise multiplication of occurred words will be multiplied by 0. 1 is subsequently subtracted in the pointsOnStake function
    var pointsEarned: Int = 0
    private lateinit var underscoredWord: String    //Words to be underscored with the help of generateUnderscores function
    private lateinit var wordToGuess: String        //word to be randomly selected from text
    var pointsOnStake = 0



    fun startNewGame() {
        life = 5
        pointsEarned = 0

        val healthOnScreen = findViewById(R.id.healthBar_View) as TextView
        healthOnScreen.text = life.toString()


        // picks a random word from GameWords list. generateUnderScores generates underscores of chosen word!!!! HUSK AT ÆNDRE TILBAGE TIL GameWOrds.words.random() og IKKE GameWords.testWords.random()
        wordToGuess = GameWords.words.random()

        //generates underscoredWord <-
        generateUnderscores(wordToGuess)


        val wordsOnScreen = findViewById(R.id.textView2) as TextView
        wordsOnScreen.text = underscoredWord


        Toast.makeText(this, "New Game has begun, Goodluck!", Toast.LENGTH_SHORT).show()

        guessWord(wordToGuess)

        val restartGame_btn: Button = findViewById(R.id.restartGame)
        restartGame_btn.setOnClickListener() {
            startNewGame()
        }


    }

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
        val wheel = NumberGenerator(1).roll()

        when (wheel) {

            3 -> { gainLife()
            }
            2 -> { loseLife()
            }
            1 -> { pointsOnStake = 100
                Toast.makeText(this, "$pointsOnStake is on stake!", Toast.LENGTH_SHORT).show()}
            //4 -> { pointsEarned = 0
            //   Toast.makeText(this, "You've went bankrupt!", Toast.LENGTH_SHORT).show() }


        }
    }


    fun guessWord(wordToGuess: String) {

        val letterSubmitButton = findViewById<Button>(R.id.SubmitLetter_Button)
        val userInput = findViewById<EditText>(R.id.input_field)


        letterSubmitButton.setOnClickListener {

            val letter = userInput.text.toString()[0]


            val indexes = mutableListOf<Int>()
            wordToGuess.forEachIndexed { index, char ->
                if (char.equals(letter, true)) {
                    indexes.add(index)
                }
            }

            var finalUnderscoreWord = "" + underscoredWord // Shows the letters, example; _ _ _ _ _ -> E _ _ _ _
            indexes.forEach { index ->
                val sb = StringBuilder(finalUnderscoreWord).also { it.setCharAt(index, letter) }
                finalUnderscoreWord = sb.toString()
            }


            if (indexes.isEmpty()) {
                loseLife()

            }
            underscoredWord = finalUnderscoreWord

            val wordsOnScreen = findViewById(R.id.textView2) as TextView
            wordsOnScreen.text = underscoredWord




            // adds occurrences of correctly guessed letters onto 'point'. Framework used is an external library called Apache Commons(StringUtils)
            pointsEarned += StringUtils.countMatches(finalUnderscoreWord,letter) * pointsOnStake
            Toast.makeText(this, "You have earned $pointsEarned points", Toast.LENGTH_SHORT).show()




        }
        pointsOnStake = 0
    }




    // hides the letters with underscores.
    fun generateUnderscores(word: String) {
        val sb = StringBuilder()
        word.forEach { char ->
            if (char == '/') {
                sb.append(' ')
            } else {
                sb.append("_")
            }
        }
        underscoredWord = sb.toString()
    }


    class NumberGenerator(val numberOfOptions: Int) {
        fun roll(): Int { return (1..numberOfOptions).random() }
    }



}

