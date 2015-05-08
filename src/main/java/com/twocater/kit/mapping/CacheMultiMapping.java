package com.twocater.kit.mapping;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CacheMultiMapping implements MultiMapping {

    private final MultiMapping multiMapping;
    private final Map<String, List<MappingResult>> resultMap;

    public CacheMultiMapping(MultiMapping multiMapping) {
        this.multiMapping = multiMapping;
        resultMap = new ConcurrentHashMap<String, List<MappingResult>>();
    }

    @Override
    public List<MappingResult> mapping(String path) {
        List<MappingResult> mappingResults = resultMap.get(path);
        if (mappingResults == null) {
            mappingResults = multiMapping.mapping(path);
            if (mappingResults != null) {
                resultMap.put(path, mappingResults);
            }
        }
        return mappingResults;
    }

    public void clear() {
        resultMap.clear();
    }

}
