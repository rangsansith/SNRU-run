package appewtc.masterung.snrurun;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Random;

public class Exercise extends AppCompatActivity {

    //Explicit
    private TextView textView;
    private RadioGroup radioGroup;
    private RadioButton choice1RadioButton, choice2RadioButton,
            choice3RadioButton, choice4RadioButton;
    private String[] questionStrings, choice1Strings,
            choice2Strings, choice3Strings, choice4Strings, answerStrings;
    private int timesAnInt = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        bindWidget();

        SynQuestion synQuestion = new SynQuestion();
        synQuestion.execute();

    }   // Main Method

    public void clickAnswer(View view) {

    }

    public class SynQuestion extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {

            try {

                OkHttpClient okHttpClient = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                Request request = builder.url("http://swiftcodingthai.com/snru/get_question.php").build();
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();

            } catch (Exception e) {
                return null;
            }

            // return null;
        }   // doInBack

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {

                JSONArray jsonArray = new JSONArray(s);

                questionStrings = new String[jsonArray.length()];
                choice1Strings = new String[jsonArray.length()];
                choice2Strings = new String[jsonArray.length()];
                choice3Strings = new String[jsonArray.length()];
                choice4Strings = new String[jsonArray.length()];
                answerStrings = new String[jsonArray.length()];

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    questionStrings[i] = jsonObject.getString("Question");
                    choice1Strings[i] = jsonObject.getString("Choice1");
                    choice2Strings[i] = jsonObject.getString("Choice2");
                    choice3Strings[i] = jsonObject.getString("Choice3");
                    choice4Strings[i] = jsonObject.getString("Choice4");
                    answerStrings[i] = jsonObject.getString("Answer");

                }   // for

                Random random = new Random();

                changeView(random.nextInt(jsonArray.length()));


            } catch (Exception e) {
                e.printStackTrace();
            }

        }   // onPost

    }   // SynQuestion Class

    private void changeView(int index) {

        textView.setText(Integer.toString(timesAnInt) + questionStrings[index]);
        choice1RadioButton.setText(choice1Strings[index]);
        choice2RadioButton.setText(choice2Strings[index]);
        choice3RadioButton.setText(choice3Strings[index]);
        choice4RadioButton.setText(choice4Strings[index]);



    }   // changeView


    private void bindWidget() {

        textView = (TextView) findViewById(R.id.textView6);
        radioGroup = (RadioGroup) findViewById(R.id.ragChoice);
        choice1RadioButton = (RadioButton) findViewById(R.id.radioButton6);
        choice2RadioButton = (RadioButton) findViewById(R.id.radioButton7);
        choice3RadioButton = (RadioButton) findViewById(R.id.radioButton8);
        choice4RadioButton = (RadioButton) findViewById(R.id.radioButton9);

    }

}   // Main Class
