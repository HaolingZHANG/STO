package com.jensenames.sto.windows;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.jensenames.sto.R;
import com.jensenames.sto.record.bean.Degree;
import com.jensenames.sto.adied.controls.TextToast;

public class SettingActivity extends PreferenceActivity
        implements Preference.OnPreferenceClickListener, Preference.OnPreferenceChangeListener {

    private AppCompatDelegate mDelegate;
    private SharedPreferences preferences;

    private Preference name;
    private ListPreference grade;
    private ListPreference degree;
    private EditTextPreference email;

    @Override
    @SuppressWarnings("deprecation")
    protected void onCreate(Bundle savedInstanceState) {
        getDelegate().installViewFactory();
        getDelegate().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_setting);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        addPreferencesFromResource(R.xml.preferences);

        init();
    }

    @SuppressWarnings("deprecation")
    private void init() {
        preferences = getApplicationContext().getSharedPreferences("config", MODE_PRIVATE);
        name = findPreference("setting_name");
        name.setOnPreferenceChangeListener(this);
        grade = (ListPreference) findPreference("setting_grade");
        grade.setOnPreferenceChangeListener(this);
        degree = (ListPreference) findPreference("setting_degree");
        degree.setOnPreferenceChangeListener(this);

        findPreference("setting_check").setOnPreferenceClickListener(this);
        email = (EditTextPreference) findPreference("setting_email");
        email.setOnPreferenceChangeListener(this);
        findPreference("setting_send").setOnPreferenceClickListener(this);
        findPreference("setting_standard").setOnPreferenceClickListener(this);

        findPreference("setting_tech").setOnPreferenceClickListener(this);
        findPreference("setting_target").setOnPreferenceClickListener(this);
        findPreference("setting_designer").setOnPreferenceClickListener(this);

        name.setSummary(preferences.getString("Name", ""));
        switch(preferences.getInt("Grade", 1)) {
            case 1:
                grade.setDefaultValue(1);
                grade.setSummary(getResources().getString(R.string.grade_1));
                break;
            case 2:
                grade.setDefaultValue(2);
                grade.setSummary(getResources().getString(R.string.grade_2));
                break;
            case 3:
                grade.setDefaultValue(3);
                grade.setSummary(getResources().getString(R.string.grade_3));
                break;
            case 4:
                grade.setDefaultValue(4);
                grade.setSummary(getResources().getString(R.string.grade_4));
        }
        switch(preferences.getInt("Degree", 1)) {
            case 1:
                degree.setDefaultValue(1);
                degree.setSummary(getResources().getString(R.string.degree_1));
                break;
            case 2:
                degree.setDefaultValue(2);
                degree.setSummary(getResources().getString(R.string.degree_2));
                break;
            case 3:
                degree.setDefaultValue(3);
                degree.setSummary(getResources().getString(R.string.degree_3));
                break;
            case 4:
                degree.setDefaultValue(4);
                degree.setSummary(getResources().getString(R.string.degree_4));
        }
        email.setSummary(preferences.getString("Emails", ""));
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        getDelegate().onPostCreate(savedInstanceState);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        getDelegate().setContentView(layoutResID);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        getDelegate().onPostResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        getDelegate().onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getDelegate().onDestroy();
    }

    private void setSupportActionBar(@Nullable Toolbar toolbar) {
        getDelegate().setSupportActionBar(toolbar);

        ActionBar supportActionBar = getDelegate().getSupportActionBar();

        if (supportActionBar != null) {
            supportActionBar.setHomeAsUpIndicator(R.mipmap.arrow_left);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                overridePendingTransition(R.anim.activity_in_2, R.anim.activity_out_2);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            this.finish();
            overridePendingTransition(R.anim.activity_in_2, R.anim.activity_out_2);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    @SuppressLint("CommitPrefEdits")
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        String key = preference.getKey();

        SharedPreferences.Editor editor = preferences.edit();
        switch(key) {
            case "setting_name":
                editor.putString("Name", (String) newValue);
                editor.apply();
                name.setSummary((String) newValue);
                return true;
            case "setting_grade":
                switch((String) newValue) {
                    case "1":
                        editor.putInt("Grade", 1);
                        grade.setSummary(getResources().getString(R.string.grade_1));
                        break;
                    case "2":
                        editor.putInt("Grade", 2);
                        grade.setSummary(getResources().getString(R.string.grade_2));
                        break;
                    case "3":
                        editor.putInt("Grade", 3);
                        grade.setSummary(getResources().getString(R.string.grade_3));
                        break;
                    case "4":
                        editor.putInt("Grade", 4);
                        grade.setSummary(getResources().getString(R.string.grade_4));
                }
                editor.apply();
                return true;
            case "setting_degree":
                switch((String) newValue) {
                    case "1":
                        editor.putInt("Degree", 1);
                        degree.setSummary(getResources().getString(R.string.degree_1));
                        Degree.initDegree(1);
                        break;
                    case "2":
                        editor.putInt("Degree", 2);
                        degree.setSummary(getResources().getString(R.string.degree_2));
                        Degree.initDegree(2);
                        break;
                    case "3":
                        editor.putInt("Degree", 3);
                        degree.setSummary(getResources().getString(R.string.degree_3));
                        Degree.initDegree(3);
                        break;
                    case "4":
                        editor.putInt("Degree", 4);
                        degree.setSummary(getResources().getString(R.string.degree_4));
                        Degree.initDegree(4);
                }
                editor.apply();
                return true;
            case "setting_email":
                editor.putString("Emails", (String) newValue);
                editor.apply();
                email.setSummary((String) newValue);
                return true;
        }
        return false;
    }

    @Override
    @SuppressLint("LongLogTag")
    public boolean onPreferenceClick(Preference preference) {
        LayoutInflater inflater = getLayoutInflater();
        View layout;
        SureBox box;
        switch(preference.getKey()) {
            case "setting_check":
                layout = inflater.inflate(R.layout.layout_score,(ViewGroup) findViewById(R.id.score));
                new AlertDialog.Builder(this).setTitle(getString(R.string.setting_check)).setView(layout).show();
                return true;
            case "setting_send":
                box = new SureBox(this);
                box.setType(SureBox.SEND);
                box.show();
                return true;
            case "setting_standard":
                layout = inflater.inflate(R.layout.layout_standard,(ViewGroup) findViewById(R.id.standard));
                new AlertDialog.Builder(this).setTitle(getString(R.string.setting_standard)).setView(layout).show();
                return true;
            case "setting_tech":
                layout = inflater.inflate(R.layout.layout_tech,(ViewGroup) findViewById(R.id.tech));
                new AlertDialog.Builder(this).setTitle(getString(R.string.setting_tech)).setView(layout).show();
                return true;
            case "setting_target":
                layout = inflater.inflate(R.layout.layout_target,(ViewGroup) findViewById(R.id.target));
                new AlertDialog.Builder(this).setTitle(getString(R.string.setting_target)).setView(layout).show();
                return true;
            case "setting_designer":
                layout = inflater.inflate(R.layout.layout_self,(ViewGroup) findViewById(R.id.self));
                new AlertDialog.Builder(this).setTitle(getString(R.string.setting_designer)).setView(layout).show();
                return true;
        }
        return false;
    }

    private AppCompatDelegate getDelegate() {
        if (mDelegate == null)
            mDelegate = AppCompatDelegate.create(this, null);

        return mDelegate;
    }

    private class SureBox extends android.app.AlertDialog.Builder implements DialogInterface.OnClickListener {

        static final int SEND = 2;

        private int sureType;

        SureBox(Context context) {
            super(context);
            this.setCancelable(false);
            this.setPositiveButton(getResources().getString(R.string.sure), this);
            this.setNegativeButton(getResources().getString(R.string.cancel), this);
        }

        void setType(int type) {
            switch (type) {
                case SEND:
                    this.setTitle(getResources().getString(R.string.send_sure));
                    this.setMessage(getResources().getString(R.string.send_sure_message));
                    sureType = SEND;
            }
        }

        @Override
        public void onClick(DialogInterface dialogInterface, int which) {
            switch (which) {
                case AlertDialog.BUTTON_POSITIVE:
                    switch (sureType) {
                        case SEND:
                            TextToast.showTextToast(getResources().getString(R.string.record_file_text_toast), getContext());
                            Intent data = new Intent(Intent.ACTION_SEND);
                            data.putExtra(Intent.EXTRA_EMAIL, preferences.getString("Emails", "").split(";"));
                            data.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.record_file_train_record));
                            data.putExtra(Intent.EXTRA_TEXT, getMessage());
                            data.setType("plain/text");
                            startActivity(Intent.createChooser(data, getResources().getString(R.string.record_file_text)));
                    }
                    break;
                case AlertDialog.BUTTON_NEGATIVE:
                    break;
            }
        }

        private String getMessage() {
            String message = getResources().getString(R.string.setting_name) + ":" + preferences.getString("Name", "") + "\n";
            message += getResources().getString(R.string.setting_grade) + ":" + preferences.getInt("Grade", 1) + "\n";
            return message;
        }
    }
}
