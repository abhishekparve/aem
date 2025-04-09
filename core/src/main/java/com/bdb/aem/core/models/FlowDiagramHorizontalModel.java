package com.bdb.aem.core.models;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.bean.FlowDiagramHorizontalImageBean;
import com.bdb.aem.core.bean.FlowDiagramHorizontalTabDataBean;
import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.util.CommonHelper;

import lombok.Getter;

/**
 * The Class FlowDiagramHorizontalModel.
 */
@Model(adaptables = { Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class FlowDiagramHorizontalModel {
    /**
     * The logger.
     */
    Logger logger = LoggerFactory.getLogger(FlowDiagramHorizontalModel.class);

	/** The title. */
    @Inject
    @Getter
    @Default(values = StringUtils.EMPTY)
    private String sectionTitle;

    /** The description. */
    @Inject
    @Getter
    @Default(values = StringUtils.EMPTY)
    private String description;
    
    @Inject
    @Getter
	@Default(values = StringUtils.EMPTY)
	private String backgroundColor;
    
    @Inject
    @Getter
	@Default(values = StringUtils.EMPTY)
	private String tabText1;
    
    @Inject
    @Getter
	@Default(values = StringUtils.EMPTY)
	private String tabText2;
    
    @Inject
    @Getter
	@Default(values = StringUtils.EMPTY)
	private String tabText3;
    
    @Inject
    @Getter
	@Default(values = StringUtils.EMPTY)
	private String tabText4;
     
    /** The sections */
	@ChildResource
	private Resource tab1;
 
	/** The sections */
	@ChildResource
	private Resource tab2;

	/** The sections */
	@ChildResource
	private Resource tab3;

	/** The sections */
	@ChildResource
	private Resource tab4;

    /** The resource resolver. */
    @SlingObject
    ResourceResolver resourceResolver;

    /** The externalizer service. */
    @Inject
    ExternalizerService externalizerService;
    
    /** The bdb api endpoint service. */
    @Inject
    BDBApiEndpointService bdbApiEndpointService;
    
    /** The current resource. */
	@Inject
	Resource currentResource;

		
	private List<FlowDiagramHorizontalTabDataBean> tabsBeanList;
	
	public String TAB_CONTENT_BACKGROUND = "tabContentBackground";
	public String CONTENT_ICON = "contentIcon";
	public String SUB_TAB_ICON = "subTabIcon";
	public String SUB_TAB_TEXT = "subTabText";
	public String TAB_TITLE = "tabTitle";
	public String SUB_TITLE = "subTitle";
	public String DESC = "description";
	public String IMAGE_TYPE = "imageType";
	public String MAGNI_FIY_GLASS_COLOR = "magnifiyGlassColor";
	public String IS_VIDEO_ENABLED = "videoEnabled";
	public String BRIGHTCOVE_VIDEO_ID = "brightcoveVideoId";
	public String PLAY_VIDEO_ICON = "playVideoIcon";
	public String LARGE_IMAGE_URL = "largeImageUrl";
	public String LARGE_ALT_TEXT = "largeImageAltText";
	public String LARGE_IMAGE_LINK_URL = "largeImageLinkUrl";
	public String OPEN_LINK_IN_NEW_TAB = "openLinkInNewTab";
	public String LARGE_IMAGE_CAPTION = "largeImageCaption";
	public String LARGE_IMAGE_ENLARGE_SIZE = "largeImageEnlargeSize";
	public String LARGE_ENLARGED_IMAGE_PATH = "largeEnlargedImagePath";
	public String LARGE_ENLARGED_IMAGE_TITLE = "largeImageTitle";
	public String LARGE_IMAGE_DESCRIPTION = "largeImageDescription";
	public String LABEL_CTA = "labelCta";
	public String URL_CTA = "urlCta";
	public String OPEN_NEW_TAB_CTA = "openNewTabCta";
	public String BLUE_PRIMARY_CTA = "bluePrimaryCta";
	public String IMAGE_URL = "imageUrl";
	public String ALT_TEXT = "altText";
	public String SMALL_IMAGE_LINK_URL = "smallImageLinkUrl";
	public String SMALL_IMAGE_CAPTION = "smallImageCaption";
	public String IMAGE_ENLARGE_SIZE = "imageEnlargeSize";
	public String ENLARGED_IMAGE_PATH = "enlargedImagePath";
	public String SMALL_IMAGE_TITLE = "smallImageTitle";
	public String SMALL_IMAGE_DESCRIPTION = "smallImageDescription";
	
	public String BORDER_ENABLE = "borderEnable";
	
	
	
	/**
     * Inits the.
     */
    @PostConstruct
    protected void init() {
        logger.debug("FlowDiagramHorizontalModel Initialized :: ");
        try {
			description = CommonHelper.HandleRTEAnchorLink(description, externalizerService, resourceResolver,StringUtils.EMPTY);
			tabsBeanList = new ArrayList<FlowDiagramHorizontalTabDataBean>();
        	getTabDatals();
        } catch (Exception e) {
            logger.error("Exception {}", e.getMessage());
        }
    }   
    
    /**
    *
    * @param tabsBeanList2 
     * @return List of Tab Values from 3 tabs
    */
	private void getTabDatals() {
		
		/** Tab 1 Details (Mandatory)**/
		if (!StringUtils.isEmpty(tabText1)) {
			FlowDiagramHorizontalTabDataBean tabData1 = new FlowDiagramHorizontalTabDataBean();
			tabData1 = getTabData(tab1, tabText1);
			tabsBeanList.add(tabData1);
		}
		
       /** Tab 2 Details (Optional)**/
		if (!StringUtils.isEmpty(tabText2)) {
			FlowDiagramHorizontalTabDataBean tabData2 = new FlowDiagramHorizontalTabDataBean();
			tabData2 = getTabData(tab2, tabText2);
			tabsBeanList.add(tabData2);
		}
		
       /** Tab 3 Details (Optional)**/
		if (StringUtils.isNotEmpty(tabText3)) {
			FlowDiagramHorizontalTabDataBean tabData3 = new FlowDiagramHorizontalTabDataBean();
			tabData3 = getTabData(tab3, tabText3);
			tabsBeanList.add(tabData3);
		}
		
		/** Tab 4 Details (Optional)**/
		if (StringUtils.isNotEmpty(tabText4)) {
			FlowDiagramHorizontalTabDataBean tabData4 = new FlowDiagramHorizontalTabDataBean();
			tabData4 = getTabData(tab4, tabText4);
			tabsBeanList.add(tabData4);
		}
	}
    
    private FlowDiagramHorizontalTabDataBean getTabData(Resource tabResource, String tabText) {
    	
    	FlowDiagramHorizontalTabDataBean tabData = new FlowDiagramHorizontalTabDataBean();
    	tabData = getTabData(tabData, tabResource, tabText);
    	List<FlowDiagramHorizontalTabDataBean> subTabDataList = new ArrayList<>();
    	subTabDataList = getSubTabDetails(tabResource);
    	tabData.setSubTabDetails(subTabDataList);
    	
		return tabData;

	}

	private List<FlowDiagramHorizontalTabDataBean> getSubTabDetails(Resource tabResource) {
		List<FlowDiagramHorizontalTabDataBean> subTabDataList = new ArrayList<>();
    	Resource subTabResource = tabResource.getChild("subTabDetails");
    	if(null != subTabResource) {
    		Iterator<Resource> resItr = subTabResource.listChildren();
			while (resItr.hasNext()) {
				FlowDiagramHorizontalTabDataBean subTabData = new FlowDiagramHorizontalTabDataBean();
				Resource childItr = resItr.next();
				subTabData = getTabData(subTabData, childItr, "");
				subTabDataList.add(subTabData);
			}
		}
		return subTabDataList;
	}
	
    private FlowDiagramHorizontalTabDataBean getTabData(FlowDiagramHorizontalTabDataBean tabData,
			Resource tabResource, String tabText) {
    	
    	ValueMap vm = tabResource.getValueMap();
    	tabData.setTabContentBackground(vm.get(TAB_CONTENT_BACKGROUND, StringUtils.EMPTY));
    	String contentIcon = vm.get(CONTENT_ICON, StringUtils.EMPTY);
    	tabData.setContentIcon(externalizerService.getFormattedUrl(contentIcon, resourceResolver));
    	String subTabIcon = vm.get(SUB_TAB_ICON, StringUtils.EMPTY);
    	tabData.setSubTabIcon(externalizerService.getFormattedUrl(subTabIcon, resourceResolver));
    	tabData.setSubTabText(vm.get(SUB_TAB_TEXT, StringUtils.EMPTY));
    	String trimmedSubTabText = vm.get(SUB_TAB_TEXT, StringUtils.EMPTY);
    	tabData.setTrimmedSubTabText(trimmedSubTabText.replaceAll("\\s", ""));
    	tabData.setTabText(tabText);
    	tabData.setTrimmedTabText(tabText.replaceAll("\\s", ""));
    	tabData.setTabTitle(vm.get(TAB_TITLE, StringUtils.EMPTY));
    	tabData.setSubTitle(vm.get(SUB_TITLE, StringUtils.EMPTY));
    	tabData.setDescription(vm.get(DESC, StringUtils.EMPTY));
    	tabData.setBorderEnable(vm.get(BORDER_ENABLE, StringUtils.EMPTY));
    	tabData.setImageType(vm.get(IMAGE_TYPE, StringUtils.EMPTY));
    	tabData.setMagnifiyGlassColor(vm.get(MAGNI_FIY_GLASS_COLOR, StringUtils.EMPTY));
    	tabData.setBrightcoveVideoId(vm.get(BRIGHTCOVE_VIDEO_ID, StringUtils.EMPTY));
    	tabData.setVideoEnabled(vm.get(IS_VIDEO_ENABLED, StringUtils.EMPTY));
    	String playVideoIcon = vm.get(PLAY_VIDEO_ICON, StringUtils.EMPTY);
    	tabData.setPlayVideoIcon(externalizerService.getFormattedUrl(playVideoIcon, resourceResolver));
    	String largeImageUrl = vm.get(LARGE_IMAGE_URL, StringUtils.EMPTY);
    	tabData.setLargeImageUrl(externalizerService.getFormattedUrl(largeImageUrl, resourceResolver));
    	tabData.setLargeAltText(vm.get(LARGE_ALT_TEXT, StringUtils.EMPTY));
    	String largeImageLinkUrl = vm.get(LARGE_IMAGE_LINK_URL, StringUtils.EMPTY);
    	tabData.setLargeImageLinkUrl(externalizerService.getFormattedUrl(largeImageLinkUrl, resourceResolver));
    	String openLinkInNewTab = vm.get(OPEN_LINK_IN_NEW_TAB, StringUtils.EMPTY);
    	tabData.setOpenLinkInNewTab(externalizerService.getFormattedUrl(openLinkInNewTab, resourceResolver));
    	tabData.setLargeImageCaption(vm.get(LARGE_IMAGE_CAPTION, StringUtils.EMPTY));
    	tabData.setLargeImageDescription(vm.get(LARGE_IMAGE_DESCRIPTION, StringUtils.EMPTY));
		tabData.setLargeImageTitle(vm.get(LARGE_ENLARGED_IMAGE_TITLE, StringUtils.EMPTY));
    	tabData.setLargeImageEnlargeSize(vm.get(LARGE_IMAGE_ENLARGE_SIZE, StringUtils.EMPTY));
    	String largeEnlargedImagePath = vm.get(LARGE_ENLARGED_IMAGE_PATH, StringUtils.EMPTY);
    	tabData.setLargeEnlargedImagePath(externalizerService.getFormattedUrl(largeEnlargedImagePath, resourceResolver));
    	tabData.setBluePrimaryCta(vm.get(BLUE_PRIMARY_CTA, StringUtils.EMPTY));
    	tabData.setLabelCta(vm.get(LABEL_CTA, StringUtils.EMPTY));
    	String ctaUrl = vm.get(URL_CTA, StringUtils.EMPTY);
    	tabData.setUrlCta(externalizerService.getFormattedUrl(ctaUrl, resourceResolver));
    	tabData.setOpenNewTabCta(vm.get(OPEN_NEW_TAB_CTA, StringUtils.EMPTY));
    	List<FlowDiagramHorizontalImageBean> imageDeatils = new ArrayList<>();
    	imageDeatils = getImageDetails(tabResource);
    	tabData.setImageList(imageDeatils);
    	String tabDescription = vm.get(DESC, StringUtils.EMPTY);
        try {
			tabDescription = CommonHelper.HandleRTEAnchorLink(tabDescription, externalizerService, resourceResolver,StringUtils.EMPTY);
        } catch (IOException e) {
			logger.error("IOException {}", e.getMessage());
        }
		tabData.setDescription(tabDescription);
        return tabData;
	}

	/**
     * Fetch the Image Data from the corresponding Data
     * @param multifield
     * @return
     */
	private List<FlowDiagramHorizontalImageBean> getImageDetails(Resource tabResource) {
		
		List<FlowDiagramHorizontalImageBean> imageList = new ArrayList<>();
		if (null != tabResource && tabResource.hasChildren()) {
			Resource imageResource = tabResource.getChild("imageList");
			if(null != imageResource) {
				Iterator<Resource> resItr = imageResource.listChildren();
				while (resItr.hasNext()) {
					FlowDiagramHorizontalImageBean image = new FlowDiagramHorizontalImageBean();
					Resource childItr = resItr.next();
					ValueMap vM = childItr.getValueMap();
					String imageUrl = vM.get(IMAGE_URL, StringUtils.EMPTY);
					image.setImageUrl(externalizerService.getFormattedUrl(imageUrl, resourceResolver));
					image.setAltText(vM.get(ALT_TEXT, StringUtils.EMPTY));
					String smallImageLinkUrl = vM.get(SMALL_IMAGE_LINK_URL, StringUtils.EMPTY);
					image.setSmallImageLinkUrl(externalizerService.getFormattedUrl(smallImageLinkUrl, resourceResolver));
					image.setOpenLinkInNewTab(vM.get(OPEN_LINK_IN_NEW_TAB, StringUtils.EMPTY));
					image.setSmallImageCaption(vM.get(SMALL_IMAGE_CAPTION, StringUtils.EMPTY));
					image.setMagnifiyGlassColor(vM.get(MAGNI_FIY_GLASS_COLOR, StringUtils.EMPTY));
					image.setImageEnlargeSize(vM.get(IMAGE_ENLARGE_SIZE, StringUtils.EMPTY));
					String enlargedImagePath = vM.get(ENLARGED_IMAGE_PATH, StringUtils.EMPTY);
					image.setEnlargedImagePath(externalizerService.getFormattedUrl(enlargedImagePath, resourceResolver));
					image.setSmallImageDescription(vM.get(SMALL_IMAGE_DESCRIPTION, StringUtils.EMPTY));
					image.setSmallImageTitle(vM.get(SMALL_IMAGE_TITLE, StringUtils.EMPTY));
					
					imageList.add(image);
				}
			}
		}
		return imageList;
	}



	/**
	 * @return the tab1
	 */
	public Resource getTab1() {
		return tab1;
	}


	/**
	 * @return the tab2
	 */
	public Resource getTab2() {
		return tab2;
	}


	/**
	 * @return the tab3
	 */
	public Resource getTab3() {
		return tab3;
	}


	/**
	 * @return the tab4
	 */
	public Resource getTab4() {
		return tab4;
	}

	/**
	 * @return the tabsBeanList
	 */
	public List<FlowDiagramHorizontalTabDataBean> getTabsBeanList() {
		return tabsBeanList;
	}
	
	/**
     * Gets the brightcove account id.
     *
     * @return the brightcove account id
     */
    public String getBrightcoveAccountId() {
        return bdbApiEndpointService.brightcoveAccountId();
    }
    
    /**
     * Gets the brightcove player id.
     *
     * @return the brightcove player id
     */
    public String getBrightcovePlayerId() {
        return bdbApiEndpointService.brightcovePlayerId();
    }

	/**
     * Gets the article id.
     *
     * @return the article id
     */
    public String getArticleId() {
		return currentResource.getParent().getName() + "-" + currentResource.getName();
	}
		
}
