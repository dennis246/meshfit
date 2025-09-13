package web.test;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import web.util.HarpQueryBuilder;
import web.util.MapUtil;

public class MapTest16 {
    public static void main(String[] args) throws Exception {

        /*
         * User userInstSet = new User();
         * userInstSet.setName("2340953904509dcvkjsjk");
         * userInstSet.setUserName("A824");
         * 
         * User userInstWhere = new User();
         * userInstWhere.setUserName("2340953904509dcvkjsjk");
         * MapUtil.HarpQueryBuilder.update(userInstSet, userInstWhere);
         */

        /*
         * var whereMap = new LinkedHashMap<>();
         * whereMap.put("UserName", userInst.getUserName());
         * MapUtil.HarpQueryBuilder.update(userInst, whereMap);
         */

        class Customer {
            private String id;
            private String name;
            private BigDecimal rvalue;
            private String ctype;
            private Date addedon;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public BigDecimal getRvalue() {
                return rvalue;
            }

            public void setRvalue(BigDecimal rvalue) {
                this.rvalue = rvalue;
            }

            public String getCtype() {
                return ctype;
            }

            public void setCtype(String ctype) {
                this.ctype = ctype;
            }

            public Date getAddedon() {
                return addedon;
            }

            public void setAddedon(Date addedon) {
                this.addedon = addedon;
            }
        }

        List<Customer> customerList = new ArrayList<>();
        Customer[] customerArr = new Customer[0];
        {
            Customer customer = new Customer();
            customer.setAddedon(new Date());
            customer.setCtype("9393B");
            customer.setId("236737");
            customer.setRvalue(new BigDecimal("22.4"));
            customer.setName("Customer Name 236737");
            customerList.add(customer);
            customerArr = MapUtil.add(customerArr, customer);
        }

        {
            Customer customer = new Customer();
            customer.setAddedon(new Date());
            customer.setCtype("9393B");
            customer.setId("C0022738");
            customer.setRvalue(new BigDecimal("22.4"));
            customer.setName("Customer Name C0022738");
            customerList.add(customer);
            customerArr = MapUtil.add(customerArr, customer);
        }

        LinkedHashMap<String, Object> createInfoMap = new LinkedHashMap<String, Object>();
        // Customer cInst = null;
        /*
         * Field[] fields = Customer.class.getDeclaredFields();
         * for (var field : fields) {
         * field.setAccessible(true);
         * 
         * var fieldname = field.getName();
         * var fieldtype = field.getClass().getSimpleName();
         * 
         * field.setAccessible(false);
         * }
         */

        {
            LinkedHashMap<String, String> propsMap = new LinkedHashMap<>();
            propsMap.put("Name", "id");
            propsMap.put("Type", "text");
            propsMap.put("IsKey", "yes");
            propsMap.put("IsAutoIncremental", "yes");
            propsMap.put("AutoInrcrementalFactor", "1");
            createInfoMap.put("id", propsMap);
        }

        {
            LinkedHashMap<String, String> propsMap = new LinkedHashMap<>();
            propsMap.put("Name", "name");
            propsMap.put("Type", "text");
            createInfoMap.put("id", propsMap);
        }

        {
            LinkedHashMap<String, String> propsMap = new LinkedHashMap<>();
            propsMap.put("Name", "ctype");
            propsMap.put("Type", "text");
            createInfoMap.put("id", propsMap);
        }

        {
            LinkedHashMap<String, String> propsMap = new LinkedHashMap<>();
            propsMap.put("Name", "rvalue");
            propsMap.put("Type", "text");
            createInfoMap.put("id", propsMap);
        }

        {
            LinkedHashMap<String, String> propsMap = new LinkedHashMap<>();
            propsMap.put("Name", "addedon");
            propsMap.put("Type", "date");
            createInfoMap.put("id", propsMap);
        }

        HarpQueryBuilder.create(customerList, createInfoMap);

    }
}
