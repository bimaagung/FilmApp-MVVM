package com.otetcode.filmappmvvm.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.otetcode.filmappmvvm.data.api.TheMovieDBInterface
import com.otetcode.filmappmvvm.data.vo.MovieDetails
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

//Repository bertanggung jawab untuk semua data yang akan digunakan di aplikasi. Mau itu menyimpan data,
// melakukan update data, menghapus data atau mencari data serahkan semuanya kepada Repository
// penyimpanan ada diserver aupun local

class MovieDetailsNetworkDataSource (private val apiService: TheMovieDBInterface, private val compositeDisposable: CompositeDisposable){


    //mendeklarasikan variabel kebutuhan untuk eksekusi data


    // ================== Install Live Data untuk menampung data ========================

    private val _networkState = MutableLiveData<NetworkState>() //MutableLiveData = data dapat diubah
    val networkState: LiveData<NetworkState> // Live Data berisi network state
    get() = _networkState

    private val _downloadedMovieDetailsResponse = MutableLiveData<MovieDetails>()
    val downloadedMovieResponse: LiveData<MovieDetails>
    get() = _downloadedMovieDetailsResponse

    // ================== Tutup Live Data untuk menampung data =========================


    //mengambil data movie by id
    fun fetchMovieDetails(movieId: Int){

        //eksekusi koneksi sedang berjalan atau loading
        //post value digunakan untuk membawa data untuk disampaikan ke tahap berikutnya
        _networkState.postValue(NetworkState.LOADING)

        try {
            //Menampilkan data melalui rxjava
            compositeDisposable.add(
                apiService.getMovieDetails(movieId)
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            _downloadedMovieDetailsResponse.postValue(it)
                            _networkState.postValue(NetworkState.LOADED)
                        },
                        {
                            _networkState.postValue(NetworkState.ERROR)
                            Log.e("MovieDetailsDataSource", it.message)
                        }

                    )
            )

        }catch (e: Exception){
            //logika aplikasi error
            Log.e("MovieDetailsDataSource",e.message)
        }
    }

}