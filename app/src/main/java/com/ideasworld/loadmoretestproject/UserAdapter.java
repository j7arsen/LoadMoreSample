package com.ideasworld.loadmoretestproject;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * Created by arsen on 10.08.17.
 */

public class UserAdapter extends BaseLoadMoreAdapter<User> {

    public UserAdapter(Context context, ILoadMoreListener loadMoreListener) {
        super(context, loadMoreListener);
    }

    @Override
    protected RecyclerView.ViewHolder createItem(ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.row_user_item, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    protected void bindItem(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof UserViewHolder) {
            User user = mDataList.get(position);
            UserViewHolder userViewHolder = (UserViewHolder) holder;
            userViewHolder.tvName.setText(user.getName());
            userViewHolder.tvEmailId.setText(user.getEmail());
        }
    }

    class UserViewHolder extends RecyclerView.ViewHolder {

        public TextView tvName;
        public TextView tvEmailId;

        public UserViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvEmailId = (TextView) itemView.findViewById(R.id.tvEmailId);
        }
    }
}
