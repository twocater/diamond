package com.twocater.diamond.api.service.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonEntryData implements JsonEntryBuilder {
    private Object entryData;
    private boolean builder;

    public JsonEntryData() {
        this.builder = false;
    }

    public JsonEntryData(Object entryData) {
        this.entryData = entryData;
        this.builder = true;
    }

    public Object getEntryData() {
        return this.entryData;
    }

    @Override
    public List<Object> getListEntry() {
        return (List<Object>) entryData;
    }

    @Override
    public boolean containListEntry(Object item) {
        if (!builder || entryData == null) {
            return false;
        }
        return getListEntry().contains(item);
    }

    @Override
    public boolean containMapEntry(String name) {
        if (!builder || entryData == null) {
            return false;
        }
        return getMapEntry().containsKey(name);
    }

    @Override
    public Map<String, Object> getMapEntry() {
        return (Map<String, Object>) entryData;
    }

    @Override
    public void setListEntry(List<Object> list) {
        if (builder) {
            throw new IllegalStateException(this.getClass().getName() + " already set list entry.");
        }
        this.entryData = list;
        builder = true;
    }

    @Override
    public void setMapEntry(Map<String, Object> map) {
        if (builder) {
            throw new IllegalStateException(this.getClass().getName() + " already set map entry.");
        }
        this.entryData = map;
        builder = true;
    }

    @Override
    public void addListEntry(Object item) {
        if (!builder) {
            setListEntry(new ArrayList<Object>());
        }
        getListEntry().add(item);
    }

    @Override
    public void addListEntrys(List<Object> items) {
        if (!builder) {
            setListEntry(new ArrayList<Object>());
        }
        getListEntry().addAll(items);
    }

    @Override
    public void removeListEntry(Object item) {
        if (builder) {
            getListEntry().remove(item);
        }
    }

    @Override
    public void addMapEntry(String name, Object value) {
        if (!builder) {
            setMapEntry(new HashMap<String, Object>());
        }
        getMapEntry().put(name, value);
    }

    @Override
    public void addMapEntrys(Map<String, Object> entry) {
        if (!builder) {
            setMapEntry(new HashMap<String, Object>());
        }
        getMapEntry().putAll(entry);
    }

    @Override
    public void removeMapEntry(String name) {
        if (builder) {
            getMapEntry().remove(name);
        }
    }

    @Override
    public void clear() {
        if (builder) {
            entryData = null;
            builder = false;
        }
    }

    @Override
    public String toString() {
        if (entryData == null) {
            return null;
        }
        return entryData.toString();
    }

}
