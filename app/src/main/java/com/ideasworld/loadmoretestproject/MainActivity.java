package com.ideasworld.loadmoretestproject;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<User> mUsers = new ArrayList<>();

    private UserAdapter mUserAdapter;

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycleView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mUserAdapter = new UserAdapter(this, new ILoadMoreListener() {
            @Override
            public void loadData() {
                new Handler().postDelayed(new Runnable() {
                    @Override public void run() {
                        List<User> userList = new ArrayList<User>();
                        int index = mUsers.size();
                        int end = index + 20;
                        for (int i = index; i < end; i++) {
                            User user = new User();
                            user.setName("Name " + i);
                            user.setEmail("alibaba" + i + "@gmail.com");
                            userList.add(user);
                        }
                        mUserAdapter.updateData(userList);
                    }
                }, 5000);
            }
        });
        mRecyclerView.setAdapter(mUserAdapter);

        mUserAdapter.loadData();

    }
}
