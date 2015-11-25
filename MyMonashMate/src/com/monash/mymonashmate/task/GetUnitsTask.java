package com.monash.mymonashmate.task;

import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.monash.mymonashmate.client.CourseClient;
import com.monash.mymonashmate.entity.Unit;

public class GetUnitsTask extends AsyncTask<String, Void, List<Unit>> {
	
	private ProgressDialog authProgressDialog;
	private Context context;
	private List<Unit> unitList;
	private CourseClient courseClient = new CourseClient();
	
	public GetUnitsTask(Context context) {
		this.context = context;
	}
	
    @Override
    protected void onPreExecute() {
        authProgressDialog = ProgressDialog.show(context, "", "Retrive units...", true, false);
    }
    @Override
    protected List<Unit> doInBackground(String... params) {
    	unitList = courseClient.getUnitsByCourses(params[0]);
        return unitList;
    }
    @Override
    protected void onPostExecute(List<Unit> result) {
        authProgressDialog.dismiss();

    }
}
