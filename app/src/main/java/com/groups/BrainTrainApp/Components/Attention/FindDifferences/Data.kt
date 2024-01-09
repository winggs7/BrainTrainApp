package com.groups.BrainTrainApp.Components.Attention.FindDifferences

import com.groups.BrainTrainApp.R

val allImageList: Map<Int, Map<String, Float>> = mapOf(
    R.drawable.df_1 to mapOf("x" to 0.6f, "y" to (1/7f), "w" to (1/5f), "h" to (1/6f)),
    R.drawable.df_2 to mapOf("x" to (1/25f), "y" to (1/7f)*4f, "w" to (1/5f), "h" to (1/5f)),
    R.drawable.df_3 to mapOf("x" to (1/6f)*3f, "y" to (1/5f)*3f, "w" to (1/6f), "h" to (1/5f)),
    R.drawable.df_4 to mapOf("x" to (1/12f)*4f, "y" to (1/10f)*6f, "w" to (1/12f), "h" to (1/10f)),
    R.drawable.df_5 to mapOf("x" to (1/7f)*2, "y" to (1/6f)*1f, "w" to (1/7f), "h" to (1/6f)),
    R.drawable.df_6 to mapOf("x" to (1/12f)*9.8f, "y" to (1/8f)*1.1f, "w" to (1/12f), "h" to (1/8f)),
    R.drawable.df_7 to mapOf("x" to (1/9f)*1f, "y" to (1/6f)*2f, "w" to (1/9f), "h" to (1/6f)),
    R.drawable.df_8 to mapOf("x" to (1/9f)*7f, "y" to (1/6f)*3f, "w" to (1/9f), "h" to (1/6f)),
    R.drawable.df_9 to mapOf("x" to (1/9f)*2f, "y" to (1/7f)*5f, "w" to (1/9f), "h" to (1/7f)),
    R.drawable.df_10 to mapOf("x" to (1/14f)*11.7f, "y" to (1/8f)*3f, "w" to (1/14f), "h" to (1/8f)),
    R.drawable.df_11 to mapOf("x" to (1/9f)*6f, "y" to (1/7f)*5f, "w" to (1/9f), "h" to (1/7f)),
    R.drawable.df_12 to mapOf("x" to (1/8f)*1f, "y" to (1/6f)*3f, "w" to (1/8f), "h" to (1/6f)),
    R.drawable.df_13 to mapOf("x" to (1/8f)*7f, "y" to (1/5f)*3f, "w" to (1/8f), "h" to (1/5f)),
    R.drawable.df_14 to mapOf("x" to (1/14f)*1.2f, "y" to (1/8f)*4.9f, "w" to (1/14f), "h" to (1/8f)),
)

const val SCORE_INCREASE = 10