package com.example.aplikacja2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private PhoneViewModel phoneViewModel; // Deklaracja obiektu ViewModel
    private RecyclerView recyclerView;
    private List<Phone> mPhoneList;
    private PhoneListAdapter adapter;
    private Button btnAdd;
    private static final int NEW_PHONE_REQUEST_CODE = 1001;
    private static final int EDIT_PHONE_REQUEST_CODE = 1000;
    ItemTouchHelper itemTouchHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicjalizacja obiektu ViewModel
        phoneViewModel = ViewModelProviders.of(this).get(PhoneViewModel.class);
        btnAdd = findViewById(R.id.button_add);


        //PhoneViewModel viewModel = ViewModelProviders.of(this).get(PhoneViewModel.class);

//        PhoneDatabase db = Room.databaseBuilder(getApplicationContext(),
//                PhoneDatabase.class, "phoneDatabase").build();

        Phone phone1 = new Phone("iPhone", "6S", "2.4", "www.iphone.com");
        Phone phone2 = new Phone("Xiaomi", "Redmi Note", "9 PRO", "www.xiaomi.com");
        Phone phone3 = new Phone("Pixel", "4", "API 31", "www.pixel.com");

        //db.phoneDao().insertAll(phone3, phone4);

        //List<Phone> phoneList = db.phoneDao().getAllPhones();

//        Button button = findViewById(R.id.addPhone);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                viewModel.insertPhone(phone3);
//            }
//        });
//
//        viewModel.getAllPhones().observe(this, phoneList -> {
//
//            Log.d("phones", ": " + phoneList.size());
//            for(Phone list: phoneList)
//            {
//                Log.d("phones", list.producer + " " + list.model + " " + list.version);
//            }
//        });


        ////////////////////////////////////////////////////////////


        ListView listView = findViewById(R.id.phoneList);

        adapter = new PhoneListAdapter(this, new ArrayList<>());
        listView.setAdapter(adapter);

        PhoneViewModel viewModel = ViewModelProviders.of(this).get(PhoneViewModel.class);
        viewModel.getAllPhones().observe(this, phoneList -> {
            adapter.clear(); // Czyści listę adaptera
            //adapter.add(phone1); // Dodaje obiekt phone1 do listy adaptera
            //adapter.add(phone2); // Dodaje obiekt phone2 do listy adaptera
            //adapter.add(phone3);
            adapter.addAll(phoneList); // Dodaje obiekty telefonów z listy pobranej z bazy danych do listy adaptera
            adapter.notifyDataSetChanged(); // Aktualizuje widok
        });



        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewPhoneActivity.class);
                startActivity(intent);
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Phone selectedPhone = (Phone) parent.getItemAtPosition(position);

                Intent intent = new Intent(MainActivity.this, EditPhoneActivity.class);
                intent.putExtra("phone", (Parcelable) selectedPhone);
                startActivityForResult(intent, EDIT_PHONE_REQUEST_CODE);
            }
        });


        ///////  DODAWANIE NOWEGO TELEFONU

//        Button button = findViewById(R.id.addPhone);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                viewModel.insertPhone(phone3);
//            }
//        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_PHONE_REQUEST_CODE && resultCode == RESULT_OK) {

            // Jeśli wynik z aktywności NewPhoneActivity jest OK, odczytaj dane z intentu
            String manufacturer = data.getStringExtra("manufacturer");
            String model = data.getStringExtra("model");
            String androidVersion = data.getStringExtra("androidVersion");
            String website = data.getStringExtra("website");


            // Stworzenie obiektu Phone z odczytanymi danymi
            Phone phone = new Phone(manufacturer, model, androidVersion, website);

            // Dodanie obiektu Phone do bazy danych
            PhoneRepository phoneRepository = new PhoneRepository(getApplication());
            phoneViewModel.insertPhone(phone);
            phoneRepository.insert(phone);

            // Aktualizacja listy telefonów w adapterze

            phoneViewModel.getAllPhones().observe(this, phoneList -> {
                adapter.clear(); // Czyści listę adaptera
                adapter.add(phone);
                phoneRepository.insert(phone);
                phoneViewModel.insertPhone(phone);
                adapter.addAll(phoneList);
                adapter.notifyDataSetChanged(); // Aktualizuje widok
            });

        }

        if (requestCode == EDIT_PHONE_REQUEST_CODE && resultCode == RESULT_OK) {
            int phoneId = data.getIntExtra("phoneId", -1); // -1 oznacza błąd, gdyby nie udało się odczytać id
            if (phoneId != -1) {
                // Odczytaj wprowadzone dane
                String manufacturer = data.getStringExtra("manufacturer");
                String model = data.getStringExtra("model");
                String androidVersion = data.getStringExtra("androidVersion");
                String website = data.getStringExtra("website");

                // Stwórz obiekt Phone z odczytanymi danymi i przypisz mu id edytowanego telefonu
                Phone phone = new Phone(manufacturer, model, androidVersion, website);
                phone.setId(phoneId);

                // Zaktualizuj wpis edytowanego telefonu w bazie danych
                PhoneRepository phoneRepository = new PhoneRepository(getApplication());
                phoneViewModel.update(phoneId, phone);
                phoneRepository.update(phoneId, phone);

                // Aktualizuj listę telefonów w adapterze

                phoneViewModel.getAllPhones().observe(this, phoneList -> {
                    adapter.clear(); // Czyści listę adaptera
                    adapter.add(phone);
                    phoneRepository.insert(phone);
                    phoneViewModel.insertPhone(phone);
                    adapter.addAll(phoneList);
                    adapter.notifyDataSetChanged(); // Aktualizuje widok
                });

            }
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_more_options) {
            // Zmiana ikony na przycisk z napisem "Usuń"
            item.setIcon(null);
            item.setTitle("Usuń");

            item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    PhoneRepository phoneRepository = new PhoneRepository(getApplication());
                    //phoneViewModel.deleteAll();
                    adapter.clear();
                    phoneRepository.deleteAll();
                    return true;
                }
            });
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}


