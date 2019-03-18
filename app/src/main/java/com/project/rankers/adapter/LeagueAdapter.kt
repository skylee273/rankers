package com.project.rankers.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.rankers.R
import com.project.rankers.model.LEAGUE

class LeagueAdapter (context: Context, items: ArrayList<LEAGUE>) :
        RecyclerView.Adapter<LeagueCustomViewHolder>() {

    interface ItemClick {
        fun onClick(view: View, position: Int)
    }

    var itemClick: ItemClick? = null

    private var context: Context
    private var items: ArrayList<LEAGUE>

    init {
        this.context = context
        this.items = items
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: LeagueCustomViewHolder, position: Int) {
        val item: LEAGUE = items[position]

        if(itemClick != null)
        {
            holder.itemView.setOnClickListener { v ->
                itemClick?.onClick(v, position)
            }
        }

        holder.number.text = item.num.toString() + "조"
        holder.one.text = item.one
        holder.two.text = item.two
        holder.three.text = item.three
        holder.four.text = item.four

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeagueCustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_league, parent, false)
        return LeagueCustomViewHolder(view)
    }

    override fun getItemCount(): Int = this.items.size

}

class LeagueCustomViewHolder constructor(itemView: View?)
    : RecyclerView.ViewHolder(itemView!!) {
    var number = itemView!!.findViewById<TextView>(R.id.text_number)!!
    var one = itemView!!.findViewById<TextView>(R.id.text_one)!!
    var two = itemView!!.findViewById<TextView>(R.id.text_two)!!
    var three = itemView!!.findViewById<TextView>(R.id.text_three)!!
    var four = itemView!!.findViewById<TextView>(R.id.text_four)!!
}