package com.cleanup.todoc;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.cleanup.todoc.database.todoc_master_database;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by Skiti on 24/10/2019
 */

@RunWith(AndroidJUnit4.class)
public class TaskDaoTest {

    // FOR DATA
    private todoc_master_database database;

    // DATA SET FOR TEST
    private static long PROJECT_ID = 1L;
    private static Project PROJECT_DEMO = Project.getProjectById(1L);
    private static Task TASK_A = new Task(1,PROJECT_ID,"Visite cet endroit de rêve !", 0);
    private static Task TASK_B = new Task(2,PROJECT_ID,"On pourrait faire du chien de traîneau ?", 1);
    private static Task TASK_C = new Task(3,PROJECT_ID,"Ce restaurant à l'air sympa", 2);

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void initDb() throws Exception {
        this.database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                todoc_master_database.class)
                .allowMainThreadQueries()
                .build();
    }

    @After
    public void closeDb() throws Exception {
        database.close();
    }

    @Test
    public void insertAndGetUser() throws InterruptedException {
        // BEFORE : Adding a new user
        this.database.mProjectDao().createProject(PROJECT_DEMO);
        // TEST
        Project project = LiveDataTestUtil.getValue(this.database.mProjectDao().getProject(PROJECT_ID));
        assertTrue(project.getName().equals(PROJECT_DEMO.getName()) && project.getId() == PROJECT_ID);
    }

    @Test
    public void getItemsWhenNoItemInserted() throws InterruptedException {
        // TEST
        List<Task> tasks = LiveDataTestUtil.getValue(this.database.mTaskDao().getAllTasks());
        assertTrue(tasks.isEmpty());
    }

    @Test
    public void insertAndGetItems() throws InterruptedException {
        // BEFORE : Adding demo user & demo items

        this.database.mProjectDao().createProject(PROJECT_DEMO);
        this.database.mTaskDao().insertTask(TASK_A);
        this.database.mTaskDao().insertTask(TASK_B);
        this.database.mTaskDao().insertTask(TASK_C);

        // TEST
        List<Task> tasks = LiveDataTestUtil.getValue(this.database.mTaskDao().getAllTasks());
        assertTrue(tasks.size() == 3);
    }

    @Test
    public void insertAndUpdateItem() throws InterruptedException {
        // BEFORE : Adding demo user & demo items. Next, update item added & re-save it
        this.database.mProjectDao().createProject(PROJECT_DEMO);
        this.database.mTaskDao().insertTask(TASK_A);
        Task taskAdded = LiveDataTestUtil.getValue(this.database.mTaskDao().getTask(PROJECT_ID));
        taskAdded.setDone(true);
        this.database.mTaskDao().updateTask(taskAdded);

        //TEST
        List<Task> tasks = LiveDataTestUtil.getValue(this.database.mTaskDao().getAllTasks());
        assertTrue(tasks.size() == 1 && tasks.get(0).getDone());
    }

    @Test
    public void insertAndDeleteItem() throws InterruptedException {
        // BEFORE : Adding demo user & demo item. Next, get the item added & delete it.
        this.database.mProjectDao().createProject(PROJECT_DEMO);
        this.database.mTaskDao().insertTask(TASK_B);
        List<Task> oldTasks = LiveDataTestUtil.getValue((this.database.mTaskDao().getAllTasks()));
        this.database.mTaskDao().deleteTask(oldTasks.get(0).getId());

        //TEST
        List<Task> tasks = LiveDataTestUtil.getValue(this.database.mTaskDao().getAllTasks());
        assertTrue(tasks.isEmpty());
    }
}