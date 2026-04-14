package com.hector.actividad

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
class AppPreferenciasFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        preferenceManager.sharedPreferencesName = "com.hector.usopreferencias_preferences"
        setPreferencesFromResource(R.xml.misapppreferencias, rootKey)
    }
}
