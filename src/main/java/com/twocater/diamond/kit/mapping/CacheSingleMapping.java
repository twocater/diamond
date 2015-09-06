package com.twocater.diamond.kit.mapping;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CacheSingleMapping implements SingleMapping {

    private final SingleMapping singleMapping;
    private final Map<String, MappingResult> resultMap;

    public CacheSingleMapping(SingleMapping singleMapping) {
        this.singleMapping = singleMapping;
        resultMap = new ConcurrentHashMap<String, MappingResult>();
    }

    @Override
    public MappingResult mapping(String path) {
        MappingResult mappingResult = resultMap.get(path);
        if (mappingResult == null) {
            mappingResult = singleMapping.mapping(path);
            if (mappingResult != null) {
                resultMap.put(path, mappingResult);
            }
        }
        return mappingResult;
    }

    public void clear() {
        resultMap.clear();
    }
}
