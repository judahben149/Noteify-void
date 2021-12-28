package com.judahben149.noteify.fragments

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.judahben149.noteify.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}