package web.test;

import java.lang.reflect.Array;
import java.util.LinkedHashMap;

import web.util.HarpQueryUtil;
import web.util.MapUtil;
import web.util.HarpQueryUtil.HarpQueryEvalType;
import web.util.HarpQueryUtil.HarpQueryType;

public class MapTest11 {
     public static void main(String[] args) throws Exception { 

        String query = """
                  BEGIN
                  ALTER TABLE Users
                  ADD(Name:UserType; Type:text; Index:2; IsKey:yes)
                  END 
                """;

        LinkedHashMap<String, Object> qpmap = new LinkedHashMap<>();
        qpmap.put("HarpPropsPath", "D:\\Harp.properties");
        qpmap.put("Query", query);
        qpmap.put("QueryType", HarpQueryType.ALTER);
        qpmap.put("QueryEvalType", HarpQueryEvalType.MainQuery);

        HarpQueryUtil.alter(qpmap); 

        //String[] ar = new String[0];
        /* String[] mainArr = {"Apple", "Mango", "Cherry", "Banana"};
        String[] resArr = MapUtil.addAt(mainArr, 10, "Lemon");
        System.out.println(resArr); */

    }
}
