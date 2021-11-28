package com.xayappz.views

import android.app.AlertDialog
import android.app.ProgressDialog
import android.os.Bundle
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
import kotlinx.android.synthetic.main.seach_view.*
import kotlinx.coroutines.*
import android.R





class BookInfo : AppCompatActivity() {
    lateinit var booksViewModel: BooksViewModel
    var titleLbl: String = ""
    var booksList: ArrayList<BooksModel> = ArrayList()
    var adapter: BooksAdapter? = null
    lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.xayappz.booksinfo.R.layout.activity_book_info)

        getIntentTitle()

    }

    private fun ProgressShow() {
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
            finish()
        }
        ProgressShow()
        getData()
        getSearch()

    }


    @DelicateCoroutinesApi
    private fun getSearch() {
        searchEditText.setOnFocusChangeListener { view, b ->
            if (view.isFocused) {
                search_close_icon.visibility = View.VISIBLE
            }
        }
        search_close_icon.setOnClickListener {
            searchEditText.setText("")
            search_close_icon.visibility = View.GONE
            searchEditText.clearFocus()
            HideKeyboard.hideKeyboard(this, it)

        }
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (!isOnline.isOnline(applicationContext)) {
                    Toaster.showToast(getString(com.xayappz.booksinfo.R.string.no_internet), this@BookInfo)
                    return
                }
                val query = s.toString()
                GlobalScope.launch(Dispatchers.Main) {
                    getSearchResult(query)


                }
            }


            override fun afterTextChanged(s: Editable) {
            }
        })
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
        adapter = BooksAdapter(booksList, this)
        books_grid_view?.adapter = adapter
        val animation: Animation = AnimationUtils.loadAnimation(this, com.xayappz.booksinfo.R.anim.grid_anim)
        val controller = GridLayoutAnimationController(animation, .2f, .2f)
        books_grid_view?.layoutAnimation = controller

    }

    private fun getData() {
        booksViewModel.books.observe(this, {
            when (it) {
                is Response.Loading -> {

                    ProgressShow()
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

    private fun getIntentTitle() {
        titleLbl = intent.getStringExtra("title").toString()
        initializeViewModel()
        initializeViews()


    }

}