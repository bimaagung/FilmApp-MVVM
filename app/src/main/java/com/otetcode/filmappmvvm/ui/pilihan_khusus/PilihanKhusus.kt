package com.otetcode.filmappmvvm.ui.pilihan_khusus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.otetcode.filmappmvvm.R
import com.otetcode.filmappmvvm.data.api.TheMovieDBClient
import com.otetcode.filmappmvvm.data.api.TheMovieDBInterface
import com.otetcode.filmappmvvm.databinding.ActivityPilihanKhususBinding
import kotlinx.android.synthetic.main.activity_pilihan_khusus.*

class PilihanKhusus : AppCompatActivity() {

    private lateinit var viewModel: PilihanKhususViewModel
    private lateinit var pilihanKhususRepository: PilihanKhususRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_pilihan_khusus)

        var viewModelBinding = ViewModelProviders.of(this).get(PilihanKhususViewModel::class.java)
        var binding : ActivityPilihanKhususBinding = DataBindingUtil.setContentView(this, R.layout.activity_pilihan_khusus)

        binding.pilihanKhusus = "data"
        binding.viewmodel = viewModelBinding

        val movieId: Int = 290859



        val apiService: TheMovieDBInterface = TheMovieDBClient.getClient()
        pilihanKhususRepository = PilihanKhususRepository(apiService)

        viewModel = getViewModel(movieId)

        viewModel.pilihanKhusus.observe(this, Observer {
//            pilihan_khusus.text = it.title


        })

        viewModel.networkState.observe(this, Observer {
            pilihan_khusus_network.text = it.toString()
        })



    }


    private fun getViewModel(movieId: Int): PilihanKhususViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T{
                @Suppress("UNCHECKED_CAST")
                return PilihanKhususViewModel(pilihanKhususRepository, movieId) as T
            }
        })[PilihanKhususViewModel::class.java]
    }
}
