package web.model;
import java.lang.String;
import java.util.Date;
import java.lang.Integer;
import java.math.BigDecimal;
public class Customer   {
     private String id;
     private String name;
     private Integer rvalue;
     private String ctype;
     private Date addedon;
     public void setid(String id)  {
          this.id = id;
          
     }
     public String getid()  {
          return this.id;
          
     }
     public void setname(String name)  {
          this.name = name;
          
     }
     public String getname()  {
          return this.name;
          
     }
     public void setrvalue(Integer rvalue)  {
          this.rvalue = rvalue;
          
     }
     public Integer getrvalue()  {
          return this.rvalue;
          
     }
     public void setctype(String ctype)  {
          this.ctype = ctype;
          
     }
     public String getctype()  {
          return this.ctype;
          
     }
     public void setaddedon(Date addedon)  {
          this.addedon = addedon;
          
     }
     public Date getaddedon()  {
          return this.addedon;
          
     }
     
}
 
