package com.twocater.diamond.api.service.json;

import java.util.List;
import java.util.Map;

public interface JsonEntryBuilder extends JsonEntry {
	void setListEntry(List<Object> list);

	void setMapEntry(Map<String, Object> map);

	void addListEntry(Object item);

	void addListEntrys(List<Object> items);

	void removeListEntry(Object item);

	void addMapEntry(String name, Object value);

	void addMapEntrys(Map<String, Object> entry);

	void removeMapEntry(String name);

	void clear();
}
