package com.project.rankers.adapter

import android.annotation.SuppressLint
import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.project.rankers.R
import com.project.rankers.retrofit.items.ContestItem


class ContestAdapter(context: Context, items: List<ContestItem>) :
RecyclerView.Adapter<CustomViewHolder>(){

    interface ItemClick
    {
        fun onClick(view: View, position: Int)
    }

    var itemClick: ItemClick? = null
    private var context : Context
    private var items : List<ContestItem>

    init {
        this.context = context
        this.items = items
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val item : ContestItem = items[position]

        if(itemClick != null)
        {
            holder.itemView.setOnClickListener { v ->
                itemClick?.onClick(v, position)
            }
        }

        holder.type.text = item.type
        holder.date.text = item.start + " ~ " + item.end
        holder.title.text = item.title
        holder.address.text = item.location
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_contest, parent, false)
        return CustomViewHolder(view)
    }

    override fun getItemCount(): Int = this.items.size

    fun itemClick(position: Int): ContestItem {
        return items[position]
    }

}

class CustomViewHolder constructor(itemView: View?)
    : RecyclerView.ViewHolder(itemView!!){
    var type = itemView!!.findViewById<TextView>(R.id.text_type)!!
    var date = itemView!!.findViewById<TextView>(R.id.text_date)!!
    var title = itemView!!.findViewById<TextView>(R.id.text_title)!!
    var address = itemView!!.findViewById<TextView>(R.id.text_address)!!
}