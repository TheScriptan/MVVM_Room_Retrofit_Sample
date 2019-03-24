package business.repository;

import android.os.AsyncTask;

import java.util.List;

import androidx.lifecycle.LiveData;
import business.database.dao.UserDao;
import business.model.User;
import business.network.NetworkAdapter;

public class UserRepository {

    private static UserRepository sInstance;
    private final UserDao userDao;
    private final NetworkAdapter networkAdapter;
    private LiveData<User> userNetworkData;
    private LiveData<List<User>> userLocalData;


    public UserRepository(UserDao userDao, NetworkAdapter networkAdapter){
        this.userDao = userDao;
        this.networkAdapter = networkAdapter;

        userNetworkData = networkAdapter.getUserData();
        userLocalData = userDao.getUsers();
        userNetworkData.observeForever(user -> {
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
        userNetworkData = networkAdapter.fetchUserData(username);
        return userNetworkData;
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

    private static class getUserByNameAsync extends AsyncTask<String, Void, Void>{
        private UserDao dao;

        public getUserByNameAsync(UserDao dao){
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(String... strings) {
            dao.getUserByName(strings[0]);
            return null;
        }
    }
}
