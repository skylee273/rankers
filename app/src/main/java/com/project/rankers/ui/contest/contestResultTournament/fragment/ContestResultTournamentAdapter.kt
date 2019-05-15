package com.project.rankers.ui.contest.contestResultTournament.fragment

import android.content.Context
import android.graphics.Color
import android.os.Handler
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.rankers.R
import com.project.rankers.data.model.db.MatchData
import com.project.rankers.ui.contest.contestResultTournament.viewholder.TournamentViewHolder
import java.util.*

class ContestResultTournamentAdapter(private val fragment: ContestResultTournamentColomnFragment, private val context: Context, private var list: ArrayList<MatchData>?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var handler: Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_contest_result_tournament_view, parent, false)
        return TournamentViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var viewHolder: TournamentViewHolder? = null
        if (holder is TournamentViewHolder) {
            viewHolder = holder
            setFields(viewHolder, position)
        }
    }

    private fun setFields(viewHolder: TournamentViewHolder, position: Int) {
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

    fun setList(colomnList: ArrayList<MatchData>) {
        this.list = colomnList
        notifyDataSetChanged()
    }
}