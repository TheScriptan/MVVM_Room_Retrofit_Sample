package application.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import application.viewmodel.UserViewModel;
import application.viewmodel.UserViewModelFactory;
import butterknife.BindView;
import butterknife.ButterKnife;
import utils.InjectorUtils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mvvm_room_retrofit_sample.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReposListFragment extends Fragment {

    private UserViewModelFactory userViewModelFactory;
    private UserViewModel userViewModel;
    @BindView(R.id.textView) TextView textView;

    public ReposListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_repos_list, container, false);
        ButterKnife.bind(this, view);

        userViewModelFactory = InjectorUtils.provideUserViewModelFactory(getActivity().getApplicationContext());
        userViewModel = ViewModelProviders.of(this, userViewModelFactory).get(UserViewModel.class);

        userViewModel.setCurrentRepo("thescriptan", "sep3");

        userViewModel.getCurrentRepoData().observe(this, repo -> {
            textView.setText(repo.getFullName());
        });

        return view;
    }

}
