package web.test;

import java.util.ArrayList;
import java.util.List;

import web.model.Item;
import web.util.ContainerUtil;
import web.util.MathUtil;
import web.util.SortMode;
import web.util.StringUtil;

public class MapTest6 {
    public static void main(String[] args) throws Exception {

        /*
         * String[] names = { "AX0002", "AX0021", "AX0044", "AX0068", "AX0073",
         * "AX0091", "AX0032" };
         * int[] intSeqArr = new int[0];
         * 
         * for (var name : names) {
         * 
         * var res = StringUtil.extractCountable(name);
         * // System.out.println(res);
         * intSeqArr = ContainerUtil.addToIntArray(intSeqArr, res);
         * }
         * 
         * System.out.println(intSeqArr);
         * var intSeqArrSorted = MathUtil.purgeSort(intSeqArr, SortMode.Asc);
         * System.out.println(intSeqArrSorted);
         */

        /*  

        List<Item> itemsList = new ArrayList<>();

        {
            Item item = new Item();
            item.setCode("AX0021");
            item.setDescription("An item with code AX0021");
            item.setQuantity(31);
            itemsList.add(item);
        }

        {

            Item item = new Item();
            item.setCode("AX0002");
            item.setDescription("An item with code AX0002");
            item.setQuantity(201);
            itemsList.add(item);
        }

        {

            Item item = new Item();
            item.setCode("AX0005");
            item.setDescription("An item with code AX0005");
            item.setQuantity(5);
            itemsList.add(item);
        }

        {

            Item item = new Item();
            item.setCode("AX0067");
            item.setDescription("An item with code AX0067");
            item.setQuantity(67);
            itemsList.add(item);
        }

        {

            Item item = new Item();
            item.setCode("AX0034");
            item.setDescription("An item with code AX0034");
            item.setQuantity(20);
            //item.setSequenceNumber(0);
            itemsList.add(item);
        }

        var resArr = ContainerUtil.sort(itemsList.toArray(Item[]::new), SortMode.Desc);
        System.out.println(resArr);
 */    


        int[] intSeqArr = {23,23,55,2,6,2,7,1,0};
        var intSeqArrSorted = MathUtil.purgeSort(intSeqArr, SortMode.Desc);
        System.out.println(intSeqArrSorted);





    }
}
