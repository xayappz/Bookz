package com.xayappz.adapters

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.bumptech.glide.Glide
import com.xayappz.booksinfo.R
import com.xayappz.booksinfo.databinding.ItemBookBinding
import com.xayappz.models.BooksModel
import com.xayappz.util.Browser


data class BooksAdapter(var booksList: List<BooksModel>, var activity: Activity) : BaseAdapter() {

    override fun getItem(position: Int): Any {
        return booksList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return booksList.size
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var holder: ViewHolder
        if (convertView == null) {
            val itemBinding: ItemBookBinding =
                ItemBookBinding.inflate(LayoutInflater.from(parent!!.context), parent, false)

            holder = ViewHolder(itemBinding)
            holder.view = itemBinding.root
            holder.view.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
        }
        holder.binding.author.text = booksList[position].author
        holder.binding.titleofbook.text = booksList[position].bookName
        val urlImg = booksList[position].bookImg
        Glide.with(activity)
            .load(urlImg)
            .centerCrop().placeholder(R.drawable.ic_round_preview_24)
            .into(holder.binding.imagebook)

        holder.view.setOnClickListener {
            Browser.navigateToChrome(booksList[position].url, activity)
        }
        return holder.view
    }

    class ViewHolder constructor(binding: ItemBookBinding) {
        var view: View = binding.root
        val binding: ItemBookBinding = binding

    }

}