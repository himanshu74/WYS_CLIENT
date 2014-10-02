package wys.AsyncTasks;

import wys.Business.BaseBusiness;
import wys.Business.UserBo;
import wys.CustomInterfaces.OnSignUpListener;
import wys.Dialogs.SignUpDialog;
import wys.Helpers.WebRequest;
import wys.httpApi.WysApi;
import android.os.AsyncTask;
import android.widget.Toast;

public class SignupTask extends BaseAsyncTaskManager {

	private static final int SUCCESS = 0;
	private static final int ERROR = 1;
    private OnSignUpListener _onSignUpListener;
	
	
	private UserBo _user;
	

	public SignupTask(BaseBusiness user, OnSignUpListener onSignUpListener) {
		this._user = (UserBo) user;
		this._onSignUpListener = onSignUpListener;
	}

	@Override
	public void ExecuteSignupTask()
	{
		new PostAsync().execute(_user);
		
	}
	
	
	private class PostAsync extends AsyncTask<UserBo, Void, Integer> {

		@Override
		protected Integer doInBackground(UserBo... params) {
			int result = WysApi.PostSignUp(params[0]);
			return result;
		}

		@Override
		protected void onPostExecute(Integer result) {

			if (result == SUCCESS) {
				
				if(_onSignUpListener !=null)
				{
					_onSignUpListener.OnSignUpSuccess();
				}
				
			} else if(result == ERROR) {
				
				if(_onSignUpListener !=null)
				{
					_onSignUpListener.OnSignUpFail();
				}
				

			}

			super.onPostExecute(result);
		}

	}

}
