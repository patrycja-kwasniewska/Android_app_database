package com.example.aplikacja2;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class PhoneViewModel extends AndroidViewModel {

    PhoneRepository phoneRepository;
    private PhoneDao phoneDao;
    LiveData<List<Phone>> phoneList;

    public PhoneViewModel(Application application)
    {
        super(application);
        phoneRepository = new PhoneRepository(application);
        phoneList = phoneRepository.getPhone();
    }

    LiveData<List<Phone>> getAllPhones()
    {
        return phoneList;
    }

    public LiveData<Phone> getPhoneById(int phoneId) {
        return phoneRepository.getPhoneById(phoneId);
    }

    public void delete(Phone phone) {
        PhoneRepository.delete(phone);
    }


    public void insertPhone(Phone phone)
    {
        phoneRepository.insert(phone);
    }

    public void update(Object id, Phone phone)
    {
        phoneRepository.update(id, phone);
    }

    public void deleteAll() {
        phoneDao.deleteAll();
    }

    public void deleteAllPhones() {
        phoneRepository.deleteAll();
    }

}
