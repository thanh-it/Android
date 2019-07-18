package androidnetworking.it.thanh.myapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private Button Sports;
    private Button Mathss;
    private String url="http://dotplays.com/android/lab3.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //String success = getIntent().getStringExtra("success");
        //String name = getIntent().getStringExtra("name");
        //getUser.setText(success);
        Sports = findViewById(R.id.sports);
        Mathss = findViewById(R.id.mathss);
        new getDatas().execute(url);


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
            JSONObject root = null;
            try {
                root = new JSONObject(s);
                JSONObject quiz = root.getJSONObject("quiz");
                JSONObject sport = quiz.getJSONObject("sport");
                final ArrayList<QzSport> sportList = new ArrayList<>();
                Log.e("sport",String.valueOf(sport));
                for (int i = 0; i < sport.length(); i++) {
                    JSONObject Sport = sport.getJSONObject("q"+ (i+1));
                    String cauhoi = String.valueOf(i);
                    JSONArray options = Sport.getJSONArray("options");
                    String A = options.getString(0);

                    String B = options.getString(1);
                    String C = options.getString(2);
                    String D = options.getString(3);
                    String WH = Sport.getString("question");
                    String QS = Sport.getString("answer");
                    QzSport qzSport = new QzSport();

                    qzSport.A = A;
                    qzSport.B = B;
                    qzSport.C = C;
                    qzSport.D = D;
                    qzSport.question = WH;
                    qzSport.answer = QS;
                    sportList.add(qzSport);
                }
                Log.e("Sport ===============",String.valueOf(sportList));
                JSONObject maths = quiz.getJSONObject("maths");
                Log.e("maths",String.valueOf(maths));
                final ArrayList<QzMath> mathList = new ArrayList<>();
                for (int i = 0; i < maths.length(); i++) {
                    JSONObject mathsJSONObject = maths.getJSONObject("q"+ (i+1));
                    String cauhoi = String.valueOf(i);
                    JSONArray options = mathsJSONObject.getJSONArray("options");
                    String A = options.getString(0);
                    String B = options.getString(1);
                    String C = options.getString(2);
                    String D = options.getString(3);
                    String WH = mathsJSONObject.getString("question");
                    String QS = mathsJSONObject.getString("answer");
                    QzMath math = new QzMath();

                    math.A = A;
                    math.B = B;
                    math.C = C;
                    math.D = D;
                    math.question = WH;
                    math.answer = QS;
                    mathList.add(math);

                }
                Sports.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(HomeActivity.this, QuizActivity.class);
                        intent.putExtra("Subject","SPORT");
                        intent.putExtra("data", sportList);
                        startActivity(intent);
                    }
                });
                Mathss.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(HomeActivity.this, QuizActivity.class);
                        intent.putExtra("Subject","MATHSS");
                        intent.putExtra("data", mathList);
                        startActivity(intent);
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //getData.setText(s.toString());
        }
    }
}
