package application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import business.model.User;
import business.repository.UserRepository;

public class UserViewModel extends ViewModel {

    private LiveData<User> userData;
    private final UserRepository repository;

    public UserViewModel(UserRepository repository){
        this.repository = repository;
    }

    public LiveData<User> getUserData(String username){
        userData = repository.getUserData(username);
        return userData;
    }

}
