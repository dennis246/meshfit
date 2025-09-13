package web.controller.page;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import web.controller.common.component.WebComponentsFactory;
import web.controller.core.AppController;
import web.controller.core.ResourceController;
import web.controller.core.ServerController;
import web.controller.scheme.DataScheme;
import web.controller.scheme.Tagged;
import web.controller.scheme.WebViewScheme;
import web.model.WebTag;
import web.util.ContainerUtil;
import web.util.FileUtil;
import web.util.MapUtil;
import web.util.StringUtil;
import web.util.WebUtil;

@Tagged(tag = "notesCtrl")
public class NotesController implements WebViewScheme, DataScheme, FileUploaderScheme, CustomPageScheme {

    private LinkedHashMap<String, Object> notesContainer;

    public NotesController() {
        notesContainer = new LinkedHashMap<>();
    }

    @Override
    public boolean scheduleView() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'scheduleView'");
    }

    public LinkedHashMap<String, Object> getNotesContainer() {
        return notesContainer;
    }

    public void setNotesContainer(LinkedHashMap<String, Object> notesContainer) {
        this.notesContainer = notesContainer;
    }

    @Override
    public boolean indexProps() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'indexProps'");
    }

    @Override
    public boolean initiateView() {
        System.out.println("initiating view for Notes Controller");
        initiateData();
        fetchNotes();
        updateView();
        return true;
    }

    public void fetchNotes() {

        try {
            String res = new ResourceController().load("Notes");
            if (res != null) {
                var mapdata = MapUtil.convertToJSONMap(res, 1);
                notesContainer.putAll(mapdata);
            }

        } catch (Exception e) {
            System.err.println(e.toString());
        }

    }

    public void updateNote() {

    }

    @Override
    public boolean createView() {
        // primary div template form

        /*
         * String innerHTML = """
         * <p>#{pageData}</p>
         * """;
         * 
         * notesContainer.put("innerHTML", innerHTML);
         */
        return true;
    }

    @Override
    public boolean updateView() {

        try {

            if (notesContainer == null) {
                initiateView();
            }
            applyProps();
            prepLivePage();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;

    }

    @Override
    public String pageBuilder() {

        String style = customStyle();
        String script = customScript();
        String body = customBody();

        Map<String, String> pageMap = new LinkedHashMap<>();
        pageMap.put("script", script);
        pageMap.put("style", style);
        pageMap.put("body", body);

        String finalOp = WebComponentsFactory.singlePageBuilder(pageMap);
        return finalOp;

    }

    @Override
    public boolean applyProps() {

        try {

            if (notesContainer.get("notes") != null) {

                var notesArr = (Object[]) notesContainer.get("notes");

                // comp1
                Object[] notesViewData = new Object[notesArr.length];

                int index = 0;
                for (var current : notesArr) {

                    LinkedHashMap<String, Object> cmap = (LinkedHashMap<String, Object>) current;
                    LinkedHashMap<String, Object> panelData = new LinkedHashMap<>();
                    panelData.put("panelHeader", cmap.get("title"));
                    panelData.put("panelDetails", cmap.get("description"));
                    panelData.put("rowNum", String.valueOf(index + 1));
                    notesViewData[index] = panelData;
                    index++;
                }

                String notesListView = WebComponentsFactory.listView(notesViewData);
                notesContainer.put("notesListView", notesListView);

                // comp2
                String fileUploader = WebComponentsFactory.fileUploader2();
                notesContainer.put("fileUploader", fileUploader);

                // comp3

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        /*
         * try {
         * 
         * Map<String, String> tagMap = new LinkedHashMap();
         * tagMap.put("type", "application/jpg");
         * tagMap.put("holder", "src");
         * // tagMap.put("value", ResourceController.resourcesPath + "/img24.jpg");
         * tagMap.put("name", "uploadedImage");
         * 
         * LinkedHashMap<String, Object> l1map = (LinkedHashMap<String, Object>)
         * ResourceController.register
         * .get("notes");
         * String templatePath = l1map.get("location").toString();
         * templatePath = ResourceController.contextPath + "\\" + templatePath;
         * String pageTemplateEdit = FileUtil
         * .convertFileContentToString(templatePath);
         * 
         * for (var comp : notesContainer.keySet()) {
         * 
         * if (!comp.startsWith("component")) {
         * continue;
         * }
         * 
         * String compname = comp.substring("component".length() + 1);
         * String contentType = tagMap.get("type").toString();
         * 
         * String exp = "#{" + notesContainer.get("placeHolder").toString() + "." +
         * compname
         * + "}";
         * 
         * Object rvalue = notesContainer.get(comp);
         * String repValue;
         * if (rvalue instanceof byte[]) {
         * repValue = new String((byte[]) rvalue);
         * } else {
         * repValue = tagMap.get("value").toString();
         * }
         * 
         * if (contentType.contains("jpg") || contentType.contains("png")) {
         * byte[] encBytes = Base64.getEncoder().encode((byte[]) rvalue);
         * String encByteString = "data:image/png;base64," + new String(encBytes);
         * repValue = encByteString;
         * }
         * 
         * pageTemplateEdit = pageTemplateEdit.replace(exp, repValue);
         * // System.out.println(pageTemplateEdit);
         * 
         * }
         * 
         * String livePath = templatePath.replace("templates", "live");
         * notesContainer.put("livePath", livePath);
         * 
         * notesContainer.put("pageResponse", pageTemplateEdit);
         * FileUtil.writeContentToFile(pageTemplateEdit,
         * notesContainer.get("livePath").toString());
         * 
         * } catch (Exception e) {
         * e.printStackTrace();
         * }
         * 
         * 
         */

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
    public boolean initiateData() {
        notesContainer = new LinkedHashMap<>();
        notesContainer.put("id", "notes");
        notesContainer.put("pageOutputNature", "dynamic");
        notesContainer.put("placeHolder", "notesCtrl");
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
    public String prepLivePage() {
        try {

            // update part of page
            String pagename = notesContainer.get("id").toString();
            String pagePath = new ResourceController().findPagePath(pagename);
            String qualifiedPagePath = ResourceController.contextPath + pagePath.replace("\"", "");

            if (notesContainer.get("pageOutputNature").toString().equals("dynamic")) {
                String livePath = qualifiedPagePath.replace("templates", "live").replace(".html", "_live.html");
                String pageTemplate = FileUtil.convertFileContentToString(qualifiedPagePath);

                String pageResponse = pageBuilder();

                /*
                 * if (pageResponse.contains("#{")) {
                 * pageResponse = pageResponse.replaceAll("\\#\\{[\\s\\S]*?\\}", "undefined");
                 * }
                 */

                FileUtil.writeContentToFile(pageResponse, livePath);
                System.out.println(pagename + " page reloaded");
                return pageResponse;

            }

        } catch (Exception e) {
            System.err.println(e.toString());
        }

        return null;
    }

    public boolean prepLivePageOld() {
        try {

            // update part of page
            String pagename = notesContainer.get("id").toString();
            String pagePath = new ResourceController().findPagePath(pagename);
            String qualifiedPagePath = ResourceController.contextPath + pagePath.replace("\"", "");

            if (notesContainer.get("pageOutputNature").toString().equals("dynamic")) {
                String livePath = qualifiedPagePath.replace("templates", "live").replace(".html", "_live.html");
                String pageTemplate = FileUtil.convertFileContentToString(qualifiedPagePath);

                String pageResponse = notesContainer.get("notesListView").toString();
                pageResponse += notesContainer.get("fileUploader").toString();

                if (pageResponse.contains("#{")) {
                    pageResponse = pageResponse.replaceAll("\\#\\{[\\s\\S]*?\\}", "?");
                }

                FileUtil.writeContentToFile(pageResponse, livePath);
                System.out.println(pagename + " page reloaded");

            }

        } catch (Exception e) {
            System.err.println(e.toString());
        }

        return true;
    }

    @Override
    public boolean applyAction(Object actionData) {

        if (notesContainer == null) {
            initiateData();
        }

        byte[] adBytes = (byte[]) actionData;
        notesContainer.put("uploadedImage", adBytes);
        applyProps();
        // ResourceController.prepLivePage(notesContainer);
        return true;
    }

    @Override
    public String customStyle() {
        return """
                body {
                 font-family: 'Calibri';
                }

                .tableHeader {
                font-size: 2rem;
                padding:0px;
                }

                 input[type="text"] {
                 padding: 0.4rem;
                 width: 100%;
                 border: 0 0 1px 0 solid black;
                 outline: none;
                 font-family: 'Calibri';

                 }

                 textarea {
                font-family: 'Calibri';
                 font-size: 1rem;
                    width: 100%;
                 }

                  .panel {

                 }

                 .header, .details {
                 position: relative;
                 }

                 .ip  {
                     width: 100%;
                    position: absolute;
                    top: 0; left: 0;
                    z-index: 1;
                 }


                 #updateBtn {
                 position:fixed;
                 padding:0.5rem;
                 border-radius:50px;
                 top:5rem;
                 right:2rem;
                 border: 0 0 1px solid black;

                 outline:none;border-radius:5px;



                 }


                 """;
    }

    @Override
    public String customScript() {
        String scriptStr = "";
        try {
            InputStream is = getClass().getResourceAsStream("/web/view/resources/js/Notes.js");
            scriptStr = new String(is.readAllBytes());
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return scriptStr;

    }

    @Override
    public String customBody() {

        String customBody = "";
        try {

            var notesArr = (Object[]) notesContainer.get("notes");

            // comp1
            Object[] notesViewData = new Object[notesArr.length];

            int index = 0;
            for (var current : notesArr) {

                LinkedHashMap<String, Object> cmap = (LinkedHashMap<String, Object>) current;
                LinkedHashMap<String, Object> panelData = new LinkedHashMap<>();
                panelData.put("panelHeader", cmap.get("title"));
                panelData.put("panelDetails", cmap.get("description"));
                notesViewData[index] = panelData;
                index++;
            }

            String notesListView = WebComponentsFactory.listView(notesViewData);
            customBody += notesListView;

            // comp2
            String fileUploader = fileUploader2();
            customBody += fileUploader;

            // comp3

            var buttonTag = WebComponentsFactory.button("updateBtn", "Update", "submit");

            LinkedHashMap<String, Object> toolbarInfo = new LinkedHashMap<>();

            LinkedHashMap<String, String> toolbarAttributes = new LinkedHashMap<>();
            toolbarAttributes.put("class", "toolbarStyle");

            List<Object> toolbarComponents = new ArrayList<>();
            {// toolbar button controls;}
                LinkedHashMap<String, Object> updateBtn = new LinkedHashMap<>();
                LinkedHashMap<String, Object> attributes = new LinkedHashMap<>();
                LinkedHashMap<String, Object> settings = new LinkedHashMap<>();
                String innerHTML = buttonTag;

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

            customBody += WebComponentsFactory.toolbar(toolbarInfo);

            notesContainer.put("templateWithMarkings", customBody);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return customBody;
    }

    public boolean processIncoming(byte[] incomingPageData) {

        var incomingPageStr = new String(incomingPageData);
        System.out.println(incomingPageStr);

        if (pageValidation(incomingPageStr)) {
            saveData();
            return true;
            // +todo{handle page response 080525}
        }

        return true;
    }

    private boolean pageValidation(String incomingPageStr) {
        /*
         * var reg = ServerController.serverInfo.get("register");
         * var cpageMap = (LinkedHashMap<String, Object>)
         * reg.get(notesContainer.get("id"));
         * String pageLocRel = cpageMap.get("location").toString();
         * var pageLocFull = ResourceController.contextPath + pageLocRel;
         * var livePageLoc = ResourceController.contextPath + pageLocRel
         * .replace("templates", "live")
         * .replace(".html", "_live.html");
         * 
         * String templateLocContent = FileUtil.convertFileContentToString(pageLocFull);
         * var templateContentList = Arrays.asList(templateLocContent.split("\n"));
         * 
         * String liveContent = FileUtil.convertFileContentToString(pageLocFull);
         * var liveContentList = Arrays.asList(templateLocContent.split("\n"));
         */

        try {

            String templateContent = notesContainer.get("templateWithMarkings").toString();
            var tmpltMap = MapUtil.convertToXMLMap(templateContent, 1);
            var incpgMap = MapUtil.convertToXMLMap(incomingPageStr, 1);

            // tempsave
            // notesContainer.put("liveDataMap", incpgMap);

            var L1TmpltList = MapUtil.flattenToList(tmpltMap);
            var L1LivePgeList = MapUtil.flattenToList(incpgMap);

            // tempsave
            notesContainer.put("L1TmpltList", L1TmpltList);
            notesContainer.put("L1LivePgeList", L1LivePgeList);

            if (L1TmpltList.size() != L1LivePgeList.size()) {
                return false;
            } else {
                if (htmlFormValidation(L1TmpltList, L1LivePgeList)) {
                    return true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;

    }

    private boolean htmlFormValidation(List<String> page1List, List<String> page2List) {

        LinkedHashMap<String, String> L1LivePgeExpValMap = new LinkedHashMap<>();
        int ri = 0;
        try {

            int matchCount = 0;
            int applicableChecksCount = 0;
            for (int i = 0; i < page1List.size(); i++, ri++) {

                if (page1List.get(i).contains("#{")) {

                    if (inspectInnerHTMLBlock(page2List.get(i)) == 1) {
                        String errorTxt = "External Manipulation Detected: Code block in place of innerHTML";
                        System.out.println(errorTxt);
                        AppController.closeAppWithErrorLog(errorTxt);
                        return false;
                    }

                    L1LivePgeExpValMap.put(page1List.get(i), page2List.get(i));
                    continue;

                }

                if (!WebTag.isPostBasedTag(page1List.get(i))) {
                    continue;
                }

                ++applicableChecksCount;

                if (WebTag.isPostBasedTag(page1List.get(i))) {

                    WebTag currentTag_P1 = WebUtil.textToWebTag(page1List.get(i));
                    WebTag currentTag_P2 = WebUtil.textToWebTag(page2List.get(i));

                    /*
                     * if (currentTag_P1 == null) {
                     * throw new Exception("Invalid tag: " + page1List.get(i));
                     * }
                     * 
                     * if (currentTag_P2 == null) {
                     * throw new Exception("Invalid tag: " + page2List.get(i));
                     * }
                     */

                    if (ContainerUtil.equateObjectFields(currentTag_P1, currentTag_P2) == 1) {
                        ++matchCount;
                    }

                }

            }

            if (L1LivePgeExpValMap != null && L1LivePgeExpValMap.size() > 0) {
                notesContainer.put("L1LivePgeExpValMap", L1LivePgeExpValMap);
            }

            if (matchCount == applicableChecksCount) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("htmlFormValidation error at: " + ri);
        }

        return false;
    }

    private int inspectInnerHTMLBlock(String codeblock) {

        for (var ctag : WebTag.tagSet_openingPatterns()) {
            if (codeblock.contains(ctag)) {
                return 1;
            }
        }

        return 0;
    }

    public boolean processIncoming() {

        System.out.println(new String("3485"));
        return false;
    }

    public String fileUploader2() {
        String finalOp = """
                <div class="fileUpload" style="display:inline;" >
                <tr>
                <p>#{filename}</p>
                <button onclick="initiateFileAdd();" >Add File</button>
                </tr>

                    <image src=#{imageData}" />

                </div>
                """
                .replace("#{postToURL}",
                        notesContainer.get("postToURL") != null ? notesContainer.get("postToURL").toString()
                                : "#{postToURL}")
                .replace("#{imageData}",
                        notesContainer.get("imageData") != null ? notesContainer.get("imageData").toString()
                                : "#{imageData}");

        return finalOp;
    }

    @Override
    public boolean processUploadedFile(Object filedata) {

        byte[] encBytes = Base64.getEncoder().encode((byte[]) filedata);
        notesContainer.put("imageData", encBytes);
        updateView();
        return true;
    }

}