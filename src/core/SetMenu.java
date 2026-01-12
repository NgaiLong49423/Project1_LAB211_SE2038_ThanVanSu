package core;

import java.text.DecimalFormat;
import java.util.Objects;

public class SetMenu {
    String code; // Mã set menu
    String name; // Tên set menu
    int price;  // Giá set menu
    String ingredients; //Các món ăn trong set menu

    // Constructor tao mới SetMenu
    public SetMenu(String code, String name, int price, String ingredients) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.ingredients = ingredients;
    }
    
    // Constructor cho tác vụ tìm kiếm theo code
    public SetMenu(String code) {
        this.code = code;
    }

    // Hành vi kiểm tra bằng nhau giữa 2 SetMenu dựa trên mã
    // Netbeans tự động sinh hàm này khi dùng chức năng insert code - generate equals and hashcode
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.code);
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
        final SetMenu other = (SetMenu) obj;
        return Objects.equals(this.code, other.code);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }
    
    // Lấy đối tượng đang chuỗi đề xuất ra màn hình làm menu item
    @Override
    public String toString() {
        return code + ", " + name + ", " + price;
    }

    // Lấy đối tượng đang chuỗi đề xuất ra màn hình
    public String toStringScreen() {
        DecimalFormat dfCuFormat;
        dfCuFormat = new DecimalFormat("#,##0");
        String S = "Code       : " + code + "\n"
                 + "Name       : " + name + "\n"
                 + "Price      : " + dfCuFormat.format(price) + "Vnd\n"
                 + "Ingredients: " + "\n"; 
        
        // Cat ingredients theo ký tự # rồi nối các chuỗi còn vào kết quả
        String[] items = ingredients.split("#");
        for (String str: items) S += str + "\n";
        S += "---------------------------------------------------------";
        return S;
    }
     
} // class SetMenu
