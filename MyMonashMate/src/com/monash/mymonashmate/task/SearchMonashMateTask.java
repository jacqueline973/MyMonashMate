package com.monash.mymonashmate.task;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.monash.mymonashmate.MateActivity;
import com.monash.mymonashmate.client.StudentClient;
import com.monash.mymonashmate.entity.MatchCriteria;
import com.monash.mymonashmate.entity.MatchMateResult;
import com.monash.mymonashmate.entity.Profile;
import com.monash.mymonashmate.util.DialogUtil;

public class SearchMonashMateTask extends AsyncTask<MatchCriteria, Void, MatchMateResult> {
	
	private ProgressDialog authProgressDialog;
	private Context context;
	private StudentClient studentClient = new StudentClient();
	private MatchMateResult matchResult = new MatchMateResult();
	private Profile profile;
	private AlertDialog.Builder errorDialogBuilder;
	private static final String TITLE_SEARCH_DIALOG_ERROR = "Search Error";
	private static final String MESSAGE_NO_RESULT_ERROR = "Can't find match mate,Please refine the search criteria";
	
	public SearchMonashMateTask(Context context, Profile profile) {
		this.context = context;
		this.profile = profile;
	}
	
    @Override
    protected void onPreExecute() {
        authProgressDialog = ProgressDialog.show(context, "", "Finding match mate...", true, false);//current activity context
    }
    
    @Override
    protected MatchMateResult doInBackground(MatchCriteria... params) {
    	matchResult = studentClient.searchMonashMate((params[0]));
    	return matchResult;
    }
    @Override
    protected void onPostExecute(MatchMateResult result) {
        authProgressDialog.dismiss();
        if (matchResult.getMatchMateList().size() > 0 ) {
        	authProgressDialog.dismiss();
			Intent intent = new Intent(context, MateActivity.class);
			intent.putExtra("profile", profile);
			intent.putExtra("matchResult", matchResult);
			context.startActivity(intent);
        }else{
			DialogUtil.showAlertDialog(
					errorDialogBuilder, context,
					TITLE_SEARCH_DIALOG_ERROR,
					MESSAGE_NO_RESULT_ERROR);
        }
    }

}
