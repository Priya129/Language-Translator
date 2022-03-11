package com.example.language_translator;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.os.Build;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.nl.translate.TranslateLanguage;
import com.google.mlkit.nl.translate.Translation;
import com.google.mlkit.nl.translate.Translator;
import com.google.mlkit.nl.translate.TranslatorOptions;

import java.util.ArrayList;
import java.util.Locale;

public class starting extends AppCompatActivity  {
    Spinner spinner1, spinner2;
    int count = 0;
    SpeechRecognizer speechRecognizer;
    ImageView mic;
    TextView tvTranslatedLanguage;
    Button idiot;
    ImageView button;
    String flc, tlc = null;
    TextToSpeech TTS;
    EditText edittext;
    String OriginalText = "";
    Translator englishHindiTranslator;
    @SuppressLint("UseCompatLoadingForDrawables")
    @RequiresApi(api = Build.VERSION_CODES.S)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting);
        button = findViewById(R.id.button);
        tvTranslatedLanguage = findViewById(R.id.tvTranslatedLanguage);
        idiot = findViewById(R.id.idiot);
        edittext = findViewById(R.id.edittext);
        mic = findViewById(R.id.mic);
        spinner1 = findViewById(R.id.spinner2);
        spinner2 = findViewById(R.id.spinner);
        String [] lantrans ={"English","German","Hindi","Spanish","Chinese",
                "French","Japanese","Korean"};
        String [] lantransto ={"English","German","Hindi","Spanish","Chinese",
                "French","Japanese","Korean"};

        ArrayAdapter<CharSequence>  adapter = ArrayAdapter.createFromResource(this,R.array.Languages, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter);
        spinner1.setAdapter(adapter);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                flc = languageCode(lantrans[position]);
            }

            private String languageCode(String lantran) {
                String lc = null;
                switch (lantran) {
                    case "English":
                        lc = TranslateLanguage.ENGLISH;
                        break;
                    case "German":
                        lc = TranslateLanguage.GERMAN;
                        break;
                    case "Hindi":
                        lc = TranslateLanguage.HINDI;
                        break;
                    case "Spanish":
                        lc = TranslateLanguage.SPANISH;
                        break;
                    case "Chinese":
                        lc = TranslateLanguage.CHINESE;
                        break;
                    case "French":
                        lc = TranslateLanguage.FRENCH;
                        break;
                    case "Japanese":
                        lc = TranslateLanguage.JAPANESE;
                        break;
                    case "Korean":
                        lc = TranslateLanguage.KOREAN;
                        break;

                    default:
                }
                return lc;

            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter<String> fromAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, lantrans);
        fromAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(fromAdapter);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tlc = languageCode(lantransto[position]);
            }

            private String languageCode(String s) {
                String lc = null;
                switch (s) {
                    case "English":
                        lc = TranslateLanguage.ENGLISH;
                        break;
                    case "German":
                        lc = TranslateLanguage.GERMAN;
                        break;
                    case "Hindi":
                        lc = TranslateLanguage.HINDI;
                        break;
                    case "Spanish":
                        lc = TranslateLanguage.SPANISH;
                        break;
                    case "Chinese":
                        lc = TranslateLanguage.CHINESE;
                        break;
                    case "French":
                        lc = TranslateLanguage.FRENCH;
                        break;
                    case "Japanese":
                        lc = TranslateLanguage.JAPANESE;
                        break;
                    case "Korean":
                        lc = TranslateLanguage.KOREAN;
                        break;
                    default:
                }
                return lc;

            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter<String> toAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, lantransto);
        toAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(toAdapter);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, 1);
        }
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        final Intent speechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        mic.setOnClickListener(v -> {
            if (count == 0) {
                mic.setImageDrawable(getDrawable(R.drawable.ic_baseline_mic_24));
                speechRecognizer.startListening(speechRecognizerIntent);
                count = 1;
            } else {
                mic.setImageDrawable(getDrawable(R.drawable.ic_baseline_mic_off_24));
                speechRecognizer.stopListening();
                count = 0;
            }
        });
        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {

            }

            @Override
            public void onBeginningOfSpeech() {
                edittext.setText("");
                edittext.setHint("Listening...");

            }

            @Override
            public void onRmsChanged(float rmsdB) {

            }

            @Override
            public void onBufferReceived(byte[] buffer) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int error) {

            }

            @Override
            public void onResults(Bundle results) {
                ArrayList<String> data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                edittext.setText(data.get(0));

            }

            @Override
            public void onPartialResults(Bundle partialResults) {

            }

            @Override
            public void onEvent(int eventType, Bundle params) {

            }

        });
        TTS = new TextToSpeech(this, status -> {

            if (status == TextToSpeech.SUCCESS) {

                int result = TTS.setLanguage(new Locale("tlc"));
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.e("TTS", "Language is not Supported");
                } else {
                    button.setEnabled(true);
                }
            } else {
                Log.e("TTS", "Initialization failed");
            }


        });

        idiot.setOnClickListener(v -> {
            OriginalText = edittext.getText().toString();
            prepareModel();

        });
        button.setOnClickListener(v -> speak());
    }


    @SuppressLint("SetTextI18n")
    private void prepareModel() {
        TranslatorOptions.Builder builder = new TranslatorOptions.Builder();

        TranslatorOptions options = builder
                .setSourceLanguage(flc)
                .setTargetLanguage(tlc)
                .build();
        englishHindiTranslator = Translation.getClient(options);
        englishHindiTranslator.downloadModelIfNeeded().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                translateLanguage();

            }

            @SuppressLint("SetTextI18n")
            private void translateLanguage() {
                englishHindiTranslator.translate(OriginalText).addOnSuccessListener(s -> {
                    tvTranslatedLanguage.setText(s);

                }).addOnFailureListener(e -> {
                    tvTranslatedLanguage.setText("Error :" + e.getMessage());

                });
            }
        }).addOnFailureListener(e -> {
            tvTranslatedLanguage.setText("Error :" + e.getMessage());

        });
    }

    private void speak() {
        String text = tvTranslatedLanguage.getText().toString();
        TTS.speak(text, TextToSpeech.QUEUE_FLUSH, null, String.valueOf(starting.this));
    }

    @Override
    protected void onDestroy() {
        TTS.stop();
        TTS.shutdown();
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }



}

