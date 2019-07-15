package androidnetworking.it.thanh.myapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HomeActivity extends AppCompatActivity {
    private TextView getUser;
    private TextView getData;
    private Button logout;
    private String url="http://dotplays.com/android/bai1.php?food=today";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getUser = (TextView) findViewById(R.id.getUser);
        getData = (TextView) findViewById(R.id.getData);
        logout = (Button) findViewById(R.id.logout);
        String success = getIntent().getStringExtra("success");
        String name = getIntent().getStringExtra("name");
        getUser.setText(success);
        new getDatas().execute(url);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    class getDatas extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                InputStream inputStream = httpURLConnection.getInputStream();

                BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));

                StringBuilder total = new StringBuilder();

                for (String line; (line = r.readLine()) != null; ) {
                    total.append(line).append('\n');
                }

                return total.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            getData.setText(s.toString());
        }
    }
}
