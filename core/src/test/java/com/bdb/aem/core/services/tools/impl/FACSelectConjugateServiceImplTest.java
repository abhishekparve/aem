package com.bdb.aem.core.services.tools.impl;

import static org.mockito.Mockito.lenient;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.function.Consumer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.models.FACConjugateColumns;
import com.bdb.aem.core.models.FACConjugateRows;
import com.bdb.aem.core.models.FACSelectConjugateModel;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class FACSelectConjugateServiceImplTest {
	@InjectMocks
	FACSelectConjugateServiceImpl facSelectConjugateServiceImpl;
	@Mock
	FACSelectConjugateModel slingModel;
	@Mock
	List<FACConjugateRows> rows=new ArrayList<>();
	@Mock
	List<FACConjugateColumns> columns=new ArrayList<>();
	@Mock
	FACConjugateRows facConjugateRows;
	@Mock
	FACConjugateColumns facConjugateColumns;
	@Mock
	ListIterator<FACConjugateColumns> columnsItr;
	
	@Test
	void test() throws Exception {
		columns.add(facConjugateColumns);
		PrivateAccessor.setField(slingModel, "rows", rows);
		lenient().when(slingModel.getConjugateRows()).thenReturn(rows);
		lenient().when(rows.size()).thenReturn(1);
		lenient().when(rows.get(0)).thenReturn(facConjugateRows);
		lenient().when(facConjugateRows.getColumns()).thenReturn(columns);
		//lenient().when(columns.forEach(Mockito.any(Consumer)));
		lenient().when(facConjugateColumns.getColHeading()).thenReturn("colHeading");
		lenient().when(columns.listIterator()).thenReturn(columnsItr);
		lenient().when(columnsItr.hasNext()).thenReturn(true,false);
		lenient().when(columnsItr.next()).thenReturn(facConjugateColumns);
		facSelectConjugateServiceImpl.updateSlingModel(slingModel);
	}
	
}
