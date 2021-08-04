package com.sign.movietoday.ui.fragments

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.sign.movietoday.R
import com.sign.movietoday.other.Constants.APP_LANGUAGE
import com.sign.movietoday.other.Constants.LANG_ENG
import com.sign.movietoday.other.Constants.LANG_RU
import com.sign.movietoday.other.Constants.LANG_UA
import com.sign.movietoday.other.Constants.SETTINGS_APP
import kotlinx.android.synthetic.main.settings_fragment_layout.*

class SettingsFragment : Fragment(R.layout.settings_fragment_layout) {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var selectedLanguage : String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = requireActivity().getSharedPreferences(SETTINGS_APP,Context.MODE_PRIVATE)

        button_change_language.setOnClickListener {
            showOptionDialog()
        }
    }

    private fun showOptionDialog() {
        var checkedItem = 0
        val languagesList = arrayOf(
            resources.getString(R.string.eng_lang),
            resources.getString(R.string.ukr_lang),
            resources.getString(R.string.rus_lang))
        AlertDialog.Builder(activity)
            .setTitle(resources.getString(R.string.choseLang))
            .setSingleChoiceItems(languagesList,0 , DialogInterface.OnClickListener { dialogInterface, position ->
                checkedItem = position
            })
            .setPositiveButton(resources.getString(R.string.submit)){dialog , id ->
                sharedPreferences.edit().putString(APP_LANGUAGE,chosenLanguagePosition(checkedItem)).apply()
                Toast.makeText(activity, resources.getString(R.string.toast_your_lang_change), Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
            .setNegativeButton(resources.getString(R.string.cancel)){ dialog, id ->
                dialog.dismiss()
            }
            .show()
    }

    private fun chosenLanguagePosition(num : Int) : String{
        when(num){
            0-> return LANG_ENG
            1-> return LANG_UA
            else ->{
                return LANG_RU
            }
        }
    }
}