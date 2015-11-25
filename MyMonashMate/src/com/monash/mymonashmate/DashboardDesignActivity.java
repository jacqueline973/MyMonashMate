package com.monash.mymonashmate;

import java.util.concurrent.ExecutionException;

import com.monash.mymonashmate.entity.Profile;
import com.monash.mymonashmate.task.GetProfileByStudentIdTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class DashboardDesignActivity extends Activity {
	
	private int studentId;
	private Profile profile = new Profile();
	private AlertDialog.Builder logoutDialogBuilder;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_layout);
        
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
        	studentId =  extras.getInt("studentId");
        	Log.i("studentId", String.valueOf(studentId));
        	try {
				profile = new GetProfileByStudentIdTask(DashboardDesignActivity.this).execute(String.valueOf(studentId)).get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
        }
        
         
        /**
         * Creating all buttons instances
         * */
        // Dashboard mate button
        Button btn_mate = (Button) findViewById(R.id.btn_mate);
         
        // Dashboard profile button
        Button btn_profile = (Button) findViewById(R.id.btn_profile);
         
        // Dashboard Permission button
        Button btn_permission = (Button) findViewById(R.id.btn_permission);
         
        /**
         * Handling all button click events
         * */
         
        // Listening to Mate button click
        btn_mate.setOnClickListener(new View.OnClickListener() {
             
            @Override
            public void onClick(View view) {
                // Launching News Feed Screen
                Intent i = new Intent(getApplicationContext(), SearchCriteriaActivity.class);
                i.putExtra("studentId", studentId);
                i.putExtra("profile", profile);
                startActivity(i);
                finish();
            }
        });
         
       // Listening Profile button click
        btn_profile.setOnClickListener(new View.OnClickListener() {
             
            @Override
            public void onClick(View view) {
                // Launching News Feed Screen
                Intent i = new Intent(getApplicationContext(), ProfileUpdateActivity.class);
                i.putExtra("studentId", studentId);
                i.putExtra("profile", profile);
                startActivity(i);
                finish();
            }
        });
         
        // Listening Permission button click
        btn_permission.setOnClickListener(new View.OnClickListener() {
             
            @Override
            public void onClick(View view) {
                // Launching News Feed Screen
                Intent i = new Intent(getApplicationContext(), PermissionActivity.class);
                i.putExtra("studentId", studentId);
                i.putExtra("profile", profile);
                startActivity(i);
                finish();
            }
        });
         
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dashboard_actionbar_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch (item.getItemId()) {
		case R.id.logout:
			setupConfirmLogoutDialog();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	public void setupConfirmLogoutDialog() {
		logoutDialogBuilder = new AlertDialog.Builder(this);
		logoutDialogBuilder.setTitle("Logout");
		logoutDialogBuilder.setMessage("Are you sure you want to logout?");
		logoutDialogBuilder.setNegativeButton("Cancel", null);
		logoutDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				Intent intent = new Intent(DashboardDesignActivity.this, MainActivity.class);
				startActivity(intent);
				finish();
				
			}});
		logoutDialogBuilder.create().show();
	}


}
