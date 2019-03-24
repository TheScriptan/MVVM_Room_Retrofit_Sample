package application;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.mvvm_room_retrofit_sample.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import business.model.User;
import butterknife.BindView;
import butterknife.ButterKnife;
import utils.AppExecutors;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder> {

    //ViewHolder
    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_username) TextView usernameText;
        @BindView(R.id.text_url) TextView urlText;
        @BindView(R.id.imageView) ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    //RecyclerView

    private List<User> userList;
    private Context context;

    public UserListAdapter(Context context){
        userList = new ArrayList<>();
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_list_user, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(userList.get(position) != null){
            String username = userList.get(position).getUsername();
            String url = userList.get(position).getHtmlUrl();
            holder.usernameText.setText(username);
            holder.urlText.setText(url);

            String image_url = userList.get(position).getAvatarUrl();
            Glide.with(holder.itemView.getContext())
                    .load(image_url)
                    .into(holder.imageView);
        }
    }

    public void setUserList(List<User> list){
        userList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}
