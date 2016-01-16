package tabortable.tables;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

public class TableTest {

	@Test
	public void ensureReturnsObjectValueAsStringWhenNonNull() {

		assertEquals("bar", Table.getValueOrNullAsString(Arrays.asList("foo", "bar", null), 1));
	}

	@Test
	public void ensureReturnsNullAsStringForNullValueInList() throws Exception {

		assertEquals("null", Table.getValueOrNullAsString(Arrays.asList("foo", "bar", null), 2));
	}

}
