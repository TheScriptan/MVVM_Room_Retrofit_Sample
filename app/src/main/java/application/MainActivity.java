package application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import business.model.User;
import butterknife.BindView;
import butterknife.ButterKnife;
import utils.InjectorUtils;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mvvm_room_retrofit_sample.R;

public class MainActivity extends AppCompatActivity {

    private UserViewModelFactory userViewModelFactory;
    private UserViewModel userViewModel;

    @BindView (R.id.textView) TextView textView;
    @BindView (R.id.edit_username) EditText editText;
    @BindView (R.id.btn_fetch) Button button;
    @BindView (R.id.userRecyclerView) RecyclerView userRecyclerView;
    @BindView (R.id.tempImage) ImageView tempImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        RecyclerView.Adapter adapter = new UserListAdapter(this);
        userRecyclerView.setAdapter(adapter);
        userRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        userViewModelFactory = InjectorUtils.provideUserViewModelFactory(this);
        userViewModel = ViewModelProviders.of(this, userViewModelFactory).get(UserViewModel.class);


        userViewModel.init("googlesamples");
        userViewModel.getUserData().observe(this, user -> {
            if(user != null){
                Log.v("TEST", "Text changed");
                textView.setText(user.getName());
            }
        });

        userViewModel.getUsers().observe(this, users -> {
            ((UserListAdapter) adapter).setUserList(users);
        });

        button.setOnClickListener((View v) -> {
            userViewModel.setNewUser(editText.getText().toString());
        });



    }
}
