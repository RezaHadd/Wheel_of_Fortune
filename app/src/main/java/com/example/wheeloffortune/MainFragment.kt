package com.example.wheeloffortune

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val starGameBtn: Button = view.findViewById(R.id.startGameBtn)
        val wordListBtn: Button = view.findViewById(R.id.listWordsBtn)

        starGameBtn.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_playGameFrag)
        }

        wordListBtn.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_recylerViewFrag)
        }
    }
}