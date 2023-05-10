package com.example.aplikacja2;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PhoneDao {

    @Insert
    void insertAll(Phone... phones);

    @Query("SELECT * FROM phone")  //MUSI BYC TUTAJ Z MALEJ LITERY
    LiveData<List<Phone>> getAllPhones();

    @Query("DELETE FROM phone")
    void deleteAll();

    @Update
    void update(Phone phone);

    @Query("SELECT * FROM phone WHERE id = :phoneId")
    LiveData<Phone> getPhoneById(int phoneId);

    @Delete
    void delete(Phone phone);


}
