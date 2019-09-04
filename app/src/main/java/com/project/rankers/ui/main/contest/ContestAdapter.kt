package com.project.rankers.ui.main.contest

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.rankers.data.remote.response.ContestResponse
import com.project.rankers.databinding.ItemContestEmptyViewBinding
import com.project.rankers.databinding.ItemContestViewBinding
import com.project.rankers.ui.base.BaseViewHolder
import com.project.rankers.ui.contest.apply.ApplyActivity

class ContestAdapter(val mContestResponseList: MutableList<ContestResponse.Repo>?) : RecyclerView.Adapter<BaseViewHolder>() {

    private var mListener: ContestAdapterListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        when (viewType) {
            VIEW_TYPE_NORMAL -> {
                val competitionViewBinding = ItemContestViewBinding.inflate(LayoutInflater.from(parent.context),
                        parent, false)
                return ContestViewHolder(competitionViewBinding)
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
        return if (mContestResponseList != null && mContestResponseList.size > 0) {
            mContestResponseList.size
        } else {
            1
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (mContestResponseList != null && !mContestResponseList.isEmpty()) {
            VIEW_TYPE_NORMAL
        } else {
            VIEW_TYPE_EMPTY
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    inner class ContestViewHolder(private val mBinding: ItemContestViewBinding) : BaseViewHolder(mBinding.root), ContestItemViewModel.ContestItemViewListener {

        private var contestItemViewModel: ContestItemViewModel? = null

        override fun onBind(position: Int) {
            val contestItem = mContestResponseList!![position]
            contestItemViewModel = ContestItemViewModel(contestItem, this)
            mBinding.viewModel = contestItemViewModel
            mBinding.executePendingBindings()
        }

        override fun onItemClick() {
            val pos = adapterPosition
            val intent = Intent(itemView.context, ApplyActivity::class.java)
            val array = arrayOf(mContestResponseList!![pos].title, mContestResponseList[pos].start + " ~ " + mContestResponseList[pos].end, mContestResponseList[pos].location, mContestResponseList[pos].host, mContestResponseList[pos].depart, mContestResponseList[pos].id)
            intent.putExtra("contest", array)
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
        mContestResponseList!!.addAll(contestItem)
        notifyDataSetChanged()
    }

    fun clearItems() {
        mContestResponseList!!.clear()
    }

    fun setListener(listener: ContestAdapterListener) {
        this.mListener = listener
    }

    interface ContestAdapterListener {
        fun onRetryClick()
    }

    companion object {
        val VIEW_TYPE_EMPTY = 0

        val VIEW_TYPE_NORMAL = 1
    }
}