package com.example.eyetestinginterface;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.Locale;

public class ResultActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    ImageView retry_button, finish_button, speak_button;
    TextView score_text;

    String score_read = "";
    String score_speak = "";

    //TTS object
    private TextToSpeech myTTS;
    //status check code
    private final int MY_DATA_CHECK_CODE = 0;

    //create the Activity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        retry_button = findViewById(R.id.retry_button);
        finish_button = findViewById(R.id.finish_button);
        speak_button = findViewById(R.id.speak_button);

        score_text = findViewById(R.id.score);

        Intent checkTTSIntent = new Intent();
        checkTTSIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkTTSIntent, MY_DATA_CHECK_CODE);

        try {

            int test_score = TestEvaluate.test_score;
            int distance = Integer.parseInt(TestFragment.distance[0]);

            score_read = TestFragment.distance[0] + " / " + TestEvaluate.test_score;
            score_speak = TestFragment.distance[0] + " by " + TestEvaluate.test_score;

            if((float)test_score/distance > 0.25 && (float)test_score/distance < 0.75){
                score_speak += "Condition is normal";
            }else{
                score_speak += "Recommended to visit an optician";
                score_text.setTextColor(Color.parseColor("#ff7161"));
            }

            score_text.setText(score_read);

        } catch (Exception e) {
            e.printStackTrace();
        }

        Toast.makeText(this, Arrays.toString(TestFragment.distance), Toast.LENGTH_SHORT).show();

        retry_button.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), Home.class);
            intent.putExtra("toOpen", "Test");
            startActivity(intent);
        });

        finish_button.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), Home.class);
            intent.putExtra("toOpen", "Maps");
            startActivity(intent);
        });

        speak_button.setOnClickListener(v -> speakWords("Your score is " + score_speak));
    }

    //speak the user text
    private void speakWords(String speech) {

        //speak straight away
        myTTS.speak(speech, TextToSpeech.QUEUE_FLUSH, null);
    }

    //act on result of TTS data check
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MY_DATA_CHECK_CODE) {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                //the user has the necessary data - create the TTS
                myTTS = new TextToSpeech(this, this);
            } else {
                //no data - install it now
                Intent installTTSIntent = new Intent();
                installTTSIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(installTTSIntent);
            }
        }
    }

    //setup TTS
    public void onInit(int initStatus) {

        //check for successful instantiation
        if (initStatus == TextToSpeech.SUCCESS) {
            if (myTTS.isLanguageAvailable(Locale.US) == TextToSpeech.LANG_AVAILABLE)
                myTTS.setLanguage(Locale.US);
        } else if (initStatus == TextToSpeech.ERROR) {
            Toast.makeText(this, "Sorry! Text To Speech failed...", Toast.LENGTH_LONG).show();
        }
    }

}