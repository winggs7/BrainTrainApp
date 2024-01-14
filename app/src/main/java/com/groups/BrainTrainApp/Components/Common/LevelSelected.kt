package com.groups.BrainTrainApp.Components.Common

import GameType
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.activityViewModels
import com.groups.BrainTrainApp.Enum.Level
import com.groups.BrainTrainApp.R

class LevelSelected : Fragment() {
    private lateinit var optionEasy: AppCompatButton
    private lateinit var optionNormal: AppCompatButton
    private lateinit var optionHard: AppCompatButton
    private val viewModel: LevelViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.activity_select_level, container, false)
        optionEasy = view.findViewById(R.id.option_easy)
        optionEasy.setOnClickListener{
            viewModel.selectLevel(Level.EASY)
        }
        optionNormal = view.findViewById(R.id.option_normal)
        optionNormal.setOnClickListener {
            viewModel.selectLevel(Level.NORMAL)
        }
        optionHard = view.findViewById(R.id.option_hard)
        optionHard.setOnClickListener {
            viewModel.selectLevel(Level.HARD)
        }
        return view
    }
}