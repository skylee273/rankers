package com.project.rankers.ui.contest.contestResultSub

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.rankers.databinding.ItemContestResultSubEmptyViewBinding
import com.project.rankers.databinding.ItemContestResultSubViewBinding
import com.project.rankers.ui.base.BaseViewHolder

class ContestResultSubAdapter(val mDepartResponseList: MutableList<String>?) : RecyclerView.Adapter<BaseViewHolder>() {

    private var mListener: ContestResultSubAdapterListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        when (viewType) {
            VIEW_TYPE_NORMAL -> {
                val competitionViewBinding = ItemContestResultSubViewBinding.inflate(LayoutInflater.from(parent.context),
                        parent, false)
                return ContestResultViewHolder(competitionViewBinding)
            }
            VIEW_TYPE_EMPTY -> {
                val emptyViewBinding = ItemContestResultSubEmptyViewBinding.inflate(LayoutInflater.from(parent.context),
                        parent, false)
                return EmptyViewHolder(emptyViewBinding)
            }
            else -> {
                val emptyViewBinding = ItemContestResultSubEmptyViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return EmptyViewHolder(emptyViewBinding)
            }
        }
    }

    override fun getItemCount(): Int {
        return if (mDepartResponseList != null && mDepartResponseList.size > 0) {
            mDepartResponseList.size
        } else {
            1
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (mDepartResponseList != null && !mDepartResponseList.isEmpty()) {
            VIEW_TYPE_NORMAL
        } else {
            VIEW_TYPE_EMPTY
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    inner class ContestResultViewHolder(private val mBinding: ItemContestResultSubViewBinding) : BaseViewHolder(mBinding.root), ContestResultSubItemViewModel.ContestResultItemViewModelListener {

        private var contestItemViewModel: ContestResultSubItemViewModel? = null

        override fun onBind(position: Int) {
            val contestItem = mDepartResponseList!![position]
            contestItemViewModel = ContestResultSubItemViewModel(contestItem, this)
            mBinding.viewModel = contestItemViewModel
            mBinding.executePendingBindings()
        }

        override fun onItemClick() {
            val pos = adapterPosition
            mListener!!.onItemClick(mDepartResponseList!![pos])
        }
    }

    inner class EmptyViewHolder(private val mBinding: ItemContestResultSubEmptyViewBinding) : BaseViewHolder(mBinding.root), ContestResultSubEmptyItemViewModel.ContestResultEmptyItemViewModelListener {

        override fun onBind(position: Int) {
            val emptyItemViewModel = ContestResultSubEmptyItemViewModel(this)
            mBinding.viewModel = emptyItemViewModel
        }

        override fun onRetryClick() {
            mListener!!.onRetryClick()
        }
    }


    fun addItems(departItem: List<String>) {
        mDepartResponseList!!.addAll(departItem)
        notifyDataSetChanged()
    }

    fun clearItems() {
        mDepartResponseList!!.clear()
    }

    fun setListener(listener: ContestResultSubAdapterListener) {
        this.mListener = listener
    }

    interface ContestResultSubAdapterListener {
        fun onItemClick(depart : String)
        fun onRetryClick()
    }

    companion object {
        val VIEW_TYPE_EMPTY = 0

        val VIEW_TYPE_NORMAL = 1
    }
}