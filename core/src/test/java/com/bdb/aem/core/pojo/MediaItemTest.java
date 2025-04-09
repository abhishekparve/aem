package com.bdb.aem.core.pojo;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

// TODO: Auto-generated Javadoc
/**
 * The Class MediaItemTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class MediaItemTest {

	/** The media item. */
	@InjectMocks
	MediaItem mediaItem;

	/** The base material number. */
	private String baseMaterialNumber;

	/** The image name. */
	private String imageName;

	/** The caption. */
	private String caption;

	/** The mime. */
	private String mime;

	/** The image metadata. */
	private String imageMetadata;

	/** The image type. */
	private String imageType;

	/**
	 * Setup.
	 *
	 * @throws NoSuchFieldException the no such field exception
	 */
	@BeforeEach
	void setup() throws NoSuchFieldException {
		PrivateAccessor.setField(mediaItem, "baseMaterialNumber", "baseMaterialNumber");
		PrivateAccessor.setField(mediaItem, "imageName", "imageName");
		PrivateAccessor.setField(mediaItem, "caption", "caption");
		PrivateAccessor.setField(mediaItem, "mime", "mime");
		PrivateAccessor.setField(mediaItem, "imageMetadata", "imageMetadata");
		PrivateAccessor.setField(mediaItem, "imageType", "imageType");

	}

	/**
	 * Test setters.
	 */
	@Test
	void testSetters() {
		mediaItem.setBaseMaterialNumber(baseMaterialNumber);
		mediaItem.setCaption(caption);
		mediaItem.setImageMetadata(imageMetadata);
		mediaItem.setImageName(imageName);
		mediaItem.setImageType(imageType);
		mediaItem.setMime(mime);
	}

	/**
	 * Test getters.
	 */
	@Test
	void testGetters() {
		assertNotNull(mediaItem.getBaseMaterialNumber());
		assertNotNull(mediaItem.getCaption());
		assertNotNull(mediaItem.getImageMetadata());
		assertNotNull(mediaItem.getImageName());
		assertNotNull(mediaItem.getImageType());
		assertNotNull(mediaItem.getMime());
	}

}
