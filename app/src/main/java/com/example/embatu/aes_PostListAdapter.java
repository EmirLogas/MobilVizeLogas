package com.example.embatu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class aes_PostListAdapter extends ArrayAdapter<aes_PostsClassHomePage> {
    Context mContext;
    int mResource;

    public aes_PostListAdapter(Context context, int resource, ArrayList<aes_PostsClassHomePage> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String user_ID = getItem(position).getUser_ID();
        String post_Text = getItem(position).getPost_Text();

        aes_PostsClassHomePage aesPostsClassHomePage = new aes_PostsClassHomePage(user_ID, post_Text);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView userID = convertView.findViewById(R.id.textView8);
        TextView postText = convertView.findViewById(R.id.textView9);

        userID.setText(user_ID);
        postText.setText(post_Text);

        return convertView;
    }
}
