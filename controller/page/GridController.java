package web.controller.page;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import web.controller.common.component.WebComponentsFactory;
import web.controller.scheme.DataScheme;
import web.controller.scheme.Tagged;
import web.controller.scheme.WebViewScheme;
import web.util.StringUtil;

@Tagged(tag = "gridCtrl")
public class GridController implements WebViewScheme, DataScheme {

    private LinkedHashMap<String, Object> gridContainer;

    @Override
    public boolean initiateData() {
        gridContainer = new LinkedHashMap<>();
        gridContainer.put("id", "grid");
        gridContainer.put("pageOutputNature", "dynamic");
        gridContainer.put("placeHolder", "gridCtrl");
        return true;
    }

    @Override
    public boolean loadData() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'loadData'");
    }

    @Override
    public boolean saveData() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveData'");
    }

    @Override
    public boolean deleteData() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteData'");
    }

    @Override
    public boolean scheduleView() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'scheduleView'");
    }

    @Override
    public boolean indexProps() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'indexProps'");
    }

    @Override
    public boolean initiateView() {
        System.out.println("initiating view for Grid Controller");
        initiateData();
        updateView();
        return true;
    }

    @Override
    public boolean createView() {
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'createView'");
        return true;
    }

    @Override
    public boolean updateView() {
        try {

            createView();
            initiateData();
            applyProps();
            //ResourceController.prepLivePage(gridContainer);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean applyProps() {

        try {

            gridContainer.put("component_ipGrid", WebComponentsFactory.inplaceTable());
            LinkedHashMap<String, Object> toolbarInfo = new LinkedHashMap<>();

            LinkedHashMap<String, String> toolbarAttributes = new LinkedHashMap<>();
            toolbarAttributes.put("class", "toolbarStyle");

            List<Object> toolbarComponents = new ArrayList<>();
            {// toolbar button controls;}
                LinkedHashMap<String, Object> updateBtn = new LinkedHashMap<>();
                LinkedHashMap<String, Object> attributes = new LinkedHashMap<>();
                LinkedHashMap<String, Object> settings = new LinkedHashMap<>();
                String innerHTML = "Update Grid";

                attributes.put("type", "submit");
                attributes.put("formmethod", "post");
                attributes.put("name", "Update");
                attributes.put("id", StringUtil.randomIDGenerator(5, true, true, false));
                attributes.put("listener", "updateGrid");
                attributes.put("styleClass", "defaultAbsoluteStyle"); 
                attributes.put("style", "position:fixed;padding:10px;right:2rem;border-radius:10rem;");

                settings.put("includeStyle", "defaultAbsoluteStyle");

                updateBtn.put("attributes", attributes);
                updateBtn.put("innerHTML", innerHTML);
                updateBtn.put("settings", settings);

                toolbarComponents.add(updateBtn);
            }

            toolbarInfo.put("attributes", toolbarAttributes);
            toolbarInfo.put("innerHTML", toolbarComponents);

            gridContainer.put("component_ipGridControls", WebComponentsFactory.inplaceToolbar(toolbarInfo));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    @Override
    public boolean cacheView() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'cacheView'");
    }

   

    @Override
    public boolean disposeView() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'disposeView'");
    }

    @Override
    public boolean applyAction(Object actionData) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'applyAction'");
    }

    @Override
    public String prepLivePage() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'prepLivePage'");
    }

    

}
