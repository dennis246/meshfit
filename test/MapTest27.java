package web.test;

import java.util.LinkedHashMap;

import web.util.HarpQueryUtil;
import web.util.HarpQueryUtil.HarpQueryEvalType;
import web.util.HarpQueryUtil.HarpQueryType;
import web.util.TxtRep;

public class MapTest27 {
    public static void main(String[] args) {

        var startedAt = System.currentTimeMillis();
        String query = """
                  BEGIN
                  SELECT name, appearance, atomic_mass, discovered_by, boil, density FROM Element{}
                  WHERE atomic_mass > 25 AND appearance != NULL
                  AND CONTAINS(appearance,'silver')
                  OR CONTAINS(appearance,'gray')
                  END
                """;

        LinkedHashMap<String, Object> qpmap = new LinkedHashMap<>();
        qpmap.put("HarpPropsPath", "D:\\Harp.properties");
        qpmap.put("Query", query);
        qpmap.put("QueryEvalType", HarpQueryEvalType.MainQuery);
        qpmap.put("QueryType", HarpQueryType.SELECT);
        // qpmap.put("EntityClass", Item.class);

        var queryInfo = (LinkedHashMap<String, Object>) HarpQueryUtil.evaluateQuery(qpmap);
        var resultData = (LinkedHashMap<String, char[]>[]) queryInfo.get("DataRows");

        // resultData.forEach(item -> {
        //     StringUtil.flatPrintMapData(item);
        // });

        var txtRepInfo = new LinkedHashMap<String, Object>();
        txtRepInfo.put("reportData", resultData);
        txtRepInfo.put("reportDataType", resultData.getClass());
        TxtRep.buildTextReport(txtRepInfo);

        System.out.println("total records: " + resultData.length);
        System.out.println("time elapsed: " + (System.currentTimeMillis() - startedAt) / 1000);

        // String kx = "\\\153";
        // System.out.println(kx.replace("\\", ""));

        // char[] ax = new char[0];
        // ax = StringUtil.addToCharArray(ax, (char) 92);
        // ax = StringUtil.addToCharArray(ax, (char) 92);
        // ax = StringUtil.addToCharArray(ax, (char) 92);

        // String rseq = "";
        // for (int i = 0; i < 100; i++) {
        // rseq += new String(ax) + i + "\r";
        // }

        // String rseqLoc = "D:\\junkyard\\rseq.txt";
        // FileUtil.writeContentToFile(rseq, rseqLoc);

        // var rseqContent = FileUtil.convertFileContentToString(rseqLoc);

        // String[] rseqContentArr = rseqContent.split("\r");

        // for(var crow : rseqContentArr){
        // System.out.println(crow);
        // }

        // String cont = "\\\72";
        // System.out.println(cont);
        // var res = StringUtil.replace(cont.toCharArray(), "\\+", "kilos");
        // System.out.println(res);

        // String location = "D:\\junkyard\\simplenote_live.html";
        // String content = FileUtil.convertFileContentToString(location);
        // var res = MapUtil.linearizeXMLContent(content);
        // FileUtil.writeContentToFile(res, location.replace(".html", "_linear.html"));
    }
}
