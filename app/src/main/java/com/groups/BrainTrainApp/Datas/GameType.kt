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
import com.groups.BrainTrainApp.Components.Math.FindSmaller
import com.groups.BrainTrainApp.Components.Math.FindSum
import com.groups.BrainTrainApp.Components.Memory.FindNewImage
import com.groups.BrainTrainApp.Components.Memory.RememberColor
import com.groups.BrainTrainApp.Components.Memory.RememberImage

import com.groups.BrainTrainApp.Model.Game
import com.groups.BrainTrainApp.R

val mainPageList: ArrayList<Game> = arrayListOf (
    Game("Memory", R.drawable.home_bg_1, GameType.MEMORY, null),
    Game("Attention", R.drawable.home_bg_2, GameType.ATTENTION, null),
    Game("Language", R.drawable.home_bg_3, GameType.LANGUAGE, null),
    Game("Math", R.drawable.home_bg_4, GameType.MATH, null),
)

val attentionList: ArrayList<Game> = arrayListOf (
    Game("Find differences", R.drawable.attention_bg_1, GameType.ATTENTION, FindDifferences::class.java ),
    Game("Find pairs", R.drawable.attention_bg_2, GameType.ATTENTION, FindPairs::class.java ),
    Game("Ship", R.drawable.attention_bg_3, GameType.ATTENTION, Ship::class.java ),
)

val memoryList: ArrayList<Game> = arrayListOf (
    Game("Find new image", R.drawable.memory_bg_2, GameType.MEMORY, FindNewImage::class.java ),
    Game("Remember color", R.drawable.memory_bg_1, GameType.MEMORY, RememberColor::class.java ),
    Game("Remember image", R.drawable.memory_bg_3, GameType.MEMORY, RememberImage::class.java ),
)

val mathList: ArrayList<Game> = arrayListOf (
    Game("Find sum", R.drawable.math_bg_1, GameType.MATH, FindSum::class.java ),
    Game("Find smaller", R.drawable.math_bg_2, GameType.MATH, FindSmaller::class.java )
)

val gameList: Map<GameType, ArrayList<Game>> = mapOf(
    GameType.ATTENTION to attentionList,
)

val languageList: ArrayList<Game> = arrayListOf(
    Game("Find Word Game", R.drawable.brain, GameType.LANGUAGE, FindWordGameActivity::class.java),
    Game("Conjunction Game", R.drawable.brain, GameType.LANGUAGE, ConjunctionGameActivity::class.java),
    Game("Sorting Characters", R.drawable.brain, GameType.LANGUAGE, SortingCharGameActivity::class.java),
    Game("Complete Word Game", R.drawable.brain, GameType.LANGUAGE, CompleteWordGameActivity::class.java),
)

