package com.adrianbutler.taskmaster;

import android.app.Application;
import android.util.Log;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.core.Amplify;

public class TaskMasterAmplifyApplication extends Application
{
	public final static String TAG = "taskmasteramplifyapplication";

	@Override
	public void onCreate()
	{
		super.onCreate();
		try
		{
			Amplify.addPlugin(new AWSApiPlugin());
			Amplify.configure(getApplicationContext());
		}
		catch (AmplifyException exception)
		{
			Log.wtf(TAG, "Error initializing Amplify: " + exception.getMessage());
		}
	}
}
