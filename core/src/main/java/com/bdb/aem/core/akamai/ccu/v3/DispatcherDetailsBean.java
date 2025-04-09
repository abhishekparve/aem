package com.bdb.aem.core.akamai.ccu.v3;

/**
 * The Class DispatcherDetailsBean.
 */
public class DispatcherDetailsBean {

	private String dispatcherDomain;
	private String user;
	private String password;
	public String getDispatcherDomain() {
		return dispatcherDomain;
	}
	public void setDispatcherDomain(String dispatcherDomain) {
		this.dispatcherDomain = dispatcherDomain;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "DispatcherDetailsBean [dispatcherDomain=" + dispatcherDomain + ", user=" + user + ", password="
				+ password + "]";
	}
	
	
}
