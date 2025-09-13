package web.test;

import java.util.LinkedHashMap;

import web.util.HarpQueryUtil;
import web.util.MapUtil;
import web.util.HarpQueryUtil.HarpQueryEvalType;
import web.util.HarpQueryUtil.HarpQueryType;

public class MapTest12 {
    
    public static void main(String[] args) throws Exception {

          String query = """
                  BEGIN
                  CREATE TABLE Users
                  ADD(Name:SerialNo; Type:numeric; IsAutoIncremental:yes; IncrementalFactor: 1)
                  ADD(Name:UserName; Type:text; IsKey:yes)
                  ADD(Name:Name; Type:text)
                  ADD(Name:Status; Type:text)
                  ADD(Name:AddedOn; Type:datetime)
                  END 
                """;

        LinkedHashMap<String, Object> qpmap = new LinkedHashMap<>();
        qpmap.put("HarpPropsPath", "D:\\Harp.properties");
        qpmap.put("Query", query);
        qpmap.put("QueryType", HarpQueryType.UPDATE);
        qpmap.put("QueryEvalType", HarpQueryEvalType.MainQuery);

        HarpQueryUtil.create(qpmap); 

    }
}
