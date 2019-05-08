package com.project.rankers.ui.contest.form

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.rankers.data.remote.response.UserRepo
import com.project.rankers.databinding.ItemFormEmptyViewBinding
import com.project.rankers.databinding.ItemFormViewBinding
import com.project.rankers.ui.base.BaseViewHolder
import java.util.*

class ApplicationFormAdapter(val mUserResponseList: MutableList<UserRepo.User>?) : RecyclerView.Adapter<BaseViewHolder>() {

    private var arrayList: ArrayList<UserRepo.User>? = null
    private var mListener: UserAdapterLisner? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        when (viewType) {
            VIEW_TYPE_NORMAL -> {
                val competitionViewBinding = ItemFormViewBinding.inflate(LayoutInflater.from(parent.context),
                        parent, false)
                return FormViewHolder(competitionViewBinding)
            }
            VIEW_TYPE_EMPTY -> {
                val emptyViewBinding = ItemFormEmptyViewBinding.inflate(LayoutInflater.from(parent.context),
                        parent, false)
                return EmptyViewHolder(emptyViewBinding)
            }
            else -> {
                val emptyViewBinding = ItemFormEmptyViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return EmptyViewHolder(emptyViewBinding)
            }
        }
    }

    override fun getItemCount(): Int {
        return if (mUserResponseList != null && mUserResponseList.size > 0) {
            mUserResponseList.size
        } else {
            1
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (mUserResponseList != null && !mUserResponseList.isEmpty()) {
            VIEW_TYPE_NORMAL
        } else {
            VIEW_TYPE_EMPTY
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)

    }

    inner class FormViewHolder(private val mBinding: ItemFormViewBinding) : BaseViewHolder(mBinding.root), ApplicationFormItemViewModel.FormItemViewModelListener {

        private var formItemViewModel: ApplicationFormItemViewModel? = null

        override fun onBind(position: Int) {
            val matchItem = mUserResponseList!![position]
            formItemViewModel = ApplicationFormItemViewModel(matchItem, this)
            mBinding.viewModel = formItemViewModel
            mBinding.executePendingBindings()
        }

        override fun onUpdatePartner() {
            val position = adapterPosition
            mListener!!.onUpdatePartner(mUserResponseList!![position].userName!!, mUserResponseList!![position].userPhone!!)
        }
    }

    inner class EmptyViewHolder(private val mBinding: ItemFormEmptyViewBinding) : BaseViewHolder(mBinding.root), ApplicationFormItemEmptyViewModel.FormEmptyItemViewModelListener {

        override fun onBind(position: Int) {
            val emptyItemViewModel = ApplicationFormItemEmptyViewModel(this)
            mBinding.viewModel = emptyItemViewModel
        }

        override fun onRetryClick() {
            mListener!!.onRetryClick()
        }
    }

    fun filter(charText: String) {
        var charText = charText
        charText = charText.toLowerCase(Locale.getDefault())
        mUserResponseList!!.clear()
        if (charText.isEmpty()) {
            mUserResponseList.addAll(arrayList!!)
        } else {
            for (recent in arrayList!!) {
                val name = recent.userName
                if (name!!.toLowerCase().contains(charText)) {
                    mUserResponseList.add(recent)
                }
            }
        }
        notifyDataSetChanged()

    }

    fun addItems(matchItem: List<UserRepo.User>) {
        mUserResponseList!!.addAll(matchItem)
        arrayList = ArrayList()
        arrayList!!.addAll(this.mUserResponseList!!)
        notifyDataSetChanged()
    }

    fun clearItems() {
        mUserResponseList!!.clear()
    }

    fun setListener(listener: UserAdapterLisner) {
        this.mListener = listener
    }

    interface UserAdapterLisner {
        fun onUpdatePartner(name : String, phone : String)
        fun onRetryClick()
    }

    companion object {
        val VIEW_TYPE_EMPTY = 0

        val VIEW_TYPE_NORMAL = 1
    }
}