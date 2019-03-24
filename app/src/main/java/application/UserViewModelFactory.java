package application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import business.repository.UserRepository;

public class UserViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final UserRepository repository;

    public UserViewModelFactory(UserRepository repository){
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        //noinspection unchecked
        return (T) new UserViewModel(repository);
    }
}
