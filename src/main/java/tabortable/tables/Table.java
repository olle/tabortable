package tabortable.tables;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Table {

	public final String name;
	private List<Row> rows = new ArrayList<>();
	private List<Column> header = new ArrayList<>();

	public Table(String name) {

		this.name = name;
	}

	public List<Row> getRows() {

		return rows;
	}

	public void addRow(List<List<Object>> cols) {

		rows.add(new Row(cols.stream().map(this::tripleToColumn).collect(Collectors.toList())));
	}

	private Column tripleToColumn(List<Object> col) {
		String name = col.get(0).toString();
		String type = col.get(1).toString();
		String value = col.get(2) == null ? "null" : col.get(2).toString();
		return new Column(name, type, value);
	}

	public List<Column> getColumns() {

		return header;
	}

	public void addColumn(String name, String type) {

		header.add(new Column(name, type));
	}

	public static final class Row {

		public final List<Column> columns;

		public Row(List<Column> columns) {

			this.columns = columns;
		}

	}

	public static final class Column {

		public final String name;
		public final String type;
		public final String value;

		public Column(String name, String type) {

			this(name, type, "");
		}

		public Column(String name, String type, String value) {

			this.name = name;
			this.type = type;
			this.value = value;
		}

	}

}