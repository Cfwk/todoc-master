package com.cleanup.todoc.ui;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repository.ProjectDataRepository;
import com.cleanup.todoc.repository.TaskDataRepository;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * Created by Skiti on 25/10/2019
 */

public class TaskViewModel extends ViewModel {

    // REPOSITORIES
    private final TaskDataRepository mTaskDataSource;
    private final ProjectDataRepository mProjectDataSource;
    private final Executor executor;
    private LiveData<List<Project>> allProjects;
    private int Taskcount;

    public TaskViewModel(TaskDataRepository taskDataSource, ProjectDataRepository projectDataSource, Executor executor) {
        this.mTaskDataSource = taskDataSource;
        this.mProjectDataSource = projectDataSource;
        this.executor = executor;
    }
    public void init() {
        if (this.allProjects != null) {
            return;
        }
        allProjects = mProjectDataSource.getAllProjects();
    }

    // -------------
    // FOR PROJECT
    // -------------

    public LiveData<List<Project>> getAllProjects() { return this.mProjectDataSource.getAllProjects();  }
    public LiveData<Project> getProject(long userId) { return this.mProjectDataSource.getProject(userId);  }
    public void createProject(Project project) { this.mProjectDataSource.createProject(project);  }

    // -------------
    // FOR ITEM
    // -------------

    public LiveData<Task> getTasks(long userId) {
        return mTaskDataSource.getTask(userId);
    }
    public LiveData<Integer> getTaskCount(){
        return mTaskDataSource.getTaskCount();
    }
    public LiveData<List<Task>> getAllTasks() {
        return mTaskDataSource.getAllTasks();
    }
    public void createTask(Task task) {
        executor.execute(() -> {
            mTaskDataSource.createTask(task);
        });
    }

    public void deleteTask(long taskId) {
        executor.execute(() -> {
            mTaskDataSource.deleteTask(taskId);
        });
    }

    public void updateTask(Task task) {
        executor.execute(() -> {
            mTaskDataSource.updateTask(task);
        });
    }
}