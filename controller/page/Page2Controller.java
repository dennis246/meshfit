package web.controller.page;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;

import web.controller.core.ResourceController;
import web.controller.scheme.Tagged;
import web.controller.scheme.WebViewScheme;
import web.util.FileUtil;

@Tagged(tag = "page2Ctrl")
public class Page2Controller implements WebViewScheme {

    private Map<String, Object> container;

    @Override
    public boolean scheduleView() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'scheduleView'");
    }

    @Override
    public boolean initiateView() {
        container = new LinkedHashMap<>();
        System.out.println("initiating Page2 Controller");
        container.put("id", "page2");
        container.put("pageOutputNature", "dynamic");
        // container.put("placeHolderTag", "#{page1Ctrl.container}");

        createView();
        updateView();

        return true;

    }

    @Override
    public boolean createView() {
        // primary div template form

        String innerHTML = """

                """;

        Map<String, String> tagMap = new LinkedHashMap();
        tagMap.put("type", "application/jpg");
        tagMap.put("holder", "src");
        tagMap.put("value", ResourceController.resourcesPath+"/img24.jpg");
        tagMap.put("name", "image24");

        container.put("component_img1", tagMap);

        container.put("innerHTML", innerHTML);
        return true;
    }

    public void preparePageTagMaps() {

        container.put("null", container);

    }

    @Override
    public boolean updateView() {

        try {

            createView();
            String appPath = ResourceController.contextPath;
            appPath = appPath.replace("\\", "//");
            String resPath = new ResourceController().findPagePath(container.get("id").toString());

            String templatePath = appPath + resPath;
            container.put("templatePath", templatePath);

            if (container.get("pageOutputNature").toString().equals("dynamic")) {
                String livePath = appPath + resPath.replace("templates", "live");
                container.put("livePath", livePath);

                /*
                 * String pageTemplate = FileUtil.convertFileContentToString(resourcePath);
                 * 
                 * String pageResponse = pageTemplate;
                 * if (container.get("placeHolderTag") != null) {
                 * pageResponse =
                 * pageTemplate.replace(container.get("placeHolderTag").toString(),
                 * innerHTMLValue);
                 * }
                 * 
                 * container.put("pageResponse", pageResponse);
                 */

                applyProps();

            }

            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;

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
    public boolean indexProps() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'indexProps'");
    }

    @Override
    public boolean applyProps() {

        try {

            String pageTemplateEdit = FileUtil.convertFileContentToString(container.get("templatePath").toString());

            for (var comp : container.keySet()) {

                if (!comp.startsWith("component")) {
                    continue;
                }

                String compname = comp.substring("component".length() + 1);
                LinkedHashMap<String, String> tagMap = (LinkedHashMap) container.get(comp);
                String contentType = tagMap.get("type").toString();

                String exp = "#{page2Ctrl." + compname + "." + tagMap.get("holder") + "}";
                String repValue = tagMap.get("value");

                if (contentType.contains("jpg") || contentType.contains("png")) {
                    // form exp
                    // exp = "#{page2Ctrl." + compname + tagMap.get("path") + "}";
                    repValue = tagMap.get("value");

                    // data:image/png;base64,iVBORw0KGgoAAAANSUhEU

                    File afile = new File(repValue);
                    InputStream is = new FileInputStream(afile);
                    byte[] imgBytes = is.readAllBytes();
                    byte[] encBytes = Base64.getEncoder().encode(imgBytes);

                    String encByteString = "data:image/png;base64," + new String(encBytes);
                    repValue = encByteString;
                }
 
                pageTemplateEdit = pageTemplateEdit.replace(exp, repValue);
                //System.out.println(pageTemplateEdit);

            }

            container.put("pageResponse", pageTemplateEdit);
            FileUtil.writeContentToFile(pageTemplateEdit, container.get("livePath").toString());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;

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
