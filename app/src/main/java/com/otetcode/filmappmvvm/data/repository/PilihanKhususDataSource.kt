package com.otetcode.filmappmvvm.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.otetcode.filmappmvvm.data.api.TheMovieDBInterface
import com.otetcode.filmappmvvm.data.vo.MovieDetails
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class PilihanKhususDataSource(private val apiService:TheMovieDBInterface, private val compositeDisposable: CompositeDisposable) {

    private val _networkState = MutableLiveData<NetworkState>() //MutableLiveData = data dapat diubah
    val networkState: LiveData<NetworkState> // Live Data berisi network state
        get() = _networkState

    private val _downloadedPilihanKhusus = MutableLiveData<MovieDetails>()
    val downloadedPilihanKhusus: LiveData<MovieDetails>
        get() = _downloadedPilihanKhusus

    fun getInformasiMovie(movieId: Int){
        _networkState.postValue(NetworkState.LOADING)

        try {
            compositeDisposable.add(
                apiService.getMovieDetails(movieId)
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            _downloadedPilihanKhusus.postValue(it)
                            _networkState.postValue(NetworkState.LOADED)
                        },
                        {
                            _networkState.postValue(NetworkState.ERROR)
                            Log.e("PilihanDataSource", it.message)
                        }
                    )
            )

        }catch (e: Exception){
            Log.e("PilihanDataSource",e.message)
        }
    }
}