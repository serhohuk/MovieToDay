package com.sign.movietoday.ui


import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.sign.movietoday.R
import com.sign.movietoday.application.MyApplication
import com.sign.movietoday.other.Constants.APP_LANGUAGE
import com.sign.movietoday.other.Constants.LANG_ENG
import com.sign.movietoday.other.Constants.SETTINGS_APP
import com.sign.movietoday.viewmodels.MovieViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import javax.inject.Inject
import javax.inject.Named


class MainActivity : AppCompatActivity() {
    @Inject
    @Named("viewModel")
    lateinit var movieViewModelFactory : ViewModelProvider.Factory
    val viewModel : MovieViewModel by viewModels { movieViewModelFactory  }
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val appComponent = (application as MyApplication).appComponent
        appComponent.inject(this)

        sharedPreferences = getSharedPreferences(SETTINGS_APP,Context.MODE_PRIVATE)
        val configurationLanguage = sharedPreferences.getString(APP_LANGUAGE, LANG_ENG)
        viewModel.requestLang = configurationLanguage.toString()
        if (configurationLanguage!!.isNotEmpty() && configurationLanguage != LANG_ENG){
            setAppLanguage(configurationLanguage)
        }

        val navHostFragment =supportFragmentManager.findFragmentById(R.id.nav_Fragment_view) as NavHostFragment
        val navController = navHostFragment.navController
        bottomNavigationView.menu.clear()
        bottomNavigationView.inflateMenu(R.menu.bottom_nav_view_menu)
        bottomNavigationView.setupWithNavController(navController)
    }

    private fun setAppLanguage(language : String){
        val config = resources.configuration
        val locale = Locale(language)
        Locale.setDefault(locale)
        config.setLocale(locale)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            createConfigurationContext(config)
        resources.updateConfiguration(config, resources.displayMetrics)
    }
}