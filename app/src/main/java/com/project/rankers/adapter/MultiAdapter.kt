package com.project.rankers.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.rankers.R
import com.project.rankers.data.remote.domain.MultiItem

class MultiAdapter(context: Context, items: List<MultiItem>) :
        RecyclerView.Adapter<MutilCustomViewHolder>(){

    private var context : Context
    private var items : List<MultiItem>

    init {
        this.context = context
        this.items = items
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MutilCustomViewHolder, position: Int) {
        val item : MultiItem = items[position]

        holder.date.text = item.date
        holder.result.text = item.result
        holder.user.text = item.partner + "," + item.other + "," + item.otherpartner
        holder.score.text = item.win + " : " + item.lose
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MutilCustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_multi, parent, false)
        val customViewHolder = MutilCustomViewHolder(view)
        return customViewHolder
    }

    override fun getItemCount(): Int = this.items.size
}

class MutilCustomViewHolder constructor(itemView: View?)
    : RecyclerView.ViewHolder(itemView!!){
    var date = itemView!!.findViewById<TextView>(R.id.text_date)!!
    var result = itemView!!.findViewById<TextView>(R.id.text_result)!!
    var user = itemView!!.findViewById<TextView>(R.id.text_user)!!
    var score = itemView!!.findViewById<TextView>(R.id.text_score)!!
}