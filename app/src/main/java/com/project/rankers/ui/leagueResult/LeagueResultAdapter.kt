package com.project.rankers.ui.leagueResult

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.rankers.data.remote.response.GroupResponse
import com.project.rankers.databinding.ItemLeagueResultEmptyViewBinding
import com.project.rankers.databinding.ItemLeagueResultViewBinding
import com.project.rankers.ui.base.BaseViewHolder

class LeagueResultAdapter(val mGroupResponseList: MutableList<GroupResponse.Group>?) : RecyclerView.Adapter<BaseViewHolder>() {

    private var mListener: GroupAdapterListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        when (viewType) {
            VIEW_TYPE_NORMAL -> {
                val competitionViewBinding = ItemLeagueResultViewBinding.inflate(LayoutInflater.from(parent.context),
                        parent, false)
                return LeagueResultViewHolder(competitionViewBinding)
            }
            VIEW_TYPE_EMPTY -> {
                val emptyViewBinding = ItemLeagueResultEmptyViewBinding.inflate(LayoutInflater.from(parent.context),
                        parent, false)
                return EmptyViewHolder(emptyViewBinding)
            }
            else -> {
                val emptyViewBinding = ItemLeagueResultEmptyViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return EmptyViewHolder(emptyViewBinding)
            }
        }
    }

    override fun getItemCount(): Int {
        return if (mGroupResponseList != null && mGroupResponseList.size > 0) {
            mGroupResponseList.size
        } else {
            1
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (mGroupResponseList != null && !mGroupResponseList.isEmpty()) {
            VIEW_TYPE_NORMAL
        } else {
            VIEW_TYPE_EMPTY
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)

    }

    inner class LeagueResultViewHolder(private val mBinding: ItemLeagueResultViewBinding) : BaseViewHolder(mBinding.root), LeagueResultItemViewModel.LeagueResultItemViewModelListener {


        private var leagueItemViewModel: LeagueResultItemViewModel? = null

        override fun onBind(position: Int) {
            val contestItem = mGroupResponseList!![position]
            leagueItemViewModel = LeagueResultItemViewModel(contestItem, this)
            mBinding.viewModel = leagueItemViewModel
            mBinding.executePendingBindings()
        }

        override fun onItemClick() {

        }

        override fun onScore1Click() {
            val position = adapterPosition
            mListener!!.onScore(1, position)
        }

        override fun onScore2Click() {
            val position = adapterPosition
            mListener!!.onScore(2, position)
        }

        override fun onScore3Click() {
            val position = adapterPosition
            mListener!!.onScore(3, position)
        }

        override fun onScore4Click() {
            val position = adapterPosition
            mListener!!.onScore(4, position)
        }

        override fun onScore5Click() {
            val position = adapterPosition
            mListener!!.onScore(5, position)
        }

        override fun onScore6Click() {
            val position = adapterPosition
            mListener!!.onScore(6, position)
        }

    }

    inner class EmptyViewHolder(private val mBinding: ItemLeagueResultEmptyViewBinding) : BaseViewHolder(mBinding.root), LeagueResultEmptyItemViewModel.LeagueResultEmptyItemViewModelListener {

        override fun onBind(position: Int) {
            val emptyItemViewModel = LeagueResultEmptyItemViewModel(this)
            mBinding.viewModel = emptyItemViewModel
        }

        override fun onRetryClick() {
            mListener!!.onRetryClick()
        }
    }


    fun modifyPlayer(position: Int, type: Int, winScore: String, loseScore: String) {

        val score = "$winScore : $loseScore"
        when (type) {
            1 -> {
                this.mGroupResponseList!![position].groupTotal1 = getTotalScore(winScore.toInt(), loseScore.toInt(), this.mGroupResponseList[position].groupTotal1!!.toInt()).toString()
                this.mGroupResponseList[position].groupTotal2 = getTotalScore(loseScore.toInt(), winScore.toInt(), this.mGroupResponseList[position].groupTotal2!!.toInt()).toString()
                this.mGroupResponseList[position].groupScore1 = score
            }
            2 -> {
                this.mGroupResponseList!![position].groupTotal1 = getTotalScore(winScore.toInt(), loseScore.toInt(), this.mGroupResponseList[position].groupTotal1!!.toInt()).toString()
                this.mGroupResponseList[position].groupTotal3 = getTotalScore(loseScore.toInt(), winScore.toInt(), this.mGroupResponseList[position].groupTotal3!!.toInt()).toString()
                this.mGroupResponseList[position].groupScore2 = score
            }
            3 -> {
                this.mGroupResponseList!![position].groupTotal1 = getTotalScore(winScore.toInt(), loseScore.toInt(), this.mGroupResponseList[position].groupTotal1!!.toInt()).toString()
                this.mGroupResponseList[position].groupTotal4 = getTotalScore(loseScore.toInt(), winScore.toInt(), this.mGroupResponseList[position].groupTotal4!!.toInt()).toString()
                this.mGroupResponseList[position].groupScore3 = score
            }
            4 -> {
                this.mGroupResponseList!![position].groupTotal2 = getTotalScore(winScore.toInt(), loseScore.toInt(), this.mGroupResponseList[position].groupTotal2!!.toInt()).toString()
                this.mGroupResponseList[position].groupTotal3 = getTotalScore(loseScore.toInt(), winScore.toInt(), this.mGroupResponseList[position].groupTotal3!!.toInt()).toString()
                this.mGroupResponseList[position].groupScore4 = score
            }
            5 -> {
                this.mGroupResponseList!![position].groupTotal2 = getTotalScore(winScore.toInt(), loseScore.toInt(), this.mGroupResponseList[position].groupTotal2!!.toInt()).toString()
                this.mGroupResponseList[position].groupTotal4 = getTotalScore(loseScore.toInt(), winScore.toInt(), this.mGroupResponseList[position].groupTotal4!!.toInt()).toString()
                this.mGroupResponseList[position].groupScore5 = score
            }
            6 -> {
                this.mGroupResponseList!![position].groupTotal3 = getTotalScore(winScore.toInt(), loseScore.toInt(), this.mGroupResponseList[position].groupTotal3!!.toInt()).toString()
                this.mGroupResponseList[position].groupTotal4 = getTotalScore(loseScore.toInt(), winScore.toInt(), this.mGroupResponseList[position].groupTotal4!!.toInt()).toString()
                this.mGroupResponseList[position].groupScore6 = score
            }
        }
        setRank(this.mGroupResponseList!![position].groupTotal1!!.toInt(), this.mGroupResponseList[position].groupTotal2!!.toInt(),
                this.mGroupResponseList[position].groupTotal3!!.toInt(), this.mGroupResponseList[position].groupTotal4!!.toInt(), position)
        notifyItemChanged(position)
    }



    private fun setRank(score1: Int, score2: Int, score3: Int, score4: Int, position: Int) {
        val rankList: MutableList<Int> = mutableListOf(score1, score2, score3, score4)
        rankList.sortedDescending()


        for ((index, value) in rankList.withIndex()) {
            if(value == score1)
                this.mGroupResponseList!![position].groupRank1 = (index + 1).toString()
            if(value == score2)
                this.mGroupResponseList!![position].groupRank1 = (index + 1).toString()
            if(value == score3)
                this.mGroupResponseList!![position].groupRank1 = (index + 1).toString()
            if(value == score4)
                this.mGroupResponseList!![position].groupRank1 = (index + 1).toString()
            when (value) {
                score1 -> this.mGroupResponseList!![position].groupRank1 = (index + 1).toString()
                score2 -> this.mGroupResponseList!![position].groupRank2 = (index + 1).toString()
                score3 -> this.mGroupResponseList!![position].groupRank3 = (index + 1).toString()
                score4 -> this.mGroupResponseList!![position].groupRank4 = (index + 1).toString()
            }
        }
    }

    private fun getTotalScore(winScore: Int, loseScore: Int, currentScore: Int): Int {
        return when {
            winScore > loseScore -> (3 + currentScore)
            winScore < loseScore -> currentScore
            else -> (1 + currentScore)
        }
    }

    fun getItems() : MutableList<GroupResponse.Group>? {
        return mGroupResponseList
    }
    fun addItems(groupItem: List<GroupResponse.Group>) {
        mGroupResponseList!!.addAll(groupItem)
        notifyDataSetChanged()
    }

    fun clearItems() {
        mGroupResponseList!!.clear()
    }


    fun setListener(listener: GroupAdapterListener) {
        this.mListener = listener
    }

    interface GroupAdapterListener {

        fun onScore(type: Int, position: Int)
        //fun onScore(mBinding: ItemLeagueResultViewBinding, type : Int)
        fun onRetryClick()
    }

    companion object {
        val VIEW_TYPE_EMPTY = 0

        val VIEW_TYPE_NORMAL = 1
    }
}