package com.monash.mymonashmate;

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
import android.widget.EditText;

import com.monash.mymonashmate.entity.MatchCriteria;
import com.monash.mymonashmate.entity.Profile;
import com.monash.mymonashmate.task.SearchMonashMateTask;
import com.monash.mymonashmate.util.DialogUtil;
import com.monash.mymonashmate.util.WidgetUtil;

public class SearchCriteriaActivity extends Activity {
	
	private Button search;
	private EditText unitCode;
	private EditText courseCode;
	private EditText nativeLanguage;
	private EditText favoriteFood;
	private EditText suburb;
	private Profile profile;
	private AlertDialog.Builder errorDialogBuilder;
	private MatchCriteria newMatchCriteria;
	private static final String TITLE_SEARCH_DIALOG_ERROR = "Search Error";
	private static final String MESSAGE_EMPTY_INPUT_ERROR = "Please fill at least one criteria.";
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_criteria);
        
		Intent intent = getIntent();
        if (intent != null) {
        	profile = (Profile)intent.getSerializableExtra("profile");
        	Log.i("profile", profile.toString());
        }
        
        search = (Button)findViewById(R.id.search_criteria_search_button);
        unitCode = (EditText) findViewById(R.id.search_criteria_unit);
        courseCode = (EditText) findViewById(R.id.search_criteria_course);
        nativeLanguage = (EditText) findViewById(R.id.search_criteria_native_language);
        favoriteFood = (EditText) findViewById(R.id.search_criteria_favorite_food);
        suburb = (EditText) findViewById(R.id.search_criteria_suburb);
        
        addListenerOnSearchButton();
        
	}
	
	private void addListenerOnSearchButton() {

		search.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (WidgetUtil.allEmptyInput(unitCode,courseCode,nativeLanguage,favoriteFood,suburb)) {
					DialogUtil.showAlertDialog(
							errorDialogBuilder, SearchCriteriaActivity.this,
							TITLE_SEARCH_DIALOG_ERROR,
							MESSAGE_EMPTY_INPUT_ERROR);
				} else {
					assembleMatchCriteria();
					new SearchMonashMateTask(SearchCriteriaActivity.this, profile).execute(newMatchCriteria);
				}

			}

			private void assembleMatchCriteria() {
				newMatchCriteria = new MatchCriteria();
				newMatchCriteria.setUnitCodes(WidgetUtil.getInput(unitCode));
				newMatchCriteria.setCourseCodes(WidgetUtil.getInput(courseCode));
				newMatchCriteria.setNativeLanguage(WidgetUtil.getInput(nativeLanguage));
				newMatchCriteria.setFavouriteFood(WidgetUtil.getInput(favoriteFood));
				newMatchCriteria.setSuburb(WidgetUtil.getInput(suburb));
				newMatchCriteria.setCurrentUserStudentId(String.valueOf(profile.getStudent().getStudentId()));
			}
		});
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
			Intent intent = new Intent(SearchCriteriaActivity.this, DashboardDesignActivity.class);
			intent.putExtra("studentId", profile.getStudent().getStudentId());
			startActivity(intent);
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
