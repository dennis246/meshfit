package web.controller.page;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import web.controller.common.component.WebComponentsFactory;
import web.controller.core.AppController;
import web.controller.core.ResourceController;
import web.controller.core.ServerController;
import web.controller.scheme.DataScheme;
import web.controller.scheme.WebViewScheme;
import web.model.Customer;
import web.model.SimpleNote;
import web.model.WebTag;
import web.util.ContainerUtil;
import web.util.FileUtil;
import web.util.HarpQueryBuilder;
import web.util.HarpQueryUtil;
import web.util.HarpQueryUtil;
import web.util.HarpQueryUtil.HarpQueryEvalType;
import web.util.HarpQueryUtil.HarpQueryType;
import web.util.MapUtil;
import web.util.StringUtil;
import web.util.WebUtil;

/* @WebPath("/simplenotes") */
public class SimpleNoteController implements CustomPageScheme, WebViewScheme, DataScheme {

    private LinkedHashMap<String, Object> notesContainer;

    public SimpleNoteController() {
        loadNotesFromStore();
    }

    private List<SimpleNote> simpleNotesList;

    private SimpleNote simpleNote;

    public List<SimpleNote> loadNotesFromStore() {

        try {

            if (ResourceController.storeMode.equals("Harp")) {

                String query = """
                        BEGIN
                        SELECT * FROM SimpleNote
                        """;

                LinkedHashMap<String, Object> qpmap = new LinkedHashMap<>();
                qpmap.put("HarpPropsPath", "D:\\Harp.properties");
                qpmap.put("Query", query);
                qpmap.put("EntityClass", Customer.class);
                qpmap.put("QueryType", HarpQueryType.SELECT);
                qpmap.put("QueryEvalType", HarpQueryEvalType.MainQuery);

                var rs = (LinkedHashMap<String, Object>) HarpQueryUtil.evaluateQuery(qpmap);
                // var mapdata = HarpQueryUtil.mapToModelObjectList(null, null)
                // res.get("DataRows");
                var dataList = (ArrayList<LinkedHashMap<String, Object>>) rs.get("DataRows");

                setSimpleNotesList(
                        HarpQueryUtil.mapToModelObjectList((Object[]) dataList.toArray(),
                                SimpleNote.class));

            } else {

                String noteString = new ResourceController().load("SimpleNote");
                // String noteString = new ResourceController().load("Notes");
                if (noteString != null) {

                    var jsonLinear = MapUtil.linearizeJSONContent(noteString);
                    var utilInfo = MapUtil.inspectArray(jsonLinear.toCharArray(), 0);

                    int isJSONObjArray = utilInfo.get("isJSONObjectArray") != null
                            && (Integer) utilInfo.get("isJSONObjectArray") == 1 ? 1 : 0;

                    int innerArrayPresence = utilInfo.get("hasInnerArray") != null
                            && (Integer) utilInfo.get("hasInnerArray") == 1 ? 1 : 0;

                    if (isJSONObjArray == 1) {

                        if (innerArrayPresence == 1) {

                            LinkedHashMap<String, Object> mapdata = MapUtil.convertToJSONMap(noteString, 1);
                            setSimpleNotesList(
                                    HarpQueryUtil.mapToModelObjectList((Object[]) mapdata.get("SimpleNotesList"),
                                            SimpleNote.class));

                            var linearStr = MapUtil.linearizeJSONContent(noteString);
                            var formattedStr = MapUtil.formatJSON(linearStr);

                        } else {

                            LinkedHashMap<String, Object> mapdata = MapUtil.convertToJSONMap(noteString, 1);
                            LinkedHashMap<String, String> parMapData = MapUtil.convertToStringMap(mapdata,
                                    "SimpleNote");
                            // MapUtil.mapToModelObject(mapdata, SimpleNote.class);
                            // parMapData.values();
                            setSimpleNote(MapUtil.mapToModelObject(parMapData, SimpleNote.class));

                        }

                    }

                }

            }

        } catch (Exception e) {
            System.err.println(e.toString());
        }

        return simpleNotesList;
    }

    public List<SimpleNote> getSimpleNotesList() {
        return simpleNotesList;
    }

    public void setSimpleNotesList(List<SimpleNote> simpleNotesList) {
        this.simpleNotesList = simpleNotesList;
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
            InputStream is = getClass().getResourceAsStream("/web/view/resources/js/SimpleNote.js");
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

            if(simpleNote == null){
                setSimpleNote(ContainerUtil.initiateWithRandomValues(SimpleNote.class));
            }

            String templateWithMarkings = """
                    <div id="container"  style="border:1px solid black; border-radius:5px;
                         font-family:calibri;padding:1rem 1rem;" >

                    <div class="header" style="font-size:18px;font-weight:bold;"  >

                            <div class="op" >
                                    <p>#{simpleNoteCtrl.simpleNote.title}</p>
                            </div>

                        <div class="ip" style="visibility:hidden;" >
                             <input type="text" value="#{simpleNoteCtrl.simpleNote.title}"
                               style="width:100%;overflow:scroll;" />
                        </div>

                    </div>


                     <div class="details" >
                            <div class="op" >
                                <p>#{simpleNoteCtrl.simpleNote.description}</p>
                            </div>

                             <div class="ip" style="visibility:hidden;" >
                               <textarea style="width:100%;overflow:scroll;" rows="5" columns="100" >
                                #{simpleNoteCtrl.simpleNote.description}
                                </textarea>
                            </div>




                            #{addMoreContent}
                     </div>

                    </div>
                     """;

            /*
             * <input type="text" value="#{simpleNoteCtrl.simpleNote.description}"
             * style="width:100%;overflow:scroll;min-height:10rem;" />
             */
            // comp3
            var buttonTag = WebComponentsFactory.button("updateBtn", "Update", null);

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

            String toolBarContent = WebComponentsFactory.toolbar(toolbarInfo);
            templateWithMarkings = templateWithMarkings.replace("#{addMoreContent}", toolBarContent);

            notesContainer.put("templateWithMarkings", templateWithMarkings);

            customBody = templateWithMarkings
                    .replace("#{simpleNoteCtrl.simpleNote.title}",  simpleNote.getTitle())
                    .replace("#{simpleNoteCtrl.simpleNote.description}", simpleNote.getDescription());

            Object[] notesViewData = new Object[simpleNotesList.size()];
            int index = 0;
            for (var crow : simpleNotesList) {

                // LinkedHashMap<String, Object> cmap = (LinkedHashMap<String, Object>) current;
                LinkedHashMap<String, Object> panelData = new LinkedHashMap<>();
                panelData.put("panelHeader", crow.getTitle());
                panelData.put("panelDetails", crow.getDescription());
                notesViewData[index] = panelData;
                index++;
            }

            String notesListView = WebComponentsFactory.listView(notesViewData);
            customBody += notesListView;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return customBody;
    }

    public boolean processIncoming(byte[] incomingPageData) {

        String incomingPageStr = new String(incomingPageData);
        if (pageValidation(incomingPageStr)) {
            saveData();
            return true;
            // +todo{handle page response 080525}
        }

        return true;

    }

    private boolean pageValidation(String incomingPageStr) {

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

        return false;
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

    private void pageValidation_ArrayCheck(String incomingPageStr) {

        String templateContent = notesContainer.get("templateWithMarkings").toString();

        String templateLinear = MapUtil.linearizeXMLContent(templateContent);
        String[] templateParts = MapUtil.divideXMLContent(templateLinear);

        String liveLinear = MapUtil.linearizeXMLContent(incomingPageStr);
        String[] liveParts = MapUtil.divideXMLContent(liveLinear);

        // matchCount
        int partIndex = 0;
        int matchCount = 0;
        for (var livePart : liveParts) {

            // input specific val
            if (livePart.contains("input")) {

                String[] liveTagAttributes = livePart.split("\s");
                String[] templateTagAttributes = templateParts[partIndex].split("\s");

                int res = tagValidation(liveTagAttributes, templateTagAttributes);
                if (res == 1) {
                    ++matchCount;
                }
            } else {

                var res = ContainerUtil.contains(templateParts, livePart);
                if (res == 1) {
                    ++matchCount;
                }
                // System.out.println(res);

            }

            partIndex++;

        }

        if (matchCount == templateParts.length) {
            System.out.println("clear");
        }

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
    }

    private int tagValidation(String[] firstArr, String[] secondArr) {

        if (firstArr.length != secondArr.length) {
            return 0;
        }

        int matchCount = 0;
        for (int i = 0; i < firstArr.length; i++) {

            if (firstArr[i].startsWith("value")) {
                continue;
            }

            if (firstArr[i].equals(secondArr[i])) {
                ++matchCount;
            }

        }

        if (matchCount == firstArr.length - 1) {
            return 1;
        }

        return 0;

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
        // formatpage
        // finalOp = MapUtil.formatXML(finalOp);
        return finalOp;

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
        System.out.println("initiating view for Notes Controller");
        initiateData();
        // fetchNotes();
        updateView();
        return true;
    }

    @Override
    public boolean createView() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createView'");
    }

    @Override
    public boolean updateView() {
        prepLivePage();
        return true;
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
    public boolean cacheView() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'cacheView'");
    }

    @Override
    public String prepLivePage() {
        try {

            loadData();
            // update part of page
            String pagename = notesContainer.get("id").toString();
            String pagePath = new ResourceController().findPagePath(pagename);
            String qualifiedPagePath = ResourceController.contextPath + pagePath.replace("\"", "");

            String livePath = qualifiedPagePath.replace("templates", "live").replace(".html", "_live.html");
            String pageTemplate = FileUtil.convertFileContentToString(qualifiedPagePath);

            String pageResponse = pageBuilder();

            FileUtil.writeContentToFile(pageResponse, livePath);
            System.out.println(pagename + " page reloaded");
            return pageResponse;

        } catch (Exception e) {
            System.err.println(e.toString());
        }

        return null;
    }

    @Override
    public boolean disposeView() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'disposeView'");
    }

    @Override
    public boolean initiateData() {
        notesContainer = new LinkedHashMap<>();
        notesContainer.put("id", "simplenote");
        notesContainer.put("pageOutputNature", "dynamic");
        notesContainer.put("placeHolder", "simpleNoteCtrl");
        notesContainer.put("updateMode", "append");
        return true;
    }

    @Override
    public boolean loadData() {

        loadNotesFromStore();
        if (simpleNotesList == null || simpleNotesList.isEmpty()) {
            simpleNotesList = new ArrayList<>();
            // for (int i = 0; i < 5; i++) {
            //     simpleNotesList.add(ContainerUtil.initiateWithRandomValues(SimpleNote.class));
            // }

            // setSimpleNote(ContainerUtil.initiateWithRandomValues(SimpleNote.class));

        }

        return true;
    }

    @Override
    public boolean saveData() {

        try {

            String jsonStr = "";

            if (notesContainer.get("L1LivePgeExpValMap") != null) {

                LinkedHashMap<String, String> L1LivePgeExpValMap = (LinkedHashMap<String, String>) notesContainer
                        .get("L1LivePgeExpValMap");

                LinkedHashMap<String, String> snFieldMap = new LinkedHashMap<>();

                for (var crow : L1LivePgeExpValMap.entrySet()) {
                    String ck = crow.getKey();
                    snFieldMap.put(ck.substring(ck.lastIndexOf(".") + 1, ck.lastIndexOf("}")), crow.getValue());
                }

                SimpleNote noteRecord = MapUtil.mapToModelObject(snFieldMap, SimpleNote.class);
                String storeLoc = new ResourceController().defaultEntityStoreLocation("SimpleNote");

                if (ResourceController.storeMode.equals("Harp")) {
                    HarpQueryBuilder.insert(noteRecord);
                    updateView();
                } else {

                    // append or replace? : settings then
                    if (notesContainer.get("updateMode").toString().equals("append")) {
                        String prevData = FileUtil.convertFileContentToString(storeLoc);
                        LinkedHashMap<String, Object> prevDataMap = MapUtil.convertToJSONMap(prevData, 1);
                        var dataArr = (Object[]) prevDataMap.get("SimpleNotesList");
                        List<SimpleNote> dataList = HarpQueryUtil.mapToModelObjectList(
                                dataArr, SimpleNote.class);
                        dataList.add(noteRecord);
                        jsonStr = MapUtil.convertModelObjectListToJSON(dataList);
                        jsonStr = MapUtil.formatJSON(jsonStr);
                    } else {
                        // if replace entire
                        jsonStr = MapUtil.convertModelObjectToJSON(noteRecord);
                        jsonStr = MapUtil.formatJSON(jsonStr);
                    }

                    // FileUtil.writeContentToFile(jsonStr, storeLoc);

                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;

    }

    @Override
    public boolean deleteData() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteData'");
    }

    public LinkedHashMap<String, Object> getNotesContainer() {
        return notesContainer;
    }

    public void setNotesContainer(LinkedHashMap<String, Object> notesContainer) {
        this.notesContainer = notesContainer;
    }

    public SimpleNote getSimpleNote() {
        return simpleNote;
    }

    public void setSimpleNote(SimpleNote simpleNote) {
        this.simpleNote = simpleNote;
    }

}
