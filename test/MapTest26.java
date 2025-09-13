package web.test;

import java.util.LinkedHashMap;

import web.util.HarpQueryUtil;
import web.util.HarpQueryUtil;
import web.util.HarpQueryUtil.HarpQueryEvalType;
import web.util.HarpQueryUtil.HarpQueryType;

public class MapTest26 {
    public static void main(String[] args) {
         String query = """
                      BEGIN
                      CREATE TABLE SimpleNote
                      ADD(Name:SerialNo; Type:numeric; IsAutoIncremental:yes; IncrementalFactor: 1; IsKey:yes)
                      ADD(Name:Title; Type:text;)
                      ADD(Name:Description; Type:text)
                      ADD(Name:Status; Type:text)
                      ADD(Name:AddedOn; Type:datetime)
                      ADD(Name:ColorCode; Type:text)
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
