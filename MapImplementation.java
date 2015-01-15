package org.adc.phase1;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MapImplementation {

	Map<String, String> map = Collections
			.synchronizedMap(new HashMap<String, String>());

	public MapImplementation() {
		map.put("123", "abc");
		map.put("456", "cde");
		map.put("980", "pom");
		map.put("783", "ewq");
		map.put("890", "mnb");
	}

	public void put(String key, String value) {
		map.put(key, value);
	}

	public String get(String key) {
		return map.get(key);
	}

	public void delete(String key) {
		map.remove(key);
	}
}
