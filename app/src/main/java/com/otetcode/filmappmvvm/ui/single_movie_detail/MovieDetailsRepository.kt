package com.otetcode.filmappmvvm.ui.single_movie_detail

import androidx.lifecycle.LiveData
import com.otetcode.filmappmvvm.data.api.TheMovieDBInterface
import com.otetcode.filmappmvvm.data.repository.MovieDetailsNetworkDataSource
import com.otetcode.filmappmvvm.data.repository.NetworkState
import com.otetcode.filmappmvvm.data.vo.MovieDetails
import io.reactivex.disposables.CompositeDisposable

class MovieDetailsRepository(private val apiService: TheMovieDBInterface) {

    lateinit var movieDetailsNetworkDataSource: MovieDetailsNetworkDataSource


    //mengambil movie detail by id
    fun fetchSingleMovieDetails(compositeDisposable: CompositeDisposable, movieId: Int) : LiveData<MovieDetails> {

        //menginisiasi MovieDetailsNetworkDataSource
        movieDetailsNetworkDataSource = MovieDetailsNetworkDataSource(apiService, compositeDisposable)
        movieDetailsNetworkDataSource.fetchMovieDetails(movieId)

        return movieDetailsNetworkDataSource.downloadedMovieResponse

    }

    //mengecek status koneksi

    fun getMovieDetailNetworkState(): LiveData<NetworkState> {
        return movieDetailsNetworkDataSource.networkState
    }



}