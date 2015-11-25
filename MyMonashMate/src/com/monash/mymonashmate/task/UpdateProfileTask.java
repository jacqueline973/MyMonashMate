package com.monash.mymonashmate.task;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.monash.mymonashmate.client.StudentClient;
import com.monash.mymonashmate.entity.Profile;
import com.monash.mymonashmate.util.DialogUtil;

public class UpdateProfileTask extends AsyncTask<Profile, Void, Boolean> {
	private ProgressDialog authProgressDialog;
	private Context context;
	private StudentClient studentClient = new StudentClient();
	private boolean success = false;
	private AlertDialog.Builder dialogBuilder;
	private static final String TITLE_SUCCESS_UPDATE_DIALOG = "Successfull";
	private static final String MESSAGE_SUCCCESS_UPDATE = "Profile has been updated.";
	
	public UpdateProfileTask(Context context) {
		this.context = context;
	}
	
    @Override
    protected void onPreExecute() {
        authProgressDialog = ProgressDialog.show(context, "", "Update profile...", true, false);
    }
    @Override
    protected Boolean doInBackground(Profile... params) {
    	success = studentClient.updateProfile(params[0]);
    	return success;
    }
    @Override
    protected void onPostExecute(Boolean result) {
        authProgressDialog.dismiss();
        if (success) {
			DialogUtil.showAlertDialog(
					dialogBuilder, context,
					TITLE_SUCCESS_UPDATE_DIALOG,
					MESSAGE_SUCCCESS_UPDATE);
        }

    }

}
