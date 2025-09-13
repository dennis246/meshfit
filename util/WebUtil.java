package web.util;

import java.util.LinkedHashMap;

import web.model.WebTag;

public class WebUtil {

     public static WebTag mapToWebTag(final LinkedHashMap<String, String> compMap) {

        final WebTag webTag = new WebTag();
        // String[] p1 = compParts.split("=");

        return webTag;

    }

    public static WebTag textToWebTag(final String compTag) {

        /*
         * Map<String, List<String>> tagMap = new LinkedHashMap<>();
         * tagMap.put("div", List.of("id", "class", "binding"));
         * tagMap.put("input", List.of("id", "type", "value", "style", "styleClass"));
         * 
         * // String[] checkTags = new String[] {"div", "input", button=}
         * Set<String> checkTagSet = Set.of("div", "input", "button");
         */
        final String compP1 = compTag.replaceAll("<|/|>|\"", "");
        final String compName = compP1.substring(0, compTag.indexOf("\s")).trim();

        if (WebTag.tagSet_postBased().contains(compName)) {
            final String[] compParts = compP1.split("\s");

            // comptomap
            final LinkedHashMap<String, String> compMap = MapUtil.splitToStringMap(compParts, "=");
            final WebTag webTag = MapUtil.mapToModelObject(compMap, WebTag.class);
            return webTag;
        }

        return null;

    }


}
