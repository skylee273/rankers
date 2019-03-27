package com.project.rankers.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.rankers.R

class ApplayAdapter(context: Context, items: List<String>) :
        RecyclerView.Adapter<ApplyCustomViewHolder>() {

    interface ItemClick {
        fun onClick(view: View, position: Int)
    }

    var itemClick: ItemClick? = null
    private var context: Context
    private var items: List<String>

    init {
        this.context = context
        this.items = items
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ApplyCustomViewHolder, position: Int) {
        val item: String = items[position]

        if (itemClick != null) {
            holder.itemView.setOnClickListener { v ->
                itemClick?.onClick(v, position)
            }
        }


        holder.depart.text = item

    }

    fun getDepart(position: Int): String {
        return items[position]
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApplyCustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_apply, parent, false)
        return ApplyCustomViewHolder(view)
    }

    override fun getItemCount(): Int = this.items.size

}

class ApplyCustomViewHolder constructor(itemView: View?)
    : RecyclerView.ViewHolder(itemView!!) {
    var depart = itemView!!.findViewById<TextView>(R.id.text_depart)!!
}