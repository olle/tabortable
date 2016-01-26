package tabortable.tables;

import java.util.HashMap;
import java.util.Map;

public final class Column {

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

	public Map<String, Object> asMap() {

		Map<String, Object> map = new HashMap<>();
		map.put(name, value);
		return map;
	}

}