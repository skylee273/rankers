package com.project.rankers.ui.main.message

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.rankers.data.remote.response.BoardRepo
import com.project.rankers.databinding.ItemMessageEmptyViewBinding
import com.project.rankers.databinding.ItemMessageViewBinding
import com.project.rankers.ui.base.BaseViewHolder
import java.util.*
import kotlin.collections.ArrayList

class MessageAdapter(val mMessageResponseList: MutableList<BoardRepo.Board>?) : RecyclerView.Adapter<BaseViewHolder>() {
    private var arrayList: ArrayList<BoardRepo.Board>? = null

    private var mListener: MessageAdapterListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        when (viewType) {
            VIEW_TYPE_NORMAL -> {
                val competitionViewBinding = ItemMessageViewBinding.inflate(LayoutInflater.from(parent.context),
                        parent, false)
                return MessageViewHolder(competitionViewBinding)
            }
            VIEW_TYPE_EMPTY -> {
                val emptyViewBinding = ItemMessageEmptyViewBinding.inflate(LayoutInflater.from(parent.context),
                        parent, false)
                return EmptyViewHolder(emptyViewBinding)
            }
            else -> {
                val emptyViewBinding = ItemMessageEmptyViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return EmptyViewHolder(emptyViewBinding)
            }
        }
    }

    override fun getItemCount(): Int {
        return if (mMessageResponseList != null && mMessageResponseList.size > 0) {
            mMessageResponseList.size
        } else {
            1
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (mMessageResponseList != null && !mMessageResponseList.isEmpty()) {
            VIEW_TYPE_NORMAL
        } else {
            VIEW_TYPE_EMPTY
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)

    }

    inner class MessageViewHolder(private val mBinding: ItemMessageViewBinding) : BaseViewHolder(mBinding.root), MessageItemViewModel.MessageItemViewListener {

        private var messageItemViewModel: MessageItemViewModel? = null

        override fun onBind(position: Int) {
            val messageItem = mMessageResponseList!![position]
            messageItemViewModel = MessageItemViewModel(messageItem, this)
            mBinding.viewModel = messageItemViewModel
            mBinding.executePendingBindings()
        }

        override fun onItemClick() {
            val pos = adapterPosition
            mListener!!.onItemClick(mMessageResponseList!![pos])
        }
    }

    inner class EmptyViewHolder(private val mBinding: ItemMessageEmptyViewBinding) : BaseViewHolder(mBinding.root), MessageEmptyViewModel.MessageEmptyItemViewModelListener {

        override fun onBind(position: Int) {
            val emptyItemViewModel = MessageEmptyViewModel(this)
            mBinding.viewModel = emptyItemViewModel
        }

        override fun onRetryClick() {
            mListener!!.onRetryClick()
        }
    }


    fun filter(charText: String) {
        var charText = charText
        charText = charText.toLowerCase(Locale.getDefault())
        mMessageResponseList!!.clear()
        try{
            if (charText.isEmpty()) {
                mMessageResponseList.addAll(arrayList!!)
            } else {
                for (recent in arrayList!!) {
                    val title = recent.boardTitle
                    val text = recent.boardText
                    val date = recent.boardDate
                    val name = recent.boardName
                    when {
                        title!!.toLowerCase().contains(charText) -> mMessageResponseList.add(recent)
                        text!!.toLowerCase().contains(charText) -> mMessageResponseList.add(recent)
                        date!!.toLowerCase().contains(charText) -> mMessageResponseList.add(recent)
                        name!!.toLowerCase().contains(charText) -> mMessageResponseList.add(recent)
                    }
                }
            }
            notifyDataSetChanged()
        }catch (e : NullPointerException){
            Log.e("BoardError", e.toString())
        }
    }
    fun addItems(contestItem: List<BoardRepo.Board>) {
        mMessageResponseList!!.addAll(contestItem)
        arrayList = ArrayList()
        arrayList!!.addAll(this.mMessageResponseList)
        notifyDataSetChanged()
    }

    fun clearItems() {
        mMessageResponseList!!.clear()
    }

    fun setListener(listener: MessageAdapterListener) {
        this.mListener = listener
    }

    interface MessageAdapterListener {
        fun onRetryClick()
        fun onItemClick( item : BoardRepo.Board)
    }

    companion object {
        val VIEW_TYPE_EMPTY = 0

        val VIEW_TYPE_NORMAL = 1
    }
}