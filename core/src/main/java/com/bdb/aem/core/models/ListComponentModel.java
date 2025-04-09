package com.bdb.aem.core.models;

import com.bdb.aem.core.bean.ListBlogsBean;
import com.bdb.aem.core.bean.ListEventsBean;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.*;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.util.*;
import java.util.stream.Collectors;

/**
 * The Class ListComponentModel.
 */
@Model(adaptables = {Resource.class}, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)

public class ListComponentModel {

    public static final String ADD_TO_HOME_PAGE = "addToHomePage";

    public static final String PATH = "path";
    public static final String P_LIMIT = "p.limit";
    public static final String TYPE = "type";
    public static final String PROPERTY = "property";
    public static final String PROPERTY_VALUE = "property.value";
    public static final String ORDERBY = "orderby";
    public static final String ORDERBY_SORT = "orderby.sort";
    /**
     * The event primary type.
     */
    private static String EVENT_PRIMARY_TYPE = "bdb-aem/proxy/components/content/eventblogDetails";
    /**
     * The date type.
     */
    private static String DATE_TYPE = "MMMM dd, yyyy";
    /**
     * The logger.
     */
    Logger logger = LoggerFactory.getLogger(ListComponentModel.class);
    /**
     * The externalizer service.
     */
    @Inject
    ExternalizerService externalizerService;
    /**
     * The resolver factory.
     */
    @Inject
    ResourceResolverFactory resolverFactory;
    /**
     * The resources rows.
     */
    @Inject
    List<ListResourcesModel> resourcesRows;
    /**
     * The popular tools rows.
     */
    @Inject
    List<ListPopularToolsModel> popularToolsRows;
    /**
     * The support rows.
     */
    @Inject
    List<ListSupportModel> supportRows;
    /**
     * The flowcytometry rows.
     */
    @Inject
    List<ListFlowcytometryNewsModel> flowcytometryRows;
    /**
     * The list of events.
     */
    List<ListEventsBean> listOfEvents;

    /**
     * The List of blogs.
     */
    List<ListBlogsBean> listOfBlogs;
    /**
     * The title.
     */
    @Inject
    private String title;


    @Inject
    private String listSelection;

    /**
     * The icon.
     */
    @Inject
    private String icon;
    /**
     * The has white background check.
     */
    @Inject
    private String hasWhiteBackground;
    /**
     * The thought leadership variation check.
     */
    @Inject
    private String thoughtLeadershipVariation;
    /**
     * The view all label.
     */
    @Inject
    private String viewAllLabel;
    /**
     * The view all URL.
     */
    @Inject
    private String viewAllURL;
    /**
     * The is placed below check.
     */
    @Inject
    private String isPlacedBelow;
    /**
     * The number limit.
     */
    @Inject
    @Default(values = "0")
    private String numberLimit;
    /**
     * The event parent page path.
     */
    @Inject
    @Default(values = StringUtils.EMPTY)
    private String eventParentPagePath;

    @Inject
    @Default(values = "0")
    private String blogsnumberLimit;
    @Inject
    @Default(values = StringUtils.EMPTY)
    private String blogParentPagePath;

    @Inject
    private boolean sortEventsByOldestFirst;

    @Inject
    private boolean sortBlogsByOldestFirst;


    public boolean isSortEventsByOldestFirst() {
        return sortEventsByOldestFirst;
    }

    public boolean isSortBlogsByOldestFirst() {
        return sortBlogsByOldestFirst;
    }

    public String getBlogsnumberLimit() {
        return blogsnumberLimit;
    }

    public String getBlogParentPagePath() {
        return blogParentPagePath;
    }

    /**
     * Inits the.
     */
    @PostConstruct
    protected void init() {
        try (ResourceResolver resourceResolver = CommonHelper.getReadServiceResolver(resolverFactory);) {
            logger.debug("ListComponentModel Initiated");
            if (StringUtils.isNotBlank(eventParentPagePath)) {
                listOfEvents = getEventsDetails(resourceResolver, listSelection, sortEventsByOldestFirst);

            }

            if (StringUtils.isNotBlank(blogParentPagePath)) {

                listOfBlogs = getBlogsDetails(resourceResolver, listSelection,sortBlogsByOldestFirst);

            }
            icon = externalizerService.getFormattedUrl(icon, resourceResolver);
            viewAllURL = externalizerService.getFormattedUrl(viewAllURL, resourceResolver);
        } catch (LoginException e) {
            logger.error("LoginException {}", e.getMessage());
        }
    }


    /**
     * Gets list selection.
     *
     * @return the list selection
     */
    public String getListSelection() {
        return listSelection;
    }


    /**
     * Gets the title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets the icon.
     *
     * @return the icon
     */
    public String getIcon() {
        return icon;
    }

    /**
     * Gets the checks for white background.
     *
     * @return the checks for white background
     */
    public String getHasWhiteBackground() {
        return hasWhiteBackground;
    }

    /**
     * Gets the thought leadership variation.
     *
     * @return the thought leadership variation
     */
    public String getThoughtLeadershipVariation() {
        return thoughtLeadershipVariation;
    }

    /**
     * Gets the view all label.
     *
     * @return the view all label
     */
    public String getViewAllLabel() {
        return viewAllLabel;
    }

    /**
     * Gets the view all URL.
     *
     * @return the view all URL
     */
    public String getViewAllURL() {
        return viewAllURL;
    }

    /**
     * Gets the checks if is placed below.
     *
     * @return the checks if is placed below
     */
    public String getIsPlacedBelow() {
        return isPlacedBelow;
    }

    /**
     * Gets the resources rows.
     *
     * @return the resources rows
     */
    public List<ListResourcesModel> getResourcesRows() {
        return new ArrayList<>(resourcesRows);
    }

    /**
     * Gets the popular tools rows.
     *
     * @return the popular tools rows
     */
    public List<ListPopularToolsModel> getPopularToolsRows() {
        return new ArrayList<>(popularToolsRows);
    }

    /**
     * Gets the support rows.
     *
     * @return the support rows
     */
    public List<ListSupportModel> getSupportRows() {
        return new ArrayList<>(supportRows);
    }

    /**
     * Gets the flowcytometry rows.
     *
     * @return the flowcytometry rows
     */
    public List<ListFlowcytometryNewsModel> getFlowcytometryRows() {
        return new ArrayList<>(flowcytometryRows);
    }

    /**
     * Gets the list of events.
     *
     * @return the list of events
     */
    public List<ListEventsBean> getListOfEvents() {
        return new ArrayList<>(listOfEvents);
    }

    public List<ListBlogsBean> getListOfBlogs() {
        return new ArrayList<>(listOfBlogs);
    }

    /**
     * Gets the number limit.
     *
     * @return the number limit
     */
    public String getNumberLimit() {
        return numberLimit;
    }

    /**
     * Gets the event parent page path.
     *
     * @return the event parent page path
     */
    public String getEventParentPagePath() {
        return eventParentPagePath;
    }


    /**
     * Gets the events details.
     *
     * @param resourceResolver the resource resolver
     * @return the events details
     */
    public List<ListEventsBean> getEventsDetails(ResourceResolver resourceResolver, String listSelection, boolean sortByOldest) {

        logger.debug("getEventsDetails Initiated");
        List<ListEventsBean> eventsList = new ArrayList<>();
        try {
            SearchResult result = getQueryResults(resourceResolver, listSelection);
            if (null != result && result.getTotalMatches() > 0) {
                for (Hit hit : result.getHits()) {
                    Resource eventPageResource = resourceResolver.getResource(hit.getPath());
                    if (null != eventPageResource) {
                        if (eventPageResource.getPath().contains(JcrConstants.JCR_CONTENT)) {
                            Resource eventPage = resourceResolver.getResource(eventPageResource.getPath().split(JcrConstants.JCR_CONTENT)[0]);
                            if (eventPage != null) {
                                if (null != eventPage.getChild(JcrConstants.JCR_CONTENT)) {
                                    Resource rootRes = eventPage.getChild(JcrConstants.JCR_CONTENT).getChild("root");
                                    eventsList.add(gettingDataFromEvent(rootRes, eventPage, resourceResolver, eventPageResource));
                                }
                            }
                        }
                    }
                }
                Collections.sort(eventsList);
                if (Boolean.FALSE.equals(sortByOldest)) {
                    Collections.reverse(eventsList);
                    List<ListEventsBean> list = new ArrayList<>();
                    for(int i =0;i<eventsList.size();i++){
                        ListEventsBean bean = eventsList.get(i);
                        if(bean.getDate().equals("")){
                            list.add(bean);
                            eventsList.remove(bean);
                        }
                    }
                    eventsList.addAll(0,list);
                }

                return eventsList;
            }
        } catch (RepositoryException e) {
            logger.error("RepositoryException | LoginException {}", e.getMessage());
        }
        return eventsList;
    }

    public List<ListBlogsBean> getBlogsDetails(ResourceResolver resourceResolver, String listSelection,boolean sortByOldest) {

        logger.debug("getEventsDetails Initiated");
        List<ListBlogsBean> blogsList = new ArrayList<>();
        try {
            SearchResult result = getQueryResults(resourceResolver, listSelection);
            if (null != result && result.getTotalMatches() > 0) {
                for (Hit hit : result.getHits()) {
                    Resource eventPageResource = resourceResolver.getResource(hit.getPath());
                    if (null != eventPageResource) {
                        if (eventPageResource.getPath().contains(JcrConstants.JCR_CONTENT)) {
                            Resource eventPage = resourceResolver.getResource(eventPageResource.getPath().split(JcrConstants.JCR_CONTENT)[0]);
                            if (eventPage != null) {
                                if (null != eventPage.getChild(JcrConstants.JCR_CONTENT)) {
                                    Resource rootRes = eventPage.getChild(JcrConstants.JCR_CONTENT).getChild("root");
                                    blogsList.add(gettingDataFromBlog(rootRes, eventPage, resourceResolver, eventPageResource));
                                }
                            }
                        }
                    }
                }
                Collections.sort(blogsList);
                if(Boolean.FALSE.equals(sortByOldest)) {
                    Collections.reverse(blogsList);
                    List<ListBlogsBean> list = new ArrayList<>();
                    for(int i =0;i<blogsList.size();i++){
                        ListBlogsBean bean = blogsList.get(i);
                        if(bean.getDate().equals("")){
                            list.add(bean);
                            blogsList.remove(bean);
                        }
                    }
                    blogsList.addAll(0,list);
                }

                return blogsList;
            }
        } catch (RepositoryException e) {
            logger.error("RepositoryException | LoginException {}", e.getMessage());
        }
        return blogsList;
    }

    private SearchResult getQueryResults(ResourceResolver resourceResolver, String listSelection) {
        Map<String, Object> params = new HashMap<>();
        if (listSelection.equalsIgnoreCase("Events")) {
            params.put(PATH, eventParentPagePath);
            params.put(P_LIMIT, numberLimit);
        } else if (listSelection.equalsIgnoreCase("Blogs")) {

            params.put(PATH, blogParentPagePath);
            params.put(P_LIMIT,blogsnumberLimit);

        }

        params.put(PROPERTY, ADD_TO_HOME_PAGE);
        params.put(PROPERTY_VALUE, "true");

        QueryBuilder queryBuilder = resourceResolver.adaptTo(QueryBuilder.class);
        Session session = resourceResolver.adaptTo(Session.class);

        Query query = queryBuilder.createQuery(PredicateGroup.create(params), session);


        return query.getResult();
    }

    /**
     * Gets the ting data from event.
     *
     * @param rootRes          the root res
     * @param eventPage        the event page
     * @param resourceResolver the resource resolver
     * @return the ting data from event
     */
    private ListEventsBean gettingDataFromEvent(Resource rootRes, Resource eventPage, ResourceResolver resourceResolver, Resource eventResource) {

        ListEventsBean eventsObj = new ListEventsBean();
        if (null != rootRes) {
            Iterator<Resource> rootResourceList = rootRes.listChildren();
            while (rootResourceList.hasNext()) {

                Resource rootResItem = rootResourceList.next();
                if (rootResItem.getResourceType().equals(EVENT_PRIMARY_TYPE)) {
                    EventBlogModel eventBlogModel = rootResItem.adaptTo(EventBlogModel.class);
                    if (null != eventBlogModel) {
                        eventsObj.setTitle(eventBlogModel.getBannerTitle());
                        eventsObj.setLocation(eventBlogModel.getEventLocation());
                        eventsObj.setEventLink(externalizerService.getFormattedUrl(eventPage.getPath(), resourceResolver));
                        String eventStartDate = getStartDate(resourceResolver, eventResource);
                        if (StringUtils.isEmpty(eventStartDate)) {
                            eventsObj.setDate("");
                        } else {

                            eventsObj.setDate(CommonHelper.getDateAndTime(eventStartDate, DATE_TYPE));
                        }

                    }
                }
            }
        }
        return eventsObj;
    }

    private ListBlogsBean gettingDataFromBlog(Resource rootRes, Resource blogPage, ResourceResolver resourceResolver, Resource blogResource) {

        ListBlogsBean blogsObj = new ListBlogsBean();
        if (null != rootRes) {
            Iterator<Resource> rootResourceList = rootRes.listChildren();
            while (rootResourceList.hasNext()) {

                Resource rootResItem = rootResourceList.next();
                if (rootResItem.getResourceType().equals(EVENT_PRIMARY_TYPE)) {

                    EventBlogModel eventBlogModel = rootResItem.adaptTo(EventBlogModel.class);
                    if (null != eventBlogModel) {
                        blogsObj.setTitle(eventBlogModel.getBannerTitleBlog());
                        blogsObj.setBlogLink(externalizerService.getFormattedUrl(blogPage.getPath(), resourceResolver));
                        blogsObj.setDate(CommonHelper.getDateAndTime(eventBlogModel.getBlogDate(), DATE_TYPE));


                    }
                }
            }
        }
        return blogsObj;
    }


    private String getStartDate(ResourceResolver resolver, Resource eventResource) {
        String eventStartDate = "";
        if (eventResource != null) {
            Resource eventDateAndTimeResource = resolver.getResource(eventResource, "eventDateAndTimeLabel");

            if (eventDateAndTimeResource != null) {
                if (eventDateAndTimeResource.hasChildren()) {
                    Iterator<Resource> iterator = eventDateAndTimeResource.getChildren().iterator();
                    if (iterator != null) {
                        while (iterator.hasNext()) {
                            Resource childResource = iterator.next();
                            ValueMap properties = childResource.adaptTo(ValueMap.class);
                            if (properties != null) {
                                eventStartDate = properties.get("multifieldEventStartDate", String.class);
                            }
                        }
                    }

                }

            }

        }
        return eventStartDate;
    }

}