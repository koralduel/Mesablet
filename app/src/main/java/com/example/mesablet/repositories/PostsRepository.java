package com.example.mesablet.repositories;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mesablet.FireBase;
import com.example.mesablet.data.LocalDatabase;
import com.example.mesablet.data.PostDao;
import com.example.mesablet.entities.Post;

import java.util.LinkedList;
import java.util.List;

public class PostsRepository {

    private PostDao dao;
    private PostListData postListData;
    private FireBase fireBase;


    public PostsRepository() {
        LocalDatabase db = LocalDatabase.getInstance();
        dao = db.postDao();
        postListData = new PostListData();
        fireBase = new FireBase(dao,postListData);

    }



    public class PostListData extends MutableLiveData<List<Post>> {

        public PostListData() {
            super();
            setValue(new LinkedList<>());
        }

        @Override
        protected void onActive() {
            super.onActive();

            new Thread(() -> {
                postListData.postValue(dao.get());
            }).start();
        }
    }

    //buid a firebase class and execute these functions
    public LiveData<List<Post>> getAll() {
        return postListData;
    }

    public void add (final Post post) {
        fireBase.add(post);
    }

    public void delete (final Post post) {
        fireBase.delete(post);
    }

    public void reload() {
        fireBase.reload();
    }
}


