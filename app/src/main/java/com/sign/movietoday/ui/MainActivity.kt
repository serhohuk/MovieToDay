package com.sign.movietoday.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.sign.movietoday.R
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject
import javax.inject.Named

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


//        @Inject
//        @Named("Main")
//        lateinit var mainViewModelFactory : ViewModelProvider.Factory
//
//        private val viewModel : MainViewModel by viewModels { mainViewModelFactory }
    }
}