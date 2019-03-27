package com.project.rankers.ui.register

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.rankers.base.BaseViewHolder
import com.project.rankers.databinding.ItemCompetiotionEmptyViewBinding
import com.project.rankers.databinding.ItemCompetitionViewBinding
import com.project.rankers.databinding.ItemContestRegisterViewBinding
import com.project.rankers.model.DepartItem
import com.project.rankers.retrofit.items.ContestItem
import com.project.rankers.ui.activity.ApplyActivity
import com.project.rankers.ui.competition.CompetitionAdapter
import com.project.rankers.ui.competition.CompetitionItemViewModel

class ContestRegisterAdapter(private val mContestRegisterList: MutableList<DepartItem>?) : RecyclerView.Adapter<BaseViewHolder>()  {


    private var mListener : ContestRegisterAdapterListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        when (viewType) {
            CompetitionAdapter.VIEW_TYPE_NORMAL -> {
                val contestRegisterViewBinding = ItemContestRegisterViewBinding.inflate(LayoutInflater.from(parent.context),
                        parent, false)
                return ContestRegisterViewHolder(contestRegisterViewBinding)
            }
            else -> {
                val contestRegisterViewBinding = ItemContestRegisterViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return ContestRegisterViewHolder(contestRegisterViewBinding)
            }
        }
    }

    override fun getItemCount(): Int {
        return if (mContestRegisterList != null && mContestRegisterList.size > 0) {
            mContestRegisterList.size
        } else {
            1
        }
    }

    override fun getItemViewType(position: Int): Int {
        return ContestRegisterAdapter.VIEW_TYPE_NORMAL
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }
    inner class ContestRegisterViewHolder(private val mBinding: ItemContestRegisterViewBinding) : BaseViewHolder(mBinding.root), ContestRegisterItemViewModel.ContestRegisterItemViewModelListner {

        private var contestRegisterItemViewModel: ContestRegisterItemViewModel? = null

        override fun onBind(position: Int) {
            if(mContestRegisterList!!.size > 0){
                val departItem = mContestRegisterList!![position]
                contestRegisterItemViewModel = ContestRegisterItemViewModel(departItem, this)
                mBinding.viewModel = contestRegisterItemViewModel
                mBinding.executePendingBindings()
            }
            }

        override fun onItemClick() {

        }
    }

    fun addItems(departItem: List<DepartItem>) {
        mContestRegisterList!!.addAll(departItem)
        notifyDataSetChanged()
    }

    fun clearItems() {
        mContestRegisterList!!.clear()
    }

    fun setListener(listener: ContestRegisterAdapter.ContestRegisterAdapterListener) {
        this.mListener = listener
    }
    interface ContestRegisterAdapterListener {

        fun onRetryClick()
    }

    companion object {
        val VIEW_TYPE_EMPTY = 0

        val VIEW_TYPE_NORMAL = 1
    }
}