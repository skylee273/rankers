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
            mListener!!.onScore(mBinding,1)
        }

        override fun onScore2Click() {
            mListener!!.onScore(mBinding,2)
        }
        override fun onScore3Click() {
            mListener!!.onScore(mBinding,3)
        }

        override fun onScore5Click() {
            mListener!!.onScore(mBinding,5)
        }

        override fun onScore6Click() {
            mListener!!.onScore(mBinding,6)
        }

        override fun onScore9Click() {
            mListener!!.onScore(mBinding,9)
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

        fun onScore(mBinding: ItemLeagueResultViewBinding, type : Int)
        fun onRetryClick()
    }

    companion object {
        val VIEW_TYPE_EMPTY = 0

        val VIEW_TYPE_NORMAL = 1
    }
}