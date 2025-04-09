package com.bdb.aem.core.services.tools.impl;

import static org.mockito.Mockito.lenient;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.models.CloneComparisonAntigenModel;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;

/**
 * The Junit test for {@link CloneComparisonAntigenServiceImpl}
 * 
 * @author ronbanerjee
 *
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class CloneComparisonAntigenServiceImplTest {

	@InjectMocks
	private CloneComparisonAntigenServiceImpl antigenService;
	
	@Mock
	private CloneComparisonAntigenModel antigenModel;
	
	@Mock
	private Resource resource;
	
	@Mock 
	private CloneComparisonServiceImpl cloneService;
	
	@Mock
	private Iterator<Resource> itr;
	
	@Mock
	private ValueMap valueMap;
	
	/**
	 * Sets up.
	 */
	@BeforeEach
	public void setup() {
		List<Resource> list = new ArrayList<>();
		list.add(resource);
		
		itr = list.iterator();
		lenient().when(antigenModel.getResource()).thenReturn(resource);
		lenient().when(resource.getChild("targetMolecules")).thenReturn(resource);
		lenient().when(resource.getChild("sortItems")).thenReturn(resource);
		lenient().when(resource.listChildren()).thenReturn(itr);
		
		lenient().when(resource.getValueMap()).thenReturn(valueMap);
		lenient().when(valueMap.get("targetMoleculeOption", String.class)).thenReturn("targetMoleculeOption");
		lenient().when(valueMap.get("text", String.class)).thenReturn("text");
		lenient().when(valueMap.get("value", String.class)).thenReturn("value");
		
		
		

	}
	
	/**
	 * Tests {@link CloneComparisonAntigenServiceImpl#updateSlingModel(CloneComparisonAntigenModel)}
	 */
	@Test
	public void testUpdateSlingModel() {
	
		antigenService.updateSlingModel(antigenModel);
	}
	
	
}
