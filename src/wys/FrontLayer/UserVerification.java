package wys.FrontLayer;

import com.example.wys_client.R;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.ViewGroup;
import wys.Base.BaseActivity;

public class UserVerification extends BaseActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.verification_code);
		getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));

	}
	
	
	private void IntiControls()
	{
		
	}
	

}
