package com.monash.mymonashmate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.monash.mymonashmate.entity.Profile;
import com.monash.mymonashmate.task.UpdateProfileTask;
import com.monash.mymonashmate.util.WidgetUtil;

public class ProfileUpdateActivity extends Activity {

	private Profile profile;
	private EditText nickname;
	private EditText nativeLanguage;
	private EditText favoriteFood;
	private EditText suburb;
	private TextView studentId;
	private TextView name;
	private TextView latitude;
	private TextView longitude;
	private TextView courses;
	private TextView units;
	private Button update;

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_profile);

		Intent intent = getIntent();
        if (intent != null) {
        	profile = (Profile)intent.getSerializableExtra("profile");
        	Log.i("profile", profile.toString());
        }
        setupViews();
        assembleFields();
        addListenerOnUpdate();

	}
	
	private void addListenerOnUpdate() {
		update.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				setFieldsValue();
				new UpdateProfileTask(ProfileUpdateActivity.this).execute(profile);
			}

			private void setFieldsValue() {
				profile.getStudent().setNickname(WidgetUtil.getInput(nickname));
				profile.getStudent().setNativeLanguage(WidgetUtil.getInput(nativeLanguage));
				profile.getStudent().setFavouriteFood(WidgetUtil.getInput(favoriteFood));
				profile.getStudent().setSuburb(WidgetUtil.getInput(suburb));
			}});	
	}
	
	private void setupViews() {
		nickname = (EditText)findViewById(R.id.update_profile_nickname);
		nativeLanguage = (EditText)findViewById(R.id.update_profile_native_language);
		favoriteFood = (EditText)findViewById(R.id.update_profile_favorite_food);
		suburb = (EditText)findViewById(R.id.update_profile_suburb);
		studentId = (TextView)findViewById(R.id.update_profile_studentId_text);
		name = (TextView)findViewById(R.id.update_profile_name_text);
		latitude = (TextView)findViewById(R.id.update_profile_latitude_text);
		longitude = (TextView)findViewById(R.id.update_profile_longitude_text);
		courses = (TextView)findViewById(R.id.update_profile_course_text);
		units = (TextView)findViewById(R.id.update_profile_unit_text);
		update = (Button)findViewById(R.id.update_profile_save_button);
	}
	
	private void assembleFields() {
		nickname.setText(profile.getStudent().getNickname());
		nativeLanguage.setText(profile.getStudent().getNativeLanguage());
		favoriteFood.setText(profile.getStudent().getFavouriteFood());
		suburb.setText(profile.getStudent().getSuburb());
		studentId.setText(String.valueOf(profile.getStudent().getStudentId()));
		name.setText(profile.getStudent().getName());
		latitude.setText(profile.getStudent().getLatitude());
		longitude.setText(profile.getStudent().getLongitude());
		courses.setText(profile.newLineEachCourse());
		units.setText(profile.newLineEachUnit());
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
			Intent intent = new Intent(ProfileUpdateActivity.this, DashboardDesignActivity.class);
			intent.putExtra("studentId", profile.getStudent().getStudentId());
			startActivity(intent);
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
