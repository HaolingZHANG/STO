package com.jensenames.sto.windows;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.jensenames.sto.R;
import com.jensenames.sto.adied.controls.TextToast;
import com.jensenames.sto.record.operate.ScoreRecord;
import com.jensenames.sto.record.bean.Degree;

public class ProgramEntry extends AppCompatActivity  implements View.OnClickListener {

    private static final int REQUEST = 1;
    private static final String[] permissions = new String[] {
            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_entry);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(getResources().getString(R.string.training));

        findViewById(R.id.calculate_run_balance).setOnClickListener(this);
        findViewById(R.id.plum_bleep_test).setOnClickListener(this);
        findViewById(R.id.legend_bleep_test).setOnClickListener(this);

        Degree.initDegree(getApplicationContext().getSharedPreferences("config", MODE_PRIVATE).getInt("Degree", 1));

        if(checkPermissionGranted(permissions))
            ActivityCompat.requestPermissions(this, permissions, REQUEST);
        else
            ScoreRecord.init(getApplicationContext());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_program_entry, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_set:
                Intent intent = new Intent(ProgramEntry.this, SettingActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.activity_in_1,  R.anim.activity_out_1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.calculate_run_balance:
                startMain(1);
                break;
            case R.id.plum_bleep_test:
                startMain(2);
                break;
            case R.id.legend_bleep_test:
                startMain(3);
        }
    }

    private void startMain(int trainType) {
        Intent intent = new Intent(ProgramEntry.this, MainActivity.class);
        intent.putExtra("type", trainType);
        startActivity(intent);
        overridePendingTransition(R.anim.activity_in_1, R.anim.activity_out_1);
    }

    private boolean checkPermissionGranted(String[] permissions){
        boolean flag = true;
        for(String p:permissions){
            if(ActivityCompat.checkSelfPermission(this, p) != PackageManager.PERMISSION_GRANTED){
                flag = false;
                break;
            }
        }
        return flag;
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST:
                if (checkPermissionGranted(permissions))
                    ScoreRecord.init(getApplicationContext());
                else {
                    TextToast.showTextToast(getResources().getString(R.string.no_permission), getApplicationContext());
                    this.finish();
                    System.exit(0);
                }
        }
    }
}
