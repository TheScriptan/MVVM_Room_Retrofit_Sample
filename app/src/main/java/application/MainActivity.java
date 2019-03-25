package application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import application.views.RepositoryListActivity;
import business.model.User;
import butterknife.BindView;
import butterknife.ButterKnife;
import utils.InjectorUtils;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
    @BindView (R.id.toolbar) Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        RecyclerView.Adapter adapter = new UserListAdapter(this);
        userRecyclerView.setAdapter(adapter);
        userRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        userViewModelFactory = InjectorUtils.provideUserViewModelFactory(this);
        userViewModel = ViewModelProviders.of(this, userViewModelFactory).get(UserViewModel.class);


        userViewModel.getUserData().observe(this, user -> {
            if(user != null){
                Log.v("TEST", "Text changed");
                textView.setText(user.getName());
            }
        });

        userViewModel.getUsers().observe(this, ((UserListAdapter) adapter)::setUserList);

        button.setOnClickListener((View v) -> {
            userViewModel.setNewUser(editText.getText().toString());
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.action_repository:
                Intent intent = new Intent(MainActivity.this, RepositoryListActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
