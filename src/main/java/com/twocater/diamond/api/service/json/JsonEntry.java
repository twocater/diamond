package com.twocater.diamond.api.service.json;

import java.util.List;
import java.util.Map;

public interface JsonEntry {
    // 获取数组 Object是String、Long、Double、Boolean或者List、Map
    List<Object> getListEntry();

    boolean containListEntry(Object item);

    boolean containMapEntry(String name);

    Map<String, Object> getMapEntry();

}
