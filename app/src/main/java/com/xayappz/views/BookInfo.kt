package com.xayappz.views

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.GridLayoutAnimationController
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.xayappz.adapters.BooksAdapter
import com.xayappz.apis.BookService
import com.xayappz.apis.Response
import com.xayappz.apis.RetrofitHelper
import com.xayappz.models.BookList
import com.xayappz.models.BooksModel
import com.xayappz.repository.BooksRepository
import com.xayappz.util.HideKeyboard
import com.xayappz.util.Toaster
import com.xayappz.util.isOnline
import com.xayappz.viewmodels.BooksViewModel
import com.xayappz.viewmodels.BooksViewModelFactory
import kotlinx.android.synthetic.main.activity_book_info.*
import kotlinx.android.synthetic.main.books.*
import kotlinx.android.synthetic.main.search_view.*
import kotlinx.coroutines.*
import java.util.*
import kotlin.collections.ArrayList


class BookInfo : AppCompatActivity() {
    lateinit var booksViewModel: BooksViewModel
    var titleLbl: String = ""
    var booksList: ArrayList<BooksModel> = ArrayList()
    var adapter: BooksAdapter? = null
    lateinit var progressDialog: ProgressDialog
    val TRIGGER_SERACH = 1
    val SEARCH_TRIGGER_DELAY_IN_MS: Long = 1000
    lateinit var handler: Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.xayappz.booksinfo.R.layout.activity_book_info)

        getIntentTitle()


        handler = object : Handler() {
            override fun handleMessage(msg: Message) {
                if (msg.what === TRIGGER_SERACH) {

                    GlobalScope.launch(Dispatchers.Main) {
                        val query = searchEditText.text.toString()
                        loading_view.visibility = View.VISIBLE
                        getSearchResult(query)


                    }

                }
            }
        }

    }

    private fun progressShow() {
        progressDialog = ProgressDialog(this, AlertDialog.THEME_HOLO_LIGHT)
        progressDialog.setTitle("Hold On...")
        progressDialog.setMessage("Getting $titleLbl Books")
        progressDialog.setCancelable(false)
        progressDialog.show()
    }

    private fun hideProgress() {
        if (progressDialog.isShowing) {
            progressDialog.hide()
        }
    }

    private fun initializeViewModel() {

        val bookService = RetrofitHelper.getInstance().create(BookService::class.java)
        val repository = BooksRepository(bookService, application)
        booksViewModel = ViewModelProvider(
                this,
                BooksViewModelFactory(repository, titleLbl)
        )[BooksViewModel::class.java]

    }


    @DelicateCoroutinesApi
    private fun initializeViews() {
        maintitle.text = titleLbl
        back_bn_iv.setOnClickListener {
            startActivity(Intent(this@BookInfo, MainActivity::class.java))
        }
        setBackgroundofSearch("#F0F0F6")
        progressShow()
        getData()
        getSearch()

    }


    @DelicateCoroutinesApi
    private fun getSearch() {
        searchEditText.setOnFocusChangeListener { view, b ->
            if (view.isFocused) {
                search_close_icon.visibility = View.VISIBLE
                setBackgroundofSearch("#5E56E7")
            }

        }
        search_close_icon.setOnClickListener {
            searchEditText.setText("")
            search_close_icon.visibility = View.GONE
            searchEditText.clearFocus()
            nothing_lay.visibility = View.GONE
            HideKeyboard.hideKeyboard(this, it)
            setBackgroundofSearch("#F0F0F6")
        }
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (!isOnline.isOnline(applicationContext)) {
                    Toaster.showToast(
                            getString(com.xayappz.booksinfo.R.string.no_internet),
                            this@BookInfo
                    )
                    return
                }
            }


            override fun afterTextChanged(s: Editable) {
                if (!isOnline.isOnline(applicationContext)) {
                    Toaster.showToast(
                            getString(com.xayappz.booksinfo.R.string.no_internet),
                            this@BookInfo
                    )
                    return
                }
                handler.removeMessages(TRIGGER_SERACH);
                handler.sendEmptyMessageDelayed(TRIGGER_SERACH, SEARCH_TRIGGER_DELAY_IN_MS);


            }


        })
    }

    private fun setBackgroundofSearch(clr: String?) {
        val drawable = GradientDrawable()
        drawable.shape = GradientDrawable.RECTANGLE
        drawable.setStroke(2, Color.parseColor(clr))
        drawable.cornerRadius = 5f
        drawable.setColor(Color.parseColor("#F0F0F6"));

        searchLinearView.setBackgroundDrawable(drawable)
    }


    private suspend fun getSearchResult(query: String) {

        booksViewModel.getSearchedBooks(query).observe(this, {

            getData(it)


        })

    }

    private fun getData(it: Response<BookList>) {
        booksList.clear()
        var author = ""
        var url = ""


        for (BookList in it.data?.results!!) {


            if (!BookList.formats.image_jpeg.isNullOrEmpty()) {
                author = if (
                        BookList.authors.isNullOrEmpty()) {
                    "-"
                } else {
                    BookList.authors[0].name
                }
                if (!BookList.formats.text_html.isNullOrEmpty()) {
                    url = BookList.formats.text_html

                } else if (!BookList.formats.application_rdf_xml.isNullOrEmpty()) {
                    url = BookList.formats.application_rdf_xml

                } else {
                    if (!BookList.formats.text_plain.isNullOrEmpty()) {
                        url = BookList.formats.text_plain

                    }
                }

                val booksModel =
                        BooksModel(
                                BookList.formats.image_jpeg,
                                BookList.title,
                                author, url
                        )
                booksList.add(booksModel)
            }

        }
        loading_view.visibility = View.GONE
        if (booksList.isEmpty()) {
            nothing_lay.visibility = View.VISIBLE
            loading_view.visibility = View.GONE
        } else {
            nothing_lay.visibility = View.GONE

        }

        adapter = BooksAdapter(booksList, this)
        books_grid_view?.adapter = adapter
        val animation: Animation =
                AnimationUtils.loadAnimation(this, com.xayappz.booksinfo.R.anim.grid_anim)
        val controller = GridLayoutAnimationController(animation, .2f, .2f)
        books_grid_view?.layoutAnimation = controller

    }

    private fun getData() {
        booksViewModel.books.observe(this, {
            when (it) {
                is Response.Loading -> {

                    progressShow()
                }
                is Response.Success -> {
                    getData(it)
                    hideProgress()

                }
                is Response.Error -> {

                    hideProgress()
                    Toaster.showToast(getString(com.xayappz.booksinfo.R.string.some_error), this)

                }
            }

        })

    }

    @DelicateCoroutinesApi
    private fun getIntentTitle() {
        titleLbl = intent.getStringExtra("title").toString()
        initializeViewModel()
        initializeViews()


    }

}