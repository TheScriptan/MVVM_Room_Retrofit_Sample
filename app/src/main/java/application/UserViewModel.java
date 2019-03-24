package application;

import android.util.Log;

import java.util.List;

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

    public void init(String username){
        if(this.userData != null){
            return;
        }
        userData = repository.getUserData(username);
    }

    public void setNewUser(String username){
        userData = repository.getUserData(username);
    }

    public LiveData<User> getUserData(){
        return this.userData;
    }

    public LiveData<List<User>> getUsers() {
        return repository.getUsers();
    }

}
