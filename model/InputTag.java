package web.model;

public class InputTag extends WebTag {
    
    private String id;

    private String type;

    private String value;

    private String style;

    private String styleClass;

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

    
}
