package com.project.rankers.ui.league

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.rankers.data.model.db.LeagueItem
import com.project.rankers.databinding.*
import com.project.rankers.ui.base.BaseViewHolder

class LeagueAdapter(val responseList: MutableList<LeagueItem>?) : RecyclerView.Adapter<BaseViewHolder>() {

    private var mListener: LeagueAdapterListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        when (viewType) {
            VIEW_TYPE_NORMAL -> {
                val leagueViewBinding = ItemLeagueViewBinding.inflate(LayoutInflater.from(parent.context),
                        parent, false)
                return LeagueViewHolder(leagueViewBinding)
            }
            VIEW_TYPE_EMPTY -> {
                val emptyViewBinding = ItemLeagueEmptyViewBinding.inflate(LayoutInflater.from(parent.context),
                        parent, false)
                return EmptyViewHolder(emptyViewBinding)
            }
            else -> {
                val emptyViewBinding = ItemLeagueEmptyViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return EmptyViewHolder(emptyViewBinding)
            }
        }
    }

    override fun getItemCount(): Int {
        return if (responseList != null && responseList.size > 0) {
            responseList.size
        } else {
            1
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (responseList != null && !responseList.isEmpty()) {
            VIEW_TYPE_NORMAL
        } else {
            VIEW_TYPE_EMPTY
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    inner class LeagueViewHolder(private val mBinding: ItemLeagueViewBinding) : BaseViewHolder(mBinding.root), LeagueItemViewModel.LeagueItemViewModelListener {
        private var leagueItemViewModel: LeagueItemViewModel? = null

        override fun onBind(position: Int) {
            val leagueItem = responseList!![position]
            leagueItemViewModel = LeagueItemViewModel(leagueItem, this)
            mBinding.viewModel = leagueItemViewModel
            mBinding.executePendingBindings()
        }

        override fun onPlayer1Click() {
            Log.d("onPlayer1Click","클릭")
            val position = adapterPosition
            mListener!!.onShowPeopleDialog(position,1)
        }

        override fun onPlayer2Click() {
            val position = adapterPosition
            mListener!!.onShowPeopleDialog(position,2)
        }

        override fun onPlayer3Click() {
            val position = adapterPosition
            mListener!!.onShowPeopleDialog(position,3)
        }

        override fun onPlayer4Click() {
            val position = adapterPosition
            mListener!!.onShowPeopleDialog(position,4)
        }


        override fun onItemClick() {

        }
    }

    inner class EmptyViewHolder(private val mBinding: ItemLeagueEmptyViewBinding) : BaseViewHolder(mBinding.root), LeagueEmptyItemViewModel.LeagueEmptyItemViewModelListener {

        override fun onBind(position: Int) {
            val emptyItemViewModel = LeagueEmptyItemViewModel(this)
            mBinding.viewModel = emptyItemViewModel
        }

        override fun onRetryClick() {
            mListener!!.onRetryClick()
        }
    }


    fun addItems(applyItem: List<LeagueItem>) {
        responseList!!.addAll(applyItem)
        notifyDataSetChanged()
    }

    fun modifyItem(position: Int, playerName : String, type : Int){
        when(type){
            1 -> responseList!![position].player1 = playerName
            2 -> responseList!![position].player2 = playerName
            3 -> responseList!![position].player3 = playerName
            4 -> responseList!![position].player4 = playerName
        }
        notifyItemChanged(position)
    }
    fun clearItems() {
        responseList!!.clear()
    }
    fun getItems() : MutableList<LeagueItem>? {
        return responseList
    }
    fun setListener(listener: LeagueAdapterListener) {
        this.mListener = listener
    }

    interface LeagueAdapterListener {
        fun onShowPeopleDialog(position : Int, type : Int)
        fun onRetryClick()
    }

    companion object {
        val VIEW_TYPE_EMPTY = 0

        val VIEW_TYPE_NORMAL = 1
    }

}