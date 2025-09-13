package web.test;

import java.util.LinkedHashMap;

import web.util.HarpQueryUtil;
import web.util.MapUtil;
import web.util.StringUtil;
import web.util.HarpQueryUtil.HarpQueryEvalType;
import web.util.HarpQueryUtil.HarpQueryType;
import web.util.MapUtil.SeekPosExclusionCriteria;

public class MapTest8 {

    public static void main(String[] args) throws Exception {

          String query = """
                  BEGIN
                  UPDATE Items
                  SET Description = 'I300'
                  WHERE Quantity = +14 
                  END 
                """;

        LinkedHashMap<String, Object> qpmap = new LinkedHashMap<>();
        qpmap.put("HarpPropsPath", "D:\\Harp.properties");
        qpmap.put("Query", query);
        qpmap.put("QueryType", HarpQueryType.UPDATE);
        qpmap.put("QueryEvalType", HarpQueryEvalType.MainQuery);

        HarpQueryUtil.update(qpmap); 

        //String content = "++88\\/\\.>2\\";
       // MapUtil.extractContentWithinBounds(uw, 0, "", "", 
        //"", null, null, StringUtil.nonAlphaNumericChars(), 0);
       // var charArr2D = MapUtil.stringArrayToCharArray2D(StringUtil.nonAlphaNumericChars_StringArray());
       //String res = new String(MapUtil.removeSequences(content.toCharArray(), charArr2D));

       //var res = MapUtil.removeNonAlphaNumericChars(content);
       //System.out.println(res);

    }
}
