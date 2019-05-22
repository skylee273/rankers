package com.project.rankers.ui.contest.modify

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.rankers.data.remote.response.ContestResponse
import com.project.rankers.databinding.ItemModifyEmptyViewBinding
import com.project.rankers.databinding.ItemModifyViewBinding
import com.project.rankers.ui.base.BaseViewHolder

class ContestModifyAdapter(val mModifyResponseList: MutableList<ContestResponse.Repo>?) : RecyclerView.Adapter<BaseViewHolder>() {

    private var mListener: ModifyAdapterListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        when (viewType) {
            VIEW_TYPE_NORMAL -> {
                val competitionViewBinding = ItemModifyViewBinding.inflate(LayoutInflater.from(parent.context),
                        parent, false)
                return OperatorViewHolder(competitionViewBinding)
            }
            VIEW_TYPE_EMPTY -> {
                val emptyViewBinding = ItemModifyEmptyViewBinding.inflate(LayoutInflater.from(parent.context),
                        parent, false)
                return EmptyViewHolder(emptyViewBinding)
            }
            else -> {
                val emptyViewBinding = ItemModifyEmptyViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return EmptyViewHolder(emptyViewBinding)
            }
        }
    }

    override fun getItemCount(): Int {
        return if (mModifyResponseList != null && mModifyResponseList.size > 0) {
            mModifyResponseList.size
        } else {
            1
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (mModifyResponseList != null && !mModifyResponseList.isEmpty()) {
            VIEW_TYPE_NORMAL
        } else {
            VIEW_TYPE_EMPTY
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)

    }

    inner class OperatorViewHolder(private val mBinding: ItemModifyViewBinding) : BaseViewHolder(mBinding.root), ContestModifyItemViewModel.ModifyItemViewModelListener {

        private var modifyItemViewModel: ContestModifyItemViewModel? = null

        override fun onBind(position: Int) {
            val contestItem = mModifyResponseList!![position]
            modifyItemViewModel = ContestModifyItemViewModel(contestItem, this)
            mBinding.viewModel = modifyItemViewModel
            mBinding.executePendingBindings()
        }

        override fun onItemClick() {
            val pos = adapterPosition
            mListener!!.onItemClick(mModifyResponseList!![pos])
        }
    }

    inner class EmptyViewHolder(private val mBinding: ItemModifyEmptyViewBinding) : BaseViewHolder(mBinding.root), ContestModifyEmptyViewModel.ModifyEmptyItemViewModelListener {

        override fun onBind(position: Int) {
            val emptyItemViewModel = ContestModifyEmptyViewModel(this)
            mBinding.viewModel = emptyItemViewModel
        }

        override fun onRetryClick() {
            mListener!!.onRetryClick()
        }
    }


    fun addItems(contestItem: List<ContestResponse.Repo>) {
        clearItems()
        mModifyResponseList!!.addAll(contestItem)
        notifyDataSetChanged()
    }

    fun clearItems() {
        mModifyResponseList!!.clear()
    }

    fun setListener(listener: ModifyAdapterListener) {
        this.mListener = listener
    }

    interface ModifyAdapterListener {
        fun onItemClick( item : ContestResponse.Repo)
        fun onRetryClick()
    }

    companion object {
        val VIEW_TYPE_EMPTY = 0

        val VIEW_TYPE_NORMAL = 1
    }
}