package web.controller.page;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import web.controller.common.component.WebComponentsFactory;
import web.controller.core.ResourceController;
import web.controller.scheme.DataScheme;
import web.controller.scheme.WebViewScheme;
import web.model.Item;
import web.util.FileUtil;
import web.util.HarpQueryUtil;
import web.util.HarpQueryUtil;
import web.util.MapUtil;
import web.util.HarpQueryUtil.HarpQueryEvalType;
import web.util.HarpQueryUtil.HarpQueryType;
import web.util.SortMode;


public class ItemsController implements CustomPageScheme, WebViewScheme, DataScheme {

    private LinkedHashMap<String, Object> itemsContainer;

    private List<Item> itemsList;

    @Override
    public boolean initiateData() {
        itemsContainer = new LinkedHashMap<>();
        itemsContainer.put("id", "items");
        itemsContainer.put("pageOutputNature", "dynamic");
        itemsContainer.put("placeHolder", "itemCtrl");
        itemsContainer.put("updateMode", "append");
        itemsContainer.put("itemsListVar", "items");
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean loadData() {

        String itemsBlock = new ResourceController().loadFromHarp("Items");
        itemsList = new ArrayList<>();

        if (itemsBlock != null) {

            

            // setItemsList((List<Item>) MapUtil.convertHarpBlockToModelObjectList(csLinear,
            // Item.class));

            // String query = "Select Brand, brandname, Quantity, code, addedon from
            // Items(count:50)";
            /*
             * String query = """
             * BEGIN
             * SELECT Brand, B.description, Quantity, code, addedon FROM Items(alias:I;
             * count:6)
             * JOIN Brands(alias:B, join:B.code=I.brand)
             * END
             * """;
             */

            String query = """
                    BEGIN
                    SELECT code, Brand, B.description, Quantity, code, description, Status, S.description, addedon FROM
                    Items(alias:I; sort:code)
                    JOIN Brands(alias:B; join:B.code=I.brand)
                    JOIN Status(alias:S; join:S.code=I.status)
                    WHERE Quantity >= 14 
                    END
                    """;

            // String query = "Select Brand, brandname, Quantity, code, addedon from Items";
            LinkedHashMap<String, Object> qparams = new LinkedHashMap<>();
            qparams.put("HarpPropsPath", "D:\\Harp.properties");
            qparams.put("Query", query);
            qparams.put("EntityClass", Item.class);
            qparams.put("QueryEvalType", HarpQueryEvalType.MainQuery);
            qparams.put("QueryType", HarpQueryType.SELECT);

            LinkedHashMap<String, Object> queryInfo = (LinkedHashMap<String, Object>) HarpQueryUtil.evaluateQuery(qparams);
            itemsContainer.putAll(queryInfo);
            setItemsList((List<Item>) queryInfo.get("DataRows"));
            itemsList.size();
            // updateView();

        }

        return true;
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
        try {

            loadData();
            // update part of page
            String pagename = itemsContainer.get("id").toString();
            String pagePath = new ResourceController().findPagePath(pagename);
            String qualifiedPagePath = ResourceController.contextPath + pagePath.replace("\"", "");

            String livePath = qualifiedPagePath.replace("templates", "live").replace(".html", "_live.html");
            // String pageTemplate = FileUtil.convertFileContentToString(qualifiedPagePath);

            String pageTemplate = pageBuilder();
            itemsContainer.put("pageTemplate", pageTemplate);
            FileUtil.writeContentToFile(pageTemplate, qualifiedPagePath);

            String pageResponse = prepLivePage();

            FileUtil.writeContentToFile(pageResponse, livePath);
            System.out.println(pagename + " page reloaded");

        } catch (Exception e) {
            System.err.println(e.toString());
        }

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

        // String finalResponse = applyValuesToItemsList();

        Map<String, Object> tableInfo = new LinkedHashMap<>();
        tableInfo.put("varName", "items");
        itemsContainer.put("itemsListVar", "items");

        /*
         * String[] columnNames = Arrays.stream(Item.class.getDeclaredFields()).map(item ->
         * item.getName())
         * .toArray(String[]::new);
         */

        String[] columnNames = (String[]) itemsContainer.get("MatchedColumns_Names");

        tableInfo.put("columnNames", columnNames);
        tableInfo.put("rowCount", itemsList.size());

        // String pageTemplate = WebComponentsFactory.dataTable(tableInfo);
        // itemsContainer.put("pageTemplate", pageTemplate);

        String finalResponse = applyValuesToItemsList_FromMap();
        //finalResponse = MapUtil.formatXML(finalResponse);

        return finalResponse;
        // String finalResponse =
        // WebComponentsFactory.dynamicList_ReadOnly(itemsContainer.get("DataRows"));

    }

    private String applyValuesToItemsList_FromMap() {
        String pageResponse = "";
        try {

            String pageTemplate = itemsContainer.get("pageTemplate").toString();
            pageResponse = pageTemplate;

            String listvar = itemsContainer.get("itemsListVar").toString();

            List<LinkedHashMap<String, Object>> dataList = (List<LinkedHashMap<String, Object>>) itemsContainer
                    .get("DataRows");

            List<LinkedHashMap<String, Object>> columnMapList = (List<LinkedHashMap<String, Object>>) itemsContainer
                    .get("EntityColumnInfoMapList");

            for (int i = 0; i < dataList.size(); i++) {

                LinkedHashMap<String, Object> currentObject = dataList.get(i);
                for (var entry : currentObject.entrySet()) {

                    // Object crowVal = currentObject.get(columnName);
                    String currentVal = new String((char[]) entry.getValue());

                    Class<?> cpropType = HarpQueryUtil.findHarpColumnTypeEquivalent(entry.getKey(), columnMapList);

                    if (cpropType != null && currentVal != null) {
                        if (cpropType.equals(Date.class)) {
                            currentVal = new SimpleDateFormat("dd/MM/yyyy")
                                    .format(MapUtil.parseDate(currentVal));
                        } else {
                            // currentVal = (String) crowVal;
                        }
                    } else {
                        //System.err.println(entry.getKey() + ": this prop not part of entity");
                    }

                    String currentExp = "#{" + listvar + "." + i + "." + entry.getKey().toLowerCase() + "}";
                    pageResponse = pageResponse.replace(currentExp, currentVal);

                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return pageResponse;

    }

    private String applyValuesToItemsList_FromObject() {
        String pageResponse = "";
        try {

            String pageTemplate = itemsContainer.get("pageTemplate").toString();

            pageResponse = pageTemplate;

            String listvar = itemsContainer.get("itemsListVar").toString();
            int[] matchedColIndexes = (int[]) itemsContainer.get("MatchedColumns_Indexes");

            Field[] fields = itemsList.get(0).getClass().getDeclaredFields();
            Field[] fieldsRO = MapUtil.reorder(fields, new Field[fields.length], matchedColIndexes, SortMode.Asc);

            for (int i = 0; i < itemsList.size(); i++) {

                Object currentObject = itemsList.get(i);
                for (Field field : fields) {

                    field.setAccessible(true);

                    String currentVal = "";
                    if (field.get(currentObject) != null) {
                        if (field.getType().equals(Date.class)) {
                            currentVal = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a").format(field.get(currentObject));
                        } /*
                           * else if (field.getType().equals(Integer.class)) {
                           * currentVal = field.get(currentObject).toString();
                           * } else if (field.getType().equals(BigDecimal.class)) {
                           * currentVal = field.get(currentObject).toString();
                           * }
                           */else {
                            currentVal = field.get(currentObject).toString();
                        }
                    }

                    if (currentVal == null) {
                        currentVal = "";
                    }

                    String fname = field.getName();
                    String currentExp = "#{" + listvar + "." + i + "." + fname + "}";
                    pageResponse = pageResponse.replace(currentExp, currentVal);

                    field.setAccessible(false);
                }

                // String currentExp = "#{" + varFormat + "." + i + "." + columnNames[c] + "}";
                // pageTemplate.replace("currentExp", itemsList.get(i));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return pageResponse;

    }

    @Override
    public boolean disposeView() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'disposeView'");
    }

    @Override
    public String customStyle() {
        return """
                table,
                th,
                td {
                    border: 1px solid black;
                    border-collapse: collapse;
                }

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

        Map<String, Object> tableInfo = new LinkedHashMap<>();
        tableInfo.put("varName", "items");
        itemsContainer.put("itemsListVar", "items");

        /*
         * String[] columnNames = Arrays.stream(Item.class.getDeclaredFields()).map(item ->
         * item.getName())
         * .toArray(String[]::new);
         */
        String[] columnNames = (String[]) itemsContainer.get("SelectColumns");

        tableInfo.put("columnNames", columnNames);
        tableInfo.put("rowCount", itemsList.size());

        return WebComponentsFactory.dataTable(tableInfo);

    }

    @Override
    public String pageBuilder() {
        String style = WebComponentsFactory.defaultStyle();
        String script = WebComponentsFactory.defaultScript();
        // String body = customBody(); // WebComponentsFactory.defaultBody("Items");

        Map<String, String> pageMap = new LinkedHashMap<>();
        pageMap.put("script", customScript());
        pageMap.put("style", customStyle());
        pageMap.put("body", customBody());

        String finalOp = WebComponentsFactory.singlePageBuilder(pageMap);
        return finalOp;
    }

    public LinkedHashMap<String, Object> getitemsContainer() {
        return itemsContainer;
    }

    public void setitemsContainer(LinkedHashMap<String, Object> itemsContainer) {
        this.itemsContainer = itemsContainer;
    }

    public List<Item> getItemsList() {
        return itemsList;
    }

    public void setItemsList(List<Item> itemsList) {
        this.itemsList = itemsList;
    }

}
