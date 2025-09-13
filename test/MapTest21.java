package web.test;

import java.util.LinkedHashMap;

import web.util.HarpQueryUtil;
import web.util.MapUtil;
import web.util.HarpQueryUtil.HarpQueryEvalType;
import web.util.HarpQueryUtil.HarpQueryType;

public class MapTest21 {

    public static void main(String[] args) throws Exception {
        String query = """
                  BEGIN
                  ALTER TABLE Customer
                  ADD(Name:id; Type:text; Index:2; IsKey:yes)
                  END
                """;

        LinkedHashMap<String, Object> qpmap = new LinkedHashMap<>();
        qpmap.put("HarpPropsPath", "D:\\Harp.properties");
        qpmap.put("Query", query);
        qpmap.put("QueryType", HarpQueryType.ALTER);
        qpmap.put("QueryEvalType", HarpQueryEvalType.MainQuery);

        HarpQueryUtil.alter(qpmap);
    }
}
