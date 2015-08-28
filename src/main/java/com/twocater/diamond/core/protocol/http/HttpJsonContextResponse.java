package com.twocater.diamond.core.protocol.http;

import com.twocater.diamond.api.protocol.http.HttpResponse;
import com.twocater.diamond.api.service.http.HttpJsonResponse;
import com.twocater.diamond.api.service.json.JsonEntryData;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author cpaladin
 */
class HttpJsonContextResponse extends HttpContextResponse implements HttpJsonResponse {
    private JsonEntryData dataEntry = new JsonEntryData();
    private byte[] data;

    public HttpJsonContextResponse(HttpResponse httpResponse) {
        super(httpResponse);
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public JsonEntryData getJsonEntryData() {
        return dataEntry;
    }


    @Override
    public void setListEntry(List<Object> list) {
        dataEntry.setListEntry(list);
    }

    @Override
    public void setMapEntry(Map<String, Object> map) {
        dataEntry.setMapEntry(map);
    }

    @Override
    public void addListEntry(Object item) {
        dataEntry.addListEntry(item);
    }

    @Override
    public void addListEntrys(List<Object> items) {
        dataEntry.addListEntrys(items);
    }

    @Override
    public void removeListEntry(Object item) {
        dataEntry.removeListEntry(item);
    }

    @Override
    public void addMapEntry(String name, Object value) {
        dataEntry.addMapEntry(name, value);
    }

    @Override
    public void addMapEntrys(Map<String, Object> entry) {
        dataEntry.addMapEntrys(entry);
    }

    @Override
    public void removeMapEntry(String name) {
        dataEntry.removeMapEntry(name);
    }

    @Override
    public void clear() {
        dataEntry.clear();
    }

    @Override
    public List<Object> getListEntry() {
        List<Object> list = dataEntry.getListEntry();
        if (list == null) {
            return null;
        }
        return Collections.unmodifiableList(list);
    }

    @Override
    public boolean containListEntry(Object item) {
        return dataEntry.containListEntry(item);
    }

    @Override
    public boolean containMapEntry(String name) {
        return dataEntry.containMapEntry(name);
    }

    @Override
    public Map<String, Object> getMapEntry() {
        Map<String, Object> map = dataEntry.getMapEntry();
        if (map == null) {
            return null;
        }
        return Collections.unmodifiableMap(map);
    }
}
