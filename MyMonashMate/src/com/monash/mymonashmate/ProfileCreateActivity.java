package com.monash.mymonashmate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.monash.mymonashmate.entity.Course;
import com.monash.mymonashmate.entity.Profile;
import com.monash.mymonashmate.entity.Student;
import com.monash.mymonashmate.entity.Unit;
import com.monash.mymonashmate.task.CreateProfileTask;
import com.monash.mymonashmate.task.GetAllCoursesTask;
import com.monash.mymonashmate.task.GetUnitsTask;
import com.monash.mymonashmate.task.StudentIdVerificationTask;
import com.monash.mymonashmate.util.DialogUtil;
import com.monash.mymonashmate.util.WidgetUtil;

public class ProfileCreateActivity extends Activity {
	
	protected CharSequence[] courseOptions;
	protected CharSequence[] unitOptions;
	protected boolean[] courseSelections;
	protected boolean[] unitSelections;
	private List<Course> courseList;
	private List<Unit> unitList;
	protected Button courseButton;
	protected Button unitButton;
	protected Button saveButton;
	private AlertDialog.Builder coursesDialogBuilder;
	private AlertDialog.Builder errorDialogBuilder;
	private AlertDialog.Builder unitsDialogBuilder;
	private TextView selectedCourseText;
	private TextView selectedUnitText;
	private List<String> selectedCourseCodes = new ArrayList<String>();
	private List<String> selectedUnitCodes = new ArrayList<String>();
	private EditText studentId;
	private EditText name;
	private EditText nickname;
	private EditText nativeLanguage;
	private EditText favouriteFood;
	private EditText suburb;
	private String userId;
		
	private static final String TITLE_UNIT_DIALOG_ERROR = "No Course has been choosed";
	private static final String TITLE_ERROR_SAVE_DIALOG_ERROR = "Save Error";
	private static final String MESSAGE_UNIT_DIALOG_ERROR = "Please choose Course first.";
	private static final String MESSAGE_EMPTY_ERROR_SAVE_DIALOG_ERROR = "Student Id, Name, Course and Unit are compulsory fields!";
	private static final String MESSAGE_STUDENT_ID_NOT_INTEGER_ERROR = "Student Id must be integer!";
	private static final String MESSAGE_STUDENT_ID_EXISTS_ERROR = "Student Id exists!";
	private static final String MESSAGE_GENERAL_SAVE_ERROR = "Failed save profile,Please contact My Monash Mate for detail.";
	private static final String DEFAULT_LATITUDE = "-37.876470";
	private static final String DEFAULT_LONGITUDE = "145.044078";
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
       
        setContentView(R.layout.activity_add_profile);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
        	userId =  extras.getString("userId");
        	Log.i("userId", userId);
        }
        
        findViews();
        
        addListenerOnCourseButton();
        addListenerOnUnitButton();
        addListenerOnSaveButton();
    }

	private void findViews() {
		selectedCourseText = (TextView)findViewById(R.id.add_profile_selected_course_text);
        selectedUnitText = (TextView)findViewById(R.id.add_profile_selected_unit_text);
        saveButton = ( Button ) findViewById( R.id.add_profile_save_button );
        courseButton = ( Button ) findViewById( R.id.add_profile_course_button );
        unitButton = ( Button ) findViewById( R.id.add_profile_unit_button );
        studentId = ( EditText ) findViewById( R.id.add_profile_student_id );
        name = ( EditText ) findViewById( R.id.add_profile_name );
        nickname = ( EditText ) findViewById( R.id.add_profile_nickname );
        nativeLanguage = ( EditText ) findViewById( R.id.add_profile_native_language );
        favouriteFood = ( EditText ) findViewById( R.id.add_profile_favourite_food );
        suburb = ( EditText ) findViewById( R.id.add_profile_suburb );
	}

    private void addListenerOnCourseButton() {
        courseButton.setOnClickListener( new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				GetAllCoursesTask getAllCoursesTask = new GetAllCoursesTask(ProfileCreateActivity.this);
				try {
					courseList = getAllCoursesTask.execute().get();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
		        if (null != courseList) {
		        	Log.i("courseList", "Get All Courses");
		        	courseOptions = new CharSequence[courseList.size()];
		        	for (int i=0; i< courseList.size(); i++) {
		        		courseOptions[i] = courseList.get(i).toString();
		        	}
		        	courseSelections =  new boolean[ courseOptions.length ];
		        	setupCoursesDialogBuilder();
					AlertDialog coursesDialog = coursesDialogBuilder.create();
					coursesDialog.show();
		        }
			}});
    }
    
	private void setupCoursesDialogBuilder() {
		coursesDialogBuilder = new AlertDialog.Builder(this,R.style.CoursesDialog);
		coursesDialogBuilder.setTitle("Choose courses");
		coursesDialogBuilder.setMultiChoiceItems(courseOptions, courseSelections, new DialogSelectionClickHandler() );
		coursesDialogBuilder.setPositiveButton("OK", new DialogButtonClickHandler());
	}
    
	private void addListenerOnUnitButton() {
		unitButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				int size = selectedCourseCodes.size();
				if (size == 0) {
					DialogUtil.showAlertDialog(errorDialogBuilder, ProfileCreateActivity.this, 
							TITLE_UNIT_DIALOG_ERROR, MESSAGE_UNIT_DIALOG_ERROR);
				} else {
					StringBuilder courseCodeBuilder = new StringBuilder();

					for (int i = 0; i < size; i++) {
						if (i == (size - 1)) {
							courseCodeBuilder.append(selectedCourseCodes.get(i));
						} else {
							courseCodeBuilder.append(selectedCourseCodes.get(i)
									+ ",");
						}
					}
					
					GetUnitsTask getUnitsTask = new GetUnitsTask(ProfileCreateActivity.this);
					try {
						unitList = getUnitsTask.execute(courseCodeBuilder.toString()).get();
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (ExecutionException e) {
						e.printStackTrace();
					}
		            if (null != unitList) {
		            	Log.i("unitList", "Get related Units");
		            	unitOptions = new CharSequence[unitList.size()];
		            	for (int i=0; i< unitList.size(); i++) {
		            		unitOptions[i] = unitList.get(i).toString();
		            	}
		            	unitSelections =  new boolean[ unitOptions.length ];
		            	setupUnitsDialogBuilder();
						AlertDialog unitDialog = unitsDialogBuilder.create();
						unitDialog.show();
		            }
				}
			}
		});
	}
	
	private void addListenerOnSaveButton() {
		saveButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(!inputDataMissing()) {
					String studentIdNumber = WidgetUtil.getInput(studentId);
					if (!WidgetUtil.isInteger(studentIdNumber)) {
						DialogUtil.showAlertDialog(
								errorDialogBuilder, ProfileCreateActivity.this,
								TITLE_ERROR_SAVE_DIALOG_ERROR,
								MESSAGE_STUDENT_ID_NOT_INTEGER_ERROR);
					} else {
						Student existStudent = null;
						StudentIdVerificationTask studentIdVerificationTask = new StudentIdVerificationTask(ProfileCreateActivity.this);
						try {
							existStudent = studentIdVerificationTask.execute(studentId.getText().toString().trim()).get();
						} catch (InterruptedException e) {
							e.printStackTrace();
						} catch (ExecutionException e) {
							e.printStackTrace();
						}
						if (null != existStudent) {
							DialogUtil.showAlertDialog(
									errorDialogBuilder, ProfileCreateActivity.this,
									TITLE_ERROR_SAVE_DIALOG_ERROR,
									MESSAGE_STUDENT_ID_EXISTS_ERROR);
						} else {
							CreateProfileTask createProfileTask = new CreateProfileTask(ProfileCreateActivity.this);
							Profile profile = new Profile();
							Student newStudent = new Student();
							newStudent.setStudentId(Integer.valueOf(studentIdNumber));
							newStudent.setName(WidgetUtil.getInput(name));
							newStudent.setNickname(WidgetUtil.getInput(nickname));
							newStudent.setNativeLanguage(WidgetUtil.getInput(nativeLanguage));
							newStudent.setFavouriteFood(WidgetUtil.getInput(favouriteFood));
							newStudent.setSuburb(WidgetUtil.getInput(suburb));
							newStudent.setLatitude(DEFAULT_LATITUDE);
							newStudent.setLongitude(DEFAULT_LONGITUDE);
							profile.setStudent(newStudent);
							profile.setSelectedCourseCodes(selectedCourseCodes);
							profile.setSelectedUnitCodes(selectedUnitCodes);
							profile.setUserId(userId);
							try {
								if(createProfileTask.execute(profile).get()) {
									Intent intent = new Intent(ProfileCreateActivity.this, DashboardDesignActivity.class);
									intent.putExtra("studentId", newStudent.getStudentId());
								    startActivity(intent);
								    finish();
								}
							} catch (InterruptedException e) {
								e.printStackTrace();
								DialogUtil.showAlertDialog(
										errorDialogBuilder, ProfileCreateActivity.this,
										TITLE_ERROR_SAVE_DIALOG_ERROR,
										MESSAGE_GENERAL_SAVE_ERROR);
							} catch (ExecutionException e) {
								e.printStackTrace();
								DialogUtil.showAlertDialog(
										errorDialogBuilder, ProfileCreateActivity.this,
										TITLE_ERROR_SAVE_DIALOG_ERROR,
										MESSAGE_GENERAL_SAVE_ERROR);
							}
						}
					}
				}
			}

			private boolean inputDataMissing() {
				if (WidgetUtil.haveEmptyInput(studentId, name)
						|| selectedCourseCodes.size() == 0
						|| selectedUnitCodes.size() == 0) {
					DialogUtil.showAlertDialog(
							errorDialogBuilder, ProfileCreateActivity.this,
							TITLE_ERROR_SAVE_DIALOG_ERROR,
							MESSAGE_EMPTY_ERROR_SAVE_DIALOG_ERROR);
					return true;
				}
				return false;
			}
		});
	}
    
    
	private void setupUnitsDialogBuilder() {
		unitsDialogBuilder = new AlertDialog.Builder(this,R.style.CoursesDialog);
		unitsDialogBuilder.setTitle("Choose units");
		unitsDialogBuilder.setMultiChoiceItems(unitOptions, unitSelections, new DialogSelectionClickHandler() );
		unitsDialogBuilder.setPositiveButton("OK", new UnitDialogButtonClickHandler());
	}
	
	private class UnitDialogButtonClickHandler implements DialogInterface.OnClickListener
	{
		private static final String NO_UNIT_SELECTED = "No unit selected";

		public void onClick( DialogInterface dialog, int clicked )
		{
			switch( clicked )
			{
				case DialogInterface.BUTTON_POSITIVE:
					setSelectedUnits();
					if (selectedUnitCodes.size() == 0) {
						selectedUnitText.setText(NO_UNIT_SELECTED);
					}
					break;
			}
		}
		
		private void setSelectedUnits() {
			StringBuilder text = new StringBuilder();
			selectedUnitCodes = new ArrayList<String>();
			for( int i = 0; i < unitOptions.length; i++ ){
				Log.i( "selected units: ", unitOptions[ i ] + " selected: " + unitSelections[i] );
				if (unitSelections[i]) {
					Unit unit = unitList.get(i);
					text.append(unit.getUnitCode() + " -- " + unit.getUnitName() + "\n");
					selectedUnitCodes.add(unit.getUnitCode());
				}
				selectedUnitText.setText(text);
			}
		}
	}
    
	private class DialogSelectionClickHandler implements DialogInterface.OnMultiChoiceClickListener
	{
		public void onClick( DialogInterface dialog, int clicked, boolean selected )
		{
			//Log.i( "ME", courseOptions[ clicked ] + " selected: " + selected );
		}
	}
	private class DialogButtonClickHandler implements DialogInterface.OnClickListener
	{
		private static final String NO_COURSE_SELECTED = "No course selected";

		public void onClick( DialogInterface dialog, int clicked )
		{
			switch( clicked )
			{
				case DialogInterface.BUTTON_POSITIVE:
					setSelectedCourses();
					if (selectedCourseCodes.size() == 0) {
						selectedCourseText.setText(NO_COURSE_SELECTED);
					}
					break;
			}
		}
	}
	
	protected void setSelectedCourses(){
		StringBuilder text = new StringBuilder();
		selectedCourseCodes = new ArrayList<String>();
		for( int i = 0; i < courseOptions.length; i++ ){
			Log.i( "selected course: ", courseOptions[ i ] + " selected: " + courseSelections[i] );
			if (courseSelections[i]) {
				String courseCode = courseList.get(i).getCourseCode();
				text.append(courseCode + " -- " + courseList.get(i).getCourseName() + "\n");
				selectedCourseCodes.add(courseCode);
			}
			selectedCourseText.setText(text);
		}
	}

}
