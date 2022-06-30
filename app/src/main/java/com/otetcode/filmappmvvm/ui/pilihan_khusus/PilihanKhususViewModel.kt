package com.otetcode.filmappmvvm.ui.pilihan_khusus

import android.view.View
import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.otetcode.filmappmvvm.data.repository.NetworkState
import com.otetcode.filmappmvvm.data.vo.MovieDetails
import io.reactivex.disposables.CompositeDisposable


class PilihanKhususViewModel (private val pilihanKhususRepository: PilihanKhususRepository, movieId: Int): ViewModel() {

    private val compositeDisposable = CompositeDisposable()


    val pilihanKhusus : LiveData<MovieDetails> by lazy {
        pilihanKhususRepository.getDetailMovieApp(compositeDisposable, movieId)
    }


    val networkState: LiveData<NetworkState> by lazy {
        pilihanKhususRepository.getPilihanKhususNetwork()
    }



    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }


}