package com.project.rankers.ui.contest.contestResultLeague

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.rankers.data.remote.response.GroupResponse
import com.project.rankers.databinding.ItemContestResultLeagueEmptyViewBinding
import com.project.rankers.databinding.ItemContestResultLeagueViewBinding
import com.project.rankers.ui.base.BaseViewHolder

class ContestResultLeagueAdapter(val mGroupResponseList: MutableList<GroupResponse.Group>?) : RecyclerView.Adapter<BaseViewHolder>() {

    private var mListener: GroupAdapterListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        when (viewType) {
            VIEW_TYPE_NORMAL -> {
                val competitionViewBinding = ItemContestResultLeagueViewBinding.inflate(LayoutInflater.from(parent.context),
                        parent, false)
                return LeagueResultViewHolder(competitionViewBinding)
            }
            VIEW_TYPE_EMPTY -> {
                val emptyViewBinding = ItemContestResultLeagueEmptyViewBinding.inflate(LayoutInflater.from(parent.context),
                        parent, false)
                return EmptyViewHolder(emptyViewBinding)
            }
            else -> {
                val emptyViewBinding = ItemContestResultLeagueEmptyViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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

    inner class LeagueResultViewHolder(private val mBinding: ItemContestResultLeagueViewBinding) : BaseViewHolder(mBinding.root), ContestResultLeagueItemViewModel.ContestResultLeagueItemViewModelListener {


        private var contestItemViewModel: ContestResultLeagueItemViewModel? = null

        override fun onBind(position: Int) {
            val contestItem = mGroupResponseList!![position]
            contestItemViewModel = ContestResultLeagueItemViewModel(contestItem, this)
            mBinding.viewModel = contestItemViewModel
            mBinding.executePendingBindings()
        }

    }

    inner class EmptyViewHolder(private val mBinding: ItemContestResultLeagueEmptyViewBinding) : BaseViewHolder(mBinding.root), ContestResultLeagueEmptyItemViewModel.ContestResultLeagueEmptyItemViewModelListener {

        override fun onBind(position: Int) {
            val emptyItemViewModel = ContestResultLeagueEmptyItemViewModel(this)
            mBinding.viewModel = emptyItemViewModel
        }
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

    interface GroupAdapterListener

    companion object {
        val VIEW_TYPE_EMPTY = 0

        val VIEW_TYPE_NORMAL = 1
    }
}