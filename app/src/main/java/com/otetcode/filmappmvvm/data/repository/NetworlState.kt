package com.otetcode.filmappmvvm.data.repository

//https://api.themoviedb.org/3/movie/475557?api_key=ebaceeb826fdec0bd4404f56ce9deae3

//untuk mendefinisikan aksi aplikasi terhubung internet atau error

enum class Status{
    RUNNING,
    SUCCESS,
    FAILED
}

class NetworkState(val status: Status, val msg: String){

    companion object {
        val LOADED: NetworkState
        val LOADING: NetworkState
        val ERROR: NetworkState
        val ENDOFLIST: NetworkState

        init {
            LOADED = NetworkState(Status.SUCCESS, "Berhasil")
            LOADING = NetworkState(Status.RUNNING, "Berjalan")
            ERROR = NetworkState(Status.FAILED, "Ada kesalahan koneksi")
            ENDOFLIST = NetworkState(Status.FAILED, "Konten sudah habis")
        }
    }

}