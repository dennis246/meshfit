package web.test;

import web.util.FileUtil;
import web.util.MapUtil;

public class MapTest {

    public static void main(String[] args) {

        String fileLoc = "D:\\WS-P\\WS\\pageContentSample2.html";
        String opLoc = "D:\\WS-P\\WS\\Page_Xconv.html";
        var contentStr = FileUtil.convertFileContentToString(fileLoc);
        /* var ucm = MapUtil.removeComments(contentStr);
        var xmlLinear = MapUtil.linearizeXMLContent(ucm); */

        // var finalOp = mapUtil.formatJSON(jsLinear);
        //var finalOp = MapUtil.formatXML(contentStr); 
        var finalOp = MapUtil.convertToXMLMap(contentStr, 1);
        System.out.println(finalOp.size());
        //FileUtil.writeContentToFile(finalOp, opLoc);
    }
}
