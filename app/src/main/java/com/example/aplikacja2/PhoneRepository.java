package com.example.aplikacja2;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class PhoneRepository {

    static PhoneDao phoneDao;

    PhoneRepository(Application application)
    {
        PhoneDatabase db = PhoneDatabase.getDatabase(application);
        phoneDao = db.phoneDao();
    }

    LiveData<List<Phone>> getPhone()
    {
        return phoneDao.getAllPhones();
    }

    void insert(Phone phone)
    {
        new insertAsyncTask(phoneDao).execute(phone);
    }

    public void deleteAll() {
        //phoneDao.deleteAll();
        new DeleteAllAsyncTask(phoneDao).execute();
    }

    public LiveData<Phone> getPhoneById(int phoneId) {
        return phoneDao.getPhoneById(phoneId);
    }

    public static void delete(Phone phone) {
        new DeletePhoneAsyncTask(phoneDao).execute(phone);
    }

    private static class DeletePhoneAsyncTask extends AsyncTask<Phone, Void, Void> {
        private PhoneDao phoneDao;

        public DeletePhoneAsyncTask(PhoneDao phoneDao) {
            this.phoneDao = phoneDao;
        }

        @Override
        protected Void doInBackground(Phone... phones) {
            phoneDao.delete(phones[0]);
            return null;
        }
    }




    public static class insertAsyncTask extends AsyncTask<Phone, Void, Void>
    {
        private PhoneDao taskDao;

        insertAsyncTask(PhoneDao phoneDao)
        {
            taskDao = phoneDao;
        }

        @Override
        protected Void doInBackground(Phone... phones) {
            taskDao.insertAll(phones[0]);
            return null;
        }
    }

    private static class DeleteAllAsyncTask extends AsyncTask<Void, Void, Void> {
        private PhoneDao asyncTaskDao;

        DeleteAllAsyncTask(PhoneDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            asyncTaskDao.deleteAll();
            return null;
        }
    }

    public void update(Object id, Phone phone) {
        new UpdatePhoneAsyncTask(phoneDao).execute(phone);
    }

    private static class UpdatePhoneAsyncTask extends AsyncTask<Phone, Void, Void> {
        private PhoneDao phoneDao;

        private UpdatePhoneAsyncTask(PhoneDao phoneDao) {
            this.phoneDao = phoneDao;
        }

        @Override
        protected Void doInBackground(Phone... phones) {
            phoneDao.update(phones[0]);
            return null;
        }
    }



}
