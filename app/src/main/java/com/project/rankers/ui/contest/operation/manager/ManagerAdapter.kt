package com.project.rankers.ui.contest.operation.manager

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.rankers.data.remote.response.UserRepo
import com.project.rankers.databinding.ItemManagerEmptyViewBinding
import com.project.rankers.databinding.ItemManagerViewBinding
import com.project.rankers.ui.base.BaseViewHolder
import java.util.*

class ManagerAdapter(val mManageResponseList: MutableList<UserRepo.User>?) : RecyclerView.Adapter<BaseViewHolder>() {

    private var arrayList: ArrayList<UserRepo.User>? = null
    private var mListener: ManagerAdapterListenr? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        when (viewType) {
            VIEW_TYPE_NORMAL -> {
                val competitionViewBinding = ItemManagerViewBinding.inflate(LayoutInflater.from(parent.context),
                        parent, false)
                return ManagerViewHolder(competitionViewBinding)
            }
            VIEW_TYPE_EMPTY -> {
                val emptyViewBinding = ItemManagerEmptyViewBinding.inflate(LayoutInflater.from(parent.context),
                        parent, false)
                return EmptyViewHolder(emptyViewBinding)
            }
            else -> {
                val emptyViewBinding = ItemManagerEmptyViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return EmptyViewHolder(emptyViewBinding)
            }
        }
    }

    override fun getItemCount(): Int {
        return if (mManageResponseList != null && mManageResponseList.size > 0) {
            mManageResponseList.size
        } else {
            1
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (mManageResponseList != null && !mManageResponseList.isEmpty()) {
            VIEW_TYPE_NORMAL
        } else {
            VIEW_TYPE_EMPTY
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)

    }

    inner class ManagerViewHolder(private val mBinding: ItemManagerViewBinding) : BaseViewHolder(mBinding.root), ManagerItemViewModel.ManagerItemViewModelListener {

        private var manageItemViewModel: ManagerItemViewModel? = null

        override fun onBind(position: Int) {
            val userItem = mManageResponseList!![position]
            manageItemViewModel = ManagerItemViewModel(userItem, this)
            mBinding.viewModel = manageItemViewModel
            mBinding.executePendingBindings()
        }

        override fun onItemClick() {

        }
    }

    inner class EmptyViewHolder(private val mBinding: ItemManagerEmptyViewBinding) : BaseViewHolder(mBinding.root), ManagerEmptyItemViewModel.ManagerEmptyItemViewModelListener {

        override fun onBind(position: Int) {
            val emptyItemViewModel = ManagerEmptyItemViewModel(this)
            mBinding.viewModel = emptyItemViewModel
        }

        override fun onRetryClick() {
            mListener!!.onRetryClick()
        }
    }

    fun filter(charText: String) {
        var charText = charText
        charText = charText.toLowerCase(Locale.getDefault())
        mManageResponseList!!.clear()
        try{
            if (charText.isEmpty()) {
                mManageResponseList.addAll(arrayList!!)
            } else {
                for (recent in arrayList!!) {
                    val name = recent.userName
                    if (name!!.toLowerCase().contains(charText)) {
                        mManageResponseList.add(recent)
                    }
                }
            }
            notifyDataSetChanged()
        }catch (e : NullPointerException){
            Log.e("RankError", e.toString())
        }
    }

    fun addItems(matchItem: List<UserRepo.User>) {
        mManageResponseList!!.addAll(matchItem)
        arrayList = ArrayList()
        arrayList!!.addAll(this.mManageResponseList)
        notifyDataSetChanged()
    }

    fun clearItems() {
        mManageResponseList!!.clear()
    }

    fun setListener(listener: ManagerAdapterListenr) {
        this.mListener = listener
    }

    interface ManagerAdapterListenr {

        fun onRetryClick()
    }

    companion object {
        val VIEW_TYPE_EMPTY = 0

        val VIEW_TYPE_NORMAL = 1
    }
}