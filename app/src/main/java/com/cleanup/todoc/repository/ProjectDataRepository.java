package com.cleanup.todoc.repository;
import android.arch.lifecycle.LiveData;
import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.model.Project;
import java.util.List;

/**
 * Created by Skiti on 24/10/2019
 */

public class ProjectDataRepository {
        private final ProjectDao mProjectDao;

        public ProjectDataRepository(ProjectDao projectDao) { this.mProjectDao = projectDao; }
        /**
         * Select the Project by ID
         * @param projectId
         * @return
         */
        public LiveData<Project> getProject(long projectId) { return this.mProjectDao.getProject(projectId); }
        /**
         * Return all the Projects
         * @return
         */
        public LiveData<List<Project>> getAllProjects(){return this.mProjectDao.getAllProjects();}
        /**
         * Add the project to the database
         * @param project
         */
        public void createProject(Project project){this.mProjectDao.createProject(project);}
    }
