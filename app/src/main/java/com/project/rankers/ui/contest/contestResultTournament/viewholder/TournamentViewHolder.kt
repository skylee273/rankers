package com.project.rankers.ui.contest.contestResultTournament.viewholder

import android.view.View
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.rankers.R
import com.project.rankers.animation.SlideAnimation

class TournamentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val teamOneName: TextView
    val teamTwoName: TextView
    val teamOneScore: TextView
    val teamTwoScore: TextView
    val teamTitle: TextView
    val dateTime: TextView

    private var animation: Animation? = null
    private val rootLayout: RelativeLayout

    init {
        teamTitle = itemView.findViewById(R.id.team_title)
        dateTime = itemView.findViewById(R.id.play_date)
        teamOneName = itemView.findViewById<View>(R.id.team_one_name) as TextView
        teamTwoName = itemView.findViewById<View>(R.id.team_two_name) as TextView
        teamOneScore = itemView.findViewById<View>(R.id.team_one_score) as TextView
        teamTwoScore = itemView.findViewById<View>(R.id.team_two_score) as TextView
        rootLayout = itemView.findViewById<View>(R.id.layout_root) as RelativeLayout

    }

    fun setAnimation(height: Int) {
        animation = SlideAnimation(rootLayout, rootLayout.height,
                height)
        animation!!.interpolator = LinearInterpolator()
        animation!!.duration = 200
        rootLayout.animation = animation
        rootLayout.startAnimation(animation)
    }
}