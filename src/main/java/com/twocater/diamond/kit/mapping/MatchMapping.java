package com.twocater.diamond.kit.mapping;

public interface MatchMapping {

    /**
     * 将path与pattern匹配，符合pattern模式返回true
     *
     * @param path
     * @param pattern
     * @return
     */
    boolean isMatch(String path, String pattern);
}
