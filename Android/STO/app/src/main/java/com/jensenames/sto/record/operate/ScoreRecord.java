package com.jensenames.sto.record.operate;

import android.content.Context;
import android.os.Environment;

import com.jensenames.sto.R;
import com.jensenames.sto.adied.controls.TextToast;
import com.jensenames.sto.record.bean.Score;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ScoreRecord {

    private static ArrayList<Score> totalScores;
    private static File recordFile;

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void init(Context context) {
        File rootFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/STO");
        recordFile = new File(rootFile.getPath() + "/record.sto");

        totalScores = new ArrayList<>();
        if(!rootFile.exists()) {
            rootFile.mkdirs();
            try {
                recordFile.createNewFile();
            } catch (IOException e) {
                TextToast.showTextToast(context.getResources().getString(R.string.record_file_create_error), context);
            }
        } else {
            if(!recordFile.exists())
                try {
                    recordFile.createNewFile();
                } catch (IOException e) {
                    TextToast.showTextToast(context.getResources().getString(R.string.record_file_create_error), context);
                }
            else {
                try {
                    InputStream stream = new FileInputStream(recordFile);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
                    String line;
                    while ((line = reader.readLine()) != null)
                        totalScores.add(new Score(line));
                    stream.close();
                    reader.close();
                } catch (IOException e) {
                    TextToast.showTextToast(context.getResources().getString(R.string.record_file_read_error), context);
                }
            }
        }
    }

    public static void write(Score score, Context context) {
        try {
            FileWriter writer = new FileWriter(recordFile.getAbsoluteFile(), true);
            writer.write(score.toString());
            writer.write("\r\n" );
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            TextToast.showTextToast(context.getResources().getString(R.string.record_file_write_error), context);
        }
        totalScores.add(score);
    }

    public static ArrayList<Score> getTotalScores() {
        return totalScores;
    }

    public static File getRecordFile() {
        return recordFile;
    }
}
