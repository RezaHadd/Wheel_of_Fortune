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
    var point: Int = 0
    var hiddenWord: String = "this is a test"
    private lateinit var underscoredWord: String
    private lateinit var wordToGuess: String



    fun startNewGame() {
        life = 5
        val healthOnScreen = findViewById(R.id.healthBar_View) as TextView
        healthOnScreen.text = life.toString()
        point = 0



        // picks a random word from GameWords list. generateUnderScores generates underscores of chosen word
        wordToGuess = GameWords.words.random()

        //generates underscoredWord <-
        generateUnderscores(wordToGuess)




        val wordsOnScreen = findViewById(R.id.textView2) as TextView
        wordsOnScreen.text = underscoredWord
        //wordsOnScreen.text = wordToGuess // midl! Brug ovenstående


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
        val wheel = NumberGenerator(2).roll()

        when (wheel) {

            1 -> { gainLife() }
            2 -> { loseLife() }
            // 3 -> { GuessWord(100) }

        }
    }


    fun guessWord(wordToGuess: String) {

        val letterSubmitButton = findViewById<Button>(R.id.SubmitLetter_Button)
        val userInput = findViewById<EditText>(R.id.input_field)


        letterSubmitButton.setOnClickListener {
            val text = userInput.text.toString()
            val letter = text[0]

            if (wordToGuess.contains(text, true)) {
                Toast.makeText(this, "works!", Toast.LENGTH_SHORT).show()


            }


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


            //Counts the number of occured letter correctly guessed.
            countSubstringInstances(finalUnderscoreWord,text)

        }

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


    fun updateUI() {
        // TODO
    }


        //uses external Apache Common library for function
    fun countSubstringInstances(string: String, substringToCount: String) {


        var count = StringUtils.countMatches(string,substringToCount).toString()
        Toast.makeText(this, count + " correct letters", Toast.LENGTH_SHORT).show()

        point = count.toInt()




         Toast.makeText(this, point.toString(), Toast.LENGTH_SHORT).show()

    }



}

