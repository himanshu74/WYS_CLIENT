package wys.Dialogs;

import com.example.wys_client.R;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

public class VerificationDialog extends Dialog {

	private Context _ctx;

	public VerificationDialog(Context context) {
		super(context);
		this._ctx = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));
		setContentView(R.layout.verification_code);

	}

}
