package com.example.wheeloffortune

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.util.*
import kotlin.random.Random

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

        //val wordToGuess = GameWords.words.random()
        //generateUnderscores(wordToGuess)

        // picks a random word from GameWords list. generateUnderScores generates underscores of chosen word
        wordToGuess = GameWords.words.random()


        generateUnderscores(wordToGuess)


        val wordsOnScreen = findViewById(R.id.textView2) as TextView
        wordsOnScreen.text = underscoredWord
        //wordsOnScreen.text = wordToGuess // midl! Brug ovenstående


        Toast.makeText(this, "New Game has begun, Goodluck!", Toast.LENGTH_SHORT).show()

        guessWord('a',wordToGuess)

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


    fun guessWord(letter: Char, wordToGuess: String) {

        val letterSubmitButton = findViewById<Button>(R.id.SubmitLetter_Button)
        val userInput = findViewById<EditText>(R.id.input_field)


        letterSubmitButton.setOnClickListener {
            val text = userInput.text.toString()

            if (wordToGuess.contains(text, true)) {
                Toast.makeText(this, "works!", Toast.LENGTH_SHORT).show()


            }


            val letter = text[0]

            val indexes = mutableListOf<Int>()
            wordToGuess.forEachIndexed { index, char ->
                if (char.equals(letter, true)) {
                    indexes.add(index)
                }
            }

            var finalUnderscoreWord = "" + underscoredWord // _ _ _ _ _ _ _ -> E _ _ _ _ _ _
            indexes.forEach { index ->
                val sb = StringBuilder(finalUnderscoreWord).also { it.setCharAt(index, letter) }
                finalUnderscoreWord = sb.toString()

            }

            if (indexes.isEmpty()) {
                life--
            }
            underscoredWord = finalUnderscoreWord



        }

    }


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



    fun gameLogic() {
        val secretPhrase = "Word Test"
        var guesses = " " // the player's guesses

        val wordsOnScreen = findViewById<TextView>(R.id.textView2)

        val letterSubmitButton = findViewById<Button>(R.id.SubmitLetter_Button)
        val userInput = findViewById<EditText>(R.id.input_field).toString()


        letterSubmitButton.setOnClickListener{

        var notDone = true
        while (true) {
            notDone = false
            for (secretLetter in secretPhrase.toCharArray()) { // iterates over the letters/char in the array/String
                if (guesses.indexOf(secretLetter) == -1) { //not one of the guesses

                    val wordsOnScreen = findViewById(R.id.textView2) as TextView
                    wordsOnScreen.text = "*"

                    notDone = true
                } else {
                    print(secretLetter)
                }
            }
            if (!notDone) {
                break
            }
            //get user's guess
            println("\nEnter your letter:")
            val letter = userInput
            guesses += letter
        }
        println("Congratulations!")

        }

    }




}

