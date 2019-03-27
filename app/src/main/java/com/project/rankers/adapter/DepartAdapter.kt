package com.project.rankers.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.project.rankers.R
import com.project.rankers.model.DepartItem

class DepartAdapter(context: Context, items: List<DepartItem>) :
        RecyclerView.Adapter<DepartCustomViewHolder>(){

    private var context : Context
    private var items : List<DepartItem>

    init {
        this.context = context
        this.items = items
    }

    override fun onBindViewHolder(holder: DepartCustomViewHolder, position: Int) {
        val item : DepartItem = items[position]

        holder.depart.text = item.depart
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DepartCustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_depart, parent, false)
        val customViewHolder = DepartCustomViewHolder(view)
        return customViewHolder
    }

    override fun getItemCount(): Int = this.items.size
}

class DepartCustomViewHolder constructor(itemView: View?)
    : RecyclerView.ViewHolder(itemView!!){
    var depart = itemView!!.findViewById<TextView>(R.id.text_depart)!!

}