package com.bdb.aem.core.models;

import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.bdb.aem.core.util.SolrUtils;
import com.day.cq.commons.inherit.InheritanceValueMap;
import com.day.cq.wcm.api.Page;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.*;
import org.apache.sling.jcr.resource.api.JcrResourceConstants;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.Iterator;

/**
 * Testimonial Component for Instruments Page
 */
@Model(adaptables = { SlingHttpServletRequest.class,
		Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class PdpTestimonialLookUpModel {
	
	/** The page properties. */
	@Inject
	InheritanceValueMap pageProperties;
	
	/** The request. */
	@Inject
	SlingHttpServletRequest request;
	
	/** The resource resolver. */
    @SlingObject
    ResourceResolver resourceResolver;
	
	/** The current page. */
	@Inject
	Page currentPage;
	
	/** Testimonial Variables */
	private String testimonialId = StringUtils.EMPTY;
	private String bgColor;
	private String ctaBgColor;
	private String ctaLabel;
	private String ctaTextColor;
	private String ctaUrl;
	private String department;
	private String description;
	private String fontColor;
	private String fontStyle;
	private String imagePath;
	private String imageAlt;
	private String name;
	private String university;
	private Boolean videoEnabled;
	private String brightcoveVideoId;
	
	/** The testimonial. */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	private String testimonial;
	
	
	/**
	 *
	 * @throws LoginException
	 */
	@PostConstruct
	protected void init() throws LoginException {
		String lookUpPage = pageProperties.getInherited(CommonConstants.TESTIMONIAL_URL, StringUtils.EMPTY);
		String catalogNumber = CommonHelper.getSelectorDetails(request);
		String country = CommonHelper.getCountry(currentPage);
		Resource hpBaseResource = null;
		if (null != request.getAttribute(CommonConstants.PRODUCT_VAR_HP_PATH)) {
			String productVarHPPath = request.getAttribute(CommonConstants.PRODUCT_VAR_HP_PATH).toString();
			hpBaseResource = resourceResolver.getResource(productVarHPPath);
			
		}
		Resource variantHpResource = SolrUtils.getVariantHpResource(hpBaseResource, catalogNumber);
		if (null != variantHpResource) {
			Resource marketingRes = resourceResolver
					.getResource(StringUtils.replace(variantHpResource.getPath(), "/hp", "/marketing"));
			if (null != marketingRes && marketingRes.hasChildren()) {
				testimonialId = fetchTestimonialId(marketingRes, country);
				if (StringUtils.isNotEmpty(testimonialId)) {
					getLookUpPageContent(lookUpPage);
				}
			}
		}

	}

	/**
	 *
	 * @param marketingRes
	 * @return Fetch Testimonial Id from commerce
	 */

	private String fetchTestimonialId(Resource marketingRes, String country) {
		Resource childRes = marketingRes.getChild(country);
		String id = StringUtils.EMPTY;
		if (null != childRes) {
			id = getIdFromValueMap(childRes);
		} else if(StringUtils.isEmpty(id)){
			Resource globalRes = marketingRes.getChild(CommonConstants.GLOBAL);
			if (null != globalRes) {
				id = getIdFromValueMap(globalRes);
			}
		}
		return id;
	}

	/**
	 *
	 * @param childRes
	 * @return Fetch Testimonial Id
	 */
	private String getIdFromValueMap(Resource childRes) {
		ValueMap vM = childRes.getValueMap();
		return vM.get("testimonialId", StringUtils.EMPTY);
	}

	/**
	 * Fetch the Testimonial Component from Page authored from pageProperties
	 * @param lookUpPage
	 * @throws LoginException
	 */
	public void getLookUpPageContent(String lookUpPage) throws LoginException {
		if (null != resourceResolver) {
			Resource testimonialRes=null;
			String homePage = "bdb-aem/proxy/components/structure/home-page";
			String genericPage = "bdb-aem/proxy/components/structure/generic-page";
			Resource lookupRes=resourceResolver.getResource(lookUpPage+CommonConstants.JCR_CONTENT);
			if(null != lookupRes && lookupRes.isResourceType(homePage)) {
				testimonialRes= resourceResolver
						.getResource(lookUpPage + CommonConstants.JCR_CONTENT_PATH);
			}
			else if(null != lookupRes && lookupRes.isResourceType(genericPage)){
				testimonialRes= resourceResolver
						.getResource(lookUpPage + "/jcr:content/root");
			}
			if (null != testimonialRes && testimonialRes.hasChildren()) {
				Iterator<Resource> resItr = testimonialRes.listChildren();
				while (resItr.hasNext()) {
					Resource componentItr = resItr.next();
					ValueMap comVm = componentItr.getValueMap();
					String resType = comVm.get(JcrResourceConstants.SLING_RESOURCE_TYPE_PROPERTY, StringUtils.EMPTY);
					if (StringUtils.equals(CommonConstants.TESTIMONIAL_DATATYPE, resType))
					{
						Resource slideRes = componentItr.getChild("slides");
						fetchSlideData(slideRes);
					}
				}
			}
		}
	}

	/**
	 * Fetch the Testimonial Items that matches the Id
	 * @param slideResource
	 */
	private void fetchSlideData(Resource slideResource) {
		if (null != slideResource) {
			Iterator<Resource> resItr = slideResource.listChildren();
			while (resItr.hasNext()) {
				Resource childRes = resItr.next();
				ValueMap vM = childRes.getValueMap();
				String vmId = vM.get("testimonialId", StringUtils.EMPTY);
				if (StringUtils.isNotBlank(vmId) && StringUtils.isNotBlank(testimonialId)
						&& StringUtils.equals(testimonialId, vmId)) {
					imagePath=vM.get("imagePath", StringUtils.EMPTY);
					bgColor=vM.get("bgColor",StringUtils.EMPTY);
					ctaBgColor=vM.get("ctaBgColor",StringUtils.EMPTY);
					ctaLabel=vM.get("ctaLabel",StringUtils.EMPTY);
					ctaTextColor=vM.get("ctaTxtColor",StringUtils.EMPTY);
					ctaUrl=vM.get("ctaUrl",StringUtils.EMPTY);
					department=vM.get("department",StringUtils.EMPTY);
					description=vM.get("description",StringUtils.EMPTY);
					fontColor=vM.get("fontColor",StringUtils.EMPTY);
					name=vM.get("pname",StringUtils.EMPTY);
					university=vM.get("university",StringUtils.EMPTY);
					String isVideoEnabled=vM.get("videoEnabled",StringUtils.EMPTY);
					videoEnabled=StringUtils.equals(isVideoEnabled,"yes")?Boolean.TRUE:Boolean.FALSE;
					brightcoveVideoId=vM.get("brightcoveVideoId",StringUtils.EMPTY);
					fontStyle=vM.get("fontStyle",StringUtils.EMPTY);
					imageAlt=vM.get("altImage",StringUtils.EMPTY);
				}
			}
		}

	}

	/**
	 *
	 * @return bgColor
	 */
	public String getBgColor() {
		return bgColor;
	}

	/**
	 *
	 * @return ctaBgColor
	 */
	public String getCtaBgColor() {
		return ctaBgColor;
	}

	/**
	 *
	 * @return ctaLabel
	 */
	public String getCtaLabel() {
		return ctaLabel;
	}

	/**
	 *
	 * @return ctaTextColor
	 */
	public String getCtaTextColor() {
		return ctaTextColor;
	}

	/**
	 *
	 * @return ctaUrl
	 */
	public String getCtaUrl() {
		return ctaUrl;
	}

	/**
	 *
	 * @return Department
	 */
	public String getDepartment() {
		return department;
	}

	/**
	 *
	 * @return Description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 *
	 * @return Font Color
	 */
	public String getFontColor() {
		return fontColor;
	}

	/**
	 *
	 * @return ImagePath
	 */
	public String getImagePath() {
		return imagePath;
	}

	/**
	 *
	 * @return Name
	 */
	public String getName() {
		return name;
	}

	/**
	 *
	 * @return University
	 */
	public String getUniversity() {
		return university;
	}

	/**
	 *
	 * @return videoEnabled
	 */
	public Boolean getVideoEnabled() {
		return videoEnabled;
	}

	/**
	 *
	 * @return Brightcove Id
	 */
	public String getBrightcoveVideoId() {
		return brightcoveVideoId;
	}

	/**
	 *
	 * @return Font Style
	 */
	public String getFontStyle() {
		return fontStyle;
	}

	/**
	 *
	 * @return Image Alt
	 */
	public String getImageAlt() {
		return imageAlt;
	}

	/**
	 *
	 * @return Testimonial
	 */
	public String getTestimonial() {
		return testimonial;
	}
}
