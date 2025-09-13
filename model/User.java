package web.model;

import java.util.Date;

import web.controller.scheme.HarpEntity;

@HarpEntity(name = "Users")
public class User {

    private int SerialNo;
    private String UserName;
    private String Name;
    private String UserType;
    
    public String getStatus() {
        return Status;
    }
    public void setStatus(String status) {
        Status = status;
    }
    private String Status;
    private Date AddedOn;
    public int getSerialNo() {
        return SerialNo;
    }
    public void setSerialNo(int serialNo) {
        SerialNo = serialNo;
    }
    public String getUserName() {
        return UserName;
    }
    public void setUserName(String userName) {
        UserName = userName;
    }
    public String getName() {
        return Name;
    }
    public void setName(String name) {
        Name = name;
    }
    public String getUserType() {
        return UserType;
    }
    public void setUserType(String userType) {
        UserType = userType;
    }
    public Date getAddedOn() {
        return AddedOn;
    }
    public void setAddedOn(Date addedOn) {
        AddedOn = addedOn;
    }

       
}
