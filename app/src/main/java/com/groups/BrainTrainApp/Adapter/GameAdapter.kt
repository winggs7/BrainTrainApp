package com.groups.BrainTrainApp.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import com.groups.BrainTrainApp.Model.Game
import com.groups.BrainTrainApp.R

class GameAdapter(private val gameList: ArrayList<Game>) :
    RecyclerView.Adapter<GameAdapter.ViewHolder>() {
    private var onClickListener: OnClickListener? = null

    class ViewHolder(val layout: View) : RecyclerView.ViewHolder(layout) {
        val name = layout.findViewById<TextView>(R.id.game_name)
        val image = layout.findViewById<ShapeableImageView>(R.id.main_game_image)
        val titleImage = layout.findViewById<ShapeableImageView>(R.id.title_game_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameAdapter.ViewHolder {
        val gameView =
            LayoutInflater.from(parent.context).inflate(R.layout.game_item, parent, false)
        return ViewHolder(gameView)
    }

    override fun onBindViewHolder(holder: GameAdapter.ViewHolder, position: Int) {
        val currentGame = gameList[position]
        holder.name.text = currentGame.name
        holder.image.setImageResource(currentGame.image)
        holder.titleImage.setImageResource(currentGame.image)

        holder.itemView.setOnClickListener {
            if (onClickListener != null) {
                onClickListener!!.onClick(position, currentGame)
            }
        }
    }

    override fun getItemCount(): Int {
        return gameList.size
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    interface OnClickListener {
        fun onClick(position: Int, game: Game)
    }
}