package com.cleanup.todoc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.cleanup.todoc.database.TodocDatabase;
import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class DaoTest {

    // Database
    private TodocDatabase database;
    private ProjectDao projectDao;
    private TaskDao taskDao;

    // Data set
    private long PROJECT_1_ID = 1L;
    private long PROJECT_2_ID = 2L;
    private long PROJECT_3_ID = 3L;
    private Project PROJECT_1 = new Project(PROJECT_1_ID, "Projet Tartampion", 0xFFEADAD1);
    private Project PROJECT_2 = new Project(PROJECT_2_ID, "Projet Lucidia", 0xFFB4CDBA);
    private Project PROJECT_3 = new Project(PROJECT_3_ID, "Projet Circus", 0xFFA3CED2);
    private List<Project> PROJECTS = Arrays.asList(PROJECT_1, PROJECT_2, PROJECT_3);

    private long TASK_1_ID = 1;
    private long TASK_2_ID = 2;
    private Task TASK_1 = new Task(TASK_1_ID, PROJECT_1_ID, "Tâche 1", 999);
    private Task TASK_2 = new Task(TASK_2_ID, PROJECT_2_ID, "Tâche 2", 999);
    private List<Task> TASKS = Arrays.asList(TASK_1, TASK_2);

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        database = Room.inMemoryDatabaseBuilder(context, TodocDatabase.class).allowMainThreadQueries().build();
        projectDao = database.projectDao();
        taskDao = database.taskDao();
    }

    @After
    public void closeDb() throws IOException {
        database.close();
    }

    @Test
    public void insertAndGetProject() throws Exception {
        projectDao.createProject(PROJECT_1);
        Project project = projectDao.getProject(PROJECT_1_ID);
        assertTrue(project.getName().equals(PROJECT_1.getName()) && project.getId() == PROJECT_1_ID);
    }

    @Test
    public void getAllProjects() throws Exception {
        projectDao.createProjects(PROJECTS);
        List<Project> projects = projectDao.getAllProjects();
        assertEquals(projects, PROJECTS);
    }

    @Test
    public void addTask() throws Exception {
        projectDao.createProject(PROJECT_1);
        taskDao.insertTask(TASK_1);
        Task task = taskDao.getTask(TASK_1_ID);
        assertTrue(task.getName().equals(TASK_1.getName()) && task.getId() == TASK_1_ID);
    }

    @Test
    public void deleteTask() throws Exception {
        projectDao.createProject(PROJECT_1);
        taskDao.insertTask(TASK_1);
        Task task = taskDao.getTask(TASK_1_ID);

        taskDao.deleteTask(TASK_1);
        assertFalse(taskDao.getAllTasks().contains(task));
    }

    @Test
    public void getAllTasks() throws Exception {
        projectDao.createProjects(PROJECTS);
        taskDao.insertTask(TASK_1);
        taskDao.insertTask(TASK_2);
        List<Task> tasks = taskDao.getAllTasks();
        assertEquals(tasks, TASKS);
    }
}
