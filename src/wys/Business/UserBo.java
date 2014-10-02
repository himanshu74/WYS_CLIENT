package wys.Business;

public class UserBo extends BaseBusiness {

	// Properties
	
	private String _username;
	private String _password;
	private String _email;
	private int _domainId;
	
	
	public String get_username() {
		return _username;
	}
	public void set_username(String _username) {
		this._username = _username;
	}
	public String get_password() {
		return _password;
	}
	public void set_password(String _password) {
		this._password = _password;
	}
	public String get_email() {
		return _email;
	}
	public void set_email(String _email) {
		this._email = _email;
	}
	public int get_domainId() {
		return _domainId;
	}
	public void set_domainId(int _domainId) {
		this._domainId = _domainId;
	}
	public int get_roleId() {
		return _roleId;
	}
	public void set_roleId(int _roleId) {
		this._roleId = _roleId;
	}
	private int _roleId;
	
	
	
	
	
	
}
