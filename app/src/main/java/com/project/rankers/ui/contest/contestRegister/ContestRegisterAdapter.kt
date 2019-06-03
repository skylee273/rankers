package com.project.rankers.ui.contest.contestRegister

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.rankers.data.model.db.DepartItem
import com.project.rankers.databinding.ItemContestRegisterEmptyViewBinding
import com.project.rankers.databinding.ItemContestRegisterViewBinding
import com.project.rankers.ui.base.BaseViewHolder
import com.project.rankers.ui.contest.competition.CompetitionAdapter

class ContestRegisterAdapter(private val mContestRegisterList: MutableList<DepartItem>?) : RecyclerView.Adapter<BaseViewHolder>() {


    private var mListener: ContestRegisterAdapterListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        when (viewType) {
            VIEW_TYPE_NORMAL -> {
                val competitionViewBinding = ItemContestRegisterViewBinding.inflate(LayoutInflater.from(parent.context),
                        parent, false)
                return ContestRegisterViewHolder(competitionViewBinding)
            }
            VIEW_TYPE_EMPTY -> {
                val emptyViewBinding = ItemContestRegisterEmptyViewBinding.inflate(LayoutInflater.from(parent.context),
                        parent, false)
                return EmptyViewHolder(emptyViewBinding)
            }
            else -> {
                val emptyViewBinding = ItemContestRegisterEmptyViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return EmptyViewHolder(emptyViewBinding)
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
        return if (mContestRegisterList != null && !mContestRegisterList.isEmpty()) {
            CompetitionAdapter.VIEW_TYPE_NORMAL
        } else {
            CompetitionAdapter.VIEW_TYPE_EMPTY
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    inner class ContestRegisterViewHolder(private val mBinding: ItemContestRegisterViewBinding) : BaseViewHolder(mBinding.root), ContestRegisterItemViewModel.ContestRegisterItemViewModelListner {


        private var contestRegisterItemViewModel: ContestRegisterItemViewModel? = null

        override fun onBind(position: Int) {
            if (mContestRegisterList!!.size > 0) {
                val departItem = mContestRegisterList[position]
                contestRegisterItemViewModel = ContestRegisterItemViewModel(departItem, this)
                mBinding.viewModel = contestRegisterItemViewModel
                mBinding.executePendingBindings()
            }
        }

        override fun onRemoveClick() {
            val pos = adapterPosition
            mListener!!.removeClick(pos)
        }
    }

    inner class EmptyViewHolder(private val mBinding: ItemContestRegisterEmptyViewBinding) : BaseViewHolder(mBinding.root), ContestRegisterEmptyViewModel.ContestRegisterEmptyItemViewModelListener {

        override fun onBind(position: Int) {
            val emptyItemViewModel = ContestRegisterEmptyViewModel(this)
            mBinding.viewModel = emptyItemViewModel
        }
    }

    fun removeItems(position: Int){
        try{
            mContestRegisterList!!.removeAt(position)
            notifyItemRemoved(position)
        }catch(e : IndexOutOfBoundsException){
            Log.e("Error", e.toString())
        }
    }

    fun addItems(departItem: DepartItem) {
        mContestRegisterList!!.add(departItem)
        notifyDataSetChanged()
    }


    fun clearItems() {
        mContestRegisterList!!.clear()
    }

    fun setListener(listener: ContestRegisterAdapter.ContestRegisterAdapterListener) {
        this.mListener = listener
    }

    interface ContestRegisterAdapterListener {

        fun removeClick(position: Int)
        fun onRetryClick()
    }

    companion object {
        val VIEW_TYPE_EMPTY = 0

        val VIEW_TYPE_NORMAL = 1
    }
}