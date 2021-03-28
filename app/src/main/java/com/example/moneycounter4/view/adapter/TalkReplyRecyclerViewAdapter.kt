package com.example.moneycounter4.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moneycounter4.R
import com.example.moneycounter4.beannew.CommentItem
import com.example.moneycounter4.beannew.findEquals
import com.example.moneycounter4.model.Config
import com.example.moneycounter4.view.costom.LikeView


class TalkReplyRecyclerViewAdapter(
    private var mContext: Context,
    private val onItemClick: (CommentItem, View) -> Unit,
    private val onInnerLongClick: (CommentItem, View) -> Unit

) :
    RecyclerView.Adapter<TalkReplyRecyclerViewAdapter.ViewHolder>() {

    private var mLayoutInflater = LayoutInflater.from(mContext)
    private var mList: ArrayList<CommentItem> = arrayListOf()


    override fun onCreateViewHolder(container: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            mLayoutInflater.inflate(
                R.layout.item_talk_inner,
                container,
                false
            )
        )


    override fun getItemCount(): Int = mList.size

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val isPraise = mList[position].praise.findEquals { it.userId == Config.userId }
        holder.imageViewLikeView?.setHint((mList[position].praise.size - if (isPraise) 1 else 0).toString())
        holder.imageViewLikeView?.setSelect(isPraise)

        holder.textViewUsrName?.text = mList[position].nickname
        holder.textViewContent?.text = mList[position].text



        holder.imageViewUsrPic?.let {
            Glide.with(mContext).load(mList[position].avatarUrl).into(it)
        }





        holder.imageViewUsrPic?.setOnClickListener {
            val navController = Navigation.findNavController(holder.itemView)
            val bundle = Bundle()
            bundle.putBoolean("isMine", false)
            bundle.putString("userId", mList[position].userId)
            navController.navigate(R.id.action_global_fragmentMine, bundle)
        }

        holder.imageViewLikeView?.setOnClickListener {
            // TODO 点赞
        }
        holder.itemView.setOnClickListener {
            onItemClick.invoke(mList[position], it)

        }
        holder.itemView.setOnLongClickListener {
            onInnerLongClick.invoke(mList[position], it)
            true
        }


        val likeListener = View.OnClickListener {
            holder.imageViewLikeView?.let {
                it.setSelect(!it.getSelect())
            }


        }
        holder.imageViewLikeView?.setOnClickListener(likeListener)


    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewUsrName: TextView? = itemView.findViewById<TextView>(R.id.tv_nickname)//
        val textViewContent: TextView? = itemView.findViewById<TextView>(R.id.tv_content)//
        val imageViewUsrPic: ImageView? = itemView.findViewById<ImageView>(R.id.iv_avatar)//
        val imageViewLikeView: LikeView? =
            itemView.findViewById<LikeView>(R.id.like_view)

    }

    fun setList(list: List<CommentItem>) {
        mList.clear()
        mList.addAll(list)
        notifyDataSetChanged()
    }


}