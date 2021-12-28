package com.judahben149.noteify

import android.app.Application
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager

class NoteifyApplication: Application(), SharedPreferences.OnSharedPreferenceChangeListener {

    override fun onCreate() {
        super.onCreate()

        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        setTheme(preferences)

        preferences.registerOnSharedPreferenceChangeListener(this)
    }



    private fun setTheme(preferences: SharedPreferences) {
        val themeKey = getThemeKey()
        val default = getString(R.string.follow_system)

        when(preferences.getString(themeKey, default)) {
            getString(R.string.light) -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            getString(R.string.dark) -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            getString(R.string.follow_system) -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }

    }

    private fun getThemeKey() = getString(R.string.themeKey)

    override fun onSharedPreferenceChanged(preferences: SharedPreferences, key: String?) {
        if (key == getThemeKey()) {
            setTheme(preferences)
        }
    }
}