package wys.AsyncTasks;

import java.util.List;

import wys.Business.BaseBusiness;
import wys.Business.UserBo;
import wys.CustomInterfaces.OnSIgnInListener;
import wysApi.SessionManager;
import wysApi.WysApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class SignInTask extends BaseAsyncTaskManager {

	private OnSIgnInListener _onSignInListener;
	private UserBo _user;
	private boolean isDirectSIgnin = false;
	private boolean _isVerified = false;
	private ProgressDialog progressDialog;
	private Context _ctx;

	public SignInTask(OnSIgnInListener onSIgnInListener, Context context) {
		this._onSignInListener = onSIgnInListener;
		this._ctx = context;
	}

	public SignInTask(OnSIgnInListener onSIgnInListener, UserBo user,
			Context ctx) {
		this._onSignInListener = onSIgnInListener;
		this._user = user;
		this._ctx = ctx;
	}

	public void ExecuteSignIn() {
		if (SessionManager.getUserBo() != null) {
			UserBo user = (UserBo) SessionManager.getUserBo();
			new SignInAsync().execute(user);
		} else {
			isDirectSIgnin = true;
			new SignInAsync().execute(this._user);
		}

	}

	private class SignInAsync extends AsyncTask<UserBo, Void, Integer> {
		@Override
		protected Integer doInBackground(UserBo... params) {

			int result = -1;
			List<BaseBusiness> users = new WysApi().DoSignIn(
					params[0].get_username(), params[0].get_password());

			if (users != null && users.size() > 0) {

				UserBo user = (UserBo) users.get(0);
				if (user.get_isVerified() == VERIFIED) {
					_isVerified = true;
					result = SUCCESS;
				} else {
					result = ERROR;
				}

			} else {
				result = ERROR;
			}

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

			if (!_isVerified && isDirectSIgnin) {
				if (_onSignInListener != null) {
					_onSignInListener.OnStillNotVerified();
				}
			} else {
				if (!_isVerified && result == ERROR && isDirectSIgnin) {
					_onSignInListener.OnStillNotVerified();
				} else if (isDirectSIgnin && result == SUCCESS) {

					if (_onSignInListener != null) {
						_onSignInListener.OnSignInSuccess();
					}
				} else if (isDirectSIgnin && result == ERROR) {
					if (_onSignInListener != null) {
						_onSignInListener.OnSignInFail();
					}

				} else if (!isDirectSIgnin && result == SUCCESS) {
					if (_onSignInListener != null) {
						_onSignInListener.OnSignInSuccess();
					}
				} else if (!isDirectSIgnin && result == ERROR) {
					if (_onSignInListener != null) {
						_onSignInListener.OnSignInFail();
					}
				}
			}

			super.onPostExecute(result);
		}

	}

}
