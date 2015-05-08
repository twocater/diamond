package com.twocater.kit.mapping;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class FilterMapping implements MultiMapping {

    private final MatchMapping matchMapping;
    private final Map<String, List<String>> filterMappings;// 路径-->名称

    public FilterMapping(MatchMapping matchMapping, Map<String, List<String>> filterMappings) {
        this.matchMapping = matchMapping;
        this.filterMappings = filterMappings;
    }

    @Override
    public List<MappingResult> mapping(String path) {
        Set<String> nameSet = new HashSet<String>();
        List<MappingResult> list = new ArrayList<MappingResult>();
        for (Entry<String, List<String>> entry : filterMappings.entrySet()) {
            if (matchMapping.isMatch(path, entry.getKey())) {
                for (String name : entry.getValue()) {
                    if (!nameSet.contains(name)) {
                        list.add(createMappingResult(entry.getKey(), name));
                    }
                }
            }
        }
        return list;
    }

    private MappingResult createMappingResult(String pattern, String desName) {
        MappingResult mappingResult = new MappingResult();
        mappingResult.setDesName(desName);
        mappingResult.setPattern(pattern);
        return mappingResult;
    }
}
