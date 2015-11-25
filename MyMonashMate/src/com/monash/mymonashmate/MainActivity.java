package com.monash.mymonashmate;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.monash.mymonashmate.client.UserAccessClient;
import com.monash.mymonashmate.entity.UserAccess;

public class MainActivity extends Activity {
	
	final Context context = this;
	private Button signIn;
	private EditText username;
	private EditText password;
	private TextView signUp;
	private AlertDialog.Builder emptyErrorBuilder;
	private AlertDialog.Builder authenticateErrorBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
                
		setupEmptyErrorBuilder();
		setupAuthenticateErrorBuilder();
		addListenerOnSignUp();
        addListenerOnSignIn();

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

	private void addListenerOnSignUp() {
		signUp = (TextView) findViewById(R.id.signUpLink);
		signUp.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
		signUp.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, SignUpActivity.class);
                startActivity(intent);
                finish();
			}
		});
	}

	private void setupEmptyErrorBuilder() {
		emptyErrorBuilder = new AlertDialog.Builder(this);
		emptyErrorBuilder.setTitle("Login Error");
		emptyErrorBuilder.setMessage("Please enter your Authcate username and password.");
		emptyErrorBuilder.setPositiveButton("OK", null);
	}
	
	private void setupAuthenticateErrorBuilder() {
		authenticateErrorBuilder = new AlertDialog.Builder(this);
		authenticateErrorBuilder.setTitle("Login Error");
		authenticateErrorBuilder.setMessage("Your username or passoword was incorrect.");
		authenticateErrorBuilder.setPositiveButton("OK", null);
	}
    
	private void addListenerOnSignIn() {
		signIn = (Button) findViewById(R.id.signInButton);
    	username = (EditText) findViewById(R.id.userName);
    	password = (EditText) findViewById(R.id.password);
		
		signIn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
		    	Editable usernameText = username.getText();
		    	Editable passwordText = password.getText();
				if (usernameOrPasswordEmpty(usernameText, passwordText)) {
					AlertDialog emptyErrorDialog = emptyErrorBuilder.create();
					emptyErrorDialog.show();
				} else {
					new LoginTask().execute(usernameText.toString().trim(), passwordText.toString().trim());
				}
			}
		});

	}
    
    private boolean usernameOrPasswordEmpty(Editable usernameText, Editable passwordText) {

    	if (usernameText.length() == 0 || passwordText.length() == 0) {
    		return true;
    	}
    	return false; 
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }
    
    private class LoginTask extends AsyncTask<String, Void, Void> {

    	private ProgressDialog authProgressDialog;
    	private boolean correctUser = false;
    	private UserAccess user;
        @Override
        protected void onPreExecute() {
            authProgressDialog = ProgressDialog.show(MainActivity.this, "", "Authenticating...", true, false);
        }

        @Override
        protected Void doInBackground(String... params) {
        	 	
        	correctUser = authenticate(params);// method that calls the API via REST
            return null;
        }

		private boolean authenticate(String... params) {
			try {
				UserAccessClient client = new UserAccessClient();
				user = client.getUserByName(params[0]);//fing user name in db
				if (null != user && decodePassword(user.getPassword()).equals(params[1])) {
					return true;
				}

			} catch (Exception e) {
				Log.e("MainActivity", e.getMessage(), e);
			}
			return false;
		}
		
		private String decodePassword(String password) {
			return new String( Base64.decode( password, Base64.DEFAULT ) );
		}

        @Override
        protected void onPostExecute(Void result) {
            authProgressDialog.dismiss();
            if (correctUser) {
            	Log.i("testing", "Correct user authenticated.");
				Intent intent = new Intent(context, DashboardDesignActivity.class);
				intent.putExtra("userId", user.getId());
				intent.putExtra("studentId", Integer.valueOf(user.getStudentId()));
                startActivity(intent);
                finish();
            } else {
            	Log.i("testing", "Incorrect user.");
				AlertDialog authenticateErrorDialog = authenticateErrorBuilder.create();
				authenticateErrorDialog.show();
            }
        }
              
    }

}
