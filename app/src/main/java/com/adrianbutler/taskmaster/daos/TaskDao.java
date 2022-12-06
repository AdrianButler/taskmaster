package com.adrianbutler.taskmaster.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.adrianbutler.taskmaster.models.Task;

import java.util.List;

@Dao
public interface TaskDao
{
	@Insert
	public void insertATask(Task task);

	@Query("SELECT * FROM Task")
	public List<Task> findAllTasks();

	@Query("SELECT * FROM Task WHERE id = :id")
	public Task findTaskById(long id);

	@Query("SELECT * FROM Task WHERE state = :state")
	public List<Task> findAllTasksByState(Task.State state);

	@Delete
	public void deleteTask(Task task);
}
