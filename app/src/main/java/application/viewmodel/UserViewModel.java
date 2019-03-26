package application.viewmodel;

import android.util.Log;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import business.model.Repo;
import business.model.User;
import business.repository.UserRepository;

public class UserViewModel extends ViewModel {

    private LiveData<User> userData;
    private LiveData<Repo> repoData;
    private final UserRepository repository;

    public UserViewModel(UserRepository repository){
        this.repository = repository;
        userData = repository.getUserData("");
        repoData = repository.getRepoData("", "");
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

    public void setCurrentRepo(String username, String repoName) {repoData = repository.getRepoData(username, repoName);}

    public LiveData<Repo> getCurrentRepoData() {return this.repoData;}

    public LiveData<List<Repo>> getRepos() {return repository.getRepos();}
}
