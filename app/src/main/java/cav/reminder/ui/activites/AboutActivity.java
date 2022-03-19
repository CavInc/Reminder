package cav.reminder.ui.activites;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import cav.reminder.BuildConfig;
import cav.reminder.R;

/**
 * Created by cav on 12.03.22.
 */

public class AboutActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_activity);

        ((TextView) findViewById(R.id.about_vesion)) .setText(BuildConfig.VERSION_NAME);
    }
}
