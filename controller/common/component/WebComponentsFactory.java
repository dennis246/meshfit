package web.controller.common.component;

import java.io.InputStream;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import web.util.StringUtil;

public class WebComponentsFactory {

    private static final String[] fonts = { "Consolas", "Arial", "Monospace" };
    private static final String fontFamily = fonts[0];
    private static final String fontSize_h1 = "10rem;";
    private static final String fontSize_h2 = "8rem;";
    private static final String fontSize_p = "6rem;";

    private static LinkedHashMap<String, String> defaultStyleMap;

    public static void loadDefaultStyleMap() {
        defaultStyleMap = new LinkedHashMap<>();
        defaultStyleMap.put("defaultAbsoluteStyle", defaultAbsoluteStyle());
    }

    public static String defaultStyle() {

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

    public static String defaultScript() {
        String scriptStr = "";
        try {
            InputStream is = WebComponentsFactory.class.getResourceAsStream("/web/view/resources/js/commonScript.js");
            scriptStr = new String(is.readAllBytes());
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return scriptStr;
    }

    public static String inplaceGrid() {
        String finalOp = "";

        int totalRows = 100;
        int totalColumns = 100;
        String prefix = "ip";
        String size = "4";

        String[][] grid = new String[totalRows][totalColumns];

        for (int row = 0; row < totalRows; row++) {
            for (int column = 0; column < totalColumns; column++) {
                String cid = prefix + "R" + row + "C" + column;
                grid[row][column] = htmlInput("text", cid, cid, size, row + "-" + column);
            }

        }

        finalOp = Arrays.deepToString(grid).replaceAll("\\[|\\]|,", "");

        return finalOp;
    }

    public static String inplaceTable() {
        String finalOp = "";

        int totalRows = 50;
        int totalColumns = 50;
        String prefix = "ip";
        String size = "4";

        // String[][] grid = new String[totalRows][totalColumns];

        finalOp += "<table>";
        // header

        // details
        for (int row = 0; row < totalRows; row++) {

            String currentRow = "";
            for (int column = 0; column < totalColumns; column++) {
                String cid = prefix + "R" + row + "C" + column;
                currentRow += column(htmlInput("text", cid, cid, size, row + "-" + column));
            }

            finalOp += row(currentRow);

        }

        finalOp += "</table>";

        return finalOp;
    }

    public static String listView(Object[] data) {

        String finalOp = "";
        int index = 0;
        for (var row : data) {

            var crow = (LinkedHashMap<String, Object>) row;
            // finalOp += hiddenText("rowNum",String.valueOf(index+1));
            finalOp += padding(row(panel(crow)), "0.5rem");
            finalOp += spacer("0.2rem");
            finalOp = finalOp.replace("#{hiddenText}", hiddenText("rowNum", String.valueOf(index + 1)));
            index++;
        }

        finalOp = tableWrap(finalOp, "");

        return finalOp;
    }

    public static String dataTable(Map<String, Object> tableInfo) {
        String dataTable = " <table>";
        try {

            // var columnCount = (Integer) tableInfo.get("columnCount");
            var rowCount = (Integer) tableInfo.get("rowCount");
            String[] columnNames = (String[]) tableInfo.get("columnNames");
            String varname = tableInfo.get("varName").toString();

            for (int c = 0; c < columnNames.length; c++) {
                dataTable += th(columnNames[c]) + "\n";
            }

            for (int i = 0; i < rowCount; i++) {

                String varFormat = varname;

                String row = "";
                for (int c = 0; c < columnNames.length; c++) {

                    String currentID = StringUtil.randomIDGenerator(5, true, true,
                            false);

                    String currentExp = "#{" + varFormat + "." + i + "." + columnNames[c].toLowerCase() + "}";

                    String htmlInput = htmlInput("input", currentID, null, null,
                            currentExp)
                            // + hiddenText("rowNumStyle", "#{rowNum_" + i + "}")
                            + "\n";

                    // dataTable += row(row) + "\n";
                    Map<String, Object> cellInfo = new LinkedHashMap<>();
                    cellInfo.put("exp", currentExp);
                    cellInfo.put("htmlInput", htmlInput);
                    row += column(cellEdit(cellInfo)) + "\n";

                }

                dataTable += row(row);

            }

            dataTable += "</table>";

        } catch (Exception e) {
            e.printStackTrace();
        }

        return dataTable;
    }

    public static String cellEdit(Map<String, Object> cellInfo) {

        return """
                 <div class="details">
                     <div class="op" >
                         <p>#{exp}</p>
                     </div>

                     <div class="ip" style="visibility:hidden;" >
                         #{htmlInput}
                     </div>
                 </div>
                """
                .replace("#{exp}", cellInfo.get("exp").toString())
                .replace("#{htmlInput}", cellInfo.get("htmlInput").toString());

    }

    private static String hiddenText(String identifier, String content) {
        return """
                <div class="#{identifier}" style="color:transparent;z-index:0;padding:0;margin:0;font-size:0px;" >#{content}</div>
                """
                .replace("#{identifier}", identifier)
                .replace("#{content}", content);
    }

    public static String tableWrap(String content, String header) {
        return """
                <div class="tableHeader">
                <p>#{header}<p>
                </div>

                <table>
                #{content}</table>
                """.replace("#{header}", header != null ? header : "")
                .replace("#{content}", content);
    }

    public static Object spacer(String factor) {
        return """
                <div style="padding:#{factor};" />
                        """.replace("#{factor}", factor);
    }

    public static String padding(String content, String factor) {
        return """
                <div style="padding:#{factor};" >#{content}</div>
                        """.replace("#{content}", content)
                .replace("#{factor}", factor);
    }

    private static String row(String currentRow) {
        return """
                <tr>
                #{row}
                </tr>
                        """.replace("#{row}", currentRow);
    }

    private static String th(String currentRow) {
        return """
                <th>
                #{headname}
                </th>
                        """.replace("#{headname}", currentRow);
    }

    private static String column(String htmlInput) {
        return """
                <td>
                #{htmlInput}
                </td>
                """.replace("#{htmlInput}", htmlInput);
    }

    private static String defaultInputStyle() {
        return """
                fontFamily: '#{fontFamily}',
                fontSize: '#{fontFamily}'
                """
                .replace("#{fontFamily}", fontFamily)
                .replace("#{fontSize}", fontSize_p);
    }

    private static String defaultAbsoluteStyle() {
        return """
                position: absolute;
                 """;
    }

    public static String htmlInput(String type, String id, String name, String size, String value) {

        return """
                <input type="#{type}" id="#{id}" name="#{name}" size="#{size}" value="#{value}" />
                """.replace("#{type}", type)
                .replace("#{id}", id != null ? id : "")
                .replace("#{name}", name != null ? name : "")
                .replace("#{size}", size != null ? size : "")
                .replace("#{value}", value != null ? value : "");

    }

    public static String htmlBody_basic(Map<String, String> innerParts) {
        return """
                <html>
                <head>
                <style>
                    #{pageStyle}
                </style>
                <head>
                <body>

                     #{bodyPart}

                </body>
                </html>

                """.replace("#{bodyPart}", innerParts.get("bodyPart"))
                .replace("#{pageStyle}", innerParts.get("pageStyle"));
    }

    public static String inplaceToolbar(Map<String, Object> toolbarInfo) {

        String toolbar = "<div ";

        var attributeSet = (Map<String, Object>) toolbarInfo.get("attributes");
        for (var part : attributeSet.keySet()) {
            toolbar += part + "=\"" + attributeSet.get(part) + "\"";
        }

        toolbar += ">";

        // innerhtml
        var innerElements = (List<Object>) toolbarInfo.get("innerHTML");
        for (var element : innerElements) {
            toolbar += inplaceButton((Map<String, Object>) element);
        }

        toolbar += "</div>";

        String toolbarStyle = """
                .toolbarStyle {
                display: flex;
                }
                """;

        // check for additional settings related to style
        if (toolbarInfo.get("settings") != null) {
            // find and include style block for containing html

        }

        var xmap = Map.of("pageStyle", toolbarStyle, "bodyPart", toolbar);
        String finalOp = htmlBody_basic(xmap);
        return finalOp;
    }

    private static String inplaceButton(Map<String, Object> buttonInfo) {

        String htmlButton = "<button ";

        var btnAttributes = (Map<String, Object>) buttonInfo.get("attributes");
        for (var part : btnAttributes.keySet()) {
            htmlButton += part + "=\"" + btnAttributes.get(part).toString() + "\"" + "\s";
        }

        htmlButton += ">";
        htmlButton += buttonInfo.get("innerHTML").toString();
        htmlButton += "</div>";

        return htmlButton;
    }

    public String componentStyleAggregator(Map<String, String> styles) {
        String styleAgg = "";

        return styleAgg;
    }

    public static String panel(Map<String, Object> panelInfo) {

        String panelComponent = """

                <div  class="panel" style="border:1px solid black; border-radius:5px;
                font-family:calibri;padding:1rem 1rem;"  >

                    <div  class="header" style="font-size:18px;font-weight:bold;"  >

                            <div class="op" >
                                    <p>#{panelHeader}</p>
                            </div>

                            <div class="ip" style="visibility:hidden;" >
                                    <input type="text" value="#{panelHeader}" style="width:100%;overflow:scroll;" />
                            </div>

                    </div>


                    <div  style="border:1px solid black; border-width:1px 0 0 0;"  />

                        <div  class="details" >
                            <div class="op" >
                                <p>#{panelDetails}</p>
                            </div>

                            <div class="ip" style="visibility:hidden;" >
                                <textarea style="width:100%;overflow:scroll;" rows="5" columns="100" >#{panelDetails}"</textarea>
                             </div>
                        </div>

                    </div>
                    #{hiddenText}


                </div>

                 """
                .replace("#{panelDetails}",
                        panelInfo.get("panelDetails").toString())
                .replace("#{panelHeader}", panelInfo.get("panelHeader").toString());

        return panelComponent;

    }

    public static String fileUploader2() {
        String finalOp = """
                <div class="fileUpload" style="display:inline;" >
                <tr>
                <p>#{filename}</p>
                <button onclick="
                javascript:{

                var postTo = '/App1/notes1';
                var x = 'javaCmd::fileAdd';
                console.log(x);
                var xhr = new XMLHttpRequest();
                xhr.open("POST", postTo, true);
                xhr.send(x);

                }
                " >Add File</button>
                </tr>
                </div>
                """;

        return finalOp;
    }

    public static String singlePageBuilder(Map<String, String> pageMap) {

        return """

                <html>
                    <head>

                        <link rel="shortcut icon" href="#">
                        <style>
                            #{style}
                        </style>

                    </head>

                    <body>
                     <form method="post"  >
                        #{body}
                     </form>

                     <script>
                            #{script}
                        </script>
                    </body>
                </html>


                        """
                .replace("#{script}", pageMap.get("script"))
                .replace("#{style}", pageMap.get("style"))
                .replace("#{body}", pageMap.get("body"));

    }

    public static String button(String id, String name, String type) {
        return """
                <button id="#{id}" type="#{type}" >#{buttonName}</button>
                """
                .replace("#{id}", id)
                .replace("#{buttonName}", name)
                .replace("#{type}", type != null ? type : "button");

    }

    public static String toolbar(Map<String, Object> toolbarInfo) {

        String toolbar = "<div ";

        var attributeSet = (Map<String, Object>) toolbarInfo.get("attributes");
        for (var part : attributeSet.keySet()) {
            toolbar += part + "=\"" + attributeSet.get(part) + "\"";
        }

        toolbar += ">";

        // innerhtml
        var innerElements = (List<Object>) toolbarInfo.get("innerHTML");
        for (var element : innerElements) {
            toolbar += inplaceButton((Map<String, Object>) element);
        }

        toolbar += "</div>";

        String toolbarStyle = """
                .toolbarStyle {
                display: flex;
                }
                """;

        // check for additional settings related to style
        if (toolbarInfo.get("settings") != null) {
            // find and include style block for containing html

        }

        return toolbar;

    }

    public static String defaultBody(String pagename) {
        return """

                <html>

                <body>
                <p>Default page content for #{pagename}</p>
                <body>
                </html>

                   """.replace("#{pagename}", pagename);
    }

    public static String dynamicList_ReadOnly(Object dataList) {

        String finalResponse = "";

        try {

            List<LinkedHashMap<String, Object>> dataMapList = (List<LinkedHashMap<String, Object>>) dataList;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return finalResponse;
    }

}
