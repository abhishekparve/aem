package com.bdb.aem.core.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class CustomerLoginResponseBean.
 */
public class CustomerLoginResponseBean {

	/** The cart ID. */
	@SerializedName("cartID")
	@Expose
	private String cartID;

	/** The first name. */
	@SerializedName("firstName")
	@Expose
	private String firstName;
	
	/** The last name. */
	@SerializedName("lastName")
	@Expose
	private String lastName;

	/** The uid. */
	@SerializedName("uid")
	@Expose
	private String uid;

	/** The hybris token. */
	@SerializedName("hybrisToken")
	@Expose
	private String hybrisToken;

	/** The sso token. */
	@SerializedName("ssoToken")
	@Expose
	private String ssoToken;

	/** The account type. */
	@SerializedName("accountType")
	@Expose
	private String accountType;
	
	/** The hybris refresh token. */
	@SerializedName("hybrisRefreshToken")
	@Expose
	private String hybrisRefreshToken;
	
	/** The sso refresh token. */
	@SerializedName("ssoRefreshToken")
	@Expose
	private String ssoRefreshToken;

	/** The account role. */
	@SerializedName("accountRole")
	@Expose
	private String accountRole;

	/** The grant enabled. */
	@SerializedName("grantEnabled")
	@Expose
	private boolean grantEnabled;
	
	/** The is smart cart user. */
	@SerializedName("isSmartCartUser")
	@Expose
	private boolean isSmartCartUser;
	
	/** The is credit card enabled for site. */
	@SerializedName("isCreditCardEnabledForSite")
	@Expose
	private boolean isCreditCardEnabledForSite;

	/** The is Rewards Enabled. */
	@SerializedName("rewardsEnabled")
	@Expose
	private boolean rewardsEnabled;

	/** The pending order count. */
	@SerializedName("pendingOrderCount")
	@Expose
	private String pendingOrderCount;

	/** The unread messages count. */
	@SerializedName("unreadMessagesCount")
	@Expose
	private String unreadMessagesCount;

	/** The expires in. */
	@SerializedName("expiresIn")
	@Expose
	private String expiresIn;
	
	/** The area of interest. */
	@SerializedName("areaOfInterest")
	@Expose
	private List<String> areaOfInterest = new ArrayList<>();

	/** The is distributor. */
	@SerializedName("distributor")
	@Expose
	private boolean distributor;
	
	/** The company name */
	@SerializedName("company")
	@Expose
	private String company;
	
	/** The country name. */
	@SerializedName("country")
	@Expose
	private String country;
	
	/** The industry */
	@SerializedName("userIndustry")
	@Expose
	private List<String> userIndustry = new ArrayList<>();
	
	/** The lead ID. */
	@SerializedName("leadId")
	@Expose
	private String leadId;
	
	/** The role */
	@SerializedName("userRole")
	@Expose
	private List<String> userRole = new ArrayList<>();
	
	/** The is active. */
	@SerializedName("active")
	@Expose
	private boolean active;
	

	/**
	 * Gets the cart ID.
	 *
	 * @return the cart ID
	 */
	public String getCartID() {
		return getIfNotNull(cartID);
	}

	/**
	 * Sets the cart ID.
	 *
	 * @param cartID the new cart ID
	 */
	public void setCartID(String cartID) {
		this.cartID = cartID;
	}

	/**
	 * Gets the first name.
	 *
	 * @return the first name
	 */
	public String getFirstName() {
		return getIfNotNull(firstName);
	}

	/**
	 * Sets the first name.
	 *
	 * @param firstName the new first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	

	/**
	 * @return the company
	 */
	public String getCompany() {
		return company;
	}

	/**
	 * @param company the company to set
	 */
	public void setCompany(String company) {
		this.company = company;
	}

	/**
	 * Gets the last name.
	 *
	 * @return the last name
	 */
	public String getLastName() {
		return getIfNotNull(lastName);
	}

	/**
	 * Sets the last name.
	 *
	 * @param lastName the new last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Gets the uid.
	 *
	 * @return the uid
	 */
	public String getUid() {
		return getIfNotNull(uid);
	}

	/**
	 * Sets the uid.
	 *
	 * @param uid the new uid
	 */
	public void setUid(String uid) {
		this.uid = uid;
	}

	/**
	 * Gets the hybris token.
	 *
	 * @return the hybris token
	 */
	public String getHybrisToken() {
		return getIfNotNull(hybrisToken);
	}

	/**
	 * Sets the hybris token.
	 *
	 * @param hybrisToken the new hybris token
	 */
	public void setHybrisToken(String hybrisToken) {
		this.hybrisToken = hybrisToken;
	}

	/**
	 * Gets the sso token.
	 *
	 * @return the sso token
	 */
	public String getSsoToken() {
		return getIfNotNull(ssoToken);
	}

	/**
	 * Sets the sso token.
	 *
	 * @param ssoToken the new sso token
	 */
	public void setSsoToken(String ssoToken) {
		this.ssoToken = ssoToken;
	}

	/**
	 * Gets the account type.
	 *
	 * @return the account type
	 */
	public String getAccountType() {
		return getIfNotNull(accountType);
	}

	/**
	 * Sets the account type.
	 *
	 * @param accountType the new account type
	 */
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	/**
	 * Gets the account role.
	 *
	 * @return the account role
	 */
	public String getAccountRole() {
		return getIfNotNull(accountRole);
	}

	/**
	 * Sets the account role.
	 *
	 * @param accountRole the new account role
	 */
	public void setAccountRole(String accountRole) {
		this.accountRole = accountRole;
	}

	/**
	 * Gets the grant enabled.
	 *
	 * @return the grant enabled
	 */
	public String getGrantEnabled() {
		return String.valueOf(grantEnabled);
	}

	/**
	 * Sets the grant enabled.
	 *
	 * @param grantEnabled the new grant enabled
	 */
	public void setGrantEnabled(boolean grantEnabled) {
		this.grantEnabled = grantEnabled;
	}
	
	/**
	 * Gets the checks if is smart cart user.
	 *
	 * @return the checks if is smart cart user
	 */
	public String getIsSmartCartUser() {
		return String.valueOf(isSmartCartUser);
	}
	
	/**
	 * Checks if is credit card enabled for site.
	 *
	 * @return true, if is credit card enabled for site
	 */
	public String isCreditCardEnabledForSite() {
		return String.valueOf(isCreditCardEnabledForSite);
	}

	/**
	 * Sets the credit card enabled for site.
	 *
	 * @param isCreditCardEnabledForSite the new credit card enabled for site
	 */
	public void setCreditCardEnabledForSite(boolean isCreditCardEnabledForSite) {
		this.isCreditCardEnabledForSite = isCreditCardEnabledForSite;
	}

	/**
	 * Sets the checks if is smart cart user.
	 *
	 * @param isSmartCartUser the new checks if is smart cart user
	 */
	public void setIsSmartCartUser(boolean isSmartCartUser) {
		this.isSmartCartUser = isSmartCartUser;
	}

	/**
	 * Gets the checks if is rewards Enabled.
	 *
	 * @return the checks if is rewards Enabled
	 */
	public String getRewardsEnabled() {
		return String.valueOf(rewardsEnabled);
	}

	/**
	 * Sets the checks if is rewards Enabled.
	 *
	 * @param rewardsEnabled checks if rewards Enabled
	 */
	public void setRewardsEnabled(boolean rewardsEnabled) {
		this.rewardsEnabled = rewardsEnabled;
	}

	/**
	 * Gets the pending order count.
	 *
	 * @return the pending order count
	 */
	public String getPendingOrderCount() {
		return getIfNotNull(pendingOrderCount);
	}

	/**
	 * Sets the pending order count.
	 *
	 * @param pendingOrderCount the new pending order count
	 */
	public void setPendingOrderCount(String pendingOrderCount) {
		this.pendingOrderCount = pendingOrderCount;
	}

	/**
	 * Gets the unread messages count.
	 *
	 * @return the unread messages count
	 */
	public String getUnreadMessagesCount() {
		return getIfNotNull(unreadMessagesCount);
	}

	/**
	 * Sets the unread messages count.
	 *
	 * @param unreadMessagesCount the new unread messages count
	 */
	public void setUnreadMessagesCount(String unreadMessagesCount) {
		this.unreadMessagesCount = unreadMessagesCount;
	}

	/**
	 * Gets the expires in.
	 *
	 * @return the expires in
	 */
	public String getExpiresIn() {
		return getIfNotNull(expiresIn);
	}

	/**
	 * Sets the expires in.
	 *
	 * @param expiresIn the new expires in
	 */
	public void setExpiresIn(String expiresIn) {
		this.expiresIn = expiresIn;
	}
	
	/**
	 * Gets the hybris refresh token.
	 *
	 * @return the hybris refresh token
	 */
	public String getHybrisRefreshToken() {
		return getIfNotNull(hybrisRefreshToken);
	}

	/**
	 * Sets the hybris refresh token.
	 *
	 * @param hybrisRefreshToken the new hybris refresh token
	 */
	public void setHybrisRefreshToken(String hybrisRefreshToken) {
		this.hybrisRefreshToken = hybrisRefreshToken;
	}

	/**
	 * Gets the sso refresh token.
	 *
	 * @return the sso refresh token
	 */
	public String getSsoRefreshToken() {
		return getIfNotNull(ssoRefreshToken);
	}

	/**
	 * Sets the sso refresh token.
	 *
	 * @param ssoRefreshToken the new sso refresh token
	 */
	public void setSsoRefreshToken(String ssoRefreshToken) {
		this.ssoRefreshToken = ssoRefreshToken;
	}

	/**
	 * Gets the if not null.
	 *
	 * @param input the input
	 * @return the if not null
	 */
	public String getIfNotNull(String input)
	{
		return input==null ? StringUtils.EMPTY : input;
	}

	/**
	 * @return the areaOfInterest
	 */
	public String getAreaOfInterest() {
		String areaStr = StringUtils.EMPTY;
		if(!areaOfInterest.isEmpty()) {
			areaStr = StringUtils.join(areaOfInterest, ",");
		}
		return areaStr;
	}

	/**
	 * @param areaOfInterest the areaOfInterest to set
	 */
	public void setAreaOfInterest(List<String> areaOfInterest) {
		if (areaOfInterest == null)
	        throw new IllegalArgumentException("Parameter areaOfInterest is null");
	    this.areaOfInterest.clear();
	    this.areaOfInterest.addAll(areaOfInterest);
	}

	/**
	 * Gets the checks if is distributor .
	 *
	 * @return the checks if is distributor
	 */
	public String getDistributor() {
		return String.valueOf(distributor);
	}

	/**
	 * Sets the checks if is distributor.
	 *
	 * @param distributor checks if distributor
	 */
	public void setDistributor(boolean distributor) {
		this.distributor = distributor;
	}
	
	/**
	 * Gets the country name.
	 *
	 * @return the country name
	 */
	public String getCountry() {
		return getIfNotNull(country);
	}

	/**
	 * Sets the country name.
	 *
	 * @param country the new country name
	 */
	public void setCountry(String country) {
		this.country = country;
	}
	
/**
	 * @return the leadId
	 */
	public String getLeadId() {
		return getIfNotNull(leadId);
	}

	/**
	 * @param leadId the leadId to set
	 */
	public void setLeadId(String leadId) {
		this.leadId = leadId;
	}

/**
	 * @return the userIndustry
	 */
	public String getUserIndustry() {
		String industryStr = StringUtils.EMPTY;
		if(!userIndustry.isEmpty()) {
			industryStr = StringUtils.join(userIndustry, ",");
		}
		return industryStr;
	}

	
	/**
	 * @param userIndustry the userIndustry to set
	 */
	public void setUserIndustry(List<String> userIndustry) {
		if (userIndustry == null)
	        throw new IllegalArgumentException("Parameter userIndustry is null");
	    this.userIndustry.clear();
	    this.userIndustry.addAll(userIndustry);
	}
	
	/**
	 * @return the userRole
	 */
	public String getUserRole() {
		String roleStr = StringUtils.EMPTY;
		if(!userRole.isEmpty()) {
			roleStr = StringUtils.join(userRole, ",");
		}
		return roleStr;
	}

	
	/**
	 * @param userRole the userRole to set
	 */
	public void setUserRole(List<String> userRole) {
		if (userRole == null)
	        throw new IllegalArgumentException("Parameter userRole is null");
	    this.userRole.clear();
	    this.userRole.addAll(userRole);
	}
	
	/**
	 * @return the active
	 */
	public String getActive() {
		return String.valueOf(active);
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}
	
	

}