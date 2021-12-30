package com.sihabudin.itask.setting

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat
import androidx.work.Data
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.sihabudin.itask.R
import com.sihabudin.itask.core.utils.AlarmWorker
import java.util.concurrent.TimeUnit

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment())
                .commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    class SettingsFragment : PreferenceFragmentCompat(),
        SharedPreferences.OnSharedPreferenceChangeListener {
        private lateinit var reminderMode: String
        private lateinit var intervalReminder: String
        private lateinit var periodicWorkRequest: PeriodicWorkRequest
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
            reminderMode = resources.getString(R.string.pref_key_notify)
            intervalReminder = resources.getString(R.string.pref_key_interval_notif)
        }

        override fun onResume() {
            super.onResume()
            preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
        }

        override fun onPause() {
            super.onPause()
            preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
        }


        override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {


            val valReminderMode = sharedPreferences.getBoolean(reminderMode, false)
            val valInterval = sharedPreferences.getString(intervalReminder, "")



            when (key) {
                reminderMode -> setupReminder(valReminderMode, valInterval!!.toLong())
                intervalReminder -> setupReminder(valReminderMode, valInterval!!.toLong())
            }

        }

        fun setupReminder(reminderMode: Boolean, interval: Long) {


            if (reminderMode) {


                val data = Data.Builder()
                    .putBoolean(AlarmWorker.activeReminder.toString(), true)
                    .build()

                periodicWorkRequest = PeriodicWorkRequest.Builder(
                    AlarmWorker::class.java,
                    interval,
                    TimeUnit.HOURS
                )
                    .setInputData(data)
                    .build()
                WorkManager.getInstance(requireContext()).enqueue(periodicWorkRequest)
            } else {
                WorkManager.getInstance(requireContext()).cancelAllWork()
            }

        }

    }

}