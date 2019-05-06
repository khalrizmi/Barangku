package com.example.barangku.data.adapter

import android.content.Context
import android.content.Intent
import android.support.v4.widget.CircularProgressDrawable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.barangku.R
import com.example.barangku.data.model.News
import com.example.barangku.ui.DetailNewsActivity

/**
 * Created by Hamz on 06/05/2019.
 * ilham011001@gmail.com
 */

class NewsAdapter(private var newses: List<News>?, val mContext: Context)
    : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): NewsViewHolder {
        return NewsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false))
    }

    override fun getItemCount(): Int = newses!!.size

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val news: News = newses!![position]
        holder.bind(news)
        holder.itemView.setOnClickListener { view ->
            var intent: Intent = Intent(mContext, DetailNewsActivity::class.java)
            intent.putExtra("id", news?.id)
            mContext.startActivity(intent)
        }
    }


    class NewsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var imageProfile: ImageView? = null
        var nameProfile: TextView? = null
        var toName: TextView? = null
        var note: TextView? = null


        init {
            imageProfile = itemView.findViewById(R.id.imageProfile)
            nameProfile = itemView.findViewById(R.id.tvNameProfile)
            toName = itemView.findViewById(R.id.tvToName)
            note = itemView.findViewById(R.id.tvNote)
        }

        fun bind(news: News) {
            nameProfile!!.setText(news?.name)
            toName!!.setText(news?.to)
            note!!.setText(news?.note)

            val circularProgressDrawable = CircularProgressDrawable(itemView.context)
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.centerRadius = 30f
            circularProgressDrawable.start()
            Glide
                    .with(itemView.context)
                    .load(news?.imageProfile)
                    .placeholder(circularProgressDrawable)
                    .into(imageProfile!!)

        }
    }
}