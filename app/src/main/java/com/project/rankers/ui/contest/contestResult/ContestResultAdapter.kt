package com.project.rankers.ui.contest.contestResult

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.rankers.data.remote.response.ContestResponse
import com.project.rankers.databinding.ItemContestEmptyViewBinding
import com.project.rankers.databinding.ItemContestViewBinding
import com.project.rankers.ui.base.BaseViewHolder
import com.project.rankers.ui.contest.contestResultSub.ContestResultSubActivity
import com.project.rankers.ui.main.contest.ContestEmptyItemViewModel
import com.project.rankers.ui.main.contest.ContestItemViewModel

class ContestResultAdapter(val mOperatorResponseList: MutableList<ContestResponse.Repo>?) : RecyclerView.Adapter<BaseViewHolder>() {

    private var mListener: ContestResultAdapterListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        when (viewType) {
            VIEW_TYPE_NORMAL -> {
                val competitionViewBinding = ItemContestViewBinding.inflate(LayoutInflater.from(parent.context),
                        parent, false)
                return ContestResultViewHolder(competitionViewBinding)
            }
            VIEW_TYPE_EMPTY -> {
                val emptyViewBinding = ItemContestEmptyViewBinding.inflate(LayoutInflater.from(parent.context),
                        parent, false)
                return EmptyViewHolder(emptyViewBinding)
            }
            else -> {
                val emptyViewBinding = ItemContestEmptyViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return EmptyViewHolder(emptyViewBinding)
            }
        }
    }

    override fun getItemCount(): Int {
        return if (mOperatorResponseList != null && mOperatorResponseList.size > 0) {
            mOperatorResponseList.size
        } else {
            1
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (mOperatorResponseList != null && !mOperatorResponseList.isEmpty()) {
            VIEW_TYPE_NORMAL
        } else {
            VIEW_TYPE_EMPTY
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    inner class ContestResultViewHolder(private val mBinding: ItemContestViewBinding) : BaseViewHolder(mBinding.root), ContestItemViewModel.ContestItemViewListener {

        private var contestItemViewModel: ContestItemViewModel? = null

        override fun onBind(position: Int) {
            val contestItem = mOperatorResponseList!![position]
            contestItemViewModel = ContestItemViewModel(contestItem, this)
            mBinding.viewModel = contestItemViewModel
            mBinding.executePendingBindings()
        }

        override fun onItemClick() {
            val pos = adapterPosition
            val intent = Intent(itemView.context, ContestResultSubActivity::class.java)
            val id = mOperatorResponseList!![pos].id
            val depart =  mOperatorResponseList[pos].depart
            intent.putExtra("CONTEST_ID",id)
            intent.putExtra("CONTEST_DEPART",depart)
            itemView.context.startActivity(intent)
        }
    }

    inner class EmptyViewHolder(private val mBinding: ItemContestEmptyViewBinding) : BaseViewHolder(mBinding.root), ContestEmptyItemViewModel.ContestEmptyItemViewModelListener {

        override fun onBind(position: Int) {
            val emptyItemViewModel = ContestEmptyItemViewModel(this)
            mBinding.viewModel = emptyItemViewModel
        }

        override fun onRetryClick() {
            mListener!!.onRetryClick()
        }
    }


    fun addItems(contestItem: List<ContestResponse.Repo>) {
        mOperatorResponseList!!.addAll(contestItem)
        notifyDataSetChanged()
    }

    fun clearItems() {
        mOperatorResponseList!!.clear()
    }

    fun setListener(listener: ContestResultAdapterListener) {
        this.mListener = listener
    }

    interface ContestResultAdapterListener {

        fun onRetryClick()
    }

    companion object {
        val VIEW_TYPE_EMPTY = 0

        val VIEW_TYPE_NORMAL = 1
    }
}