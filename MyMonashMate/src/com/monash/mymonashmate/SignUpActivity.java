package com.monash.mymonashmate;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.monash.mymonashmate.client.UserAccessClient;
import com.monash.mymonashmate.entity.UserAccess;
import com.monash.mymonashmate.util.DialogUtil;
import com.monash.mymonashmate.util.SignUpUtil;

public class SignUpActivity extends Activity {
	
	private static final String TITLE_SIGN_UP_ERROR = "Sign Up Error";
	private static final String MESSAGE_MISSING_FIELD_ERROR = "Please fill all fields.";
	private static final String MESSAGE_PASSWORD_MATCH_ERROR = "Your password and confirmation password do not match.";
	private static final String MESSAGE_USERNAME_INVALID_ERROR = "Username only allow a-z, 0-9, underscore, hyphen and at least 4 characters and maximum length is 15.";
	private static final String MESSAGE_PASSWORD_INVALID_ERROR = "Password only allow a-z, 0-9, underscore, hyphen and at least 4 characters and maximum length is 15.";
	final Context context = this;
	private Button signUp;
	private EditText username;
	private EditText password;
	private EditText passwordConfirm;
	private AlertDialog.Builder errorDialogBuilder;
	private AlertDialog.Builder usernameExistingErrorBuilder;
	private ImageButton backButton;
	private UserAccessClient webServiceClient = new UserAccessClient();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowCustomEnabled(false);
		LayoutInflater li = LayoutInflater.from(this);
		View customView = li.inflate(R.layout.sign_up_actionbar_layout, null);
		actionBar.setCustomView(customView);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		setContentView(R.layout.activity_sign_up);
		
		setupUserNameExistingErrorBuilder();
		addListenerOnSignUp();
		addListenerOnBackButton();

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}
	
	private void addListenerOnBackButton() {
		backButton = (ImageButton) findViewById(R.id.sign_up_back_button);
		backButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
                finish();
			}
		});
	}
	
	
	private void setupUserNameExistingErrorBuilder() {
		usernameExistingErrorBuilder = new AlertDialog.Builder(this);
		usernameExistingErrorBuilder.setTitle(TITLE_SIGN_UP_ERROR);
		usernameExistingErrorBuilder.setMessage("Username already exists,Please choose another one.");
		usernameExistingErrorBuilder.setPositiveButton("OK", null);
	}
	
    
	private void addListenerOnSignUp() {
		signUp = (Button) findViewById(R.id.signUpButton);
		username = (EditText) findViewById(R.id.sign_up_username);
		password = (EditText) findViewById(R.id.sign_up_password);
		passwordConfirm = (EditText) findViewById(R.id.sign_up_re_passoword);

		signUp.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Editable usernameText = username.getText();
				Editable passwordText = password.getText();
				Editable passwordConfirmText = passwordConfirm.getText();
				if (SignUpUtil.missionFields(usernameText, passwordText,
						passwordConfirmText)) {
					DialogUtil.showAlertDialog(
							errorDialogBuilder, SignUpActivity.this,
							TITLE_SIGN_UP_ERROR,
							MESSAGE_MISSING_FIELD_ERROR);
				} else {
					String usernameString = usernameText.toString().trim();
					String passwordString = passwordText.toString().trim();
					String passwordConfirmString = passwordConfirmText
							.toString().trim();
					if (!SignUpUtil.passwordIsValid(passwordString)) {
						DialogUtil.showAlertDialog(
								errorDialogBuilder, SignUpActivity.this,
								TITLE_SIGN_UP_ERROR,
								MESSAGE_PASSWORD_INVALID_ERROR);
					}else{
						if (SignUpUtil.passwordNotMatch(passwordString, passwordConfirmString)) {
							DialogUtil.showAlertDialog(
									errorDialogBuilder, SignUpActivity.this,
									TITLE_SIGN_UP_ERROR,
									MESSAGE_PASSWORD_MATCH_ERROR);
						} else {
							if (SignUpUtil.usernameIsValid(usernameString)) {
								new UsernameVerficationTask().execute(usernameString,
										passwordString);
							}else{
								DialogUtil.showAlertDialog(
										errorDialogBuilder, SignUpActivity.this,
										TITLE_SIGN_UP_ERROR,
										MESSAGE_USERNAME_INVALID_ERROR);
							}
						}
					}

				}
			}
		});
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
			View rootView = inflater.inflate(R.layout.fragment_sign_up,
					container, false);
			return rootView;
		}
	}
	
    private class UsernameVerficationTask extends AsyncTask<String, Void, Void> {

    	private ProgressDialog progressDialog;
    	private boolean usernameExists = false;
    	private UserAccess existingUser;
    	private String username;
    	private String password;
    	
        @Override
        protected void onPreExecute() {
        	progressDialog = ProgressDialog.show(SignUpActivity.this, "", "Verify username...", true, false);
        }

        @Override
        protected Void doInBackground(String... params) {
        	username = params[0];
        	password = params[1];
        	usernameExists(username);
        	return null;

        }

		private void usernameExists(String username) {
			try {
				existingUser = webServiceClient.getUserByName(username);
				if (null != existingUser) {
					usernameExists = true;
				}

			} catch (Exception e) {
				e.printStackTrace();
				Log.e("SignUpActivity", e.getMessage(), e);
			}
		}
		
		private String encryptPassword(String plainText) {
			return Base64.encodeToString( plainText.getBytes(), Base64.DEFAULT );
		}

        @Override
        protected void onPostExecute(Void result) {
        	progressDialog.dismiss();
            if (usernameExists) {
				AlertDialog usernameExistingErrorDialog = usernameExistingErrorBuilder.create();
				usernameExistingErrorDialog.show();
            } else {
            	new CreateUserTask().execute(username, encryptPassword(password));
            }
        }
              
    }

	private class CreateUserTask extends AsyncTask<String, Void, Void> {

		private ProgressDialog progressDialog;
		private UserAccess newUser;
		private UserAccess savedUser;
		
        @Override
        protected void onPreExecute() {
        	progressDialog = ProgressDialog.show(SignUpActivity.this, "", "Creating user...", true, false);
        }
		
		@Override
		protected Void doInBackground(String... params) {
    		newUser = new UserAccess();
    		newUser.setName(params[0]);
    		newUser.setPassword(params[1]);
    		savedUser = webServiceClient.createNewUser(newUser);
			return null;
		}
		
        @Override
        protected void onPostExecute(Void result) {
        	progressDialog.dismiss();
            if (null != savedUser) {
				Intent intent = new Intent(context, ProfileCreateActivity.class);
				intent.putExtra("userId", String.valueOf(savedUser.getId()));
                startActivity(intent);
                finish();
            } 
        }
		
	}

}
