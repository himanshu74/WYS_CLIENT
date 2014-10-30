package wys.AsyncTasks;

import java.util.ArrayList;
import java.util.List;

import wys.Business.BaseBusiness;
import wys.Business.UserBo;
import wys.CustomInterfaces.OnSIgnInListener;
import wysApi.SessionManager;
import wysApi.WysApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

public class SignInTask extends BaseAsyncTaskManager {

	private OnSIgnInListener _onSignInListener;
	private UserBo _user;
	private boolean _isVerified = false;
	private int _roleID = -1;
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
			
			new SignInAsync().execute(this._user);
		}

	}

	private class SignInAsync extends AsyncTask<UserBo, Void, Integer> {
		@Override
		protected Integer doInBackground(UserBo... params) {

			int result = -1;
			ArrayList<BaseBusiness> users = new WysApi().DoSignIn(
					params[0].get_username(), params[0].get_password());

			if (users != null && users.size() > 0) {
				result = SUCCESS;
				UserBo user = (UserBo) users.get(0);
				if (user.get_isVerified() == VERIFIED) {
					_isVerified = true;
					_roleID = user.get_roleId();
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
			progressDialog.setMessage("Logging in...");
			progressDialog.setCancelable(false);
			progressDialog.setIndeterminate(false);
			progressDialog.show();

			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(Integer result) {
			progressDialog.dismiss();

			/*
			 * if (!_isVerified && isDirectSIgnin) { if (_onSignInListener !=
			 * null) { _onSignInListener.OnStillNotVerified(); } } else { if
			 * (!_isVerified && result == ERROR && isDirectSIgnin) {
			 * _onSignInListener.OnStillNotVerified(); } else if (isDirectSIgnin
			 * && result == SUCCESS) {
			 * 
			 * if (_onSignInListener != null) {
			 * _onSignInListener.OnSignInSuccess(); } } else if (isDirectSIgnin
			 * && result == ERROR) { if (_onSignInListener != null) {
			 * _onSignInListener.OnSignInFail(); }
			 * 
			 * } else if (!isDirectSIgnin && result == SUCCESS) { if
			 * (_onSignInListener != null) {
			 * _onSignInListener.OnSignInSuccess(); } } else if (!isDirectSIgnin
			 * && result == ERROR) { if (_onSignInListener != null) {
			 * _onSignInListener.OnSignInFail(); } } }
			 */

			if (result == SUCCESS) {
				if (_isVerified) {
					if (_roleID == USER_ROLE_ID && _onSignInListener != null) {
						_onSignInListener.OnUserSignIN();
					} else if (_roleID == ORG_ROLE_ID
							&& _onSignInListener != null) {
						_onSignInListener.OnOrgSignIN();
					}
				} else {
					if (_onSignInListener != null) {
						_onSignInListener.OnStillNotVerified();
					}
				}
			} else if (result == ERROR) {
				if (_onSignInListener != null) {
					_onSignInListener.OnSignInFail();
				}
			}
			super.onPostExecute(result);
		}

	}

}
