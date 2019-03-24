package business.repository;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import business.database.AppDatabase;
import business.database.dao.UserDao;
import business.model.User;
import business.network.NetworkAdapter;

public class UserRepository {

    private static UserRepository sInstance;
    private final UserDao userDao;
    private final NetworkAdapter networkAdapter;
    private LiveData<User> userData;


    public UserRepository(UserDao userDao, NetworkAdapter networkAdapter){
        this.userDao = userDao;
        this.networkAdapter = networkAdapter;

        userData = networkAdapter.getUserData();
        userData.observeForever(user -> {
            new insertUser(userDao).execute(user);
        });
    }

    public static UserRepository getInstance(UserDao userDao, NetworkAdapter networkAdapter){
        if(sInstance == null){
            synchronized (UserRepository.class){
                if(sInstance == null){
                    sInstance = new UserRepository(userDao, networkAdapter);
                }
            }
        }
        return sInstance;
    }

    public LiveData<User> getUserData(String username){
        userData = networkAdapter.fetchUserData(username);
        return userData;
    }

    private static class insertUser extends AsyncTask<User, Void, Void>{

        private UserDao dao;

        public insertUser(UserDao dao){
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(User... users) {
            dao.addUser(users[0]);
            return null;
        }
    }
}
