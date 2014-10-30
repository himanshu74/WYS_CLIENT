package wys.Dialogs;

import com.example.wys_client.R;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class ForgotPassword extends Dialog implements OnClickListener {

	private Button btn_forgotPass;
	private Context _ctx;
	private ImageView iv_back;

	public ForgotPassword(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this._ctx = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));
		setContentView(R.layout.forgot_pass);
		InitControls();
	}

	private void InitControls() {
		btn_forgotPass = (Button) findViewById(R.id.btn_send);
		btn_forgotPass.setOnClickListener(this);
		iv_back = (ImageView) findViewById(R.id.iv_backbtn);
		iv_back.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == btn_forgotPass.getId()) {

		} else if (v.getId() == iv_back.getId()) {
			ForgotPassword.this.dismiss();
			SignInDialog signInDialog = new SignInDialog(_ctx);
			signInDialog.setCanceledOnTouchOutside(false);
			signInDialog.show();
		}
	}
}
