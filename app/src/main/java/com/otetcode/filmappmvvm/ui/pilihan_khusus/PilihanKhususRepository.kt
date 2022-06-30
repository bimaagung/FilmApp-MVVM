package com.otetcode.filmappmvvm.ui.pilihan_khusus

import androidx.lifecycle.LiveData
import com.otetcode.filmappmvvm.data.api.TheMovieDBInterface
import com.otetcode.filmappmvvm.data.repository.NetworkState
import com.otetcode.filmappmvvm.data.repository.PilihanKhususDataSource
import com.otetcode.filmappmvvm.data.vo.MovieDetails
import io.reactivex.disposables.CompositeDisposable

class PilihanKhususRepository(private val apiService: TheMovieDBInterface) {

    lateinit var pilihanKhususDataSource: PilihanKhususDataSource

    fun getDetailMovieApp(compositeDisposable: CompositeDisposable, movieId: Int) : LiveData<MovieDetails> {

        pilihanKhususDataSource = PilihanKhususDataSource(apiService, compositeDisposable)
        pilihanKhususDataSource.getInformasiMovie(movieId)

        return pilihanKhususDataSource.downloadedPilihanKhusus
    }

    fun getPilihanKhususNetwork(): LiveData<NetworkState>{
        return pilihanKhususDataSource.networkState
    }
}