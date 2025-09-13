package web.test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashMap;

import web.model.Customer;
import web.util.HarpQueryUtil;
import web.util.HarpQueryBuilder;
import web.util.MapUtil;

public class MapTest18 {
    public static void main(String[] args) throws Exception {
       /*  {
            LinkedHashMap<String, Object> qpmap = new LinkedHashMap<>();
            qpmap.put("HarpPropsPath", "D:\\Harp.properties");
            qpmap.put("EntityClassName", "Brands");
            qpmap.put("EntityName", "Brands");

            Customer customer = new Customer();
            customer.setaddedon(new Date());
            customer.setctype("9393B");
            customer.setid("C0022738");
            customer.setrvalue(435);
            customer.setctype("Create Update delete test");

            Customer customerWhere = new Customer();
            customerWhere.setid("C0022738");

            MapUtil.HarpQueryBuilder.update(customer, customerWhere);
        } */

        {
            Customer customerWhere = new Customer();
            customerWhere.setid("C0022738");
            var res = HarpQueryBuilder.select(null, customerWhere);
            res.size();
        }

    }

}
