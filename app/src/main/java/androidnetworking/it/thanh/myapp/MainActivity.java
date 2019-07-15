package androidnetworking.it.thanh.myapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {
    private ImageView logo;
    private RelativeLayout user;
    private TextView tvUsername;
    private EditText username;
    private RelativeLayout pass;
    private TextView tvPassword;
    private EditText password;
    private Button login;
    private EditText name;
    private static final String LINE_FEED = "\r\n";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logo = (ImageView) findViewById(R.id.logo);
        user = (RelativeLayout) findViewById(R.id.user);
        tvUsername = (TextView) findViewById(R.id.tv_username);
        username = (EditText) findViewById(R.id.username);
        pass = (RelativeLayout) findViewById(R.id.pass);
        tvPassword = (TextView) findViewById(R.id.tv_password);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        name = (EditText) findViewById(R.id.name);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initialize  AsyncLogin() class with email and password
                new AsyncLogin().execute(username.getText().toString(),password.getText().toString(),name.getText().toString());

            }
        });
    }
    private class AsyncLogin extends AsyncTask<String, String, String>
    {
        ProgressDialog pdLoading = new ProgressDialog(MainActivity.this);
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }
        @Override
        protected String doInBackground(String... params) {
            try {

                // Enter URL address where your php file resides
                url = new URL("http://dotplays.com/android/login.php");

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return "exception";
            }
            try {
                // Setup HttpURLConnection class to send and receive data from php and mysql
                conn = (HttpURLConnection)url.openConnection();
                conn.setRequestMethod("POST");
                StringBuilder data = new StringBuilder();
                data.append("username");
                data.append("=");
                data.append(params[0]);
                data.append("&");
                data.append("password");
                data.append("=");
                data.append(params[1]);
                data.append("&");
                data.append("name");
                data.append("=");
                data.append(params[2]);
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));

                writer.append(data);
                writer.flush();
                writer.close();
                os.close();
                StringBuilder response = new StringBuilder();

                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    String line;
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    while ((line = br.readLine()) != null) {
                        response.append(line);
                    }
                }
                return response.toString();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return "exception";
            }
        }

        @Override
        protected void onPostExecute(String result) {

            //this method will be running on UI thread

            pdLoading.dismiss();

            Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();
               if (result.equalsIgnoreCase("Trưa nay không được ăn cơm rồi !!!")){

                // If username and password does not match display a error message
                Toast.makeText(MainActivity.this, "Invalid email or password: Trưa nay không được ăn cơm rồi !!!", Toast.LENGTH_LONG).show();

            } else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {

                Toast.makeText(MainActivity.this, "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();

            }else {
                   Intent intent = new Intent(MainActivity.this,HomeActivity.class);
                   intent.putExtra("success", result);
                   startActivity(intent);
               }
        }

    }

}
