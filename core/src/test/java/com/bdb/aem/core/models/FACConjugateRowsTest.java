package com.bdb.aem.core.models;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class FACConjugateRowsTest {
	@InjectMocks
	FACConjugateRows facConjugateRows;
	@Mock
	FACConjugateColumns facConjugateColumns;
	@Mock
	List<FACConjugateColumns> columns;

	@Test
	void testAllGettersNull() {
		facConjugateRows.getRowHeading();
		facConjugateRows.getColumns();

	}

	@Test
	void testAllGetters() throws Exception {
		PrivateAccessor.setField(facConjugateRows, "rowHeading", "rowHeading");
		PrivateAccessor.setField(facConjugateRows, "columns", columns);
		facConjugateRows.getRowHeading();
		facConjugateRows.getColumns();
	}
}
