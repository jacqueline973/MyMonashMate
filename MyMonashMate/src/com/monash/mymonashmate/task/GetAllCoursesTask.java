package com.monash.mymonashmate.task;

import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.monash.mymonashmate.client.CourseClient;
import com.monash.mymonashmate.entity.Course;

public class GetAllCoursesTask extends AsyncTask<String, Void, List<Course>> {

	private Context context;
	private ProgressDialog authProgressDialog;
	private List<Course> courseList;
	private CourseClient courseClient = new CourseClient();
	
	public GetAllCoursesTask(Context context) {
		this.context = context;
	}
	
    @Override
    protected void onPreExecute() {
        authProgressDialog = ProgressDialog.show(context, "", "Retrive courses...", true, false);
    }
    @Override
    protected List<Course> doInBackground(String... params) {
    	courseList = courseClient.getAllCourses();
        return courseList;
    }
    @Override
    protected void onPostExecute(List<Course> result) {
        authProgressDialog.dismiss();

    }

}
