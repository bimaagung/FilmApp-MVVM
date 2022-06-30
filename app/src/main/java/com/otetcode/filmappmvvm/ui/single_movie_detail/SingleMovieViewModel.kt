package com.otetcode.filmappmvvm.ui.single_movie_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.otetcode.filmappmvvm.data.repository.NetworkState
import com.otetcode.filmappmvvm.data.vo.MovieDetails
import io.reactivex.disposables.CompositeDisposable

class SingleMovieViewModel (private val movieRepository : MovieDetailsRepository, movieId:Int): ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    //Menginisiasi movieDetail
    val movieDetails : LiveData<MovieDetails> by lazy { //lazy mnegembalikan nilai yang ada di garis bawah
        movieRepository.fetchSingleMovieDetails(compositeDisposable, movieId)
    }

    //Menginisiasi network state atau mengecek koneksi
    val networkState : LiveData<NetworkState> by lazy{
        movieRepository.getMovieDetailNetworkState()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}