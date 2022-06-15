package com.example.mesablet.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.mesablet.entities.PostPhoto;

import java.util.List;

@Dao
public interface PhotoDao {

    @Query("SELECT * FROM PostPhoto WHERE PostId = :id")
    List<PostPhoto> getAllPhotos(int id);

    @Query("SELECT * FROM PostPhoto WHERE id = :id")
    PostPhoto get(int id);

    @Insert
    void insertPhoto(PostPhoto...postPhotos);

    @Delete
    void delete(PostPhoto postPhoto);
}
