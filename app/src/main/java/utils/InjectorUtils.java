package utils;

import android.content.Context;

import application.UserViewModelFactory;
import business.database.AppDatabase;
import business.database.dao.UserDao;
import business.network.NetworkAdapter;
import business.repository.UserRepository;

public class InjectorUtils {

    public static UserRepository provideRepository(Context context){
        AppDatabase db = AppDatabase.getInstance(context);
        NetworkAdapter networkAdapter = NetworkAdapter.getInstance();
        return UserRepository.getInstance(db.userDao(), networkAdapter);
    }

    public static NetworkAdapter provideNetworkAdapter(UserDao userDao){
        return NetworkAdapter.getInstance();
    }

    public static UserViewModelFactory provideUserViewModelFactory(Context context){
        UserRepository repository = provideRepository(context);
        return new UserViewModelFactory(repository);
    }
}
