package com.example.z00c1.passpie_android_app;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

/**
 * Created by Chris Zhu on 2/24/2018.
 */

public class UserAsyncTask extends AsyncTask<String, Integer, GitHubUser> {
    public static final String URL_HEADER = "https://api.github.com/user?access_token=";
    //private Context context;
    private TextView textView;
    //private EditText editText;
    private String urlString;

    public UserAsyncTask(TextView textView, String urlString) {
        //this.context = context;
        this.textView = textView;
        //this.editText = editText;
        this.urlString = urlString;
    }

    @Override
    protected GitHubUser doInBackground(String... params) {
        try {
            URL url = new URL(urlString);

            URLConnection connection = url.openConnection();
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            InputStreamReader inStreamReader =
                    new InputStreamReader(inputStream, Charset.forName("UTF-8"));

            Gson gson = new Gson();
            return gson.fromJson(inStreamReader, GitHubUser.class);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(GitHubUser gitHubUser) {
        if (gitHubUser == null) {
            return;
        }
        textView.setText(gitHubUser.getLogin());
    }
}
