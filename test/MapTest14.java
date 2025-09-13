package web.test;

import java.util.LinkedHashMap;

import web.util.HarpQueryUtil;
import web.util.MapUtil;
import web.util.HarpQueryUtil.HarpQueryEvalType;
import web.util.HarpQueryUtil.HarpQueryType;

public class MapTest14 {
    public static void main(String[] args) throws Exception {

          String query = """
                  BEGIN
                  DROP TABLE Users
                  END 
                """;

        LinkedHashMap<String, Object> qpmap = new LinkedHashMap<>();
        qpmap.put("HarpPropsPath", "D:\\Harp.properties");
        qpmap.put("Query", query);
        qpmap.put("QueryType", HarpQueryType.DROP);
        qpmap.put("QueryEvalType", HarpQueryEvalType.MainQuery);

        HarpQueryUtil.drop(qpmap); 

    }
}
