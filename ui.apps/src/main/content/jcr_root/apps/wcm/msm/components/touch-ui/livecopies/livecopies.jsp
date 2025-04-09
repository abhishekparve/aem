<%--
  ADOBE CONFIDENTIAL

  Copyright 2013 Adobe Systems Incorporated
  All Rights Reserved.

  NOTICE:  All information contained herein is, and remains
  the property of Adobe Systems Incorporated and its suppliers,
  if any.  The intellectual and technical concepts contained
  herein are proprietary to Adobe Systems Incorporated and its
  suppliers and may be covered by U.S. and Foreign Patents,
  patents in process, and are protected by trade secret or copyright law.
  Dissemination of this information or reproduction of this material
  is strictly forbidden unless prior written permission is obtained
  from Adobe Systems Incorporated.
--%>
<%@page session="false"
        import="java.util.ArrayList,
      java.util.Arrays,
                java.util.Calendar,
                java.util.Collections,
                java.util.Comparator,
                java.util.Date,
                java.util.Iterator,
                org.apache.sling.api.resource.ValueMap,
                java.util.List,
                java.util.ResourceBundle,
                javax.jcr.RangeIterator,
                org.apache.sling.api.resource.Resource,
                org.apache.sling.api.resource.ResourceUtil,
                org.slf4j.Logger,
                org.slf4j.LoggerFactory,
                com.adobe.granite.security.user.util.AuthorizableUtil,
                com.adobe.granite.toggle.api.ToggleRouter,
                com.adobe.granite.ui.components.AttrBuilder,
                com.adobe.granite.ui.components.Config,
                com.day.cq.commons.date.RelativeTimeFormat,
                com.day.cq.wcm.api.Page,
                com.day.cq.wcm.msm.api.LiveRelationship,
                com.day.cq.wcm.msm.api.LiveRelationshipManager,
                com.day.cq.wcm.msm.api.LiveStatus,
                com.day.cq.wcm.msm.api.MSMNameConstants,
                com.day.cq.wcm.msm.api.RolloutManager" %>
<%
%>
<%@include file="/libs/granite/ui/global.jsp" %>
<%
    String blueprintPath = slingRequest.getRequestPathInfo().getSuffix();
    String warning = "WARNING! There are components with broken inheritance.";
    String inheritanceCheck = "";
    Resource blueprint = resourceResolver.getResource(blueprintPath);
    if (blueprint == null) {
        return;
    }

    Config cfg = cmp.getConfig();
    ResourceBundle resourceBundle = slingRequest.getResourceBundle(slingRequest.getLocale());
    LiveRelationshipManager relationshipManager = sling.getService(LiveRelationshipManager.class);

    // Feature toggle allowing sorting on last rollout date, last modified date and other columns
    ToggleRouter toggleRouter = sling.getService(ToggleRouter.class);
    boolean isSortFeatureEnabled = toggleRouter.isEnabled("ft-cq-4305638");

    RelativeTimeFormat relativeTime = new RelativeTimeFormat(RelativeTimeFormat.SHORT, resourceBundle);
    RangeIterator relations = relationshipManager.getLiveRelationships(blueprint, null, RolloutManager.Trigger.ROLLOUT);

    // Data Structure to hold container components that can contain components inside them

    // Sorting of all relations on last rollout date is done only when feature is enabled
    Iterator relationsSorted;
    if (isSortFeatureEnabled) {
        relationsSorted = sortByLastRolloutDate(relations);
    } else {
        relationsSorted = relations;
    }

    long relationsSize = relations.getSize();
    long maxSize = 40;
    boolean allowSorting = false;
    if ("true".equals(request.getParameter("all"))) {
        maxSize = relationsSize;
        allowSorting = true;
    } else {
        try {
            maxSize = Long.parseLong(request.getParameter("maxSize"));
        } catch (Exception e) {
        }
    }

    if (relationsSize <= maxSize) { //check edge case here when count is same
        allowSorting = true;
    }

    // Client side sorting is supported only when feature is enabled
    AttrBuilder sortableAttr = new AttrBuilder(slingRequest, xssAPI);
    if (allowSorting && isSortFeatureEnabled) {
        sortableAttr.set("sortable", "");
    }
%>


<div style="color:#FA0F00;font-size:14px;display:none;" id="inheritance-warning"> <%= warning %></div>
<div class="live-copy-list foundation-items-collection" data-live-copy-list-size="<%= relationsSize %>">

    <table is="coral-table" selectable multiple>
        <colgroup>
            <col is="coral-table-column" fixedwidth>
            <col is="coral-table-column" <%=sortableAttr.build()%>>
            <col is="coral-table-column" <%=sortableAttr.build()%> >
            <col is="coral-table-column" <%=sortableAttr.build()%> >
            <col is="coral-table-column" <%=sortableAttr.build()%> >
        </colgroup>
        <thead is="coral-table-head" class="card-page grid-0" sticky>
        <tr is="coral-table-row" class="label">
            <th is="coral-table-headercell" class="live-copy-list-table-headercell" alignment="left">
                <div class="select-option">
                    <coral-checkbox class="select-rollout" coral-table-select><%
                        if (relationsSize > maxSize) {
                    %><%= xssAPI.filterHTML(i18n.get("All ({0})", "{0} is the number of all items", relationsSize)) %><%
                    } else {
                    %><%= xssAPI.filterHTML(i18n.get("All")) %><%
                        }%>
                    </coral-checkbox>
                </div>
            </th>
            <th is="coral-table-headercell">
                <div class="status">
                    <%= xssAPI.filterHTML(i18n.get("Status")) %>
                </div>
            </th>
            <th is="coral-table-headercell">
                <div class="main">
                    <%= xssAPI.filterHTML(i18n.get("Name")) %>
                </div>
            </th>
            <th is="coral-table-headercell">
                <div class="rollout">
                    <%= xssAPI.filterHTML(i18n.get("Last Rollout")) %>
                </div>
            </th>

            <th is="coral-table-headercell">
                <div class="modified">
                    <%= xssAPI.filterHTML(i18n.get("Modified")) %>
                </div>
            </th>
            <th is="coral-table-headercell">
                <div class="">
                    <%= xssAPI.filterHTML(i18n.get("Component Inheritance Status")) %>
                </div>
            </th>
            <th is="coral-table-headercell">
                <div class="">
                    <%= xssAPI.filterHTML(i18n.get("Component Instances List")) %>
                </div>
            </th>
        </tr>
        </thead>
        <tbody is="coral-table-body" class="live-copy-list-items card-page grid-1">


        <%

            long itemsLeft = maxSize;
            while (relationsSorted.hasNext()) {
                if (itemsLeft <= 0) {
                    break;
                }
                itemsLeft--;
                LiveRelationship lr = (LiveRelationship) relationsSorted.next();
                LiveStatus lrStatus = lr.getStatus();
                String targetPath = lr.getTargetPath();

                String sourcePath = lr.getSourcePath();
                Boolean isCancelled = lrStatus.isCancelled();
                if (isCancelled == null) {
                    isCancelled = Boolean.FALSE;
                }
                String iconStatus = isCancelled ? "linkOff" : "link";
                String iconTitle = isCancelled ? "Suspended" : "Synchronized";
                Resource targetResource = resourceResolver.resolve(targetPath);

                String componentJcrRootPath = targetPath +"/jcr:content";
                Resource componentJcrRootPathResource = resourceResolver.resolve(componentJcrRootPath);
                                String inheritanceStatus ="Inheritance Not broken";
                 List compList =new ArrayList();
                if(componentJcrRootPathResource!=null && componentJcrRootPathResource.hasChildren()){

                Iterator<Resource> rootResIter = componentJcrRootPathResource.listChildren();
                if(rootResIter !=null){

                	while(rootResIter.hasNext()){

                    //String componentRootPath = targetPath +"/jcr:content/root";
                    Resource componentRootPathResource = rootResIter.next();
                    ValueMap rootProps = componentRootPathResource.adaptTo(ValueMap.class);
                  		if(rootProps.containsKey("jcr:mixinTypes") ){
                             String[] rootArr = rootProps.get("jcr:mixinTypes",String[].class);
                                  if(Arrays.asList(rootArr).contains("cq:LiveSyncCancelled")){
                                             inheritanceStatus= "Inheritance broken";
                                             inheritanceCheck= inheritanceStatus;
                                             compList.add(componentRootPathResource.getName());


                                    }

                        }
                      if(componentRootPathResource!=null && componentRootPathResource.hasChildren()){
                      Iterator<Resource> resIter = componentRootPathResource.listChildren();
                     			if(resIter !=null){

                        			while(resIter.hasNext()){
                            			Resource childComponents = resIter.next();
                            				if(childComponents!=null){
                                				ValueMap props = childComponents.adaptTo(ValueMap.class);

                               						 if(props.containsKey("jcr:mixinTypes") ){
                                      						String[] arr = props.get("jcr:mixinTypes",String[].class);
                                         						if(Arrays.asList(arr).contains("cq:LiveSyncCancelled")){
                                                                     inheritanceStatus= "Inheritance broken";
                                                                     inheritanceCheck= inheritanceStatus;
                                                                     compList.add(childComponents.getName());


                                                                  }

                                                       }

                    if(childComponents.hasChildren() && props.containsKey("sling:resourceType") ){

                               if(childComponents!=null && childComponents.hasChildren()){
                  					Iterator<Resource> childresIter = childComponents.listChildren();
                     					if(childresIter !=null){

                        						while(childresIter.hasNext()){
                            						Resource childComps = childresIter.next();
                            							if(childComps!=null){
                                							ValueMap childProps = childComps.adaptTo(ValueMap.class);
                                  								if(childProps.containsKey("jcr:mixinTypes") ){
                                      								String[] childArr = childProps.get("jcr:mixinTypes",String[].class);
                                         								if(Arrays.asList(childArr).contains("cq:LiveSyncCancelled")){
                                             								inheritanceStatus= "Inheritance broken";
                                                                            inheritanceCheck= inheritanceStatus;
                                             								compList.add(childComps.getName());


                                                                          }

                                  								}
                            							}
                        					}

                    			}
                }

                Iterator<Resource> secondLevelChildresIter = childComponents.listChildren();
                     if(secondLevelChildresIter !=null){

                        while(secondLevelChildresIter.hasNext()){
                            Resource secondLevelChildComps = secondLevelChildresIter.next();
                	            if(secondLevelChildComps!=null){
                    		           ValueMap childProps = secondLevelChildComps.adaptTo(ValueMap.class);
                            			    if(secondLevelChildComps.hasChildren() && childProps.containsKey("sling:resourceType") ){

                               						 if(secondLevelChildComps!=null && secondLevelChildComps.hasChildren()){
                  										Iterator<Resource> subchildresIter = secondLevelChildComps.listChildren();
                     										if(subchildresIter !=null){

                        										while(subchildresIter.hasNext()){
                            										Resource subchildComps = subchildresIter.next();
                            											if(subchildComps!=null){
                                											ValueMap subChildProps = subchildComps.adaptTo(ValueMap.class);
                                  												if(subChildProps.containsKey("jcr:mixinTypes") ){
                                      													String[] subChildArr = subChildProps.get("jcr:mixinTypes",String[].class);
                                         														if(Arrays.asList(subChildArr).contains("cq:LiveSyncCancelled")){
                                            														 	inheritanceStatus= "Inheritance broken";
                                                                                                        inheritanceCheck= inheritanceStatus;
                                             															compList.add(subchildComps.getName());


                                         															}

                                  												}
                            											}
                        									}

                    						}
                				}
                       }
                     }
                   }
                }
              }
            }

          }
        }
	}
	}
	}
    }
                Page lcPage = null;
                if (!ResourceUtil.isNonExistingResource(targetResource)) {
                    lcPage = targetResource.adaptTo(Page.class);
                }

                String lastModifUser = AuthorizableUtil.getFormattedName(resourceResolver, lcPage != null
                        ? lcPage.getLastModifiedBy()
                        : "");

                String lastRolloutUser = AuthorizableUtil.getFormattedName(resourceResolver, lrStatus != null
                        ? lrStatus.getLastRolledOutBy()
                        : "");
                lastRolloutUser = lastRolloutUser == null ? "" : lastRolloutUser;

                Calendar lastModifDateRaw = lcPage != null
                        ? lcPage.getLastModified()
                        : null;
                String modifiedDate = lastModifDateRaw == null ?
                        i18n.get("never") :
                        relativeTime.format(lastModifDateRaw.getTimeInMillis(), true);

                Date lastRolloutDateRaw = lrStatus != null
                        ? lrStatus.getLastRolledOut()
                        : null;

                String lastRolloutDate = lastRolloutDateRaw == null ?
                        i18n.get("never") :
                        relativeTime.format(lastRolloutDateRaw.getTime(), true);

                AttrBuilder rowAttrs = new AttrBuilder(slingRequest, xssAPI);
                if(!isCancelled) {
                    rowAttrs.add("selected", "");
                }

                String lastModifiedDateTimestamp = lastModifDateRaw != null
                        ? String.valueOf(lastModifDateRaw.getTimeInMillis())
                        : "";
                String lastRolloutDateTimestamp = lastRolloutDateRaw != null
                        ? String.valueOf(lastRolloutDateRaw.getTime())
                        : "";

                String lctitle = lcPage != null ? lcPage.getTitle() : targetResource.getName();


        %>

        <%!

        public Boolean checkCancelledIheritance(Resource childRes){


           if(childRes!=null && childRes.hasChildren()){
                  Iterator<Resource> childresIter = childRes.listChildren();
                     if(childresIter !=null){

                        while(childresIter.hasNext()){
                            Resource childComps = childresIter.next();
                            if(childComps!=null){
                                ValueMap childProps = childComps.adaptTo(ValueMap.class);
                                  if(childProps.containsKey("jcr:mixinTypes") ){
                                      String[] childArr = childProps.get("jcr:mixinTypes",String[].class);
                                         if(Arrays.asList(childArr).contains("cq:LiveSyncCancelled")){
                                             return true;


                                         }

                                  }
                            }
                        }

                    }
                }


return false;

        }

        %>



        <tr is="coral-table-row" class="label" <%=rowAttrs.build()%> data-path="<%=xssAPI.encodeForHTMLAttr(targetPath)%>">
            <td is="coral-table-cell" class="select-option" alignment="left">
                <coral-checkbox class="select-rollout" coral-table-rowselect></coral-checkbox>
            </td>
            <td is="coral-table-cell" class="status" value="<%=iconStatus %>">
                <coral-icon icon="<%=iconStatus %>" title="<%=xssAPI.encodeForHTMLAttr(i18n.get(iconTitle))%>" size="M">
                </coral-icon>
            </td>
            <td is="coral-table-cell" role="rowheader" class="main" value="<%=xssAPI.encodeForHTMLAttr(targetPath)%>">
                <p class="full-info">
                        <span class="title">
                            <%= xssAPI.encodeForHTML(lctitle) %>
                        </span><br>
                        <span class="path">
                            <%= xssAPI.encodeForHTML(targetPath) %>
                        </span>
                </p>
            </td>
            <td is="coral-table-cell" class="last-rollout"
                value="<%=xssAPI.encodeForHTMLAttr(lastRolloutDateTimestamp)%>">
                <p class="rollout" title="Last Rollout Data">
                        <span class="date" data-timestamp="<%=xssAPI.encodeForHTMLAttr(lastRolloutDateTimestamp)%>">
                            <%=xssAPI.encodeForHTML(lastRolloutDate) %>
                        </span><br>
                        <span class="user">
                            <%=lastRolloutUser %>
                        </span>
                </p>
            </td>
            <td is="coral-table-cell" class="info all" value="<%=xssAPI.encodeForHTMLAttr(lastModifiedDateTimestamp)%>">
                <p class="modified" title="Page Modification Data">
                        <span class="date" data-timestamp="<%=xssAPI.encodeForHTMLAttr(lastModifiedDateTimestamp)%>">
                            <%=xssAPI.encodeForHTML(modifiedDate) %>
                        </span><br>
                        <span class="user">
                            <%=lastModifUser %>
                        </span>
                </p>
            </td>

<td is="coral-table-cell" class="status" value="<%=iconStatus %>" alignment="center"><%=xssAPI.encodeForHTML(inheritanceStatus) %></td>
<td is="coral-table-cell" class="status"  alignment="center">


<%if(compList.size()>0){%>
<select name = "comps">
<option value ="Component Name"> Component Name</option>
<% for(int i=0;i<compList.size();i++){
%>
<option value ="<%=compList.get(i)%>"><%=compList.get(i)%></option>
<%}%>

</select>
<%}else{%>
Component Instances Inheritance Intact

<%}%>

            </td>

        </tr>
        <%
            }
        %>
        </tbody>
    </table>
</div>
<%
    if (relationsSize > maxSize) {
%>
<a is="coral-anchorbutton" href="?all=true">
    <%= i18n.get("Load All Live Copies") %>
</a>
<%
    }
%>
<%!
    /**
     * Sort the entire list of liveRelationships on last rolled out date time,
     * The most recently rolled out live copy will be shown on the top.
     * @param relations unsorted liveRelationships
     * @return sorted Iterator or liveRelationships
     */
    private Iterator sortByLastRolloutDate(RangeIterator relations) {
        Logger log = LoggerFactory.getLogger(getClass());
        long sortingStartTime = System.currentTimeMillis();
        List liveRelations = new ArrayList<LiveRelationship>();
        while (relations.hasNext()) {
            LiveRelationship lr = (LiveRelationship) relations.next();
            liveRelations.add(lr);
        }
        Collections.sort(liveRelations, new SortByLastRollout());
        long sortingEndTime = System.currentTimeMillis();
        log.info("Sorting of total {} livecopies for rollout dialog took {} ms",
                liveRelations.size(), (sortingEndTime - sortingStartTime));
        return liveRelations.iterator();
    }

    private class SortByLastRollout implements Comparator<LiveRelationship> {

        /**
         * Sort the LiveRelationships in ascending order of last rollout date time.
         * The live copy which was most recently rolled out will be shown on the top of the list
         * @param a relationship first
         * @param b relationship second
         * @return -1 when a was rolled out recently then b, 0 when both were rolled out at the same time and 1 when b
         * was rolled out recently then a
         */
        public int compare(LiveRelationship a, LiveRelationship b) {
            LiveStatus statusOfa = a.getStatus();
            LiveStatus statusOfb = b.getStatus();
            Date lastRolloutOfA = statusOfa.getLastRolledOut();
            Date lastRolloutOfB = statusOfb.getLastRolledOut();

            // In case last rollout is null which means the live copy was never rolled out
            long lastRolloutOfAms = lastRolloutOfA != null ? lastRolloutOfA.getTime() : 0;
            long lastRolloutOfBms = lastRolloutOfB != null ? lastRolloutOfB.getTime() : 0;

            if (lastRolloutOfAms > lastRolloutOfBms) {
                return -1;
            } else if (lastRolloutOfAms == lastRolloutOfBms) {
                return 0;
            }
            return 1;
        }
    }
%>

<script type="text/javascript">
var warningDiv= document.getElementById('inheritance-warning');
var inheritanceVal = "<%= inheritanceCheck %>";
if(inheritanceVal =="Inheritance broken"){
warningDiv.style.display ="block";
}

</script>