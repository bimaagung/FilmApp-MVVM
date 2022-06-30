package com.otetcode.filmappmvvm.ui.single_movie_detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.otetcode.filmappmvvm.R
import com.otetcode.filmappmvvm.data.api.POSTER_BASE_URL
import com.otetcode.filmappmvvm.data.api.TheMovieDBClient
import com.otetcode.filmappmvvm.data.api.TheMovieDBInterface
import com.otetcode.filmappmvvm.data.repository.NetworkState
import com.otetcode.filmappmvvm.data.vo.MovieDetails
import kotlinx.android.synthetic.main.activity_single_movie.*
import java.text.NumberFormat
import java.util.*

class SingleMovie : AppCompatActivity() {

    private lateinit var viewModel: SingleMovieViewModel // view model
    private lateinit var movieRepository : MovieDetailsRepository //repository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_movie)

        val movieId: Int = intent.getIntExtra("id", 1) //mengambil id movie

        val apiService: TheMovieDBInterface = TheMovieDBClient.getClient() //akses API
        movieRepository = MovieDetailsRepository(apiService) //impementasi api di repository

        //mengimplementasikan viewModel ke Activity
        viewModel = getViewModel(movieId)

        //====================== Menyampaikan live data ke UI ================

        //perantara mengambil data ke tampilan
        viewModel.movieDetails.observe(this, Observer {
            bindUI(it)
        })

        //perantara memeriksan internet ke tampilan
        viewModel.networkState.observe(this, Observer{
            progress_bar.visibility = if (it == NetworkState.LOADING) View.VISIBLE else View.GONE
            txt_error.visibility = if (it == NetworkState.ERROR) View.VISIBLE else View.GONE
        })

        //====================== Tutup Menyampaikan live data ke UI ================

    }

    fun bindUI(it: MovieDetails) {

        movie_title.text = it.title
        movie_tagline.text = it.tagline
        movie_release_date.text = it.releaseDate
        movie_rating.text = it.rating.toString()
        movie_runtime.text = it.runtime.toString() + " menit"
        movie_overview.text = it.overview

        val formatCurrency : NumberFormat = NumberFormat.getCurrencyInstance(Locale.US)
        movie_budget.text = formatCurrency.format(it.budget)
        movie_reveneue.text = formatCurrency.format(it.revenue)

        val moviePosterURL:String = POSTER_BASE_URL + it.posterPath
        Glide.with(this)
            .load(moviePosterURL)
            .into(iv_movie_poster)
    }

    //function mengimplementasikan viewModel ke Activity
    private fun getViewModel(movieId: Int): SingleMovieViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory{
            override fun <T : ViewModel ?> create(modelClass: Class<T>): T{
                @Suppress("UNCHECKED_CAST")
                return SingleMovieViewModel(movieRepository, movieId) as T
            }
        })[SingleMovieViewModel::class.java]
    }
}
