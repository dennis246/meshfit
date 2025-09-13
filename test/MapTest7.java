package web.test;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalUnit;
import java.util.Date;
import java.util.LinkedHashMap;

import web.util.HarpQueryUtil;
import web.util.MapUtil;
import web.util.HarpQueryUtil.HarpQueryEvalType;
import web.util.HarpQueryUtil.HarpQueryType;

public class MapTest7 {
    public static void main(String[] args) throws Exception {

       /*  LocalDate ld = LocalDate.now(); 
        var ld2 = ld.plus(20, ChronoUnit.DAYS);
        System.out.println(ld.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date dd = Date.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant());
         */
        
        String query = 
        """
          BEGIN
          SELECT Brand, B.description, Quantity, code, Status, S.description, addedon FROM
          Items(alias:I; count:10; sort:quantity;)
          JOIN Brands(alias:B; join:B.code=I.brand)
          JOIN Status(alias:S; join:S.code=I.status)
          WHERE addedOn > '2025-01-03' AND S.description = 'Active'
          END
        """; 


        LinkedHashMap<String, Object> qpmap = new LinkedHashMap<>(); 
        qpmap.put("HarpPropsPath", "D:\\Harp.properties");
        qpmap.put("Query", query);
        qpmap.put("QueryEvalType", HarpQueryEvalType.MainQuery);
        qpmap.put("QueryType", HarpQueryType.SELECT);
        // qpmap.put("EntityClass", Item.class);

        var queryInfo = (LinkedHashMap<String,Object>) HarpQueryUtil.evaluateQuery(qpmap);
        var resultData = queryInfo.get("DataRows");
        System.out.println(resultData.toString());


        


    }
}
