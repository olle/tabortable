package tabortable.tables;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Table {

	public final String name;
	public final String selected;
	private List<Row> rows = new ArrayList<>();
	private List<Column> header = new ArrayList<>();

	public Table(String name, boolean selected) {

		this.name = name;
		this.selected = selected ? "selected" : "";
	}

	public List<Row> getRows() {

		return rows;
	}

	public void addRow(List<List<Object>> cols) {

		rows.add(new Row(cols.stream().map(this::tripleToColumn).collect(Collectors.toList())));
	}

	private Column tripleToColumn(List<Object> col) {
		String name = getValueOrNullAsString(col, 0);
		String type = getValueOrNullAsString(col, 1);
		String value = getValueOrNullAsString(col, 2);
		return new Column(name, type, value);
	}

	/**
	 * Retrieves the value from the given object list by index
	 * @param objects holding the values to retrieve
	 * @param index of the value to fetch
	 * @return the value as returned by a call {@code toString}, or the string {@code "null"}.
	 */
	public static String getValueOrNullAsString(List<Object> objects, int index) {
		return objects.get(index) == null ? "null" : objects.get(index).toString();
	}

	public List<Column> getColumns() {

		return header;
	}

	public void addColumn(String name, String type) {

		header.add(new Column(name, type));
	}

	@Override
	public String toString() {

		return String.format("%s '%s' %s", getClass().getSimpleName(), name, selected);
	}

}
