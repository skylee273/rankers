package com.project.rankers.ui.contest.tournament

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.rankers.data.model.db.Tournament
import com.project.rankers.databinding.ItemTournamentEmptyViewBinding
import com.project.rankers.databinding.ItemTournamentViewBinding
import com.project.rankers.ui.base.BaseViewHolder

class TournamentAdapter (val mGroupResponseList: MutableList<Tournament>?) : RecyclerView.Adapter<BaseViewHolder>() {

    private var mListener: TournamentAdapterListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        when (viewType) {
            VIEW_TYPE_NORMAL -> {
                val competitionViewBinding = ItemTournamentViewBinding.inflate(LayoutInflater.from(parent.context),
                        parent, false)
                return TournamentViewHolder(competitionViewBinding)
            }
            VIEW_TYPE_EMPTY -> {
                val emptyViewBinding = ItemTournamentEmptyViewBinding.inflate(LayoutInflater.from(parent.context),
                        parent, false)
                return EmptyViewHolder(emptyViewBinding)
            }
            else -> {
                val emptyViewBinding = ItemTournamentEmptyViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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

    inner class TournamentViewHolder(private val mBinding: ItemTournamentViewBinding) : BaseViewHolder(mBinding.root), TournamentItemViewModel.TournamentItemViewModelListener {

        private var tournamentItemViewModel: TournamentItemViewModel? = null

        override fun onBind(position: Int) {
            val item = mGroupResponseList!![position]
            tournamentItemViewModel = TournamentItemViewModel(item, this)
            mBinding.viewModel = tournamentItemViewModel
            mBinding.executePendingBindings()
        }

        override fun onTeamOneClick() {
            val position = adapterPosition
            if(mGroupResponseList!![position].teamOneName.isNotEmpty())
                mListener!!.onShowPeopleDialog(position,1, mGroupResponseList[position])
        }

        override fun onTeamTwoClick() {
            val position = adapterPosition
            if(mGroupResponseList!![position].teamTwoName.isNotEmpty())
                mListener!!.onShowPeopleDialog(position,2, mGroupResponseList[position])
        }


        override fun onItemClick() {

        }

    }

    inner class EmptyViewHolder(private val mBinding: ItemTournamentEmptyViewBinding) : BaseViewHolder(mBinding.root), TournamentEmptyItemViewModel.TournamentEmptyItemViewModelListener {

        override fun onBind(position: Int) {
            val emptyItemViewModel = TournamentEmptyItemViewModel(this)
            mBinding.viewModel = emptyItemViewModel
        }

        override fun onRetryClick() {
            mListener!!.onRetryClick()
        }
    }

    fun getItems() : List<Tournament>? {
        return mGroupResponseList
    }
    fun addItems(groupItem: List<Tournament>) {
        mGroupResponseList!!.addAll(groupItem)
        notifyDataSetChanged()
    }

    fun modifyItem(position: Int, type : Int, playerName : String, rank: String){
        when(type){
            1 -> {
                mGroupResponseList!![position].teamOneName = playerName
                mGroupResponseList[position].teamOneRank = rank
            }
            2 -> {
                mGroupResponseList!![position].teamTwoName = playerName
                mGroupResponseList[position].teamTwoRank = rank
            }
        }
        notifyItemChanged(position)
    }

    fun clearItems() {
        mGroupResponseList!!.clear()
    }


    fun setListener(listener: TournamentAdapterListener) {
        this.mListener = listener
    }

    interface TournamentAdapterListener {
        fun onRetryClick()
        fun onShowPeopleDialog(position : Int, type : Int, item : Tournament)
    }

    companion object {
        val VIEW_TYPE_EMPTY = 0

        val VIEW_TYPE_NORMAL = 1
    }
}