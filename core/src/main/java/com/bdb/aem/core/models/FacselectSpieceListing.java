package com.bdb.aem.core.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.ExporterOption;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.bdb.aem.core.services.tools.impl.FACReportsServiceImpl;
import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import com.day.cq.wcm.api.Page;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Model(adaptables = { SlingHttpServletRequest.class, Resource.class }, resourceType = {
"bdb-aem/components/content/tools/facselect/speciesListing" }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = "jackson", extensions = "json", options = {
@ExporterOption(name = "SerializationFeature.WRITE_DATES_AS_TIMESTAMPS", value = "true") })
public class FacselectSpieceListing extends BaseSlingModel {

	/** The constant Serial Version UUID */
	private static final long serialVersionUID = -6570419121067882355L;
	
	@ValueMapValue
	private String rootPath;
	
	@ValueMapValue
	private String spieceName;
	
	@OSGiService
	private transient FACReportsServiceImpl facSelectService;
	
	@ChildResource
	private List<FlurochromeModel> flurocrome;
	
	
	private Boolean flag = false;

	@JsonProperty("dataList")
	@JsonInclude(Include.NON_NULL)
	public List<Object> getFlurochromeMappingList() {
		List<Object> fluorochromes = new ArrayList<>();
		ArrayList<String> pagePath = getSpiecePageList();
		
		if(null!=pagePath) {
			for(String page : pagePath) {
				Resource pageRes = getResolver().getResource(page);
				ValueMap vm = pageRes.getValueMap();
				Map<String,String> mp = new HashMap<String, String>();
				FACReportsModel sm = pageRes.adaptTo(FACReportsModel.class);
				Map<String,Object> map  = new HashMap<String, Object>();
				List<Map<String, Serializable>> list = setFluorochromes(sm);
				for(int i =0; i<list.size();i++) {
					 Object flagvalue = list.get(i).get("flag");
					 if(null!=flagvalue) {
							if(flagvalue.toString().equalsIgnoreCase("true")) {
								list.remove(i);
								mp.put("clone", vm.get("clone").toString());
								mp.put("target", vm.get("target").toString());
								map.put("specificity", mp);
								map.put("fluorochromes", list);
								fluorochromes.add(map);
							}
						}
				}											
			}
		}
		
		return fluorochromes;
	}
	
	@JsonIgnore
	public ArrayList<String> getSpiecePageList() {
		ArrayList<String> childPagePath = new ArrayList<>();
		if(null!=rootPath && null!=spieceName) {
			Resource rootRes = getResolver().getResource(rootPath);
			if(null!=rootRes && rootRes.hasChildren()) {
				Iterable<Resource> childRes = rootRes.getChildren();
				for(Resource nodeRes : childRes) {
					Resource selectedSpiece = getResolver().getResource(nodeRes.getPath()+"/jcr:content/root/report/selectedSpecies");
					if(null!=selectedSpiece && selectedSpiece.hasChildren()) {
						Iterable<Resource> selectedSpieceChild = selectedSpiece.getChildren();
						for(Resource spiece : selectedSpieceChild) {
							ValueMap vm = spiece.getValueMap();
							if(spieceName.trim().equalsIgnoreCase(vm.get("speciesLabel").toString().trim())) {
								childPagePath.add(spiece.getParent().getParent().getPath());
							}
						}
					}
				}
			}
		}		
		return childPagePath;
	}
	@JsonIgnore
	private List<Map<String, Serializable>> setFluorochromes(FACReportsModel sm) {
		List<Map<String, Serializable>> fList = new ArrayList<>();
		Map<String, Serializable> flagMap = new HashMap<>();
		// TBD: The tag path '/content/cq:tags/facselect' has to be read from OSGi
		// config.
		Iterator<Tag> ts = Optional.ofNullable(sm).map(s -> s.getResolver())
				.map(r -> this.getChildTags(r, "/content/cq:tags/facselect")).orElse(null);
		if (ts != null) {
			while (ts.hasNext()) {
				Tag currentTag = ts.next();
					if(!currentTag.getName().equalsIgnoreCase("green")) {
						Map<String, Serializable> fluorochromes = new HashMap<>();
						fluorochromes.put("band", checkNull(currentTag.getName()));
						fluorochromes.put("bandName", checkNull(currentTag.getTitle()));
						fluorochromes.put("items", setItemsList(currentTag, sm));
						fList.add(fluorochromes);				
					}
				}
				
		}
		flagMap.put("flag",getFlag());
		fList.add(flagMap);
		setFlag(false);
		return fList;
	}
	
	
	
	@JsonIgnore
	private Iterator<Tag> getChildTags(ResourceResolver resolver, String tag) {
		return Optional.ofNullable(resolver).map(r -> r.adaptTo(TagManager.class)).map(tm -> tm.resolve(tag))
				.map(t -> t.listChildren()).orElse(null);
	}
	@JsonIgnore
	private String checkNull(String val) {
		return Optional.ofNullable(val).orElse(StringUtils.EMPTY);
	}
	@JsonIgnore
	private ArrayList<Map<String, Serializable>> setItemsList(Tag currentTag, FACReportsModel sm) {
		ArrayList<Map<String, Serializable>> itemsList = new ArrayList<>();

		final TagManager tm = Optional.ofNullable(sm).map(s -> s.getResolver()).map(r -> r.adaptTo(TagManager.class))
				.orElse(null);
		Iterator<Tag> ts = Optional.ofNullable(sm).map(s -> s.getResolver())
				.map(r -> this.getChildTags(r, currentTag.getPath())).orElse(null);
		if (ts != null) {
			while (ts.hasNext()) {
				Tag currentChildTag = ts.next();
				if(null != sm) {
					itemsList.add(handleItems(sm.getSelectedFluorochromes(), currentChildTag, tm));
				}
			}
		}
		return itemsList;

	}
	@JsonIgnore
	private Map<String, Serializable> handleItems(List<FluorochromesModel> selectedFluorochromes, Tag currentChildTag,
			TagManager tm) {
		Map<String, Serializable> items = new HashMap<>();
		List<String> exists = new ArrayList<>();
		if (!selectedFluorochromes.isEmpty() && tm != null) {
			selectedFluorochromes.forEach(i -> {
				Tag t = tm.resolve(i.getFname());
				if (t.getPath().equals(currentChildTag.getPath())) {
					items.put("label", checkNull(t.getTitle()));
					items.put("reagentsDataAvailable", i.getData());
					items.put("onlyReagentsAvailable", i.getReagent());
					items.put("notSuggestedAvailable", i.getNotSuggested());
					items.put("tableValue", i.getTableValue());
					exists.add(currentChildTag.getPath());
					if(!getFlag() && StringUtils.isNotEmpty(i.getTableValue())) {
						setFlag(true);
					}
				} else if (!exists.contains(currentChildTag.getPath())) {
					items.put("label", checkNull(currentChildTag.getTitle()));
					items.put("reagentsDataAvailable", false);
					items.put("onlyReagentsAvailable", false);
					items.put("notSuggestedAvailable", false);
					items.put("tableValue", null);
				}
			});
		} else if (selectedFluorochromes.isEmpty()) {
			items.put("label", checkNull(currentChildTag.getTitle()));
			items.put("reagentsDataAvailable", false);
			items.put("onlyReagentsAvailable", false);
			items.put("notSuggestedAvailable", false);
			items.put("tableValue", null);
		}
		return items;
	}
	
	@JsonIgnore
	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}
	
	@JsonIgnore
	public List<FlurochromeModel> getFlurocrome() {
		return Optional.ofNullable(flurocrome).orElseGet(ArrayList::new);
	}
	
	@JsonIgnore
	public String getFacDataEndpoint() {
		return Optional.ofNullable(page).map(Page::getPath)
				.map(p -> p.concat("/jcr:content/root/specieslisting.model.tidy.json")).orElse(StringUtils.EMPTY);
	}
}
