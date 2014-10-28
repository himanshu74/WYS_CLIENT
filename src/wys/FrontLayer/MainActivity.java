package wys.FrontLayer;

import wys.Base.BaseActivity;
import wys.CustomInterfaces.OnSignUpListener;
import wys.Dialogs.SignInDialog;
import wys.Dialogs.SignUpDialog;

import com.example.wys_client.R;
import com.example.wys_client.R.layout;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends BaseActivity implements OnSignUpListener {

	// Private Variables and Views
	private Button btnSignup;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		initControls();
	}

	private void initControls() {
      btnSignup = (Button)findViewById(R.id.btn_sign_up);
		/*if (GetWYSPreferences().is_firstTimeUse() == false) 
		{
             btn_signup.setText("Sign in");
		}*/
	}

//	@Override
//	protected void onResume() {
//		 if(GetWYSPreferences().is_firstTimeUse() == false){
//	    	 btn_signup.setText("Sign in");
//	    }
//	    
//	super.onResume();
//	}
	
	public void moveInsideWys(final View v) {

		if (getWYSPreferences().isFirstTimeUse()) {

			SignUpDialog signUpDialog = new SignUpDialog(MainActivity.this,
					getWYSPreferences(),MainActivity.this);
			signUpDialog.setCanceledOnTouchOutside(false);
			signUpDialog.show();
		} else if (!getWYSPreferences().isFirstTimeUse()) {
			SignInDialog signInDialog = new SignInDialog(MainActivity.this);
			signInDialog.setCanceledOnTouchOutside(false);
			signInDialog.show();
		}

	}

	@Override
	public void onSignUpSuccess() {
		
		btnSignup.setText("Sign in");
	}

	@Override
	public void onSignUpFail() {
		// TODO Auto-generated method stub
		
	}

}
