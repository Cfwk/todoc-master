package com.cleanup.todoc.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.ContentValues;
import android.content.Context;
import android.support.annotation.NonNull;

import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

/**
 * Created by Skiti on 22/10/2019
 */

@Database(entities = {Task.class, Project.class}, version = 1, exportSchema = false)
public abstract class todoc_master_database extends RoomDatabase {

    // --- SINGLETON ---
    private static volatile todoc_master_database INSTANCE;

    // --- DAO ---
    public abstract TaskDao mTaskDao();
    public abstract ProjectDao mProjectDao();

    // --- INSTANCE ---
    public static todoc_master_database getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (todoc_master_database.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            todoc_master_database.class, "MyDatabase.db")
                            .addCallback(prepopulateDatabase())
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    // ---

    private static Callback prepopulateDatabase(){
        return new Callback() {

            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);

                ContentValues contentValues = new ContentValues();
                contentValues.put("id", 1);
                contentValues.put("username", "Philippe");
                contentValues.put("urlPicture", "https://oc-user.imgix.net/users/avatars/15175844164713_frame_523.jpg?auto=compress,format&q=80&h=100&dpr=2");

                db.insert("User", OnConflictStrategy.IGNORE, contentValues);
            }
        };
    }
}
