package com.project.rankers.ui.contest.tournamentResult.adapter


import android.content.Context
import android.graphics.Color
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.rankers.R
import com.project.rankers.data.model.db.ColomnData
import com.project.rankers.data.model.db.MatchData
import com.project.rankers.ui.contest.tournamentResult.Fragment.BracketsColomnFragment
import com.project.rankers.ui.contest.tournamentResult.viewholder.BracketsCellViewHolder
import java.util.*

/**
 * Created by Emil on 21/10/17.
 */

class BracketsCellAdapter(private val fragment: BracketsColomnFragment, private val context: Context, private var list: ArrayList<MatchData>?, private var sectionList : ArrayList<ColomnData>?, sectionNumber : Int) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var handler: Boolean = false
    var itemClick: ItemClick? = null
    private var sectionNumber = sectionNumber
    interface ItemClick {
        fun onClick(view: View, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.layout_cell_brackets, parent, false)
        return BracketsCellViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var viewHolder: BracketsCellViewHolder? = null
        if (holder is BracketsCellViewHolder) {
            viewHolder = holder
            setFields(viewHolder, position)
        }
        if (itemClick != null) {
            holder.itemView.setOnClickListener { v ->
                itemClick?.onClick(v, position)
            }
        }

    }

    private fun setFields(viewHolder: BracketsCellViewHolder, position: Int) {
        handler = Handler().postDelayed({ viewHolder.setAnimation(list!![position].height) }, 100)
        viewHolder.dateTime.text = list!![position].competitorOne.number

        viewHolder.teamOneScore.setTextColor(Color.parseColor("#464646"))
        viewHolder.teamOneName.setTextColor(Color.parseColor("#464646"))
        viewHolder.teamTwoScore.setTextColor(Color.parseColor("#464646"))
        viewHolder.teamTwoName.setTextColor(Color.parseColor("#464646"))

        if (list!![position].competitorOne.round == "2강")
            viewHolder.teamTitle.text = "결승"
        else
            viewHolder.teamTitle.text = list!![position].competitorOne.round
        viewHolder.teamOneName.text = list!![position].competitorOne.name
        viewHolder.teamTwoName.text = list!![position].competitorTwo.name
        viewHolder.teamOneScore.text = list!![position].competitorOne.score
        viewHolder.teamTwoScore.text = list!![position].competitorTwo.score
        when {
            list!![position].competitorOne.score.toInt() > list!![position].competitorTwo.score.toInt() -> {
                viewHolder.teamOneScore.setTextColor(Color.parseColor("#2a5291"))
                viewHolder.teamOneName.setTextColor(Color.parseColor("#2a5291"))
            }
            list!![position].competitorOne.score.toInt() < list!![position].competitorTwo.score.toInt() -> {
                viewHolder.teamTwoScore.setTextColor(Color.parseColor("#2a5291"))
                viewHolder.teamTwoName.setTextColor(Color.parseColor("#2a5291"))
            }
        }

    }

    override fun getItemCount(): Int {
        return this.list!!.size
    }

    fun getList(position: Int): MatchData {
        return list!![position]
    }
    fun getAllItem() : ArrayList<ColomnData>?{
        return sectionList
    }

    fun modifyList(position: Int, winScore: String, loseScore: String) {
        this.list!![position].competitorOne.score = winScore
        this.list!![position].competitorTwo.score = loseScore
        if((sectionList!!.size-1) != sectionNumber){
            when {
                winScore.toInt() > loseScore.toInt() -> {
                    if(position % 2 == 0){
                        sectionList!![sectionNumber+1].matches[position/2].competitorOne.name = list!![position].competitorOne.name
                    }else{
                        sectionList!![sectionNumber+1].matches[(position-1)/2].competitorTwo.name = list!![position].competitorOne.name
                    }
                }
                winScore.toInt() < loseScore.toInt() -> {
                    if(position % 2 == 0){
                        sectionList!![sectionNumber+1].matches[position/2].competitorOne.name = list!![position].competitorTwo.name
                    }else{
                        sectionList!![sectionNumber+1].matches[(position-1)/2].competitorTwo.name = list!![position].competitorTwo.name
                    }
                }
            }
        }

        notifyItemChanged(position)
    }

    fun setList(colomnList: ArrayList<MatchData>) {
        this.list = colomnList
        notifyDataSetChanged()
    }
}
