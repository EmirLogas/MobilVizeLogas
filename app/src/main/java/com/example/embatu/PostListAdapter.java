package com.example.embatu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class PostListAdapter extends ArrayAdapter<PostsClassHomePage> {
    Context mContext;
    int mResource;

    public PostListAdapter(Context context, int resource, ArrayList<PostsClassHomePage> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String user_ID = getItem(position).getUser_ID();
        String post_Text = getItem(position).getPost_Text();

        PostsClassHomePage postsClassHomePage = new PostsClassHomePage(user_ID, post_Text);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView userID = convertView.findViewById(R.id.textView8);
        TextView postText = convertView.findViewById(R.id.textView9);

        userID.setText(user_ID);
        postText.setText(post_Text);

        return convertView;
    }
}
