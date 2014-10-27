package wysApi;

public class UrlManager {

	
	public static final String FETCH_CHECK_USERNAME_URL ="users/check/?username=%s";
	public static final String FETCH_SIGNUP_URL = "users/create";
	public static final String FETCH_VERIFY_USER_URL = "account/verify/%s/%s";
	public static final String FETCH_SIGNIN_URL = "account/login/?username=%s&password=%s";
	public static final String FETCH_RESEND_CODE_URL ="users/request/code/?username=%s&email=%s";
}
