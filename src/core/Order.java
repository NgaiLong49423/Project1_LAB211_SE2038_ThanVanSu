// Lớp mô tả cho 1 dơn đặt tiệc
package core;
import java.util.Date;
import java.io.Serializable;

public class Order implements Serializable {
    int orderCode; //Số đơn đặt tiệc
    String custCode; //Mã khách hàng đăt tiệc
    String setMenuCode; //Mã set menu được chọn
    int numTable; //Số bàn đặt tiệc
    Date preferedDate; //Ngày tổ chức tiệc

    //Constructor 5 tham số - Netbeans tự tạo

    public Order(int orderCode, String custCode, String setMenuCode, int numTable, Date preferedDate) {
        this.orderCode = orderCode;
        this.custCode = custCode;
        this.setMenuCode = setMenuCode;
        this.numTable = numTable;
        this.preferedDate = preferedDate;
    }
    
    // Constructor cho việc tìm kiếm the orderCode - Netbeans tự tạo
    public Order(int orderCode) {
        this.orderCode = orderCode;
    }

    // Hành vi kiểm tra bằng nhau đưa trên orderCode - Netbeans tự tạo

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + this.orderCode;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Order other = (Order) obj;
        return this.orderCode == other.orderCode;
    }

    // Getters và Setters - Netbeans tự tạo

    public int getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(int orderCode) {
        this.orderCode = orderCode;
    }

    public String getCustCode() {
        return custCode;
    }

    public void setCustCode(String custCode) {
        this.custCode = custCode;
    }

    public String getSetMenuCode() {
        return setMenuCode;
    }

    public void setSetMenuCode(String setMenuCode) {
        this.setMenuCode = setMenuCode;
    }

    public int getNumTable() {
        return numTable;
    }

    public void setNumTable(int numTable) {
        this.numTable = numTable;
    }

    public Date getPreferedDate() {
        return preferedDate;
    }

    public void setPreferedDate(Date preferedDate) {
        this.preferedDate = preferedDate;
    }

    
}//class Order
