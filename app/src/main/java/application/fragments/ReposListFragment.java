package application.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import application.RepoListAdapter;
import application.viewmodel.UserViewModel;
import application.viewmodel.UserViewModelFactory;
import butterknife.BindView;
import butterknife.ButterKnife;
import utils.InjectorUtils;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mvvm_room_retrofit_sample.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReposListFragment extends Fragment {

    private UserViewModelFactory userViewModelFactory;
    private UserViewModel userViewModel;
    //@BindView(R.id.textView) TextView textView;
    @BindView (R.id.repos_recycler_view) RecyclerView repoRecyclerView;
    @BindView (R.id.repo_name) EditText repoNameText;
    @BindView(R.id.repo_owner) EditText ownerText;
    @BindView (R.id.button_fetch) Button buttonFetch;

    public ReposListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_repos_list, container, false);
        ButterKnife.bind(this, view);

        //RecyclerView
        repoRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        RecyclerView.Adapter adapter = new RepoListAdapter(getContext());
        repoRecyclerView.setAdapter(adapter);


        //ViewModel & Factory
        userViewModelFactory = InjectorUtils.provideUserViewModelFactory(getActivity().getApplicationContext());
        userViewModel = ViewModelProviders.of(this, userViewModelFactory).get(UserViewModel.class);


        //Initializing data
        userViewModel.setCurrentRepo("thescriptan", "sep3");

//        userViewModel.getCurrentRepoData().observe(this, repo -> {
//            textView.setText(repo.getFullName());
//        });

        userViewModel.getRepos().observe(this, ((RepoListAdapter) adapter)::setRepoList);

        buttonFetch.setOnClickListener((View v) -> {
            if(!TextUtils.isEmpty(repoNameText.getText()) && !TextUtils.isEmpty(ownerText.getText())){
                String repoName = repoNameText.getText().toString();
                String ownerName = ownerText.getText().toString();

                userViewModel.setCurrentRepo(ownerName, repoName);
            } else {
                Toast.makeText(getContext(), "Invalid strings", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

}
