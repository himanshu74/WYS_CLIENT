package wys.User;



import com.example.wys_client.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import wys.Base.BaseActivity;
import wys.FrontLayer.HomeActivity;
import wys.FrontLayer.MainActivity;
import wysApi.SessionManager;

public class UserHomeActivity extends BaseActivity  implements OnClickListener{
	
	private Button btn_logout;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_home);
		InitControls();
		
	}
	
	
	private void InitControls()
	{
		btn_logout = (Button)findViewById(R.id.btn_logout);
		btn_logout.setOnClickListener(this);
	}


	@Override
	public void onClick(View v) {
   if(v.getId() == btn_logout.getId())
   {
	   SessionManager.setUserBo(null);
	   Intent i = new Intent(UserHomeActivity.this, MainActivity.class);
	   startActivity(i);
   }

		
	}

}
