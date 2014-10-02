package wys.Dialogs;

import com.example.wys_client.R;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class SignInDialog extends Dialog implements OnClickListener {

	private EditText et_username;
	private EditText et_password;
	private Button btn_signin;

	public SignInDialog(Context context) {
		super(context);
		getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));
		setContentView(R.layout.sign_in);
		InitControls();
	}

	private void InitControls() {
		et_username = (EditText) findViewById(R.id.et_username);
		et_password = (EditText) findViewById(R.id.et_password);
		btn_signin = (Button) findViewById(R.id.btn_signin);
		btn_signin.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {

	}

}
