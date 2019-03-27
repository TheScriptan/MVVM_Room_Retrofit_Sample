package application;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mvvm_room_retrofit_sample.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import business.model.User;
import butterknife.BindView;
import butterknife.ButterKnife;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder> {

    //ViewHolder
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {

        @BindView(R.id.text_username) TextView usernameText;
        @BindView(R.id.text_url) TextView urlText;
        @BindView(R.id.imageView) ImageView imageView;
        @BindView(R.id.user_linear_layout)
        LinearLayout mainLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mainLayout.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View v) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(userList.get(getAdapterPosition()).getHtmlUrl()));
            context.startActivity(intent);
            return true;
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
                    .apply(RequestOptions.circleCropTransform())
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
