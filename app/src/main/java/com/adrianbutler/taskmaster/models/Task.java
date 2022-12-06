package com.adrianbutler.taskmaster.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class Task
{
	@PrimaryKey(autoGenerate = true)
	public Long id;

	private Date dateCreated;

	private String title;
	private String body;
	private State state;

	public Task(){}

	public Task(String title, String body, State state)
	{
		this.title = title;
		this.body = body;
		this.state = state;

		dateCreated = new Date();
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getBody()
	{
		return body;
	}

	public void setBody(String body)
	{
		this.body = body;
	}

	public State getState()
	{
		return state;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Date getDateCreated()
	{
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated)
	{
		this.dateCreated = dateCreated;
	}

	public void setState(State state)
	{
		this.state = state;
	}

	public enum State
	{
		NEW("New"),
		ASSIGNED("Assigned"),
		IN_PROGRESS("In progress"),
		COMPLETE("Complete");

		private final String currentState;

		State(String currentState)
		{
			this.currentState = currentState;
		}

		public static State fromString(String possibleState)
		{
			for (State state : State.values())
			{
				if (state.currentState.equals(possibleState))
				{
					return state;
				}
			}

			return null;
		}

		@NonNull
		@Override
		public String toString()
		{
			if (currentState == null)
			{
				return "";
			}
			return currentState;
		}
	}
}


