package web.util;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.*;
import java.util.Map.Entry;

public class TxtRepOld {

    // public static void main(String ag[]) {
    // // sampleRep();
    // }

    private static LinkedHashMap<String, List<BasicCell>> columnsMap;
    private static ArrayList<Object> reportDataList;

    
    static void printSections() {

        try {

            // cell processing
            int maxRL = 0;
            List<String> headers = new ArrayList<>();
            // var columnsMap2 = new LinkedHashMap<String, List<BasicCell>>();
            for (String key : columnsMap.keySet()) {
                headers.add(key);
                maxRL += columnsMap.get(key).get(0).getLeftPadding() + columnsMap.get(key).get(0).getTextWidth()
                        + columnsMap.get(key).get(0).getRightPadding();

                columnsMap.put(key, setColumnSizeAsPerMaxLen(columnsMap.get(key)));
            }
            // columnsMap = columnsMap2;
            maxRL += 10;

            // headers
            {
                List<BasicCell> cellsH = new ArrayList<>();
                for (int x = 0; x < headers.size(); x++) {

                    // headertext formatting
                    String ht = headers.get(x).substring(0, 1).toUpperCase()
                            + headers.get(x).substring(1);
                    // basic split
                    // Set<Integer> stops = new HashSet<>();
                    // System.out.println(Character.valueOf('\u00DD'));

                    char[] htarr = ht.toCharArray();
                    List<String> newHtArr = new ArrayList();
                    for (int a = 0; a < htarr.length; a++) {
                        if (a > 0 && Character.isUpperCase(htarr[a])) {
                            // stops.add(a);
                            newHtArr.add("\s");
                        }
                        newHtArr.add(String.valueOf(htarr[a]));
                    }

                    String finalText = String.join("", newHtArr);

                    cellsH.add(new BasicCell(
                            columnsMap.get(headers.get(x)).get(0).getLeftPadding(),
                            columnsMap.get(headers.get(x)).get(0).getRightPadding(),
                            finalText,
                            columnsMap.get(headers.get(x)).get(0).getTextWidth(),
                            CellAlign.center));
                }

                printRow(cellsH, maxRL);
            }

            Map<String, CellAlign> columnAlignProps = new HashMap<String, CellAlign>();
            // columnAlignProps.put("description", CellAlign.left);

            // data row
            var reportContainer = (ArrayList) reportDataList;
            List<BasicCell> cells = new ArrayList<>();

            for (var crowL1 : reportContainer) {

                var crowL1Map = (LinkedHashMap) crowL1;
                for (var crowL2 : crowL1Map.keySet()) {

                    var crowL2Id = (String) crowL2;

                    String itemval = "";
                    if (crowL1Map.get(crowL2Id) instanceof String) {
                        itemval = (String) crowL1Map.get(crowL2Id);
                        cells.add(new BasicCell(6, 6, itemval, itemval.length()));
                    } else if (crowL1Map.get(crowL2Id) instanceof char[]) {
                        itemval = new String((char[]) crowL1Map.get(crowL2Id));
                        cells.add(new BasicCell(6, 6, itemval, itemval.length()));
                    }

                    CellAlign alignprop = (columnAlignProps.keySet().contains(crowL2)
                            ? columnAlignProps.get(crowL2)
                            : columnsMap.get(crowL2).get(0).getAlign());

                    cells.add(
                            new BasicCell(
                                    columnsMap.get(crowL2).get(0).getLeftPadding(),
                                    columnsMap.get(crowL2).get(0).getRightPadding(),
                                    itemval,
                                    columnsMap.get(crowL2).get(0).getTextWidth(),
                                    alignprop));

                    columnsMap.put(crowL2Id, cells);

                }

                printRow(cells, maxRL);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // public static void main(String[] args) {
    // sampleRep();
    // }

    public static void sampleRep() {
        // Exp3_2 exp3 = new Exp3_2();

        List<BasicItem> itemsList = new ArrayList<>();
        itemsList.add(new BasicItem("CX001", "Item X0934809", "shipped", "2024-09-02", "2024-09-04"));
        itemsList.add(new BasicItem("CX002",
                "Item X0934809 - 3094 093 093409 809 Google Chrome is a fast web browser available at no charge. Before you download, you can check if Chrome supports your operating system and that you have all the other system requirements.",
                "in_transit", "2024-11-15",
                "2024-11-18"));
        itemsList.add(new BasicItem("CX003", "Item SBSHSHSH", "in_transit", "2024-11-16",
                "2024-11-19"));

        itemsList.add(new BasicItem("CX004",
                "Item 349sdjuj- The film shows a man drawing a cartoon face on an easel. He draws a bottle of wine and a glass, then takes them off the paper and has a drink. He then gives the cartoon face a drink of wine, and the face breaks into a broad smile. He then draws a hat on the face's head, removes it, and puts it on. Next a cigar appears in the face's mouth, and the man removes it to the face's unhappiness. He then places all of the objects back into the image, and the face's eyes and grin grow wider in appreciation.",
                "dispatched", "2024-11-20",
                "2024-11-21"));

        LinkedHashMap<String, List<BasicCell>> columnsMap = new LinkedHashMap<>();

        try {

            for (Field field : itemsList.get(0).getClass().getDeclaredFields()) {

                List<BasicCell> cells = new ArrayList<>();
                for (BasicItem citem : itemsList) {
                    field.setAccessible(true);
                    String cfieldValue = field.get(citem).toString();
                    cells.add(new BasicCell(6, 6, cfieldValue, cfieldValue.length()));
                }

                columnsMap.put(field.getName(), cells);
            }

            // cell processing
            int maxRL = 0;
            List<String> headers = new ArrayList<>();
            var columnsMap2 = new LinkedHashMap<String, List<BasicCell>>();
            for (String key : columnsMap.keySet()) {
                headers.add(key);
                maxRL += columnsMap.get(key).get(0).getLeftPadding() + columnsMap.get(key).get(0).getTextWidth()
                        + columnsMap.get(key).get(0).getRightPadding();

                columnsMap2.put(key, setColumnSizeAsPerMaxLen(columnsMap.get(key)));
            }
            columnsMap = columnsMap2;
            maxRL += 10;

            // headers
            {
                List<BasicCell> cellsH = new ArrayList<>();
                for (int x = 0; x < headers.size(); x++) {

                    // headertext formatting
                    String ht = headers.get(x).substring(0, 1).toUpperCase() + headers.get(x).substring(1);
                    // basic split
                    // Set<Integer> stops = new HashSet<>();
                    // System.out.println(Character.valueOf('\u00DD'));

                    char[] htarr = ht.toCharArray();
                    List<String> newHtArr = new ArrayList();
                    for (int a = 0; a < htarr.length; a++) {
                        if (a > 0 && Character.isUpperCase(htarr[a])) {
                            // stops.add(a);
                            newHtArr.add("\s");
                        }
                        newHtArr.add(String.valueOf(htarr[a]));
                    }

                    String finalText = String.join("", newHtArr);

                    cellsH.add(new BasicCell(
                            columnsMap.get(headers.get(x)).get(0).getLeftPadding(),
                            columnsMap.get(headers.get(x)).get(0).getRightPadding(),
                            finalText,
                            columnsMap.get(headers.get(x)).get(0).getTextWidth(),
                            CellAlign.center));
                }

                printRow(cellsH, maxRL);
            }

            Map<String, CellAlign> columnAlignProps = new HashMap<String, CellAlign>();
            columnAlignProps.put("description", CellAlign.left);

            // data row
            for (BasicItem citem : itemsList) {

                List<BasicCell> cellsDH = new ArrayList<>();

                for (Field field : citem.getClass().getDeclaredFields()) {
                    field.setAccessible(true);
                    String cfieldValue = field.get(citem).toString();

                    CellAlign alignprop = (columnAlignProps.keySet().contains(field.getName())
                            ? columnAlignProps.get(field.getName())
                            : columnsMap.get(field.getName()).get(0).getAlign());

                    cellsDH.add(
                            new BasicCell(
                                    columnsMap.get(field.getName()).get(0).getLeftPadding(),
                                    columnsMap.get(field.getName()).get(0).getRightPadding(),
                                    cfieldValue,
                                    columnsMap.get(field.getName()).get(0).getTextWidth(),
                                    alignprop));
                    field.setAccessible(false);
                }

                printRow(cellsDH, maxRL);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void printRow(List<BasicCell> cellsH, int rs) {

        try {

            // int padding_X = 4;
            // int rs = totalRL + (padding_X * items.size());
            int adjustFact = 5;
            line_X(rs + adjustFact);

            boolean overflowExists = false;

            System.out.print("\n|");

            for (int x = 0; x < cellsH.size(); x++) {

                int tw = cellsH.get(x).getTextWidth();
                String currentText = cellsH.get(x).getText();

                if (cellsH.get(x).getText().length() > tw) {
                    currentText = cellsH.get(x).getText().substring(0, tw);
                    cellsH.get(x).setOverflowInd(true);
                    overflowExists = true;
                }
                /*
                 * int additionalSpace = tw - cellsH.get(x).getText().length() -
                 * cellsH.get(x).getLeftPadding()
                 * - cellsH.get(x).getRightPadding();
                 */
                int additionalSpace = cellsH.get(x).getTextWidth() - cellsH.get(x).getText().length();
                additionalSpace = additionalSpace < 0 ? 0 : additionalSpace;
                if (cellsH.get(x).getAlign().equals(CellAlign.right)) {
                    basicPadding(additionalSpace + cellsH.get(x).getLeftPadding());
                    System.out.print(currentText + (cellsH.get(x).getText().length() % 2 != 0 ? "\s" : ""));
                    basicPadding(cellsH.get(x).getRightPadding());
                    System.out.print("|");
                } else if (cellsH.get(x).getAlign().equals(CellAlign.left)) {
                    basicPadding(cellsH.get(x).getLeftPadding());
                    System.out.print(currentText + (cellsH.get(x).getText().length() % 2 != 0 ? "\s" : ""));
                    basicPadding(cellsH.get(x).getRightPadding() + additionalSpace);
                    System.out.print("|");
                } else if (cellsH.get(x).getAlign().equals(CellAlign.center)) {
                    basicPadding(cellsH.get(x).getLeftPadding() + additionalSpace / 2);
                    System.out.print(currentText + (cellsH.get(x).getText().length() % 2 != 0 ? "\s" : ""));
                    basicPadding(cellsH.get(x).getRightPadding() + additionalSpace / 2);
                    System.out.print("|");
                }

            }

            if (overflowExists) {
                handleCellsOverflow(cellsH);
            }

            System.out.print("\n");

            line_X(rs + adjustFact);
            System.out.print("\n");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void handleCellsOverflow(List<BasicCell> cellsH) {

        System.out.print("\n|");

        boolean overflowExists = false;

        for (int x = 0; x < cellsH.size(); x++) {

            int tw = cellsH.get(x).getTextWidth();

            int additionalSpace = cellsH.get(x).getTextWidth() - cellsH.get(x).getText().length();
            if (!cellsH.get(x).getOverflowInd()) {
                // printSpaces(additionalSpace+tw+cellsH.get(x).getLeftPadding()+cellsH.get(x).getRightPadding());

                if (cellsH.get(x).getAlign().equals(CellAlign.right)) {
                    basicPadding(additionalSpace + cellsH.get(x).getLeftPadding());
                    // System.out.print(currentText + (cellsH.get(x).getText().length() % 2 != 0 ?
                    // "\s" : ""));
                    printSpaces(additionalSpace + tw);
                    basicPadding(cellsH.get(x).getRightPadding());
                    System.out.print("|");
                } else if (cellsH.get(x).getAlign().equals(CellAlign.left)) {
                    basicPadding(cellsH.get(x).getLeftPadding());
                    // System.out.print(currentText + (cellsH.get(x).getText().length() % 2 != 0 ?
                    // "\s" : ""));
                    printSpaces(additionalSpace + tw);
                    basicPadding(cellsH.get(x).getRightPadding() + additionalSpace);
                    System.out.print("|");
                } else if (cellsH.get(x).getAlign().equals(CellAlign.center)) {
                    basicPadding(cellsH.get(x).getLeftPadding() + additionalSpace / 2);
                    // System.out.print(currentText + (cellsH.get(x).getText().length() % 2 != 0 ?
                    // "\s" : ""));
                    printSpaces(additionalSpace + tw);
                    basicPadding(cellsH.get(x).getRightPadding() + additionalSpace / 2);
                    System.out.print("|");
                }

            } else {
                String overflowedText = "";

                int parts = cellsH.get(x).getText().length() / cellsH.get(x).getTextWidth();

                if (cellsH.get(x).getOverflowDX() == 0) {

                    overflowedText = cellsH.get(x).getText().substring(tw);
                    if (overflowedText.length() > cellsH.get(x).getTextWidth()) {
                        cellsH.get(x).setOverflowDX(cellsH.get(x).getOverflowDX() + 1);
                    }

                } else {
                    int stopfrom = tw * cellsH.get(x).getOverflowDX();
                    int stopto = stopfrom + cellsH.get(x).getTextWidth();

                    if (stopto > cellsH.get(x).getText().length()) {
                        stopto = cellsH.get(x).getText().length();
                    }

                    overflowedText = cellsH.get(x).getText().substring(stopfrom, stopto);
                }

                if (cellsH.get(x).getOverflowDX() < (parts)) {
                    cellsH.get(x).setOverflowDX(cellsH.get(x).getOverflowDX() + 1);
                    overflowExists = true;
                } else {
                    overflowExists = false;
                }

                int ctstopto = overflowedText.length() < cellsH.get(x).getTextWidth()
                        ? overflowedText.length()
                        : cellsH.get(x).getTextWidth();
                String currentText = overflowedText.substring(0, ctstopto);

                if (overflowedText.length() < cellsH.get(x).getTextWidth()) {
                    currentText = currentText + addSpaces(cellsH.get(x).getTextWidth() - overflowedText.length());
                }

                // int additionalSpace = cellsH.get(x).getTextWidth() -
                // cellsH.get(x).getText().length();
                if (cellsH.get(x).getAlign().equals(CellAlign.right)) {
                    basicPadding(additionalSpace + cellsH.get(x).getLeftPadding());
                    System.out.print(currentText + (cellsH.get(x).getText().length() % 2 != 0 ? "\s" : ""));
                    basicPadding(cellsH.get(x).getRightPadding());
                    System.out.print("|");
                } else if (cellsH.get(x).getAlign().equals(CellAlign.left)) {
                    basicPadding(cellsH.get(x).getLeftPadding());
                    System.out.print(currentText + (cellsH.get(x).getText().length() % 2 != 0 ? "\s" : ""));
                    basicPadding(cellsH.get(x).getRightPadding() + additionalSpace);
                    System.out.print("|");
                } else if (cellsH.get(x).getAlign().equals(CellAlign.center)) {
                    basicPadding(cellsH.get(x).getLeftPadding() + additionalSpace / 2);
                    System.out.print(currentText + (cellsH.get(x).getText().length() % 2 != 0 ? "\s" : ""));
                    basicPadding(cellsH.get(x).getRightPadding() + additionalSpace / 2);
                    System.out.print("|");
                }

            }

        }

        if (overflowExists) {
            handleCellsOverflow(cellsH);
        }

    }

    public static void printSpaces(int size) {
        for (int i = 0; i < size; ++i) {
            System.out.print(" ");
        }
    }

    public static String addSpaces(int size) {
        String spaceText = "";
        for (int i = 0; i < size; ++i) {
            spaceText = spaceText + "\s";
        }
        return spaceText;
    }

    static List<BasicCell> setColumnSizeAsPerMaxLen(List<BasicCell> cells) {
        Integer[] lens = cells.stream().map(item -> Integer.valueOf(item.getText().length())).toArray(Integer[]::new);
        int ccolumnmaxval = findMaxLen(lens);

        if (ccolumnmaxval % 2 != 0) {
            ccolumnmaxval += 1;
        }

        if (ccolumnmaxval > 40) {
            ccolumnmaxval = 40;
        }

        var maxlValfinal = ccolumnmaxval;
        cells.forEach(item -> {
            item.setTextWidth(maxlValfinal);

            if (item.getText().length() % 2 != 0) {
                item.setText(item.getText() + "\s");
            }

        });
        return cells;
    }

    public static int findMaxLen(Integer[] dataRowLengths) {

        int maxVal = 0;
        for (int i = 0; i < dataRowLengths.length; i++) {
            if (dataRowLengths[i] > maxVal) {
                maxVal = dataRowLengths[i];
            }
        }

        return maxVal;

    }

    public static int findMaxLen(int[] dataRowLengths) {

        int maxVal = 0;
        for (int i = 0; i < dataRowLengths.length; i++) {
            if (dataRowLengths[i] > maxVal) {
                maxVal = dataRowLengths[i];
            }
        }

        return maxVal;

    }

    /*
     * public void function3(List<String> items, int rs) {
     * 
     * int padding_X = 8;
     * int adjustFact = 5;
     * 
     * line_X(rs + adjustFact);
     * System.out.print("\n|");
     * 
     * for (String item : items) {
     * 
     * basicPadding(padding_X / 2);
     * System.out.print(item);
     * 
     * basicPadding(padding_X / 2);
     * System.out.print("|");
     * 
     * }
     * 
     * System.out.print("\n");
     * line_X(rs + adjustFact);
     * System.out.print("\n");
     * 
     * }
     */

    static void basicPadding(int pf) {

        for (int i = 0; i < pf; i++) {
            System.out.print("\s");
        }

    }

    public static void line_X(int n) {
        System.out.print("\s");
        for (var c = 1; c < n; c++) {
            System.out.print("-");
        }
        // System.out.print("\n");
    }

}

class BasicItem {

    private String code;
    private String description;

    private String status;

    private String stockIn;

    private String stockOut;

    public BasicItem(String code, String description, String status, String stockIn, String stockOut) {
        this.code = code;
        this.description = description;
        this.status = status;
        this.stockIn = stockIn;
        this.stockOut = stockOut;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStockIn() {
        return stockIn;
    }

    public void setStockIn(String stockIn) {
        this.stockIn = stockIn;
    }

    public String getStockOut() {
        return stockOut;
    }

    public void setStockOut(String stockOut) {
        this.stockOut = stockOut;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

enum CellAlign {
    left, right, center
}


class BasicCell<BasicItem> {

    private int leftPadding = 2;
    private int rightPadding = 2;

    private String text;

    private int textWidth;

    private CellAlign align;

    private Boolean overflowInd = false;

    private int overflowDX = 0;

    public Boolean getOverflowInd() {
        return overflowInd;
    }

    public void setOverflowInd(Boolean ind) {
        this.overflowInd = ind;
    }

    public int getOverflowDX() {
        return overflowDX;
    }

    public void setOverflowDX(int dx) {
        this.overflowDX = dx;
    }

    public CellAlign getAlign() {
        return align;
    }

    public void setAlign(CellAlign align) {
        this.align = align;
    }

    public int getTextWidth() {
        return textWidth;
    }

    public void setTextWidth(int textWidth) {
        this.textWidth = textWidth;
    }

    public BasicCell(int leftPadding, int rightPadding, String text, int textWidth) {
        this.leftPadding = leftPadding;
        this.leftPadding = rightPadding;
        this.text = text;
        this.textWidth = textWidth;
        this.align = CellAlign.center;
    }

    public BasicCell(int leftPadding, int rightPadding, String text, int textWidth, CellAlign align) {
        this.leftPadding = leftPadding;
        this.leftPadding = rightPadding;
        this.text = text;
        this.textWidth = textWidth;
        this.align = align;
    }

    public int getLeftPadding() {
        return this.leftPadding;
    }

    public int getRightPadding() {
        return this.rightPadding;
    }

    public String getText() {
        return this.text;
    }

    public void setLeftPadding(int padding) {
        this.leftPadding = padding;
    }

    public void setRightPadding(int padding) {
        this.rightPadding = padding;
    }

    public void setText(String text) {
        this.text = text;
    }

}
