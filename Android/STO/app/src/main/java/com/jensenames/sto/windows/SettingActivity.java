package com.jensenames.sto.windows;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.jensenames.sto.R;
import com.jensenames.sto.adied.controls.TextToast;
import com.jensenames.sto.record.bean.Degree;
import com.jensenames.sto.record.operate.ScoreRecord;

public class SettingActivity extends PreferenceActivity
        implements Preference.OnPreferenceClickListener, Preference.OnPreferenceChangeListener {

    private AppCompatDelegate mDelegate;
    private SharedPreferences preferences;

    private ListPreference degree;

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

        findPreference("setting_standard").setOnPreferenceClickListener(this);

        degree = (ListPreference) findPreference("setting_degree");
        degree.setOnPreferenceChangeListener(this);

        findPreference("setting_history").setOnPreferenceClickListener(this);
        findPreference("setting_calcu").setOnPreferenceClickListener(this);
        findPreference("setting_share").setOnPreferenceClickListener(this);
        findPreference("setting_clean").setOnPreferenceClickListener(this);

        findPreference("setting_tech").setOnPreferenceClickListener(this);
        findPreference("setting_target").setOnPreferenceClickListener(this);
        findPreference("setting_designer").setOnPreferenceClickListener(this);

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
        }
        return false;
    }

    @Override
    @SuppressLint("LongLogTag")
    public boolean onPreferenceClick(Preference preference) {
        LayoutInflater inflater = getLayoutInflater();
        View layout;
        switch(preference.getKey()) {
            case "setting_history":
                layout = inflater.inflate(R.layout.layout_history,(ViewGroup) findViewById(R.id.history));
                new AlertDialog.Builder(this).setTitle(getString(R.string.setting_history)).setView(layout).show();
                return true;
            case "setting_calcu":
                layout = inflater.inflate(R.layout.layout_statistics,(ViewGroup) findViewById(R.id.statistics));
                new AlertDialog.Builder(this).setTitle(getString(R.string.setting_calcu)).setView(layout).show();
                return true;
            case "setting_share":
                Uri uri = Uri.fromFile(ScoreRecord.getRecordFile());
                Log.d("share", "uri:" + uri);
                Intent share = new Intent();
                share.setAction(Intent.ACTION_SEND);
                share.putExtra(Intent.EXTRA_STREAM, uri);
                share.setType("text/plain");
                startActivity(Intent.createChooser(share, getResources().getString(R.string.share_text)));
                return true;
            case "setting_clean":
                new CleanBox(this, R.style.AppDialog).show();
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

    private class CleanBox extends AlertDialog.Builder implements DialogInterface.OnClickListener {

        CleanBox(Context context, int theme) {
            super(context, theme);
            this.setCancelable(false);
            this.setTitle(getResources().getString(R.string.setting_clean));
            this.setMessage(getResources().getString(R.string.clean_prompt));
            this.setPositiveButton(getResources().getString(R.string.sure), this);
            this.setNegativeButton(getResources().getString(R.string.cancel), this);
        }

        @Override
        public void onClick(DialogInterface dialogInterface, int which) {
            switch (which) {
                case android.app.AlertDialog.BUTTON_POSITIVE:
                    ScoreRecord.delete();
                    TextToast.showTextToast(getResources().getString(R.string.clean_complete), getContext());
                    break;
                case android.app.AlertDialog.BUTTON_NEGATIVE:
                    break;
            }
        }
    }
}
