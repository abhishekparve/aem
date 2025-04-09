package com.bdb.aem.core.services.tools.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Component;

import com.bdb.aem.core.models.FACReportsModel;
import com.bdb.aem.core.models.FACSelectListModel;
import com.day.cq.wcm.api.Page;

/**
 * The FAC List Service Impl.
 * 
 * @author ronbanerjee
 *
 */
@Component(service = FACSelectListServiceImpl.class)
public class FACSelectListServiceImpl extends AbstractSlingModelService<FACSelectListModel> {

	/**
	 * @see AbstractSlingModelService#updateSlingModel(com.bdb.aem.core.models.BaseSlingModel)
	 */
	@Override
	protected void updateSlingModel(FACSelectListModel slingModel) {

		List<FACReportsModel> l = new ArrayList<>();
		ResourceResolver r = slingModel.getResolver();
		Optional.ofNullable(slingModel).map(s -> s.getCurrentPage()).ifPresent(p -> {

			Iterator<Page> pItr = p.listChildren();
			if (pItr != null) {
				while (pItr.hasNext()) {
					Page nP = pItr.next();
					FACReportsModel m = getFacResource(nP, r) == null ? null
							: getFacResource(nP, r).adaptTo(FACReportsModel.class);
					if (m != null) {
						m.setComponentId(null);
						l.add(m);

					}
				}
			}
		});

		slingModel.setReportsList(l);
	}

	/**
	 * Gets the FAC Resource.
	 * 
	 * @param page the page
	 * @param r    the resource resolver
	 * @return the resource
	 */
	private Resource getFacResource(Page page, ResourceResolver r) {

		Iterator<Resource> rItr = Optional.ofNullable(getResourceFromPage(page, r)).map(res -> res.listChildren()).orElse(null);
				
		if (rItr != null) {
			while (rItr.hasNext()) {
				Resource nR = rItr.next();
				if (nR.isResourceType("bdb-aem/components/content/tools/facselect/reports")) {
					return nR;
				}
			}
		}
		return null;

	}

	/**
	 * Gets the Resource from Page.
	 * 
	 * @param page the page
	 * @param r    the resource resolver
	 * @return the resource
	 */
	private Resource getResourceFromPage(Page page, ResourceResolver r) {
		return Optional.ofNullable(page).map(p -> p.getPath()).map(pth -> pth.concat("/jcr:content/root"))
				.map(u -> r.getResource(u)).orElse(null);
	}

}
