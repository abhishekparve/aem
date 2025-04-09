package com.bdb.aem.core.models;

import com.bdb.aem.core.bean.TableDetailsBean;
import com.bdb.aem.core.bean.TableImageDetailBean;
import com.bdb.aem.core.bean.TableRowDetailsBean;
import com.bdb.aem.core.bean.TableTabDetailBean;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.util.CommonConstants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Model that generate Comparison Table
 * Window Object
 */
@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ComparisonTableModel {
	Logger log = LoggerFactory.getLogger(ComparisonTableModel.class);
	@Inject
	@Via("resource")
	private Resource imageDetails;
	@Inject
	@Via("resource")
	private Resource imageDetails2;
	@Inject
	@Via("resource")
	private Resource imageDetails3;
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String tabTitle;
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String tabTitle2;
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String tabTitle3;
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String hideFirstColumnTab1;
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String hideFirstColumnTab2;
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String hideFirstColumnTab3;
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String loadInModalTab1;
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String loadInModalTab2;
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String loadInModalTab3;
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String componentTitle;
	
	/** The grey background. */
	@Inject
	@Via("resource")
	private Boolean greyBackground;
	
	/** Reduce Top Padding. */
	@Inject
	@Via("resource")
	private Boolean togglePaddingTop;
	
	/** Reduce Bottom Padding. */
	@Inject
	@Via("resource")
	private Boolean togglePaddingBottom;
	
	/** The description. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String description;
	
	/** The Image Link Url. */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	private String imageLinkUrl;
	
	/** The openNewImageLinkTab. */
	@Inject
	@Via("resource")
	private Boolean openNewImageLinkTab;
	
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	private String redirectLink;

	/** Products Component unique name. */
	private String compUniqueName;

    /** Final Window Object value **/
	String tabValue = StringUtils.EMPTY;
	/**
	 * Resource Resolver
	 */
	@Inject
	private ResourceResolver resourceResolver;
	/**
	 * Externalizer Service
	 */
	@Inject
	private ExternalizerService externalizerService;
    /**
     * Init Method of Model Class
     */
	@PostConstruct
	protected void init() {
		TableDetailsBean finalData = new TableDetailsBean();
		finalData.setComponentTitle(componentTitle);		
		finalData.setComponentDesc(formatDescption(description));
		finalData.setGreyBackground(greyBackground);
		finalData.setTogglePaddingTop(togglePaddingTop);
		finalData.setTogglePaddingBottom(togglePaddingBottom);
		compUniqueName = "ComparisonTable" + Math.abs(new SecureRandom().nextInt(Integer.MAX_VALUE));
		List<TableTabDetailBean> tabData = getTableTabDetailBeans();
		finalData.setData(tabData);
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		tabValue = gson.toJson(finalData);
		log.debug("Final Value of the Table{}", tabValue);
	}
	
	/**
	 * 
	 * @return formatted description string
	 */
	private String formatDescption(String desc)
	{
		String formattedString = desc.replaceAll("/content/bdb/[a-zA-Z]{2,5}/[a-zA-Z0-9]{2,5}", ""); 
		return formattedString;
		
	}

    /**
     *
     * @return List of Tab Values from 3 tabs
     */
	private List<TableTabDetailBean> getTableTabDetailBeans() {
		List<TableTabDetailBean> tabData = new ArrayList<>();
		/** Tab 1 Details (Mandatory)**/
		TableTabDetailBean tab1 = new TableTabDetailBean();
		tab1.setTabTitle(tabTitle);
		tab1.setHideFirstColumn((StringUtils.isNotEmpty(hideFirstColumnTab1)&& hideFirstColumnTab1.equalsIgnoreCase(CommonConstants.TRUE)?Boolean.TRUE:Boolean.FALSE));
		tab1.setIsRequestQuoteForm((StringUtils.isNotEmpty(loadInModalTab1)&& loadInModalTab1.equalsIgnoreCase(CommonConstants.TRUE)?Boolean.TRUE:Boolean.FALSE));
		List<TableImageDetailBean> image1 = new ArrayList<>();
		if (null != imageDetails && imageDetails.hasChildren()) {
			image1 = getImageDetails(imageDetails);
		}
		tab1.setImageDetails(image1);
		tabData.add(tab1);
        /** Tab 2 Details (Optional)**/
		if (!StringUtils.isEmpty(tabTitle2)) {
			TableTabDetailBean tab2 = new TableTabDetailBean();
			tab2.setTabTitle(tabTitle2);
			tab2.setHideFirstColumn((StringUtils.isNotEmpty(hideFirstColumnTab2)&& hideFirstColumnTab2.equalsIgnoreCase(CommonConstants.TRUE)?Boolean.TRUE:Boolean.FALSE));
			tab2.setIsRequestQuoteForm((StringUtils.isNotEmpty(loadInModalTab2)&& loadInModalTab2.equalsIgnoreCase(CommonConstants.TRUE)?Boolean.TRUE:Boolean.FALSE));
			List<TableImageDetailBean> image2 = new ArrayList<>();
			if (null != imageDetails2 && imageDetails2.hasChildren()) {
				image2 = getImageDetails(imageDetails2);
			}
			tab2.setImageDetails(image2);
			tabData.add(tab2);
		}
        /** Tab 3 Details (Optional)**/
		if (StringUtils.isNotEmpty(tabTitle3)) {
			TableTabDetailBean tab3 = new TableTabDetailBean();
			tab3.setTabTitle(tabTitle3);
			tab3.setHideFirstColumn((StringUtils.isNotEmpty(hideFirstColumnTab3)&& hideFirstColumnTab3.equalsIgnoreCase(CommonConstants.TRUE)?Boolean.TRUE:Boolean.FALSE));
			tab3.setIsRequestQuoteForm((StringUtils.isNotEmpty(loadInModalTab3)&& loadInModalTab3.equalsIgnoreCase(CommonConstants.TRUE)?Boolean.TRUE:Boolean.FALSE));
			List<TableImageDetailBean> image3 = new ArrayList<>();
			if (null != imageDetails3 && imageDetails3.hasChildren()) {
				image3 = getImageDetails(imageDetails3);
			}
			tab3.setImageDetails(image3);
			tabData.add(tab3);
		}
		return tabData;
	}

    /**
     * Fetch the Image Data from the corresponding Data
     * @param multifield
     * @return
     */
	private List<TableImageDetailBean> getImageDetails(Resource multifield) {
		List<TableImageDetailBean> imageList = new ArrayList<>();
		if (null != multifield && multifield.hasChildren()) {

			Iterator<Resource> resItr = multifield.listChildren();
			while (resItr.hasNext()) {
				TableImageDetailBean image = new TableImageDetailBean();
				Resource childItr = resItr.next();
				ValueMap vM = childItr.getValueMap();
				image.setImage(vM.get("imageUrl", StringUtils.EMPTY));
				String titleText=vM.get("titleText", StringUtils.EMPTY);
				image.setTitleText(titleText);
				if(StringUtils.isNotEmpty(titleText))
				{
				image.setRedirectSectionId(titleText.replaceAll("[^a-zA-Z0-9]", "-").toLowerCase().trim());
				}
				image.setRedirectLink(externalizerService.getFormattedUrl(vM.get("redirectLink",StringUtils.EMPTY),resourceResolver));
				image.setImageLinkUrl(externalizerService.getFormattedUrl(vM.get("imageLinkUrl",StringUtils.EMPTY),resourceResolver));
				image.setOpenNewImageLinkTab(vM.get("openNewImageLinkTab",StringUtils.EMPTY));
				image.setSubtext(formatDescption(vM.get("subText", StringUtils.EMPTY)));
				if(StringUtils.isNotBlank(vM.get("ctaText", StringUtils.EMPTY))) {
					image.setCtaText(vM.get("ctaText", StringUtils.EMPTY));
					image.setCtaLink(
							externalizerService.getFormattedUrl(vM.get("ctaLink", StringUtils.EMPTY), resourceResolver));
				}
				if(StringUtils.isNotBlank(vM.get("ctaText2", StringUtils.EMPTY))) {
					image.setCtaText1(vM.get("ctaText2", StringUtils.EMPTY));
					image.setCtaLink1(
							externalizerService.getFormattedUrl(vM.get("ctaLink2", StringUtils.EMPTY), resourceResolver));
				}
				if (childItr.hasChildren()) {
					getRowDetails(childItr, image);
				}
				imageList.add(image);
			}

		}
		return imageList;
	}

    /**
     * Fetching Row Details
     * @param childItr
     * @param image
     */
	private void getRowDetails(Resource childItr, TableImageDetailBean image) {
		List<TableRowDetailsBean> rowDetails = new ArrayList<>();
		Resource rowRes = childItr.listChildren().next();
		Iterator<Resource> optionsItr = rowRes.listChildren();
		while (optionsItr.hasNext()) {
			TableRowDetailsBean rowData = new TableRowDetailsBean();
			Resource options = optionsItr.next();
			ValueMap optVm = options.getValueMap();
			rowData.setDropDownValue(optVm.get("dropdownValue", StringUtils.EMPTY));
			rowData.setImage(optVm.get("image", StringUtils.EMPTY));
			rowData.setText(optVm.get("text", StringUtils.EMPTY));
			rowData.setCtaLink(
					externalizerService.getFormattedUrl(optVm.get("textCtaLink", StringUtils.EMPTY), resourceResolver));
			rowData.setImagePathUrl(
					externalizerService.getFormattedUrl(optVm.get("imagePathUrl", StringUtils.EMPTY), resourceResolver));
			rowData.setOpenNewImagePathTab(optVm.get("openNewImagePathTab",StringUtils.EMPTY));
			rowData.setTextLeftAlignment(optVm.get("textLeftAlignment", StringUtils.EMPTY));
			rowDetails.add(rowData);
		}
		image.setRowDetails(rowDetails);
	}

    /**
     *
     * @return Final JsonString Value
     */
	public String getTabValue() {
		return tabValue;
	}

	public String getCompUniqueName() {
		return compUniqueName;
	}
}
