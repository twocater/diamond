package com.twocater.diamond.api.service.json;

import java.util.List;
import java.util.Map;

public interface JsonEntry {
    // ��ȡ���� Object��String��Long��Double��Boolean����List��Map
    List<Object> getListEntry();

    boolean containListEntry(Object item);

    boolean containMapEntry(String name);

    Map<String, Object> getMapEntry();

}
