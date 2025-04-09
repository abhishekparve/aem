package com.bdb.aem.core.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;

@Model(adaptables = { SlingHttpServletRequest.class,
		Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class FACSelectTextModel extends BaseSlingModel {

	/** The constant Serial Version UUID */
	private static final long serialVersionUID = 4275311619615770730L;
	
	@ChildResource
	private List<FACGatingImages> gatingImages;

	@ChildResource
	private List<FACGatingImages> notesDescription;

	@ChildResource
	private List<FACGatingImages> permBuffers;

	@ChildResource
	private List<FACGatingImages> entrezLinks;
	
	/**
	 * Gets the Gating Images.
	 * 
	 * @return the gating images
	 */
	public List<FACGatingImages> getGatingImages() {
		return Optional.ofNullable(gatingImages).orElseGet(ArrayList::new);
	}

	/**
	 * Gets the Notes Description.
	 * 
	 * @return the notes description
	 */
	public List<FACGatingImages> getNotesDescription() {
		return Optional.ofNullable(notesDescription).orElseGet(ArrayList::new);
	}

	/**
	 * Gets the Perm Buffers.
	 * 
	 * @return the perm buffers
	 */
	public List<FACGatingImages> getPermBuffers() {
		return Optional.ofNullable(permBuffers).orElseGet(ArrayList::new);
	}

	/**
	 * Gets the entrez links.
	 * 
	 * @return the entrez links
	 */
	public List<FACGatingImages> getEntrezLinks() {
		return Optional.ofNullable(entrezLinks).orElseGet(ArrayList::new);
	}

}