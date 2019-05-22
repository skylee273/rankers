package com.project.rankers.ui.contest.operation.result

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.rankers.databinding.ItemResultContestEmptyViewBinding
import com.project.rankers.databinding.ItemResultContestViewBinding
import com.project.rankers.ui.base.BaseViewHolder

class ResultContestAdapter(val mResultResponseList: ArrayList<String>) : RecyclerView.Adapter<BaseViewHolder>() {

    private var mListener: ResultContestAdapterListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        when (viewType) {
            VIEW_TYPE_NORMAL -> {
                val dashboardViewBinding = ItemResultContestViewBinding.inflate(LayoutInflater.from(parent.context),
                        parent, false)
                return ResultContestViewHolder(dashboardViewBinding)
            }
            VIEW_TYPE_EMPTY -> {
                val emptyViewBinding = ItemResultContestEmptyViewBinding.inflate(LayoutInflater.from(parent.context),
                        parent, false)
                return EmptyViewHolder(emptyViewBinding)
            }
            else -> {
                val emptyViewBinding = ItemResultContestEmptyViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return EmptyViewHolder(emptyViewBinding)
            }
        }
    }

    override fun getItemCount(): Int {
        return if (mResultResponseList.size > 0) {
            mResultResponseList.size
        } else {
            1
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (!mResultResponseList.isEmpty()) {
            VIEW_TYPE_NORMAL
        } else {
            VIEW_TYPE_EMPTY
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)

    }

    inner class ResultContestViewHolder(private val mBinding: ItemResultContestViewBinding) : BaseViewHolder(mBinding.root), ResultContestItemViewModel.ReusltContestItemViewListener {

        private var resultItemViewModel: ResultContestItemViewModel? = null

        override fun onBind(position: Int) {
            val resultItem = mResultResponseList[position]
            resultItemViewModel = ResultContestItemViewModel(resultItem, this)
            mBinding.viewModel = resultItemViewModel
            mBinding.executePendingBindings()
        }

        override fun onItemClick() {
            val pos = adapterPosition
            mListener!!.onItemClick(pos)
        }
    }

    inner class EmptyViewHolder(private val mBinding: ItemResultContestEmptyViewBinding) : BaseViewHolder(mBinding.root), ResultContestEmptyViewModel.ResultContestEmptyItemViewModelListener {

        override fun onBind(position: Int) {
            val emptyItemViewModel = ResultContestEmptyViewModel(this)
            mBinding.viewModel = emptyItemViewModel
        }

        override fun onRetryClick() {
            mListener!!.onRetryClick()
        }
    }

    fun getPositionItem(position: Int) : String {
        return mResultResponseList[position]
    }

    fun addItems(dashboardItem: List<String>) {
        mResultResponseList.addAll(dashboardItem)
        notifyDataSetChanged()
    }

    fun clearItems() {
        mResultResponseList.clear()
    }

    fun setListener(listener: ResultContestAdapterListener) {
        this.mListener = listener
    }

    interface ResultContestAdapterListener {
        fun onRetryClick()
        fun onItemClick(position: Int)
    }

    companion object {
        const val VIEW_TYPE_EMPTY = 0
        const val VIEW_TYPE_NORMAL = 1
    }
}