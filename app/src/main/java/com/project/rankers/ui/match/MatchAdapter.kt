package com.project.rankers.ui.match

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.rankers.data.remote.response.MatchRepo
import com.project.rankers.databinding.ItemMatchEmptyViewBinding
import com.project.rankers.databinding.ItemMatchViewBinding
import com.project.rankers.ui.base.BaseViewHolder
import java.util.*

class MatchAdapter(val mMatchResponseList: MutableList<MatchRepo.Match>?) : RecyclerView.Adapter<BaseViewHolder>() {

    private var arrayList: ArrayList<MatchRepo.Match>? = null
    private var mListener: MatchAdapterListner? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        when (viewType) {
            VIEW_TYPE_NORMAL -> {
                val competitionViewBinding = ItemMatchViewBinding.inflate(LayoutInflater.from(parent.context),
                        parent, false)
                return MatchViewHolder(competitionViewBinding)
            }
            VIEW_TYPE_EMPTY -> {
                val emptyViewBinding = ItemMatchEmptyViewBinding.inflate(LayoutInflater.from(parent.context),
                        parent, false)
                return EmptyViewHolder(emptyViewBinding)
            }
            else -> {
                val emptyViewBinding = ItemMatchEmptyViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return EmptyViewHolder(emptyViewBinding)
            }
        }
    }

    override fun getItemCount(): Int {
        return if (mMatchResponseList != null && mMatchResponseList.size > 0) {
            mMatchResponseList.size
        } else {
            1
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (mMatchResponseList != null && !mMatchResponseList.isEmpty()) {
            VIEW_TYPE_NORMAL
        } else {
            VIEW_TYPE_EMPTY
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)

    }

    inner class MatchViewHolder(private val mBinding: ItemMatchViewBinding) : BaseViewHolder(mBinding.root), MatchItemViewModel.MatchItemViewModelListener {

        private var matchItemViewModel: MatchItemViewModel? = null

        override fun onBind(position: Int) {
            val matchItem = mMatchResponseList!![position]
            matchItemViewModel = MatchItemViewModel(matchItem, this)
            mBinding.viewModel = matchItemViewModel
            mBinding.executePendingBindings()
        }

        override fun onItemClick() {

        }
    }

    inner class EmptyViewHolder(private val mBinding: ItemMatchEmptyViewBinding) : BaseViewHolder(mBinding.root), MatchEmptyItemViewModel.MatchEmptyItemViewModelListener {

        override fun onBind(position: Int) {
            val emptyItemViewModel = MatchEmptyItemViewModel(this)
            mBinding.viewModel = emptyItemViewModel
        }

        override fun onRetryClick() {
            mListener!!.onRetryClick()
        }
    }

    fun filter(charText: String) {
        var charText = charText
        charText = charText.toLowerCase(Locale.getDefault())
        mMatchResponseList!!.clear()
        if (charText.isEmpty()) {
            mMatchResponseList.addAll(arrayList!!)
        } else {
            for (recent in arrayList!!) {
                val name = recent.matchOther
                val partner = recent.matchPartner
                val otherPartner = recent.matchOtherPartner
                val date = recent.matchDate
                when {
                    name!!.toLowerCase().contains(charText) -> mMatchResponseList.add(recent)
                    partner!!.toLowerCase().contains(charText) -> mMatchResponseList.add(recent)
                    otherPartner!!.toLowerCase().contains(charText) -> mMatchResponseList.add(recent)
                    date!!.toLowerCase().contains(charText) -> mMatchResponseList.add(recent)
                }
            }
        }
        notifyDataSetChanged()

    }

    fun addItems(matchItem: List<MatchRepo.Match>) {
        mMatchResponseList!!.addAll(matchItem)
        arrayList = ArrayList()
        arrayList!!.addAll(this.mMatchResponseList)
        notifyDataSetChanged()
    }

    fun clearItems() {
        mMatchResponseList!!.clear()
    }

    fun setListener(listener: MatchAdapterListner) {
        this.mListener = listener
    }

    interface MatchAdapterListner {

        fun onRetryClick()
    }

    companion object {
        val VIEW_TYPE_EMPTY = 0

        val VIEW_TYPE_NORMAL = 1
    }
}