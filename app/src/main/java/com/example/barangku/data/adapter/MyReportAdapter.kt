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
import com.bumptech.glide.Glide
import com.example.barangku.R
import com.example.barangku.data.model.News
import com.example.barangku.ui.DetailNewsActivity

/**
 * Created by Hamz on 06/05/2019.
 * ilham011001@gmail.com
 */

class MyReportAdapter(private var listNews: List<News>?, private var mContext: Context)
    : RecyclerView.Adapter<MyReportAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyViewHolder {
        return MyReportAdapter.MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_myreport, parent, false))
    }

    override fun getItemCount(): Int = listNews!!.size

    override fun onBindViewHolder(holder: MyViewHolder, p1: Int) {
        val news: News = listNews!![p1]

        val circularProgressDrawable = CircularProgressDrawable(mContext)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()
        Glide
                .with(mContext)
                .load(news?.image)
                .placeholder(circularProgressDrawable)
                .into(holder.image)

        Glide
                .with(mContext)
                .load(news?.imageProfile)
                .placeholder(circularProgressDrawable)
                .into(holder.imageProfile)

        holder.tvName.setText(news?.name)

        holder.itemView.setOnClickListener { view ->
            var intent: Intent = Intent(mContext, DetailNewsActivity::class.java)
            intent.putExtra("id", news?.id)
            mContext.startActivity(intent)
        }
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var image: ImageView = itemView.findViewById(R.id.image)
        var imageProfile: ImageView = itemView.findViewById(R.id.imageProfile)
        var tvName: TextView = itemView.findViewById(R.id.tvName)
    }
}