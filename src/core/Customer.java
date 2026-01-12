//Lớp mô tả cho 1 khách hàng
package core;

import java.io.Serializable; //cho phép chuyển object thành chuỗi byte để lưu file
import java.util.Objects; // dùng trong hàm hashcode và equals


public class Customer implements Serializable {

    String code; // Mã khách hàng
    String name; // Tên khách hàng
    String phone; // Số điện thoại
    String email; // Địa chỉ email

    public Customer(String code, String name, String phone, String email) {
        this.code = code;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    //ctor phục vụ chức năng tìm kiếm trong list
    public Customer(String code) {
        this.code = code;
    }

    // overrie hành vi equals phục vụ chức năng tìm kiếm
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.code);
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
        final Customer other = (Customer) obj;
        return Objects.equals(this.code, other.code);
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        name = name.trim();
        int L = name.length();
        if (L >= 2 && L <= 25) {
            this.name = name;
        }
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        phone = phone.trim();
        if (phone.matches("^0[235789][\\d]{8}$")) {
            this.phone = phone;
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        email = email.trim();
        if (email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            this.email = email;
        }
    }

    //Hành vi đổi object sang chuỗi đề xuất ra màn hình
    @Override
    public String toString() {
        return String.format("%-8s|% -22s|% -12s|% -18s\n", code, name, phone, email);
        
    }

}
