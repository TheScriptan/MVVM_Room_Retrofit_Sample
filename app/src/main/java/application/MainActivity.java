package application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import business.model.User;
import utils.InjectorUtils;

import android.os.Bundle;
import android.widget.TextView;

import com.example.mvvm_room_retrofit_sample.R;

public class MainActivity extends AppCompatActivity {

    private UserViewModelFactory userViewModelFactory;
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userViewModelFactory = InjectorUtils.provideUserViewModelFactory(this);
        userViewModel = ViewModelProviders.of(this, userViewModelFactory).get(UserViewModel.class);

        TextView textView = findViewById(R.id.textView);
        userViewModel.getUserData("TheScriptan").observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                textView.setText(user.getBio());
            }
        });
    }
}
