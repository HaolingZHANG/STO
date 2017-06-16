package com.jensenames.sto.windows;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.jensenames.sto.R;
import com.jensenames.sto.calculate.view.CalculateLayout;
import com.jensenames.sto.legend.bean.Legend;
import com.jensenames.sto.plum.bean.Position;
import com.jensenames.sto.plum.view.PlumLayout;
import com.jensenames.sto.legend.view.LegendLayout;
import com.jensenames.sto.adied.leger.service.SoundPrompt;
import com.jensenames.sto.adied.leger.service.VibratorPrompt;
import com.jensenames.sto.adied.controls.TextToast;

import java.lang.reflect.Field;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private CalculateLayout calculateLayout;
    private PlumLayout plumLayout;
    private LegendLayout legendLayout;

    @SuppressLint("StaticFieldLeak")
    private static MainActivity staticActivity;

    private static boolean isProcess;
    private static int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();

        if (supportActionBar != null) {
            supportActionBar.setHomeAsUpIndicator(R.mipmap.arrow_left);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }

        type = getIntent().getIntExtra("type", 0);
        switch (type) {
            case 0:this.finish();break;
            case 1:
                setTitle(getResources().getString(R.string.calculate_run_balance));
                calculateLayout = (CalculateLayout) findViewById(R.id.calculate);
                calculateLayout.setVisibility(View.VISIBLE);
                break;
            case 2:
                setTitle(getResources().getString(R.string.plum_bleep_test));
                plumLayout = (PlumLayout) findViewById(R.id.plum);
                plumLayout.setVisibility(View.VISIBLE);
                break;
            case 3:
                setTitle(getResources().getString(R.string.legend_bleep_test));
                legendLayout = (LegendLayout) findViewById(R.id.legend);
                legendLayout.setVisibility(View.VISIBLE);
        }
        findViewById(R.id.start).setOnClickListener(this);
        findViewById(R.id.stop).setOnClickListener(this);

        isProcess = false;
        staticActivity = this;

        new EnterBox(this).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        returnEnter();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start:
                if(isProcess) {
                    TextToast.showTextToast(getResources().getString(R.string.is_start), getApplicationContext());
                } else {
                    isProcess = true;
                    TextToast.showTextToast(getResources().getString(R.string.start), getApplicationContext());
                    switch (type) {
                        case 1:
                            calculateLayout.start();
                            break;
                        case 2:
                            SoundPrompt.startRing(MainActivity.this);
                            plumLayout.start();
                            break;
                        case 3:
                            SoundPrompt.startRing(MainActivity.this);
                            legendLayout.start();
                    }
                }
                break;
            case R.id.stop:
                if(isProcess)
                    new StopBox(this).show();
                else
                    TextToast.showTextToast(getResources().getString(R.string.is_stop), getApplicationContext());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                if(isProcess)
                    TextToast.showTextToast(getResources().getString(R.string.is_process), getApplicationContext());
                else
                    returnEnter();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void returnEnter() {
        isProcess = false;
        this.finish();
        Position.clear();
        Legend.clear();
        overridePendingTransition(R.anim.activity_in_2, R.anim.activity_out_2);
    }

    public static MainActivity getStaticActivity() {
        return staticActivity;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return keyCode == KeyEvent.KEYCODE_BACK || super.onKeyDown(keyCode, event);
    }

    public static void call() {
        VibratorPrompt.Vibrate(staticActivity, 300);
    }

    public static boolean isProcess() {
        return isProcess;
    }

    public static void clearProcess() {
        isProcess = false;
    }

    @SuppressLint("NewApi")
    private class EnterBox extends AlertDialog.Builder implements DialogInterface.OnClickListener {

        private EditText inputServer;

        EnterBox(Context context) {
            super(context);
            this.setCancelable(false);
            String title = "";
            switch (type) {
                case 1:title = getResources().getString(R.string.enter_length);break;
                case 2:title = getResources().getString(R.string.enter_count);break;
                case 3:title = getResources().getString(R.string.enter_trip);
            }
            initView(context,title);
        }

        private void initView(Context context,String title) {
            this.setTitle(title);
            inputServer = new EditText(context);
            inputServer.setGravity(Gravity.CENTER);
            inputServer.setCursorVisible(true);
            inputServer.setInputType(EditorInfo.TYPE_CLASS_PHONE);
            this.setView(inputServer);
            this.setPositiveButton(getResources().getString(R.string.sure), this);
            this.setNegativeButton(getResources().getString(R.string.reset), this);
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            try {
                Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
                field.setAccessible(true);
                field.set(dialog, false);
                field.setAccessible(false);

                switch (which) {
                    case AlertDialog.BUTTON_POSITIVE:
                        try {
                            int number = Integer.parseInt(String.valueOf(inputServer.getText()));

                            switch (type) {
                                case 1:
                                    if(number >= 800 && number <= 8000) {
                                        field.setAccessible(true);
                                        field.set(dialog, true);
                                        field.setAccessible(false);
                                        calculateLayout.setLength(number);
                                        hide();
                                    } else {
                                        TextToast.showTextToast(getResources().getString(R.string.wrong_number), getApplicationContext());
                                        inputServer.setText("");
                                    }
                                    break;
                                case 2:
                                    if(number >= 20 && number <= 100) {
                                        field.setAccessible(true);
                                        field.set(dialog, true);
                                        field.setAccessible(false);
                                        plumLayout.setCount(number);
                                        hide();
                                    } else {
                                        TextToast.showTextToast(getResources().getString(R.string.wrong_number), getApplicationContext());
                                        inputServer.setText("");
                                    }
                                    break;
                                case 3:
                                    if(number >= 20 && number <= 100) {
                                        field.setAccessible(true);
                                        field.set(dialog, true);
                                        field.setAccessible(false);
                                        legendLayout.setCount(number);
                                        hide();
                                    } else {
                                        TextToast.showTextToast(getResources().getString(R.string.wrong_number), getApplicationContext());
                                        inputServer.setText("");
                                    }
                            }
                        } catch (NumberFormatException ex) {
                            TextToast.showTextToast(getResources().getString(R.string.not_number), getApplicationContext());
                            inputServer.setText("");
                        }
                        break;
                    case AlertDialog.BUTTON_NEGATIVE:
                        if(Objects.equals(inputServer.getText().toString(), ""))
                            returnEnter();
                        inputServer.setText("");
                        break;
                    default:break;
                }
            } catch ( NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        private void hide() {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(inputServer.getWindowToken(), 0);
        }
    }

    private class StopBox extends AlertDialog.Builder implements DialogInterface.OnClickListener {

        StopBox(Context context) {
            super(context);
            this.setCancelable(false);
            this.setTitle(getResources().getString(R.string.quit_train));
            this.setMessage(getResources().getString(R.string.quit_train_message));
            this.setPositiveButton(getResources().getString(R.string.sure), this);
            this.setNegativeButton(getResources().getString(R.string.cancel), this);
        }

        @Override
        public void onClick(DialogInterface dialogInterface, int which) {
            switch (which) {
                case AlertDialog.BUTTON_POSITIVE:
                    isProcess = false;
                    TextToast.showTextToast(getResources().getString(R.string.stop), getApplicationContext());
                    switch (type) {
                        case 1:
                            calculateLayout.stop();
                            break;
                        case 2:
                            SoundPrompt.stopRing();
                            plumLayout.stop();
                            break;
                        case 3:
                            SoundPrompt.stopRing();
                            legendLayout.stop();
                    }
                    break;
                case AlertDialog.BUTTON_NEGATIVE:
                    break;
            }
        }
    }
}
