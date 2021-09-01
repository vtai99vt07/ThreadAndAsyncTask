package com.example.threadandasync;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

public class AsyncActivity extends AppCompatActivity implements View.OnClickListener {

    private ProgressBar progressBar;
    private Button btnRun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async);
        initView();
    }

    private void initView() {
        progressBar = findViewById(R.id.progressBar);
        btnRun = findViewById(R.id.btnRun);
        btnRun.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnRun:
                new ProgressAsyncTask().execute();
                break;
            default:
                break;
        }
    }

    private class ProgressAsyncTask extends AsyncTask<Void, Integer, String> {

        @Override
        protected String doInBackground(Void... voids) {
            for (int i = 0; i <= 100; i++) {
                publishProgress(i);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return "COMPLETE!";
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(AsyncActivity.this, s, Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
        }
    }
}