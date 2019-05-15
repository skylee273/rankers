package com.project.rankers.ui.contest.operation.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.rankers.databinding.ItemDashboardEmptyViewBinding
import com.project.rankers.databinding.ItemDashboardViewBinding
import com.project.rankers.ui.base.BaseViewHolder

class DashBoardAdapter(val mDashBoardResponseList: ArrayList<String>) : RecyclerView.Adapter<BaseViewHolder>() {

    private var mListener: DashboardAdapterListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        when (viewType) {
            VIEW_TYPE_NORMAL -> {
                val dashboardViewBinding = ItemDashboardViewBinding.inflate(LayoutInflater.from(parent.context),
                        parent, false)
                return DashboardViewHolder(dashboardViewBinding)
            }
            VIEW_TYPE_EMPTY -> {
                val emptyViewBinding = ItemDashboardEmptyViewBinding.inflate(LayoutInflater.from(parent.context),
                        parent, false)
                return EmptyViewHolder(emptyViewBinding)
            }
            else -> {
                val emptyViewBinding = ItemDashboardEmptyViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return EmptyViewHolder(emptyViewBinding)
            }
        }
    }

    override fun getItemCount(): Int {
        return if (mDashBoardResponseList.size > 0) {
            mDashBoardResponseList.size
        } else {
            1
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (!mDashBoardResponseList.isEmpty()) {
            VIEW_TYPE_NORMAL
        } else {
            VIEW_TYPE_EMPTY
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)

    }

    inner class DashboardViewHolder(private val mBinding: ItemDashboardViewBinding) : BaseViewHolder(mBinding.root), DashBoardItemViewModel.DashboardItemViewListener {

        private var dashBoardItemViewModel: DashBoardItemViewModel? = null

        override fun onBind(position: Int) {
            val dashboarditem = mDashBoardResponseList[position]
            dashBoardItemViewModel = DashBoardItemViewModel(dashboarditem, this)
            mBinding.viewModel = dashBoardItemViewModel
            mBinding.executePendingBindings()
        }

        override fun onItemClick() {
            val pos = adapterPosition
            mListener!!.onItemClick(pos)
        }
    }

    inner class EmptyViewHolder(private val mBinding: ItemDashboardEmptyViewBinding) : BaseViewHolder(mBinding.root), DashBoardItemEmptyViewModel.DashBoardEmptyItemViewModelListener {

        override fun onBind(position: Int) {
            val emptyItemViewModel = DashBoardItemEmptyViewModel(this)
            mBinding.viewModel = emptyItemViewModel
        }

        override fun onRetryClick() {
            mListener!!.onRetryClick()
        }
    }

    fun getPositionItem(position: Int) : String {
        return mDashBoardResponseList[position]
    }

    fun addItems(dashboardItem: List<String>) {
        mDashBoardResponseList.addAll(dashboardItem)
        notifyDataSetChanged()
    }

    fun clearItems() {
        mDashBoardResponseList.clear()
    }

    fun setListener(listener: DashboardAdapterListener) {
        this.mListener = listener
    }

    interface DashboardAdapterListener {
        fun onRetryClick()
        fun onItemClick(position: Int)
    }

    companion object {
        const val VIEW_TYPE_EMPTY = 0
        const val VIEW_TYPE_NORMAL = 1
    }
}