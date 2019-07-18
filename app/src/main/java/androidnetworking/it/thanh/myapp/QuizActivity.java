package androidnetworking.it.thanh.myapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends AppCompatActivity {
    private TextView title;
    private TextView wh;
    private RadioButton A;
    private RadioButton B;
    private RadioButton C;
    private RadioButton D;
    private Button btnNext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        title = (TextView) findViewById(R.id.title);
        wh = (TextView) findViewById(R.id.wh);
        A = (RadioButton) findViewById(R.id.A);
        B = (RadioButton) findViewById(R.id.B);
        C = (RadioButton) findViewById(R.id.C);
        D = (RadioButton) findViewById(R.id.D);
        btnNext = (Button) findViewById(R.id.btn_next);
        final int[] count = {0};
        final Intent intent = getIntent();
        String success = intent.getStringExtra("Subject");

        title.setText(success);
        if(success.equalsIgnoreCase("sport")){
            final ArrayList<QzSport> nameValuePairs = (ArrayList<QzSport>) intent.getSerializableExtra("data");
            A.setText(nameValuePairs.get(count[0]).A);
            B.setText(nameValuePairs.get(count[0]).B);
            C.setText(nameValuePairs.get(count[0]).C);
            D.setText(nameValuePairs.get(count[0]).D);
            wh.setText(nameValuePairs.get(count[0]).question);
            btnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(count[0] <nameValuePairs.size()){
                        count[0] = count[0] +1;
                    A.setText(nameValuePairs.get(count[0]).A);
                    B.setText(nameValuePairs.get(count[0]).B);
                    C.setText(nameValuePairs.get(count[0]).C);
                    D.setText(nameValuePairs.get(count[0]).D);
                    wh.setText(nameValuePairs.get(count[0]).question);
                    }else{
                        Intent intent1 = new Intent(QuizActivity.this, KQActivity.class);
                        intent1.putExtra("kq", count[0]);
                        startActivity(intent1);}
                }
            });
        }else{
            final ArrayList<QzMath> QzMaths = (ArrayList<QzMath>) intent.getSerializableExtra("data");
            A.setText(QzMaths.get(count[0]).A);
            B.setText(QzMaths.get(count[0]).B);
            C.setText(QzMaths.get(count[0]).C);
            D.setText(QzMaths.get(count[0]).D);
            wh.setText(QzMaths.get(count[0]).question);
            btnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(count[0] <QzMaths.size()){
                        count[0] = count[0] +1;
                    A.setText(QzMaths.get(count[0]).A);
                    B.setText(QzMaths.get(count[0]).B);
                    C.setText(QzMaths.get(count[0]).C);
                    D.setText(QzMaths.get(count[0]).D);
                    wh.setText(QzMaths.get(count[0]).question);
                    }else{
                        Intent intent1 = new Intent(QuizActivity.this, KQActivity.class);
                        intent1.putExtra("kq", count[0]);
                        startActivity(intent1);}
                }
            });
        }



    }
}
