package com.randomappsinc.simpleflashcards.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.randomappsinc.simpleflashcards.Adapters.SettingsAdapter;
import com.randomappsinc.simpleflashcards.R;
import com.randomappsinc.simpleflashcards.Utils.MiscUtils;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

/**
 * Created by alexanderchiou on 11/20/15.
 */
public class SettingsActivity extends StandardActivity {
    public static final String SUPPORT_EMAIL = "chessnone@gmail.com";
    public static final String REPO_URL = "https://github.com/Gear61/Simple-Flashcards";

    @Bind(R.id.coordinator_layout) View parent;
    @Bind(R.id.settings_options) ListView settingsOptions;
    @BindString(R.string.feedback_subject) String feedbackSubject;
    @BindString(R.string.send_email) String sendEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        settingsOptions.setAdapter(new SettingsAdapter(this));
    }

    @OnItemClick(R.id.settings_options)
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Intent intent = null;
        switch (position) {
            case 0:
                String uriText = "mailto:" + SUPPORT_EMAIL + "?subject=" + Uri.encode(feedbackSubject);
                Uri mailUri = Uri.parse(uriText);
                Intent sendIntent = new Intent(Intent.ACTION_SENDTO, mailUri);
                startActivity(Intent.createChooser(sendIntent, sendEmail));
                return;
            case 1:
                Uri uri =  Uri.parse("market://details?id=" + getApplicationContext().getPackageName());
                intent = new Intent(Intent.ACTION_VIEW, uri);
                if (!(getPackageManager().queryIntentActivities(intent, 0).size() > 0)) {
                    MiscUtils.showSnackbar(parent, getString(R.string.play_store_error), Snackbar.LENGTH_LONG);
                    return;
                }
                break;
            case 2:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse(REPO_URL));
                break;
        }
        startActivity(intent);
    }
}
