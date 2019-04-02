package com.project.rankers.ui.competition

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.rankers.ui.base.BaseViewHolder
import com.project.rankers.data.remote.response.ContestResponse
import com.project.rankers.databinding.ItemCompetiotionEmptyViewBinding
import com.project.rankers.databinding.ItemCompetitionViewBinding
import com.project.rankers.ui.activity.ApplyActivity

class CompetitionAdapter(val mCompetitionResponseList: MutableList<ContestResponse.Repo>?) : RecyclerView.Adapter<BaseViewHolder>() {

    private var mListener: CompetitionAdapterListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        when (viewType) {
            VIEW_TYPE_NORMAL -> {
                val competitionViewBinding = ItemCompetitionViewBinding.inflate(LayoutInflater.from(parent.context),
                        parent, false)
                return CompetitionViewHolder(competitionViewBinding)
            }
            VIEW_TYPE_EMPTY -> {
                val emptyViewBinding = ItemCompetiotionEmptyViewBinding.inflate(LayoutInflater.from(parent.context),
                        parent, false)
                return EmptyViewHolder(emptyViewBinding)
            }
            else -> {
                val emptyViewBinding = ItemCompetiotionEmptyViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return EmptyViewHolder(emptyViewBinding)
            }
        }
    }

    override fun getItemCount(): Int {
        return if (mCompetitionResponseList != null && mCompetitionResponseList.size > 0) {
            mCompetitionResponseList.size
        } else {
            1
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (mCompetitionResponseList != null && !mCompetitionResponseList.isEmpty()) {
            VIEW_TYPE_NORMAL
        } else {
            VIEW_TYPE_EMPTY
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)

    }

    inner class CompetitionViewHolder(private val mBinding: ItemCompetitionViewBinding) : BaseViewHolder(mBinding.root), CompetitionItemViewModel.CompetitionItemViewModelListener {

        private var competitionItemViewModel: CompetitionItemViewModel? = null

        override fun onBind(position: Int) {
            val contestItem = mCompetitionResponseList!![position]
            competitionItemViewModel = CompetitionItemViewModel(contestItem, this)
            mBinding.viewModel = competitionItemViewModel
            mBinding.executePendingBindings()
        }

        override fun onItemClick() {
            val pos = adapterPosition
            val intent = Intent(itemView.context, ApplyActivity::class.java)
            Log.d("ItemClick", mCompetitionResponseList.toString())
            val array = arrayOf(mCompetitionResponseList!![pos].title, mCompetitionResponseList[pos].start + " ~ " + mCompetitionResponseList[pos].end, mCompetitionResponseList[pos].location, mCompetitionResponseList[pos].host, mCompetitionResponseList[pos].depart, mCompetitionResponseList[pos].id)
            intent.putExtra("contest", array)
            itemView.context.startActivity(intent)
        }
    }

    inner class EmptyViewHolder(private val mBinding: ItemCompetiotionEmptyViewBinding) : BaseViewHolder(mBinding.root), CompetitionEmptyItemViewModel.CompetitionEmptyItemViewModelListener {

        override fun onBind(position: Int) {
            val emptyItemViewModel = CompetitionEmptyItemViewModel(this)
            mBinding.viewModel = emptyItemViewModel
        }

        override fun onRetryClick() {
            mListener!!.onRetryClick()
        }
    }


    fun addItems(contestItem: List<ContestResponse.Repo>) {
        mCompetitionResponseList!!.addAll(contestItem)
        notifyDataSetChanged()
    }

    fun clearItems() {
        mCompetitionResponseList!!.clear()
    }

    fun setListener(listener: CompetitionAdapterListener) {
        this.mListener = listener
    }

    interface CompetitionAdapterListener {

        fun onRetryClick()
    }

    companion object {
        val VIEW_TYPE_EMPTY = 0

        val VIEW_TYPE_NORMAL = 1
    }
}