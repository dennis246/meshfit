package web.test;

import java.util.Arrays;
import java.util.Date;

import web.model.Customer;
import web.model.User;
import web.util.HarpQueryUtil;
import web.util.HarpQueryBuilder;
import web.util.MapUtil;

public class MapTest15 {

  

  public static void main(String[] ag) throws Exception {


    Customer customer = new Customer();
      customer.setid("Cx00" + 1);
      customer.setname("Customer" + 1);
      customer.setaddedon(new Date());
      HarpQueryBuilder.insert(customer);


   /*  Customer[] customerArr = new Customer[0];
    for (int i = 1; i < 500; i++) {
      Customer customer = new Customer();
      customer.setid("Cx00" + i);
      customer.setname("Customer" + i);
      customer.setaddedon(new Date());
      customerArr = MapUtil.add(customerArr, customer);
      
      MapUtil.HarpQueryBuilder.insert(customer);
      System.err.println(i);
     
    } */

    //Arrays.stream(customerArr).forEach(null);

    //Arrays.asList(customerArr).parallelStream().forEach(item -> MapUtil.HarpQueryBuilder.insert(item));
  }
}
