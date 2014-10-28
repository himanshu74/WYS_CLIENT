package wys.Business;

public class UserBO extends BaseBusiness {

	// Properties
	
	private String username_;
	private String password_;
	private String email_;
	private int domainId_;
	private int roleId_;
	
	
	
	public String getUsername() {
		return username_;
	}
	public void setUsername(String username_) {
		this.username_ = username_;
	}
	public String getPassword() {
		return password_;
	}
	public void setPassword(String password_) {
		this.password_ = password_;
	}
	public String getEmail() {
		return email_;
	}
	public void setEmail(String email_) {
		this.email_ = email_;
	}
	public int getDomainId() {
		return domainId_;
	}
	public void setDomainId(int domainId_) {
		this.domainId_ = domainId_;
	}
	public int getRoleId() {
		return roleId_;
	}
	public void setRoleId(int roleId_) {
		this.roleId_ = roleId_;
	}
}
