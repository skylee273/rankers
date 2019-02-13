package com.project.rankers.adprer

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.project.rankers.R
import com.project.rankers.model.KTA

class ContestAdapter(context: Context, items: List<KTA>) :
        RecyclerView.Adapter<CustomViewHolder>(){

    private var context : Context
    private var items : List<KTA>

    init {
        this.context = context
        this.items = items
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val item : KTA =  items.get(position)

        holder.type.text = item.type
        holder.date.text = item.date
        holder.title.text = item.title
        holder.address.text = item.address
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_contest, parent, false)
        val customViewHolder = CustomViewHolder(view)
        return customViewHolder
    }

    override fun getItemCount(): Int = this.items.size
}

class CustomViewHolder constructor(itemView: View?)
    : RecyclerView.ViewHolder(itemView!!){
    var type = itemView!!.findViewById<TextView>(R.id.text_type)!!
    var date = itemView!!.findViewById<TextView>(R.id.text_date)!!
    var title = itemView!!.findViewById<TextView>(R.id.text_title)!!
    var address = itemView!!.findViewById<TextView>(R.id.text_address)!!
}