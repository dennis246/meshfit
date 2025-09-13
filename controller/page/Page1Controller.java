package web.controller.page;

import java.io.File;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import web.controller.core.ResourceController;
import web.controller.scheme.Tagged;
import web.controller.scheme.WebViewScheme;
import web.util.FileUtil;
import web.util.TxtClip;



@Tagged(tag = "page1Ctrl")
public class Page1Controller implements WebViewScheme {

    private Map<String, Object> container;

    /*
     * public void initiate() {
     * container = new LinkedHashMap<>();
     * System.out.println("initiating Page1 Controller");
     * container.put("id", "page1");
     * container.put("placeHolderTag", "#{page1Ctrl.container}");
     * initiateView();
     * }
     */

    @Override
    public boolean initiateView() {

        container = new LinkedHashMap<>();
        System.out.println("initiating Page1 Controller");
        container.put("id", "page1");
        container.put("pageOutputNature", "dynamic");
        container.put("placeHolderTag", "#{page1Ctrl.container}");

        loadSampleData();
        return true;
    }

    @Override
    public boolean createView() {
        // primary div template form

        String innerHTML = """
                <p>#{pageData}</p>
                """;

        container.put("innerHTML", innerHTML);
        return true;
    }

    @Override
    public boolean updateView() {

        try {

            createView();
            //ResourceController.prepLivePage(container);
           /*  String innerHTML = container.get("innerHTML").toString();
            String innerHTMLValue = innerHTML.replace("#{pageData}", container.get("innerHTMLValue").toString());

            // update part of page
            String appPath = System.getProperty("user.dir");
            appPath = appPath.replace("\\", "//");
            String resPath = ResourceController.findPagePath(container.get("id").toString());
            String resPathDest = appPath + resPath;

            if (container.get("pageOutputNature").toString().equals("dynamic")) {
                resPathDest = resPathDest.replace("templates", "live");

                String resourcePath = appPath + resPath;
                String pageTemplate = FileUtil.convertFileContentToString(resourcePath);

                String pageResponse = pageTemplate.replace(container.get("placeHolderTag").toString(), innerHTMLValue);
                container.put("pageResponse", pageResponse);
                FileUtil.writeContentToFile(pageResponse, resPathDest);
            } */

            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;

    }

    @Override
    public boolean cacheView() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'cache'");
    }

    @Override
    public boolean disposeView() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'dispose'");
    }

    @Override
    public boolean scheduleView() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'scheduleView'");
    }

    public void loadSampleData() {

        String sampleContent = """
                A program consist of set of instructions which are either variables or functions.\n
                By default, external variables and functions have the property that all references to the same concept.
                        """;
        String innerHTMLValue = TxtClip.clipContent(sampleContent);
        container.put("innerHTMLValue", innerHTMLValue);
        container.put("info_addedOn", LocalDateTime.now());

        createView();
        updateView();
    }

    public Map<String, Object> getContainer() {
        return container;
    }

    public void setContainer(Map<String, Object> container) {
        this.container = container;
    }

    @Override
    public boolean indexProps() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'indexProps'");
    }

    @Override
    public boolean applyProps() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'applyProps'");
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
