package com.example.aplikacja2;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class NewPhoneActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = " ok ";
    private EditText etManufacturer;
    private EditText etModel;
    private EditText etAndroidVersion;
    private EditText etWebsite;
    private Button btnSave;
    private Button btnCancel;
    private Button btnWebsite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newphone);

        etManufacturer = findViewById(R.id.editTextManufacturer);
        etModel = findViewById(R.id.editTextModel);
        etAndroidVersion = findViewById(R.id.editTextAndroidVersion);
        etWebsite = findViewById(R.id.editTextWebsite);
        btnSave = findViewById(R.id.buttonSave);
        btnCancel = findViewById(R.id.buttonCancel);
        btnWebsite = findViewById(R.id.buttonWebsite);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pobierz wprowadzone dane
                String manufacturer = etManufacturer.getText().toString();
                String model = etModel.getText().toString();
                String androidVersion = etAndroidVersion.getText().toString();
                String website = etWebsite.getText().toString();

                // Sprawdź czy wszystkie pola są wypełnione
                if (!manufacturer.isEmpty() && !model.isEmpty() && !androidVersion.isEmpty()) {
                    // Stworzenie obiektu Phone z wprowadzonymi danymi
                    Phone phone = new Phone(manufacturer, model, androidVersion, website);

                    // Dodanie obiektu Phone do bazy danych
                    PhoneRepository phoneRepository = new PhoneRepository(getApplication());
                    phoneRepository.insert(phone);

                    // Przekazanie danych nowego telefonu jako wynik aktywności
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("manufacturer", manufacturer);
                    resultIntent.putExtra("model", model);
                    resultIntent.putExtra("androidVersion", androidVersion);
                    resultIntent.putExtra("website", website);
                    setResult(RESULT_OK, resultIntent);
                    finish(); // Zakończ aktywność
                } else {
                    Toast.makeText(NewPhoneActivity.this, "Wszystkie pola muszą być wypełnione!", Toast.LENGTH_SHORT).show();
                }
            }
        });


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Zakończenie aktywności bez zapisywania danych
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });

        btnWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Otwarcie strony www w przeglądark
                String websiteUrl = etWebsite.getText().toString();
                if (!websiteUrl.isEmpty()) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(websiteUrl));
                    startActivity(intent);
                } else {
                    Toast.makeText(NewPhoneActivity.this, "Brak adresu strony www", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //sprawdzenie czy pola nie sa puste - walidacja

        etManufacturer.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String manufacturer = etManufacturer.getText().toString();
                    if (manufacturer.isEmpty()) {
                        etManufacturer.setError("To pole nie może być puste!");
                    }
                }
            }
        });

        etAndroidVersion.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String androidVersion = etAndroidVersion.getText().toString();
                    if (androidVersion.isEmpty()) {
                        etAndroidVersion.setError("To pole nie może być puste!");
                    }
                }
            }
        });

        etModel.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String model = etModel.getText().toString();
                    if (model.isEmpty()) {
                        etModel.setError("To pole nie może być puste!");
                    }
                }
            }
        });

        etWebsite.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String website = etWebsite.getText().toString();
                    if (website.isEmpty()) {
                        etWebsite.setError("To pole nie może być puste!");
                    }
                }
            }
        });

    }

}
