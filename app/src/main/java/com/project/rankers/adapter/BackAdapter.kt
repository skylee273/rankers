package com.project.rankers.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.rankers.R

class DashBoardAdapter(context: Context, items: List<String>) :
        RecyclerView.Adapter<DashBoardCustomViewHolder>(){

    interface ItemClick
    {
        fun onClick(view: View, position: Int)
    }

    var itemClick: ItemClick? = null
    private var context : Context
    private var items : List<String>

    init {
        this.context = context
        this.items = items
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: DashBoardCustomViewHolder, position: Int) {
        val item : String = items[position]

        if(itemClick != null)
        {
            holder.itemView.setOnClickListener { v ->
                itemClick?.onClick(v, position)
            }
        }

        holder.depart.text = item
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashBoardCustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_dashboard_view, parent, false)
        return DashBoardCustomViewHolder(view)
    }

    override fun getItemCount(): Int = this.items.size

    fun getDeaprt(position: Int): String {
        return items[position]
    }
}

class DashBoardCustomViewHolder constructor(itemView: View?)
    : RecyclerView.ViewHolder(itemView!!){
    var depart = itemView!!.findViewById<TextView>(R.id.text_depart)!!
}