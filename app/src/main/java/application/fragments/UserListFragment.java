package application.fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import application.UserListAdapter;
import application.viewmodel.UserViewModel;
import application.viewmodel.UserViewModelFactory;
import butterknife.BindView;
import butterknife.ButterKnife;
import utils.InjectorUtils;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mvvm_room_retrofit_sample.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserListFragment extends Fragment {

    private UserViewModelFactory userViewModelFactory;
    private UserViewModel userViewModel;

    @BindView(R.id.textView) TextView textView;
    @BindView(R.id.edit_username) EditText editText;
    @BindView(R.id.btn_fetch) Button button;
    @BindView(R.id.userRecyclerView) RecyclerView userRecyclerView;

    public UserListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_list, container, false);
        ButterKnife.bind(this, view);
        RecyclerView.Adapter adapter = new UserListAdapter(getContext());
        userRecyclerView.setAdapter(adapter);

        userRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        userViewModelFactory = InjectorUtils.provideUserViewModelFactory(getActivity().getApplicationContext());
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

        return view;
    }

}
