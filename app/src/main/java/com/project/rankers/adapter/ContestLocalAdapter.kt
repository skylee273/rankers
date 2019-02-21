package com.project.rankers.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.project.rankers.R
import com.project.rankers.model.LOCAL

class ContestLocalAdapter(context: Context, items: List<LOCAL>) :
        RecyclerView.Adapter<CustomLocalViewHolder>(){

    private var context : Context
    private var items : List<LOCAL>

    init {
        this.context = context
        this.items = items
    }

    override fun onBindViewHolder(holder: CustomLocalViewHolder, position: Int) {
        val item : LOCAL = items[position]

        holder.type.text = item.type
        holder.date.text = item.date
        holder.title.text = item.title
        holder.address.text = item.address
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomLocalViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_local, parent, false)
        val customViewHolder = CustomLocalViewHolder(view)
        return customViewHolder
    }

    override fun getItemCount(): Int = this.items.size
}

class CustomLocalViewHolder constructor(itemView: View?)
    : RecyclerView.ViewHolder(itemView!!){
    var type = itemView!!.findViewById<TextView>(R.id.text_type)!!
    var date = itemView!!.findViewById<TextView>(R.id.text_date)!!
    var title = itemView!!.findViewById<TextView>(R.id.text_title)!!
    var address = itemView!!.findViewById<TextView>(R.id.text_address)!!
}