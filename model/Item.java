package web.model;

import java.math.BigDecimal;
import java.util.Date;

import web.util.Container;
import web.util.StringUtil;

public class Item extends Container {

    public Item() {
        //System.out.println("Item Contruct");
        //super.sequenceNumber = StringUtil.extractCountable(this.getCode());
    }

    private String code;
    private String description;
    private String status;
    private String brand;
    private Integer quantity;
    private BigDecimal cost;
    private Date addedon;

    public Date getAddedon() {
        return addedon;
    }

    public void setAddedon(Date addedon) {
        this.addedon = addedon;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
        super.sequenceNumber = StringUtil.extractCountable(this.getCode());
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

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    /*
     * @Override
     * public int getSequenceNumber() {
     * return StringUtil.extractCountable(this.getCode());
     * }
     */

}
