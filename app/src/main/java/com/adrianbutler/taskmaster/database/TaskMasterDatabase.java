package com.adrianbutler.taskmaster.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.adrianbutler.taskmaster.daos.TaskDao;
import com.adrianbutler.taskmaster.models.Task;

@TypeConverters({TaskMasterDatabaseConverters.class})
@Database(entities = {Task.class}, version = 1)
public abstract class TaskMasterDatabase extends RoomDatabase
{
	public abstract TaskDao taskDao();
}
