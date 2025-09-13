package web.model;
import java.lang.String;
import java.util.Date;
import java.lang.Integer;
import java.math.BigDecimal;
public class Brands   {
     private String Code;
     private String Description;
     private String Status;
     private Date AddedOn;
     public void setCode(String Code)  {
          this.Code = Code;
          
     }
     public String getCode()  {
          return this.Code;
          
     }
     public void setDescription(String Description)  {
          this.Description = Description;
          
     }
     public String getDescription()  {
          return this.Description;
          
     }
     public void setStatus(String Status)  {
          this.Status = Status;
          
     }
     public String getStatus()  {
          return this.Status;
          
     }
     public void setAddedOn(Date AddedOn)  {
          this.AddedOn = AddedOn;
          
     }
     public Date getAddedOn()  {
          return this.AddedOn;
          
     }
     
}
 
