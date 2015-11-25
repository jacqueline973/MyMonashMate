package com.monash.mymonashmate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.monash.mymonashmate.entity.Profile;
import com.monash.mymonashmate.entity.StudentSetting;
import com.monash.mymonashmate.entity.StudentSettingPK;
import com.monash.mymonashmate.task.UpdatePermissionTask;
import com.monash.mymonashmate.util.DialogUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Switch;

public class PermissionActivity extends Activity {
	
	public static final String SUBURB = "SUBURB";
	public static final String FAVOURITE_FOOD = "FAVOURITE_FOOD";
	public static final String NATIVE_LANGUAGE = "NATIVE_LANGUAGE";
	public static final String NICKNAME = "NICKNAME";
	private Switch nickname;
	private Switch nativeLanguage;
	private Switch favouriteFood;
	private Switch suburb;
	private Button saveButton;
	private Profile profile;
	private List<StudentSetting> newStudentSettings;
	private AlertDialog.Builder dialogBuilder;
	private static final String TITLE_SUCCESS_UPDATE_DIALOG = "Successfull";
	private static final String MESSAGE_SUCCCESS_UPDATE = "Setting has been updated.";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_permission_setting);
		
		nickname = (Switch) findViewById(R.id.tb_permission_nickname);
		nativeLanguage = (Switch) findViewById(R.id.tb_permission_native_language);
		favouriteFood = (Switch) findViewById(R.id.tb_permission_favourite_food);
		suburb = (Switch) findViewById(R.id.tb_permission_suburb);
		saveButton = (Button) findViewById(R.id.permission_setting_save_button);
		
		Intent intent = getIntent();
        if (intent != null) {
        	profile = (Profile)intent.getSerializableExtra("profile");
        	Log.i("profile", profile.toString());
        }
        
        initialSwitchButtons();
        addListenerOnSaveButton();

	}
	
	private void addListenerOnSaveButton() {
		saveButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				assembleNewSetting();
				try {
					boolean successful = new UpdatePermissionTask(PermissionActivity.this).execute(profile).get();
					if (successful) {
						DialogUtil.showAlertDialog(
								dialogBuilder, PermissionActivity.this,
								TITLE_SUCCESS_UPDATE_DIALOG,
								MESSAGE_SUCCCESS_UPDATE);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
				
			}});
	}
	
	private void assembleNewSetting() {
		newStudentSettings = new ArrayList<StudentSetting>();
		
		StudentSetting nicknameSetting = new StudentSetting();
		StudentSettingPK nicknamePK = new StudentSettingPK();
		Integer studentId = profile.getStudent().getStudentId();
		nicknamePK.setStudentId(studentId);
		nicknamePK.setAttribute(NICKNAME);
		nicknameSetting.setStudentSettingPK(nicknamePK);
		nicknameSetting.setRestricted(!nickname.isChecked());
		
		StudentSetting nativeLanguageSetting = new StudentSetting();
		StudentSettingPK nativeLanguagePK = new StudentSettingPK();
		nativeLanguagePK.setStudentId(studentId);
		nativeLanguagePK.setAttribute(NATIVE_LANGUAGE);
		nativeLanguageSetting.setStudentSettingPK(nativeLanguagePK);
		nativeLanguageSetting.setRestricted(!nativeLanguage.isChecked());
		
		StudentSetting favouriteFoodSetting = new StudentSetting();
		StudentSettingPK favouriteFoodPK = new StudentSettingPK();
		favouriteFoodPK.setStudentId(studentId);
		favouriteFoodPK.setAttribute(FAVOURITE_FOOD);
		favouriteFoodSetting.setStudentSettingPK(favouriteFoodPK);
		favouriteFoodSetting.setRestricted(!favouriteFood.isChecked());
		
		StudentSetting suburbSetting = new StudentSetting();
		StudentSettingPK suburbPK = new StudentSettingPK();
		suburbPK.setStudentId(studentId);
		suburbPK.setAttribute(SUBURB);
		suburbSetting.setStudentSettingPK(suburbPK);
		suburbSetting.setRestricted(!suburb.isChecked());
		
		newStudentSettings.add(nicknameSetting);
		newStudentSettings.add(nativeLanguageSetting);
		newStudentSettings.add(favouriteFoodSetting);
		newStudentSettings.add(suburbSetting);
		
		profile.setStudentSettings(newStudentSettings);
	}

	private void initialSwitchButtons() {
		for(StudentSetting setting: profile.getStudentSettings()) {
			if (setting.getStudentSettingPK().getAttribute().equals(NICKNAME)) {
				nickname.setChecked(!setting.isRestricted());
			}		
			if (setting.getStudentSettingPK().getAttribute().equals(NATIVE_LANGUAGE)) {
				nativeLanguage.setChecked(!setting.isRestricted());
			}		
			if (setting.getStudentSettingPK().getAttribute().equals(FAVOURITE_FOOD)) {
				favouriteFood.setChecked(!setting.isRestricted());
			}		
			if (setting.getStudentSettingPK().getAttribute().equals(SUBURB)) {
				suburb.setChecked(!setting.isRestricted());
			}		
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.match_mate, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch (item.getItemId()) {
		case R.id.mate_back_dashboard:
			Intent intent = new Intent(PermissionActivity.this, DashboardDesignActivity.class);
			intent.putExtra("studentId", profile.getStudent().getStudentId());
			startActivity(intent);
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
