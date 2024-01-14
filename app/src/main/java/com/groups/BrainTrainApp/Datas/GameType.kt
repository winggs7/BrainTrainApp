package com.groups.BrainTrainApp.Datas

import GameType
import com.groups.BrainTrainApp.Components.Attention.FindDifferences.FindDifferences
import com.groups.BrainTrainApp.Components.Attention.FindPairs.FindPairs
import com.groups.BrainTrainApp.Components.Attention.Ship.Ship
import com.groups.BrainTrainApp.Components.Math.FindSmaller
import com.groups.BrainTrainApp.Components.Math.FindSum
import com.groups.BrainTrainApp.Components.Memory.FindNewImage
import com.groups.BrainTrainApp.Components.Memory.RememberColor
import com.groups.BrainTrainApp.Components.Memory.RememberImage

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

val memoryList: ArrayList<Game> = arrayListOf (
    Game("Find new image", R.drawable.brain, GameType.MEMORY, FindNewImage::class.java ),
    Game("Remember color", R.drawable.brain, GameType.MEMORY, RememberColor::class.java ),
    Game("Remember image", R.drawable.brain, GameType.MEMORY, RememberImage::class.java ),
)

val mathList: ArrayList<Game> = arrayListOf (
    Game("Find sum", R.drawable.brain, GameType.MATH, FindSum::class.java ),
    Game("Find smaller", R.drawable.brain, GameType.MATH, FindSmaller::class.java )
)

val gameList: Map<GameType, ArrayList<Game>> = mapOf(
    GameType.ATTENTION to attentionList,
)

