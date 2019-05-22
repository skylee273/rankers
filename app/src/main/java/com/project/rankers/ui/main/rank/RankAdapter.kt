package com.project.rankers.ui.main.rank

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.rankers.data.remote.response.RankRepo
import com.project.rankers.databinding.ItemRankEmptyViewBinding
import com.project.rankers.databinding.ItemRankViewBinding
import com.project.rankers.ui.base.BaseViewHolder
import java.util.*

class RankAdapter(val mRankResponseList: MutableList<RankRepo.Rank>?) : RecyclerView.Adapter<BaseViewHolder>() {

    private var arrayList: ArrayList<RankRepo.Rank>? = null
    private var mListener: RankAdapterListenr? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        when (viewType) {
            VIEW_TYPE_NORMAL -> {
                val competitionViewBinding = ItemRankViewBinding.inflate(LayoutInflater.from(parent.context),
                        parent, false)
                return RankViewHolder(competitionViewBinding)
            }
            VIEW_TYPE_EMPTY -> {
                val emptyViewBinding = ItemRankEmptyViewBinding.inflate(LayoutInflater.from(parent.context),
                        parent, false)
                return EmptyViewHolder(emptyViewBinding)
            }
            else -> {
                val emptyViewBinding = ItemRankEmptyViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return EmptyViewHolder(emptyViewBinding)
            }
        }
    }

    override fun getItemCount(): Int {
        return if (mRankResponseList != null && mRankResponseList.size > 0) {
            mRankResponseList.size
        } else {
            1
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (mRankResponseList != null && !mRankResponseList.isEmpty()) {
            VIEW_TYPE_NORMAL
        } else {
            VIEW_TYPE_EMPTY
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)

    }

    inner class RankViewHolder(private val mBinding: ItemRankViewBinding) : BaseViewHolder(mBinding.root), RankItemViewModel.RankItemViewModelListener {

        private var matchItemViewModel: RankItemViewModel? = null

        override fun onBind(position: Int) {
            val matchItem = mRankResponseList!![position]
            matchItemViewModel = RankItemViewModel(matchItem, this)
            mBinding.viewModel = matchItemViewModel
            mBinding.executePendingBindings()
        }

        override fun onItemClick() {

        }
    }

    inner class EmptyViewHolder(private val mBinding: ItemRankEmptyViewBinding) : BaseViewHolder(mBinding.root), RankEmptyViewModel.RankEmptyItemViewModelListener {

        override fun onBind(position: Int) {
            val emptyItemViewModel = RankEmptyViewModel(this)
            mBinding.viewModel = emptyItemViewModel
        }

        override fun onRetryClick() {
            mListener!!.onRetryClick()
        }
    }

    fun filter(charText: String) {
        var charText = charText
        charText = charText.toLowerCase(Locale.getDefault())
        mRankResponseList!!.clear()
        try{
            if (charText.isEmpty()) {
                mRankResponseList.addAll(arrayList!!)
            } else {
                for (recent in arrayList!!) {
                    val name = recent.rankName
                    if (name!!.toLowerCase().contains(charText)) {
                        mRankResponseList.add(recent)
                    }
                }
            }
            notifyDataSetChanged()
        }catch (e : NullPointerException){
            Log.e("RankError", e.toString())
        }
    }

    fun addItems(matchItem: List<RankRepo.Rank>) {
        mRankResponseList!!.addAll(matchItem)
        arrayList = ArrayList()
        arrayList!!.addAll(this.mRankResponseList)
        notifyDataSetChanged()
    }

    fun clearItems() {
        mRankResponseList!!.clear()
    }

    fun setListener(listener: RankAdapterListenr) {
        this.mListener = listener
    }

    interface RankAdapterListenr {

        fun onRetryClick()
    }

    companion object {
        val VIEW_TYPE_EMPTY = 0

        val VIEW_TYPE_NORMAL = 1
    }
}