package com.monash.mymonashmate.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.monash.mymonashmate.client.StudentClient;
import com.monash.mymonashmate.entity.Profile;

public class GetProfileByStudentIdTask extends AsyncTask<String, Void, Profile>{
	private ProgressDialog authProgressDialog;
	private Context context;
	private StudentClient studentClient = new StudentClient();
	
	public GetProfileByStudentIdTask(Context context) {
		this.context = context;
	}
	
    @Override
    protected void onPreExecute() {
        authProgressDialog = ProgressDialog.show(context, "", "Retrive profile...", true, false);
    }
    @Override
    protected Profile doInBackground(String... params) {
    	return studentClient.getProfile(params[0]);
    }
    @Override
    protected void onPostExecute(Profile result) {
        authProgressDialog.dismiss();

    }

}
