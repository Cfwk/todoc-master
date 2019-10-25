package com.cleanup.todoc.Injections;

import android.content.Context;

import com.cleanup.todoc.database.todoc_master_database;
import com.cleanup.todoc.repository.ProjectDataRepository;
import com.cleanup.todoc.repository.TaskDataRepository;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by Skiti on 25/10/2019
 */

public class Injection {

    public static TaskDataRepository provideTaskDataSource(Context context) {
        todoc_master_database database = todoc_master_database.getInstance(context);
        return new TaskDataRepository(database.mTaskDao());
    }

    public static ProjectDataRepository provideProjectDataSource(Context context) {
        todoc_master_database database = todoc_master_database.getInstance(context);
        return new ProjectDataRepository(database.mProjectDao());
    }

    public static Executor provideExecutor(){ return Executors.newSingleThreadExecutor(); }

    public static ViewModelFactory provideViewModelFactory(Context context) {
        TaskDataRepository dataSourceTask = provideTaskDataSource(context);
        ProjectDataRepository dataSourceProject = provideProjectDataSource(context);
        Executor executor = provideExecutor();
        return new ViewModelFactory(dataSourceTask, dataSourceProject, executor);
    }
}