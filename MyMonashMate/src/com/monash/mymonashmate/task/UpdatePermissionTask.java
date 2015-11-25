package com.monash.mymonashmate.task;

import com.monash.mymonashmate.client.StudentClient;
import com.monash.mymonashmate.entity.Profile;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class UpdatePermissionTask extends AsyncTask<Profile, Void, Boolean> {
	
	private ProgressDialog authProgressDialog;
	private Context context;
	private StudentClient studentClient = new StudentClient();
	
	public UpdatePermissionTask(Context context) {
		this.context = context;
	}
	
    @Override
    protected void onPreExecute() {
        authProgressDialog = ProgressDialog.show(context, "", "Update permission...", true, false);
    }
    @Override
    protected Boolean doInBackground(Profile... params) {
    	return studentClient.updateStudentSetting(params[0]);
    }
    @Override
    protected void onPostExecute(Boolean result) {
        authProgressDialog.dismiss();

    }
}
