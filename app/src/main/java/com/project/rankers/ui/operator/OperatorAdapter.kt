package com.project.rankers.ui.operator

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.rankers.ui.base.BaseViewHolder
import com.project.rankers.data.remote.response.ContestResponse
import com.project.rankers.databinding.ItemOperatorEmptyViewBinding
import com.project.rankers.databinding.ItemOperatorViewBinding
import com.project.rankers.ui.operation.ContestOperationActivity

class OperatorAdapter(val mOperatorResponseList: MutableList<ContestResponse.Repo>?) : RecyclerView.Adapter<BaseViewHolder>() {

    private var mListener: OperatorAdapterListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        when (viewType) {
            VIEW_TYPE_NORMAL -> {
                val competitionViewBinding = ItemOperatorViewBinding.inflate(LayoutInflater.from(parent.context),
                        parent, false)
                return OperatorViewHolder(competitionViewBinding)
            }
            VIEW_TYPE_EMPTY -> {
                val emptyViewBinding = ItemOperatorEmptyViewBinding.inflate(LayoutInflater.from(parent.context),
                        parent, false)
                return EmptyViewHolder(emptyViewBinding)
            }
            else -> {
                val emptyViewBinding = ItemOperatorEmptyViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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

    inner class OperatorViewHolder(private val mBinding: ItemOperatorViewBinding) : BaseViewHolder(mBinding.root), OperatorItemViewModel.OperatorItemViewModelListener {

        private var operatorItemViewModel: OperatorItemViewModel? = null

        override fun onBind(position: Int) {
            val contestItem = mOperatorResponseList!![position]
            operatorItemViewModel = OperatorItemViewModel(contestItem, this)
            mBinding.viewModel = operatorItemViewModel
            mBinding.executePendingBindings()
        }

        override fun onItemClick() {
            val pos = adapterPosition
            val intent = Intent(itemView.context, ContestOperationActivity::class.java)
            val id = mOperatorResponseList!![pos].id
            val depart =  mOperatorResponseList!![pos].depart
            intent.putExtra("CONTEST_ID",id)
            intent.putExtra("CONTEST_DEPART",depart)
            itemView.context.startActivity(intent)
        }
    }

    inner class EmptyViewHolder(private val mBinding: ItemOperatorEmptyViewBinding) : BaseViewHolder(mBinding.root), OperatorEmptyItemViewModel.OperatorEmptyItemViewModelListener {

        override fun onBind(position: Int) {
            val emptyItemViewModel = OperatorEmptyItemViewModel(this)
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

    fun setListener(listener: OperatorAdapterListener) {
        this.mListener = listener
    }

    interface OperatorAdapterListener {

        fun onRetryClick()
    }

    companion object {
        val VIEW_TYPE_EMPTY = 0

        val VIEW_TYPE_NORMAL = 1
    }
}