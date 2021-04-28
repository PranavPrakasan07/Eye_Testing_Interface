package com.example.eyetestinginterface;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Locale;

public class TestEvaluate extends AppCompatActivity {

    private final int RecordAudioRequestCode = 1;
    TextView question_text;
    EditText speech_to_text;
    ImageView mic_button, next_button;
    boolean mic_on = false;

    String question = "";

    int question_number = 0;
    int incorrect = 0;

    String[] question_array = new String[]{"E", "F P", "T O Z", "L P E D", "P E C F D", "E D F C Z P", "F E L O P Z D", "D E F P O T E C", "L E F O D P C T"};
    int[] font_size_array = new int[]{152, 130, 108, 87, 65, 43, 33, 21, 15, 9};
    int[] distance_array = new int[]{70, 60, 50, 40, 30, 20, 15, 10, 7, 4};

    static int test_score = 70;


    private SpeechRecognizer speechRecognizer;
//    @SuppressLint("ClickableViewAccessibility")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_evaluate);

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            checkPermission();
        }

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String distance = bundle.getString("distance");

        Toast.makeText(this, distance, Toast.LENGTH_SHORT).show();

        question_text = findViewById(R.id.text);
        speech_to_text = findViewById(R.id.speech_to_text);
        mic_button = findViewById(R.id.mic_button);
        next_button = findViewById(R.id.next_button);

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        final Intent speechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        speechRecognizer.setRecognitionListener(new RecognitionListener() {

            @Override
            public void onReadyForSpeech(Bundle bundle) {
                speech_to_text.setText("");
                speech_to_text.setHint("Listening...");
            }

            @Override
            public void onBeginningOfSpeech() {
                speech_to_text.setText("");
                speech_to_text.setHint("Listening...");
            }

            @Override
            public void onRmsChanged(float v) {

            }

            @Override
            public void onBufferReceived(byte[] bytes) {

            }

            @Override
            public void onEndOfSpeech() {
                mic_button.setImageResource(R.drawable.ic_baseline_mic_off_24);

            }

            @Override
            public void onError(int i) {
                mic_button.setImageResource(R.drawable.ic_baseline_mic_off_24);
            }

            @Override
            public void onResults(Bundle bundle) {
                mic_button.setImageResource(R.drawable.ic_baseline_mic_off_24);
                ArrayList<String> data = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

                String response = data.get(0).toUpperCase();

                String response_letter_removed = response.replaceAll("LETTER ", "");

                speech_to_text.setText(response_letter_removed);

                check_response(response_letter_removed);
            }

            @Override
            public void onPartialResults(Bundle bundle) {

            }

            @Override
            public void onEvent(int i, Bundle bundle) {

            }
        });

        mic_button.setOnClickListener(v -> {
            if (!mic_on) {
                mic_button.setImageResource(R.drawable.ic_baseline_mic_none_24);
                speechRecognizer.startListening(speechRecognizerIntent);
            } else {
                speechRecognizer.stopListening();
                mic_button.setImageResource(R.drawable.ic_baseline_mic_none_24);
            }
        });

        next_button.setOnClickListener(v -> {

            if (speech_to_text.getText().toString().equals("")) {
                Toast.makeText(TestEvaluate.this, "Please give your response!", Toast.LENGTH_SHORT).show();
            } else {
                check_response(speech_to_text.getText().toString().toUpperCase());
            }
        });
    }

    private void check_response(String response) {

        question = question_array[question_number];

        if (response.equals("NEXT")) {
            question_number++;
            next_question();
        }

        if (response.equals(question)) {
            question_text.setTextColor(getColor(R.color.correct));

            new Handler().postDelayed(() -> {
                incorrect = 0;
                question_number++;
                next_question();
            }, 1000);

        } else {
            incorrect++;
            test_score = distance_array[question_number];
        }

        if (incorrect > 2) {
            question_text.setTextColor(getColor(R.color.error));
            Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
            startActivity(intent);
        }
    }

    private void next_question() {
        try {
            speech_to_text.setText("");
            question_text.setTextColor(getColor(R.color.text_color));
            question_text.setText(question_array[question_number]);
            question_text.setTextSize(TypedValue.COMPLEX_UNIT_SP, font_size_array[question_number]);
        } catch (Exception e) {
            speech_to_text.setText("");
            next_button.setVisibility(View.INVISIBLE);

            Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
            startActivity(intent);

            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        speechRecognizer.destroy();
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, RecordAudioRequestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == RecordAudioRequestCode && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
        }
    }
}