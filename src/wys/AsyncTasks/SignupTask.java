package wys.AsyncTasks;

import java.util.List;

import wys.Business.BaseBusiness;
import wys.Business.UserBo;
import wys.CustomInterfaces.OnCheckUserListener;
import wys.CustomInterfaces.OnSignUpListener;
import wys.Dialogs.SignUpDialog;
import wys.Helpers.WebRequest;
import wysApi.WysApi;
import android.os.AsyncTask;
import android.widget.Toast;

public class SignupTask extends BaseAsyncTaskManager {

	private static final int SUCCESS = 0;
	private static final int ERROR = 1;
	private static final int AVAIL =0;
	private static final int NOT_AVAIL =1;
    private OnSignUpListener _onSignUpListener;
    private OnCheckUserListener _oncheCheckUserListener;
	
	
	private UserBo _user;
	

	public SignupTask(BaseBusiness user, OnSignUpListener onSignUpListener) {
		this._user = (UserBo) user;
		this._onSignUpListener = onSignUpListener;
	}
	
	public SignupTask(OnCheckUserListener onCheckUserListener)
	{
		this._oncheCheckUserListener = onCheckUserListener;
	}

	@Override
	public void ExecuteSignupTask()
	{
		new PostAsync().execute(_user);
		
	}
	
	@Override
	public void ExcecuteCheckUsername(String username)
	{
			 new CheckUserAsync().execute(username);
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

	
	public static int CheckUserAvail(String username)
	{
		List<BaseBusiness> users = new WysApi().GetUserByUsername(username);
		int result = users.get(0).getIsUsernameAvail();
		return result;
	}
	
	
	
	private class CheckUserAsync extends AsyncTask<String, Void, Integer>
	{

		@Override
		protected Integer doInBackground(String... params) {
			List<BaseBusiness> users = new WysApi().GetUserByUsername(params[0]);
			int result = users.get(0).getIsUsernameAvail();
			return result;
		}
		
		@Override
		protected void onPostExecute(Integer result) {
			
			if(result == AVAIL)
			{
				if(_oncheCheckUserListener!=null)
					_oncheCheckUserListener.OnUserAvail();
				
			}
			else 
			{
				if(_oncheCheckUserListener !=null)
					_oncheCheckUserListener.OnUserNotAvail();
					
			}
			
			super.onPostExecute(result);
		}
		
	}
	
	
}
