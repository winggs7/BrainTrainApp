package com.groups.BrainTrainApp.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.imageview.ShapeableImageView
import com.groups.BrainTrainApp.Model.Game
import com.groups.BrainTrainApp.R

class GameAdapter(private val gameList: ArrayList<Game>, private val viewPager2: ViewPager2)
    : RecyclerView.Adapter<GameAdapter.GameViewHolder>(){
    private var onClickListener: GameAdapter.OnClickListener? = null

    class GameViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.game_name)
        val image = itemView.findViewById<ShapeableImageView>(R.id.main_game_image)
        val titleImage = itemView.findViewById<ShapeableImageView>(R.id.title_game_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.game_item, parent, false)
        return GameViewHolder(view)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val currentGame = gameList[position]
        holder.name.text = currentGame.name
        holder.image.setImageResource(currentGame.image)
        holder.titleImage.setImageResource(currentGame.image)

        if (position == gameList.size-1) {
            viewPager2.post(runnable)
        }

        holder.itemView.setOnClickListener {
            if (onClickListener != null) {
                onClickListener!!.onClick(position, currentGame)
            }
        }
    }

    override fun getItemCount(): Int {
        return gameList.size
    }

    private val runnable = Runnable {
        gameList.addAll(gameList)
        notifyDataSetChanged()
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    interface OnClickListener {
        fun onClick(position: Int, game: Game)
    }
}