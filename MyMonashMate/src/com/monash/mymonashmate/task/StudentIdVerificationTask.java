package com.monash.mymonashmate.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.monash.mymonashmate.client.StudentClient;
import com.monash.mymonashmate.entity.Student;

public class StudentIdVerificationTask extends AsyncTask<String, Void, Student>{
	
	private ProgressDialog authProgressDialog;
	private Context context;
	private Student student;
	private StudentClient studentClient = new StudentClient();
	
	public StudentIdVerificationTask(Context context) {
		this.context = context;
	}
	
    @Override
    protected void onPreExecute() {
        authProgressDialog = ProgressDialog.show(context, "", "Verify Student Id...", true, false);
    }
    @Override
    protected Student doInBackground(String... params) {
    	student = studentClient.getUserById(params[0]);
        return student;
    }
    @Override
    protected void onPostExecute(Student result) {
        authProgressDialog.dismiss();

    }
}
