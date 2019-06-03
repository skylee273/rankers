package com.project.rankers.ui.reply

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.rankers.data.remote.response.ReplyRepo
import com.project.rankers.databinding.ItemReplyEmptyViewBinding
import com.project.rankers.databinding.ItemReplyViewBinding
import com.project.rankers.ui.base.BaseViewHolder

class ReplyAdapter(val mReplyResponseList: MutableList<ReplyRepo.Reply>?) : RecyclerView.Adapter<BaseViewHolder>() {

    private var mListener: ReplyAdapterListner? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        when (viewType) {
            VIEW_TYPE_NORMAL -> {
                val competitionViewBinding = ItemReplyViewBinding.inflate(LayoutInflater.from(parent.context),
                        parent, false)
                return MatchViewHolder(competitionViewBinding)
            }
            VIEW_TYPE_EMPTY -> {
                val emptyViewBinding = ItemReplyEmptyViewBinding.inflate(LayoutInflater.from(parent.context),
                        parent, false)
                return EmptyViewHolder(emptyViewBinding)
            }
            else -> {
                val emptyViewBinding = ItemReplyEmptyViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return EmptyViewHolder(emptyViewBinding)
            }
        }
    }

    override fun getItemCount(): Int {
        return if (mReplyResponseList != null && mReplyResponseList.size > 0) {
            mReplyResponseList.size
        } else {
            1
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (mReplyResponseList != null && !mReplyResponseList.isEmpty()) {
            VIEW_TYPE_NORMAL
        } else {
            VIEW_TYPE_EMPTY
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)

    }

    inner class MatchViewHolder(private val mBinding: ItemReplyViewBinding) : BaseViewHolder(mBinding.root), ReplyItemViewModel.ReplyItemViewModelListener {

        private var replyItemViewModel: ReplyItemViewModel? = null

        override fun onBind(position: Int) {
            val replyItem = mReplyResponseList!![position]
            replyItemViewModel = ReplyItemViewModel(replyItem, this)
            mBinding.viewModel = replyItemViewModel
            mBinding.executePendingBindings()
        }

        override fun onItemClick() {

        }
    }

    inner class EmptyViewHolder(private val mBinding: ItemReplyEmptyViewBinding) : BaseViewHolder(mBinding.root), ReplyEmptyItemViewModel.ReplyEmptyItemViewModelListener {

        override fun onBind(position: Int) {
            val emptyItemViewModel = ReplyEmptyItemViewModel(this)
            mBinding.viewModel = emptyItemViewModel
        }

        override fun onRetryClick() {
            mListener!!.onRetryClick()
        }
    }


    fun addItems(replyItem: List<ReplyRepo.Reply>) {
        clearItems()
        mReplyResponseList!!.addAll(replyItem)
        notifyDataSetChanged()
    }

    fun clearItems() {
        mReplyResponseList!!.clear()
    }

    fun setListener(listener: ReplyAdapterListner) {
        this.mListener = listener
    }

    interface ReplyAdapterListner {

        fun onRetryClick()
    }

    companion object {
        val VIEW_TYPE_EMPTY = 0

        val VIEW_TYPE_NORMAL = 1
    }
}