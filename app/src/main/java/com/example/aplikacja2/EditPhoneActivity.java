package com.example.aplikacja2;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import java.util.Objects;

public class EditPhoneActivity extends AppCompatActivity {

    public static final String EXTRA_ID = "o k";
    private EditText etManufacturer;
    private EditText etModel;
    private EditText etAndroidVersion;
    private EditText etWebsite;
    private Button btnUpdate;
    private Button btnCancel;
    private Button btnWebsite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editphone);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Phone selectedPhone = intent.getParcelableExtra("phone");

        etManufacturer = findViewById(R.id.editTextManufacturer);
        etModel = findViewById(R.id.editTextModel);
        etAndroidVersion = findViewById(R.id.editTextAndroidVersion);
        etWebsite = findViewById(R.id.editTextWebsite);
        btnUpdate = findViewById(R.id.buttonUpdate);
        btnCancel = findViewById(R.id.buttonCancel);
        btnWebsite = findViewById(R.id.buttonWebsite);

        // Set the text fields to the data of the selected phone
        etManufacturer.setText(selectedPhone.getManufacturer());
        etModel.setText(selectedPhone.getModel());
        etAndroidVersion.setText(selectedPhone.getVersion());
        etWebsite.setText(selectedPhone.getWebsite());

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the entered data
                String manufacturer = etManufacturer.getText().toString();
                String model = etModel.getText().toString();
                String androidVersion = etAndroidVersion.getText().toString();
                String website = etWebsite.getText().toString();

                // Check if all fields are filled
                if (!manufacturer.isEmpty() && !model.isEmpty() && !androidVersion.isEmpty()) {
                    // Create a new Phone object with the entered data
                    Phone phone = new Phone(manufacturer, model, androidVersion, website);
                    phone.setId(selectedPhone.getId());

                    // Update the selected phone in the database
                    PhoneRepository phoneRepository = new PhoneRepository(getApplication());
                    phoneRepository.update(selectedPhone.getId(), phone);

                    // Pass the updated phone data as the result of the activity
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("phone", phone);
                    setResult(RESULT_OK, resultIntent);
                    finish(); // Finish the activity

                } else {
                    Toast.makeText(EditPhoneActivity.this, "All fields must be filled!", Toast.LENGTH_SHORT).show();
                }
            }
        });



        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the activity without saving any data
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });

        btnWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the website in a web browser
                String websiteUrl = etWebsite.getText().toString();
                if (!websiteUrl.isEmpty()) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(websiteUrl));
                    startActivity(intent);
                } else {
                    Toast.makeText(EditPhoneActivity.this, "No website address", Toast.LENGTH_SHORT).show();
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
