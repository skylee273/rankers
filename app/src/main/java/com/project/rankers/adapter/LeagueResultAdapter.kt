package com.project.rankers.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.rankers.R
import com.project.rankers.model.LEAGUE_RESULT
import com.project.rankers.retrofit.items.ContestItem
import com.project.rankers.retrofit.items.LeagueItem

class LeagueResultAdapter(context: Context, items: MutableList<LEAGUE_RESULT>) :
        RecyclerView.Adapter<LeagueResultCustomViewHolder>(){

    interface ItemClick
    {
        fun onClick(view: View, position: Int)
    }

    var itemClick: ItemClick? = null
    private var context : Context
    private var items : MutableList<LEAGUE_RESULT>

    init {
        this.context = context
        this.items = items
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: LeagueResultCustomViewHolder, position: Int) {
        val item : LEAGUE_RESULT = items[position]

        if(itemClick != null)
        {
            holder.itemView.setOnClickListener { v ->
                itemClick?.onClick(v, position)
            }
        }

        holder.number.text = item.groupNumber.toString() + "조"
        holder.player1.text = item.player1
        holder.player2.text = item.player2
        holder.player3.text = item.player3
        holder.player4.text = item.player4
        holder.player5.text = item.player1
        holder.player6.text = item.player2
        holder.player7.text = item.player3
        holder.player8.text = item.player4

        holder.score1.text = item.score1
        holder.score4.text = item.score1.reversed()
        holder.score2.text = item.score2
        holder.score7.text = item.score2.reversed()
        holder.score3.text = item.score3
        holder.score10.text = item.score3.reversed()
        holder.score5.text = item.score4
        holder.score8.text = item.score4.reversed()
        holder.score6.text = item.score5
        holder.score11.text = item.score5.reversed()
        holder.score9.text = item.score6
        holder.score12.text = item.score6.reversed()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeagueResultCustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_contest_league, parent, false)
        return LeagueResultCustomViewHolder(view)
    }

    override fun getItemCount(): Int = this.items.size

    fun itemClick(position: Int): LEAGUE_RESULT {
        return items[position]
    }

    fun setItem(position: Int, updateItem : LEAGUE_RESULT){
        items[position] = updateItem
        notifyDataSetChanged()
    }
}

class LeagueResultCustomViewHolder constructor(itemView: View?)
    : RecyclerView.ViewHolder(itemView!!){
    var number = itemView!!.findViewById<TextView>(R.id.text_group_number)!!
    var player1 = itemView!!.findViewById<TextView>(R.id.text_player1)!!
    var player2 = itemView!!.findViewById<TextView>(R.id.text_player2)!!
    var player3 = itemView!!.findViewById<TextView>(R.id.text_player3)!!
    var player4 = itemView!!.findViewById<TextView>(R.id.text_player4)!!
    var player5 = itemView!!.findViewById<TextView>(R.id.text_player5)!!
    var player6 = itemView!!.findViewById<TextView>(R.id.text_player6)!!
    var player7 = itemView!!.findViewById<TextView>(R.id.text_player7)!!
    var player8 = itemView!!.findViewById<TextView>(R.id.text_player8)!!
    var score1 = itemView!!.findViewById<TextView>(R.id.text_score1)!!
    var score2 = itemView!!.findViewById<TextView>(R.id.text_score2)!!
    var score3 = itemView!!.findViewById<TextView>(R.id.text_score3)!!
    var score4 = itemView!!.findViewById<TextView>(R.id.text_score4)!!
    var score5 = itemView!!.findViewById<TextView>(R.id.text_score5)!!
    var score6 = itemView!!.findViewById<TextView>(R.id.text_score6)!!
    var score7 = itemView!!.findViewById<TextView>(R.id.text_score7)!!
    var score8 = itemView!!.findViewById<TextView>(R.id.text_score8)!!
    var score9 = itemView!!.findViewById<TextView>(R.id.text_score9)!!
    var score10 = itemView!!.findViewById<TextView>(R.id.text_score10)!!
    var score11 = itemView!!.findViewById<TextView>(R.id.text_score11)!!
    var score12 = itemView!!.findViewById<TextView>(R.id.text_score12)!!

}