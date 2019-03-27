package application;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mvvm_room_retrofit_sample.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import business.model.Repo;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RepoListAdapter extends RecyclerView.Adapter<RepoListAdapter.ViewHolder> {

    //ViewHolder

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        @BindView(R.id.repo_list_items) LinearLayout mainLayout;
        @BindView(R.id.repo_parent_items) LinearLayout parentLayout;
        @BindView(R.id.repo_child_items) LinearLayout childLayout;
        @BindView(R.id.repo_expander) TextView expanderText;
        @BindView(R.id.repo_fullName) TextView fullNameText;
        @BindView(R.id.repo_description) TextView descriptionText;
        @BindView(R.id.repo_language) TextView languageText;
        @BindView(R.id.repo_createdAt) TextView createdText;
        @BindView(R.id.repo_updatedAt) TextView updatedAt;
        @BindView(R.id.repo_pushedAt) TextView pushedAt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            childLayout.setVisibility(View.GONE);
            mainLayout.setOnClickListener(this);
            mainLayout.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(childLayout.getVisibility() == View.GONE) {
                expanderText.setText(" + ");
                childLayout.setVisibility(View.VISIBLE);
            } else if(childLayout.getVisibility() == View.VISIBLE) {
                expanderText.setText(" - ");
                childLayout.setVisibility(View.GONE);
            }
        }

        @Override
        public boolean onLongClick(View v) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(repoList.get(getAdapterPosition()).getHtmlUrl()));
            context.startActivity(intent);
            return true;
        }
    }

    //RecyclerView

    private Context context;
    private List<Repo> repoList;

    public RepoListAdapter(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_list_repos, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int position) {
        if(repoList.get(position) != null){
            Repo repo = repoList.get(position);
            h.expanderText.setText(" - ");
            h.fullNameText.setText(repo.getFullName());
            h.descriptionText.setText(repo.getDescription());
            h.languageText.setText(repo.getLanguage());
            h.createdText.setText(repo.getCreatedAt());
            h.updatedAt.setText(repo.getUpdatedAt());
            h.pushedAt.setText(repo.getPushedAt());

        }
    }

    public void setRepoList(List<Repo> repos){
        this.repoList = repos;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return repoList.size();
    }
}
