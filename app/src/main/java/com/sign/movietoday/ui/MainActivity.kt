package com.sign.movietoday.ui


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.sign.movietoday.R
import com.sign.movietoday.application.MyApplication
import com.sign.movietoday.viewmodels.MovieViewModel
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject
import javax.inject.Named


class MainActivity : AppCompatActivity() {
    @Inject
    @Named("viewModel")
    lateinit var movieViewModelFactory : ViewModelProvider.Factory
    val viewModel : MovieViewModel by viewModels { movieViewModelFactory  }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val appComponent = (application as MyApplication).appComponent
        appComponent.inject(this)

        val navHostFragment =supportFragmentManager.findFragmentById(R.id.nav_Fragment_view) as NavHostFragment
        val navController = navHostFragment.navController
        bottomNavigationView.setupWithNavController(navController)
    }


}