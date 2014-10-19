package wys.Dialogs;

import wys.AsyncTasks.IAsyncTask;
import wys.AsyncTasks.SignupTask;
import wys.Business.UserBo;
import wys.CustomInterfaces.OnSignUpListener;
import wys.FrontLayer.UserVerification;
import wys.Helpers.PreferenceHelper;

import com.example.wys_client.R;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.sax.StartElementListener;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import wys.httpApi.*;

public class SignUpDialog extends Dialog implements
		android.view.View.OnClickListener, OnSignUpListener {

	private static final int SUCCESS = 0;
	private static final int ERROR = 1;
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

		OpenVerificationScreen();

		/*
		 * UserBo user = new UserBo(); String username =
		 * et_username.getText().toString(); String password =
		 * et_password.getText().toString(); String email =
		 * et_email.getText().toString(); String confirm =
		 * et_confirm.getText().toString();
		 * 
		 * if (CheckFields(username, password, email, confirm)) {
		 * user.set_username(et_username.getText().toString());
		 * user.set_password(et_password.getText().toString());
		 * user.set_email(et_email.getText().toString()); IAsyncTask asyncTask =
		 * new SignupTask(user, SignUpDialog.this);
		 * asyncTask.ExecuteSignupTask(); }
		 */
	}

	private void OpenVerificationScreen() {

		SignUpDialog.this.dismiss();
		/*
		 * Intent i = new Intent(this._ctx,UserVerification.class);
		 * _ctx.startActivity(i);
		 */

		VerificationDialog veriDialog = new VerificationDialog(_ctx);
		veriDialog.setCanceledOnTouchOutside(false);
		veriDialog.show();

	}

	private boolean CheckFields(String username, String pass, String email,
			String confirmPass) {
		if (email.isEmpty()) {

			et_email.setError("email cannot be empty");
			/*
			 * Toast.makeText(_ctx, "All fields are mandatory",
			 * Toast.LENGTH_SHORT) .show();
			 */
			return false;

		} else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email)
				.matches()) {
			et_email.setError("Please enter a valid email address");
			return false;
		}

		else if (username.isEmpty()) {
			et_username.setError("username cannot be empty");
			return false;

		}

		else if (pass.isEmpty()) {
			et_password.setError("password cannot be empty");
			return false;
		} else if (confirmPass.isEmpty()) {
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

	@Override
	public void OnSignUpSuccess() {
		/*
		 * if (_prefeHelper.is_firstTimeUse() == true) {
		 * _prefeHelper.set_firstTimeUse(false); }
		 */
		SignUpDialog.this.dismiss();
		if (_OnSignUpListener != null) {

		}
		Toast.makeText(
				_ctx,
				"Email with Verification Code has been sent to you, Please Verify your Account",
				Toast.LENGTH_SHORT).show();
	}

	@Override
	public void OnSignUpFail() {

		SignUpDialog.this.dismiss();
		Toast.makeText(_ctx, "Oops, Something went wrong, Try again",
				Toast.LENGTH_SHORT).show();
	}

}
