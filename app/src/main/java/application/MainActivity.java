package application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import application.fragments.ReposListFragment;
import application.fragments.UserListFragment;
import application.viewmodel.UserViewModel;
import application.viewmodel.UserViewModelFactory;
import butterknife.BindView;
import butterknife.ButterKnife;
import utils.InjectorUtils;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mvvm_room_retrofit_sample.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    final Fragment userListFragment = new UserListFragment();
    final Fragment reposListFragment = new ReposListFragment();
    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = userListFragment;

    @BindView (R.id.toolbar) Toolbar toolbar;
    @BindView (R.id.bottomNav) BottomNavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //Toolbar setup
        setSupportActionBar(toolbar);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setTitle("Github Users");

        //Fragment setup
        fm.beginTransaction().add(R.id.main_container, reposListFragment, "2").hide(reposListFragment).commit();
        fm.beginTransaction().add(R.id.main_container, userListFragment, "1").commit();


        //Bottom nav view setup
        navigationView.setOnNavigationItemSelectedListener(navigationListener);
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

        }
        return super.onOptionsItemSelected(item);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationListener
            = menuItem -> {
                int id = menuItem.getItemId();
                switch(id){
                    case R.id.navigation_users:
                        fm.beginTransaction().hide(active).show(userListFragment).commit();
                        active = userListFragment;
                        getSupportActionBar().setTitle("Github Users");
                        return true;
                    case R.id.navigation_repos:
                        fm.beginTransaction().hide(active).show(reposListFragment).commit();
                        active = reposListFragment;
                        getSupportActionBar().setTitle("Github Repositories");
                        return true;
                }
                return false;
            };
}
