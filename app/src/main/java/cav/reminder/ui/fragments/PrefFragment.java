package cav.reminder.ui.fragments;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import cav.reminder.R;

public class PrefFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref);
    }
}