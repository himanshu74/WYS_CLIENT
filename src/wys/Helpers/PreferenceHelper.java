package wys.Helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class PreferenceHelper {

	// DEFINED KEYS
	private static final String PREF_KEY = "wys_pref";
	private static final String IS_FIRST_TIME_USE = "firstTimeUse";

	// PRIVATE VARIABLES
	private Context context_;
	private boolean firstTimeUse_;

	public boolean isFirstTimeUse() {
		return firstTimeUse_;
	}

	public void set_firstTimeUse(boolean firstTimeUse_) {
		this.firstTimeUse_ = firstTimeUse_;
		updateIsFirstTimeUse(firstTimeUse_);
	}

	
	// Constructor
	public PreferenceHelper(Context context) {
		this.context_ = context;
		loadPreferences();
	}

	private void loadPreferences() {
		SharedPreferences wysPreferences = context_.getSharedPreferences(PREF_KEY,
				context_.MODE_PRIVATE);

		firstTimeUse_ = wysPreferences.getBoolean(IS_FIRST_TIME_USE, true);

	}

	private void updateIsFirstTimeUse(boolean value) {
		SharedPreferences wysPrefs = context_.getSharedPreferences(PREF_KEY,
				context_.MODE_PRIVATE);
		SharedPreferences.Editor editor = wysPrefs.edit();
		editor.putBoolean(IS_FIRST_TIME_USE, false);
		editor.commit();
	}

}
