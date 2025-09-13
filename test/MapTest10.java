package web.test;

import java.util.LinkedHashMap;

import web.util.MapUtil;
import web.util.HarpQueryUtil.HarpQueryEvalType;
import web.util.HarpQueryUtil.HarpQueryType;
import web.util.HarpQueryUtil;

public class MapTest10 {

    public static void main(String[] args) {
         String query = """
                  BEGIN
                  DELETE FROM Items
                  WHERE Code = 'A0004' AND Status = '02'
                  END 
                """;

        LinkedHashMap<String, Object> qpmap = new LinkedHashMap<>();
        qpmap.put("HarpPropsPath", "D:\\Harp.properties");
        qpmap.put("Query", query);
        qpmap.put("QueryType", HarpQueryType.DELETE);
        qpmap.put("QueryEvalType", HarpQueryEvalType.MainQuery);

        HarpQueryUtil.delete(qpmap); 
    }
    
}
