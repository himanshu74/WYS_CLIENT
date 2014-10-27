package wys.AsyncTasks;

import java.util.List;

import wys.Business.BaseBusiness;
import wys.Business.UserBo;
import wys.CustomInterfaces.OnVerifyingUser;
import wysApi.SessionManager;
import wysApi.WysApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class VerifyUserTask extends BaseAsyncTaskManager {

	private String CLASS_TAG = VerifyUserTask.class.getSimpleName();
	private OnVerifyingUser _onVerifyingUser;

	private Context _ctx;
	private ProgressDialog progressDialog = null;

	public VerifyUserTask(OnVerifyingUser OnVerifyingUser, Context context) {
		_onVerifyingUser = OnVerifyingUser;
		this._ctx = context;

	}

	public void ExecuteVerifyUser(String code) {
		UserBo user = (UserBo) SessionManager.getUserBo();
		user.setVerificationCode(code);
		new VerifyAsync().execute(user);
	}

	public void ExecuteResendCodeAsync() {
		if (SessionManager.getUserBo() != null) {
			UserBo user = (UserBo) SessionManager.getUserBo();
			new ResendCodeAsync().execute(user);
		}
	}

	private class VerifyAsync extends AsyncTask<UserBo, Void, Integer> {
		@Override
		protected Integer doInBackground(UserBo... params) {
			int result = -1;
			List<BaseBusiness> users = new WysApi().DoVerifyUser(
					params[0].get_username(), params[0].getVerificationCode());
			if (users != null) {
				result = users.get(0).getStatus();
			}

			return result;
		}

		@Override
		protected void onPostExecute(Integer result) {

			if (result == VERIFIED) {
				if (_onVerifyingUser != null) {
					_onVerifyingUser.OnSuccessFulVerification();
				}

			}

			else if (result == NOT_VERIFIED) {
				if (_onVerifyingUser != null)

				{
					_onVerifyingUser.OnVerificationFail();
				}
			} else {

			}
			super.onPostExecute(result);
		}
	}

	private class ResendCodeAsync extends AsyncTask<UserBo, Void, Integer> {
		@Override
		protected Integer doInBackground(UserBo... params) {

			int result = -1;
			String username = params[0].get_username();
			String email = params[0].get_email();
			List<BaseBusiness> users = new WysApi().DoResendVerificationCode(
					username, email);
			result = users.get(0).getStatus();
			return result;
		}

		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(_ctx);
			progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			// Set the dialog title to 'Loading...'
			// progressDialog.setTitle("Please wait..");
			progressDialog.setMessage("Logging in...");
			// Set the dialog message to 'Loading application View, please
			// wait...'
			// progressDialog.setMessage("Loading application View, please wait...");
			// This dialog can't be canceled by pressing the back key
			progressDialog.setCancelable(false);
			// This dialog isn't indeterminate
			progressDialog.setIndeterminate(false);

			// Display the progress dialog
			progressDialog.show();
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(Integer result) {

			progressDialog.dismiss();
			if (result == SUCCESS) {
				if (_onVerifyingUser != null) {
					_onVerifyingUser.OnResendSucces();
				}
			} else if (result == ERROR) {
				_onVerifyingUser.OnResendFail();
			}

			else {
				Log.e(CLASS_TAG, "There is error in wysApi call");
			}

			super.onPostExecute(result);
		}
	}

}
