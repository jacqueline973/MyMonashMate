package com.monash.mymonashmate.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.monash.mymonashmate.client.StudentClient;
import com.monash.mymonashmate.entity.Profile;

public class CreateProfileTask extends AsyncTask<Object, Void, Boolean> {
	
	private ProgressDialog authProgressDialog;
	private Context context;
	private StudentClient studentClient = new StudentClient();
	
	public CreateProfileTask(Context context) {
		this.context = context;
	}
	
    @Override
    protected void onPreExecute() {
        authProgressDialog = ProgressDialog.show(context, "", "Create profile...", true, false);
    }
    @Override
    protected Boolean doInBackground(Object... params) {
    	return studentClient.createNewProfile((Profile)params[0]);
    }
    @Override
    protected void onPostExecute(Boolean result) {
        authProgressDialog.dismiss();

    }

}
