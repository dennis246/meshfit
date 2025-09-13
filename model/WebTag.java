package web.model;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import web.util.MapUtil;

public class WebTag extends XMLTag {

    private String id;

    private String type;

    private String value;

    private String style;

    private String styleClass;

    private String binding;

    public static final Set<String> tagSet_postBased() {
        return Set.of("form", "input", "textarea", "img" /* "button","div" */);
    }

    public static final Set<String> tagSet_openingPatterns() {
        return Set.of("<input", "<textarea", "<button",
                "<form", "<script", "<style", "<div",
                "javascript:{", "function()");
    }

    public static Map<String, List<String>> defaultTagMap() {

        Map<String, List<String>> tagMap = new LinkedHashMap<>();
        tagMap.put("div", List.of("id", "class", "binding"));
        tagMap.put("input", List.of("id", "type", "value", "style", "styleClass"));
        return tagMap;

    }

    @Override
    public boolean equals(Object obj) {

        WebTag objInst = (WebTag) obj;

        var res = this.id != null && this.id == objInst.id &&
                this.type != null && this.type == objInst.type &&
                this.value != null && this.value == objInst.value &&
                this.style != null && this.style == objInst.style &&
                this.styleClass != null && this.styleClass == objInst.styleClass &&
                this.binding != null && this.binding == objInst.binding;

        return res;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getStyleClass() {
        return styleClass;
    }

    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }

    public String getBinding() {
        return binding;
    }

    public void setBinding(String binding) {
        this.binding = binding;
    }

    public static boolean isPostBasedTag(String textBlock) {

        if (textBlock.isEmpty() || textBlock.length() < 4 || !textBlock.startsWith("<")) {
            return false;
        }

        if (WebTag.tagSet_postBased().contains(textBlock)) {
            return true;
        }

        return false;

    }

    public static WebTag mapToWebTag(LinkedHashMap<String, String> compMap) {

        WebTag webTag = new WebTag();
        // String[] p1 = compParts.split("=");

        return webTag;

    }

    public static WebTag textToWebTag(String compTag) {

        /*
         * Map<String, List<String>> tagMap = new LinkedHashMap<>();
         * tagMap.put("div", List.of("id", "class", "binding"));
         * tagMap.put("input", List.of("id", "type", "value", "style", "styleClass"));
         * 
         * // String[] checkTags = new String[] {"div", "input", button=}
         * Set<String> checkTagSet = Set.of("div", "input", "button");
         */
        String compP1 = compTag.replaceAll("<|/|>|\"", "");
        String compName = compP1.substring(0, compTag.indexOf("\s")).trim();

        if (WebTag.tagSet_postBased().contains(compName)) {
            String[] compParts = compP1.split("\s");

            // comptomap
            LinkedHashMap<String, String> compMap = MapUtil.splitToStringMap(compParts, "=");
            WebTag webTag = MapUtil.mapToModelObject(compMap, WebTag.class);
            return webTag;
        }

        return null;

    }
}
