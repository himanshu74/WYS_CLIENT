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
	private Button btn_signup;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		InitControls();

	}

	private void InitControls() {
      btn_signup = (Button)findViewById(R.id.btn_sign_up);
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
	
	public void MoveInsideWys(final View v) {

		if (GetWYSPreferences().is_firstTimeUse()) {

			SignUpDialog signUpDialog = new SignUpDialog(MainActivity.this,
					GetWYSPreferences(),MainActivity.this);
			signUpDialog.setCanceledOnTouchOutside(false);
			signUpDialog.show();
		} else if (!GetWYSPreferences().is_firstTimeUse()) {
			SignInDialog signInDialog = new SignInDialog(MainActivity.this);
			signInDialog.setCanceledOnTouchOutside(false);
			signInDialog.show();
		}

	}

	@Override
	public void OnSignUpSuccess() {
		
		btn_signup.setText("Sign in");
	}

	@Override
	public void OnSignUpFail() {
		// TODO Auto-generated method stub
		
	}

}
