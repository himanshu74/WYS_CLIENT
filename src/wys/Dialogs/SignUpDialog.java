package wys.Dialogs;

import wys.AsyncTasks.IAsyncTask;
import wys.AsyncTasks.SignupTask;
import wys.Business.UserBO;
import wys.CustomInterfaces.OnCheckUserListener;
import wys.CustomInterfaces.OnSignUpListener;
import wys.FrontLayer.UserVerification;
import wys.Helpers.PreferenceHelper;
import wys.Http.*;

import com.example.wys_client.R;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.sax.StartElementListener;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpDialog extends Dialog implements
		android.view.View.OnClickListener, OnSignUpListener,
		OnCheckUserListener {

	private static final int SUCCESS = 0;
	private static final int ERROR = 1;
	private static final int AVAIL = 0;
	private static final int NOTAVAIL = 1;
	private boolean IsUsernameAvail = false;
	private boolean IsUsernameValidated = false;

	private PreferenceHelper _prefeHelper;
	private Context _ctx;
	private Button btn_signup;
	private EditText et_username, et_password, et_email, et_confirm;

	private OnSignUpListener _OnSignUpListener;

	public SignUpDialog(Context context, PreferenceHelper preferenceHelper,
			OnSignUpListener onListener) {
		super(context);
		this._ctx = context;
		this._prefeHelper = preferenceHelper;
		this._OnSignUpListener = onListener;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));
		setContentView(R.layout.signup);
		InitControls();
	}

	private void InitControls() {
		btn_signup = (Button) findViewById(R.id.btn_signup);
		btn_signup.setOnClickListener(this);
		et_username = (EditText) findViewById(R.id.et_username);

		et_password = (EditText) findViewById(R.id.et_password);
		et_email = (EditText) findViewById(R.id.et_email);
		et_confirm = (EditText) findViewById(R.id.et_confirm);

	}

	@Override
	public void onClick(View v) {

		// OpenVerificationScreen();

		validateAndSignUpUser();

	}

	private void OpenVerificationScreen() {

		SignUpDialog.this.dismiss();

		VerificationDialog veriDialog = new VerificationDialog(_ctx);
		veriDialog.setCanceledOnTouchOutside(false);
		veriDialog.show();

	}

	private void validateAndSignUpUser() {

		String username = et_username.getText().toString();
		String password = et_password.getText().toString();
		String email = et_email.getText().toString();
		String confirm = et_confirm.getText().toString();

		/**
		 * 
		 */
		boolean isValidated = validateFields(username, password, email, confirm);
		if (isValidated) {
			validateUsernameAvail(username);
		}

	}

	private boolean validateFields(String username, String pass, String email,
			String confirmPass) {
		if (username.isEmpty() && pass.isEmpty() && email.isEmpty()
				&& confirmPass.isEmpty()) {
			et_username.setError("username cannot be empty");
			et_email.setError("email cannot be empty");
			et_password.setError("password cannot be empty");
			et_confirm.setError("please enter confirm password");

			return false;
		}

		else if (username.isEmpty() || username.length() <= 0) {

			et_username.setError("username cannot be empty");
			return false;

		}

		else if (email.isEmpty() || email.length() <= 0) {
			et_email.setError("email cannot be empty");
			return false;

		} else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email)
				.matches()) {
			et_email.setError("Please enter a valid email address");
			return false;
		}

		else if (pass.isEmpty() || pass.length() <= 0) {
			et_password.setError("password cannot be empty");
			return false;
		} else if (confirmPass.isEmpty() || confirmPass.length() <= 0) {
			et_confirm.setError("please enter confirm password");
			return false;
		} else {
			if (!pass.equals(confirmPass)) {
				et_confirm.setError("Passwords dont match, please re enter");
				return false;
			}

		}

		return true;

	}

	private void validateUsernameAvail(String username) {
		IAsyncTask asyncTask = new SignupTask(SignUpDialog.this);

		asyncTask.ExcecuteCheckUsername(username);

		// return SignupTask.CheckUserAvail(username);
	}

	private void saveUser(String username, String pass, String email) {
		UserBO user = new UserBO();
		user.setUsername(username);
		user.setPassword(pass);
		user.setEmail(email);
		IAsyncTask asyncTask = new SignupTask(user, SignUpDialog.this);
		asyncTask.ExecuteSignupTask();
	}

	@Override
	public void onUserAvail() {
		String username = et_username.getText().toString();
		saveUser(et_username.getText().toString(), et_password.getText()
				.toString(), et_email.getText().toString());
	}

	@Override
	public void onUserNotAvail() {

		et_username.setError("Sorry, Username is not available");

	}

	@Override
	public void onSignUpSuccess() {
		/*
		 * if (_prefeHelper.is_firstTimeUse() == true) {
		 * _prefeHelper.set_firstTimeUse(false); }
		 */
		SignUpDialog.this.dismiss();
		
		Toast.makeText(
				_ctx,
				"Email with Verification Code has been sent to you, Please Verify your Account",
				Toast.LENGTH_LONG).show();
	}

	@Override
	public void onSignUpFail() {

		SignUpDialog.this.dismiss();
		Toast.makeText(_ctx, "Oops, Something went wrong, Try again",
				Toast.LENGTH_SHORT).show();
	}

}
