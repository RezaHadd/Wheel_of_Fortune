package com.example.wheeloffortune

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.Navigation
import org.apache.commons.lang3.StringUtils

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PlayGameFrag.newInstance] factory method to
 * create an instance of this fragment.
 */
class PlayGameFrag : Fragment() {




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_play_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Listens to when button is pressed and executes wheel spinning
        val rollButton: Button = view.findViewById(R.id.button)
        rollButton.setOnClickListener {
            spinWheel()

        }

        val restartGame_btn: Button = view.findViewById(R.id.restartGame)
        restartGame_btn.setOnClickListener() {
            startNewGame()
        }
    }


    var life: Int = 5
    var pointsEarned: Int = 0
    private lateinit var underscoredWord: String    //Words to be underscored with the help of generateUnderscores function
    private lateinit var wordToGuess: String        //word to be randomly selected from text
    private var lettersUed: String = ""
    var letter: Char = '\u0000'
    var pointsOnStake = 0



    fun startNewGame() {
        life = 5
        pointsEarned = 0
        lettersUed = ""
        pointsOnStake = 0

        val healthOnScreen = view?.findViewById(R.id.healthBar_View) as TextView
        healthOnScreen.text = life.toString()


        // picks a random word from GameWords list. generateUnderScores generates underscores of chosen word!!!! HUSK AT ÆNDRE TILBAGE TIL GameWOrds.words.random() og IKKE GameWords.testWords.random()
        //wordToGuess = GameWords.words.random()
        wordToGuess = "test"


        //generates underscoredWord <-
        generateUnderscores(wordToGuess)


        val wordsOnScreen = view?.findViewById(R.id.textView2) as TextView
        wordsOnScreen.text = underscoredWord


        Toast.makeText(activity, "New Game has begun, Goodluck!", Toast.LENGTH_SHORT).show()

        guessWord(wordToGuess)





    }

    fun gainLife() {
        life++
        Toast.makeText(activity, "You gained a life", Toast.LENGTH_SHORT).show()
        val healthOnScreen = view?.findViewById(R.id.healthBar_View) as TextView
        healthOnScreen.text = life.toString()

    }

    fun loseLife() {
        life--
        Toast.makeText(activity, "You lost a life", Toast.LENGTH_SHORT).show()
        val healthOnScreen = view?.findViewById(R.id.healthBar_View) as TextView
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
                Toast.makeText(activity, "$pointsOnStake is on stake!", Toast.LENGTH_SHORT).show()}
            //4 -> { pointsEarned = 0
            //   Toast.makeText(this, "You've went bankrupt!", Toast.LENGTH_SHORT).show() }


        }
    }


    fun guessWord(wordToGuess: String) {

        val letterSubmitButton = view?.findViewById<Button>(R.id.SubmitLetter_Button)
        val userInput = view?.findViewById<EditText>(R.id.input_field)




        if (letterSubmitButton != null) {
            letterSubmitButton.setOnClickListener {


                if (userInput != null) {
                    letter = userInput.text.toString()[0]
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

                val wordsOnScreen = view?.findViewById(R.id.textView2) as TextView
                wordsOnScreen.text = underscoredWord



                if(underscoredWord.contains(finalUnderscoreWord) && !lettersUed.contains(letter)) {

                    // adds occurrences of correctly guessed letters onto 'point'. Framework used is an external library called Apache Commons(StringUtils). Iterating through a loop would have worked too.
                    pointsEarned += StringUtils.countMatches(finalUnderscoreWord,letter) * pointsOnStake
                    Toast.makeText(activity, "You have earned $pointsEarned points", Toast.LENGTH_SHORT).show()
                    lettersUed += letter
                }

                if(lettersUed.contains(letter)) {
                    Toast.makeText(activity, "$lettersUed has now been used and can't be used again", Toast.LENGTH_SHORT).show()
                }


                if(finalUnderscoreWord == wordToGuess) {
                    gameWon()
                }

                if (life == 0) {
                    gameLost()
                }

            }
        }
        pointsOnStake = 0
    }


    fun gameWon() {
        view?.let { Navigation.findNavController(it).navigate(R.id.action_playGameFrag_to_gameWon) }
    }

    fun gameLost() {
        view?.let { Navigation.findNavController(it).navigate(R.id.action_playGameFrag_to_gameLost) }
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