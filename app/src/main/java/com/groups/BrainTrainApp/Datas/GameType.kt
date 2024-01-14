package com.groups.BrainTrainApp.Datas

import GameType
import com.groups.BrainTrainApp.Components.Attention.FindDifferences.FindDifferences
import com.groups.BrainTrainApp.Components.Attention.FindPairs.FindPairs
import com.groups.BrainTrainApp.Components.Attention.Language.CompleteWordGameActivity
import com.groups.BrainTrainApp.Components.Attention.Language.ConjunctionGameActivity
import com.groups.BrainTrainApp.Components.Attention.Language.FindWordGameActivity
import com.groups.BrainTrainApp.Components.Attention.Language.LanguageActivity
import com.groups.BrainTrainApp.Components.Attention.Language.SortingCharGameActivity
import com.groups.BrainTrainApp.Components.Attention.Ship.Ship
import com.groups.BrainTrainApp.Model.Game
import com.groups.BrainTrainApp.R

val mainPageList: ArrayList<Game> = arrayListOf (
    Game("Memory", R.drawable.brain, GameType.MEMORY, null),
    Game("Attention", R.drawable.puzzle, GameType.ATTENTION, null),
    Game("Language", R.drawable.brain, GameType.LANGUAGE, null),
    Game("Math", R.drawable.puzzle, GameType.MATH, null),
)

val attentionList: ArrayList<Game> = arrayListOf (
    Game("Find differences", R.drawable.brain, GameType.ATTENTION, FindDifferences::class.java ),
    Game("Find pairs", R.drawable.brain, GameType.ATTENTION, FindPairs::class.java ),
    Game("Ship", R.drawable.brain, GameType.ATTENTION, Ship::class.java ),
)
val languageList: ArrayList<Game> = arrayListOf(
    Game("Find  Word Game", R.drawable.brain, GameType.LANGUAGE, FindWordGameActivity::class.java),
    Game("Conjunction Game", R.drawable.brain, GameType.LANGUAGE, ConjunctionGameActivity::class.java),
    Game("Sorting Characters", R.drawable.brain, GameType.LANGUAGE, SortingCharGameActivity::class.java),
    Game("Complete Word Game", R.drawable.brain, GameType.LANGUAGE, CompleteWordGameActivity::class.java),
)