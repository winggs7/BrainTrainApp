package com.groups.BrainTrainApp.Model

import GameType

data class Game (val name: String, val image: Int, val type: GameType, val className: Class<*>?)