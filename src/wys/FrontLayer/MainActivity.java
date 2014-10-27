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

public class MainActivity extends BaseActivity implements OnClickListener {

	// Private Variables and Views
	private Button btn_signup,btn_SignIn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		InitControls();

	}

	private void InitControls() {
		btn_signup = (Button) findViewById(R.id.btn_sign_up);
		btn_SignIn = (Button)findViewById(R.id.btn_signin);
		btn_SignIn.setOnClickListener(MainActivity.this);

	}

	public void MoveInsideWys(final View v) {

		if (GetWYSPreferences().is_firstTimeUse()) {

			SignUpDialog signUpDialog = new SignUpDialog(MainActivity.this,
					GetWYSPreferences());
			signUpDialog.setCanceledOnTouchOutside(false);
			signUpDialog.show();
		} else if (!GetWYSPreferences().is_firstTimeUse()) {
			SignInDialog signInDialog = new SignInDialog(MainActivity.this);
			signInDialog.setCanceledOnTouchOutside(false);
			signInDialog.show();
		}

	}

	public void SignIn(final View v) {
       if(v.getId() == btn_SignIn.getId()){
    	   SignInDialog signInDialog = new SignInDialog(MainActivity.this);
    	   signInDialog.setCanceledOnTouchOutside(false);
    	   signInDialog.show();
       }
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == btn_SignIn.getId()){
	    	   SignInDialog signInDialog = new SignInDialog(MainActivity.this);
	    	   signInDialog.setCanceledOnTouchOutside(false);
	    	   signInDialog.show();
	       }
		
	}

}
