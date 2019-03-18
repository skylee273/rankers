package com.project.rankers.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.rankers.R
import com.project.rankers.retrofit.items.SingleItem

class SingleAdapter(context: Context, items: List<SingleItem>) :
        RecyclerView.Adapter<SingleCustomViewHolder>(){

    private var context : Context
    private var items : List<SingleItem>

    init {
        this.context = context
        this.items = items
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: SingleCustomViewHolder, position: Int) {
        val item : SingleItem = items[position]

        holder.date.text = item.date
        holder.result.text = item.result
        holder.user.text = item.other
        holder.score.text = item.win + " : " + item.lose
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingleCustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_single, parent, false)
        val customViewHolder = SingleCustomViewHolder(view)
        return customViewHolder
    }

    override fun getItemCount(): Int = this.items.size
}

class SingleCustomViewHolder constructor(itemView: View?)
    : RecyclerView.ViewHolder(itemView!!){
    var date = itemView!!.findViewById<TextView>(R.id.text_date)!!
    var result = itemView!!.findViewById<TextView>(R.id.text_result)!!
    var user = itemView!!.findViewById<TextView>(R.id.text_user)!!
    var score = itemView!!.findViewById<TextView>(R.id.text_score)!!
}