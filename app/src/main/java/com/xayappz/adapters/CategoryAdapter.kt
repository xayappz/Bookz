package com.xayappz.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.xayappz.booksinfo.R
import com.xayappz.models.CategoryModel


class CategoryAdapter(var mCtx: Context, var resource: Int, var items: List<CategoryModel>) :
    ArrayAdapter<CategoryModel>(mCtx, resource, items) {


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)

        val view: View = layoutInflater.inflate(resource, null)
        val categoryImg: ImageView = view.findViewById(R.id.category_icon)
        var categoryName: TextView = view.findViewById(R.id.category_name)


        var categoryData: CategoryModel = items[position]

        categoryImg.setImageDrawable(mCtx.resources.getDrawable(categoryData.catPhoto))
        categoryName.text = categoryData.name

        return view
    }

}