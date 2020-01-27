package com.example.gill.riko;

import android.speech.tts.TextToSpeech;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Locale;
import com.apptakk.http_request.HttpRequest;
import com.apptakk.http_request.HttpRequestTask;
import com.apptakk.http_request.HttpResponse;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private TextView txvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txvResult = (TextView) findViewById(R.id.txvResult);
    }

    public void getSpeechInput(View view) {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 10);
        } else {
            Toast.makeText(this, "Your Device Don't Support Speech Input", Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 10:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    txvResult.setText(result.get(0));


                    new HttpRequestTask(
                            new HttpRequest("https://riko-266107.appspot.com/transcripts", HttpRequest.POST, "{ \"Text\": \"".concat(result.get(0)).concat("\"}")),
                            new HttpRequest.Handler() {
                                @Override
                                public void response(HttpResponse response) {
                                    if (response.code == 200) {
                                        Log.d(this.getClass().toString(), "Request successful!");
                                    } else {
                                        Log.e(this.getClass().toString(), "Request unsuccessful: " + response);
                                    }
                                }
                            }).execute();
               }
                break;
        }
    }
}


//                    textToSpeech.speak(result.get(0), TextToSpeech.QUEUE_FLUSH, null, null);
//
//    TextToSpeech textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
//        @Override
//        public void onInit(int status) {
//            if (status == TextToSpeech.SUCCESS) {
//                int result = textToSpeech.setLanguage(Locale.ENGLISH);
//                if (result == TextToSpeech.LANG_MISSING_DATA ||
//                        result == TextToSpeech.LANG_NOT_SUPPORTED) {
//                    Toast.makeText(getApplicationContext(), "This language is not supported!",
//                            Toast.LENGTH_SHORT).show();
//                } else {
//                    textToSpeech.setPitch(0.6f);
//                    textToSpeech.setSpeechRate(1.0f);
//                }
//            }
//        }
//    });


