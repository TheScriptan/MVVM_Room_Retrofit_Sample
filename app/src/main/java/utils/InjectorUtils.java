package utils;

import android.content.Context;

import application.UserViewModelFactory;
import business.database.AppDatabase;
import business.database.dao.UserDao;
import business.network.NetworkAdapter;
import business.repository.UserRepository;

public class InjectorUtils {

    public static UserRepository provideRepository(Context context){
        AppExecutors executors = AppExecutors.getInstance();
        AppDatabase db = AppDatabase.getInstance(context);
        NetworkAdapter networkAdapter = NetworkAdapter.getInstance(executors);
        return UserRepository.getInstance(db.userDao(), networkAdapter, executors);
    }

    public static NetworkAdapter provideNetworkAdapter(){
        AppExecutors executors = AppExecutors.getInstance();
        return NetworkAdapter.getInstance(executors);
    }

    public static UserViewModelFactory provideUserViewModelFactory(Context context){
        UserRepository repository = provideRepository(context);
        return new UserViewModelFactory(repository);
    }
}
