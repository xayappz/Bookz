package com.xayappz.views

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.xayappz.adapters.CategoryAdapter
import com.xayappz.booksinfo.R
import com.xayappz.models.CategoryModel
import com.xayappz.util.Toaster
import com.xayappz.util.isOnline
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeData()
    }

    private fun initializeData() {
        val list = mutableListOf<CategoryModel>()
        list.add(CategoryModel(R.drawable.ic_fiction, "Fiction"))
        list.add(CategoryModel(R.drawable.ic_drama, "Drama"))
        list.add(CategoryModel(R.drawable.ic_humour, "Humor"))
        list.add(CategoryModel(R.drawable.ic_politics, "Politics"))
        list.add(CategoryModel(R.drawable.ic_philosphy, "Philosophy"))
        list.add(CategoryModel(R.drawable.ic_history, "History"))
        list.add(CategoryModel(R.drawable.ic_adventure, "Adventure"))

        category_listView.adapter = CategoryAdapter(this, R.layout.item_category, list)

        category_listView.setOnItemClickListener { parent, _, position, _ ->
            val selectedItem = parent.getItemAtPosition(position) as CategoryModel
            startNewActivity(selectedItem.name)
        }
    }

    private fun startNewActivity(name: String) {
        if (isOnline.isOnline(this)) {

            val intent = Intent(this@MainActivity, BookInfo::class.java)
            intent.putExtra("title", name)
            startActivity(intent)
            overridePendingTransition(R.anim.bottom_up, R.anim.nothing)
        } else {
            Toaster.showToast(getString(R.string.no_internet), this)
        }
    }

}