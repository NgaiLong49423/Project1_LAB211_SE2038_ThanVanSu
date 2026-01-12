//Lớp cho danh sách khách hàng
package core;

import java.util.ArrayList;
import tool.ConsoleInputter; // Lớp giúp nhập dữ liệu
import java.io.File;           // Các lớp giúp thao tác đối tượng tập tin
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.EOFException;   // Các lớp mô tả lỗi khi truy xuất tập tin
import java.io.IOException;

public class CustList extends ArrayList<Customer>{
    static String CODE_PAT = "^[cCgGkK][\\d]{4}$";
    static String PHONE_PAT = "^0[235789][\\d]{8}$";
    static String EMAIL_PAT = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    static String NAME_PAT = "^[\\w][\\w ]{1,24}$"; // Tên có ít nhất 2 ký tự

    /* Thêm Customes, data lấy từ bàn phím
    Input: Không có, vì bàn phím vì thiết bị
    Output: List có thêm một phần tử
    Giải thuật
        LOOP
            mã KH = ConsolInputter.getStr("Code", CODE_PAT, "Code: [CGK+4 ky so]").toUppercase();
            pos = vị trí có mã này trong list = this.indexOf(mew Customer(mã KH));
            if (pos>=0) Xuất "Trùng mã rồi!";"
        WHILE (pos>=0);
        Nhập tên KH, tên KH phải có length = 2..25
        Nhập số Phone của KH đúng mẫu
        Nhập email của KH đúng mẫu
        Tạo mới một customer với data đã có nhập đc
        Thêm customer này vào list
        Thông báo "Đã thêm KH!"
        Ngừng
    */
    public void addCustomer() {
        // Khai báo các dữ liệu sẽ nhập
        String code, name, phone, email;
        int pos; // Vị trí tìm kiếm mã khách hàng
        
        // Nhập mã khách hàng
        do {
            code = ConsoleInputter.getStr("Cust.code (CGK 4 digits)", CODE_PAT, 
                                          "Định dạng: [CGK]0000").toUpperCase();
            
            pos = this.indexOf(new Customer(code));
            if (pos >= 0) {
                System.out.println("The code is duplicated!");
            }
        } while (pos >= 0);
        // Nhập name, phong, email
        name = ConsoleInputter.getStr("Cust.name", NAME_PAT, "Name contains at least 2 characters");
        phone = ConsoleInputter.getStr("Phoneno.", PHONE_PAT, "VN Phone: 10 digits");
        email = ConsoleInputter.getStr("Email", EMAIL_PAT, "Email: any@domain.com");
        // Tạo mới Customer và thêm vào danh sách
        Customer newCust = new Customer(code, name, phone, email);
        // Thêm new Customer vào danh sách
        this.add(newCust);
        // Thông báo
        System.out.println("A new customer has been added.");
    }// addCustomer()

    /* Xuất danh sách khách hàng
       Đầu vào: Không có
       Đầu ra: Thông tin danh sách được xuất ra màn hình
       Thuật toán:
           Nếu (danh sách trống) Xuất thông báo "Danh sách trống"
           Ngược lại {
               Xuất khung các cột (Tiêu đề)
               Duyệt và xuất từng khách hàng trong danh sách
               Xuất dòng kết thúc khung (Chân trang)
           }
       Kết thúc
    */
    public void print() {
        // Kiểm tra nếu danh sách hiện tại không có dữ liệu
        if (this.isEmpty()) {
            System.out.println("Empty List.");
        } else {
            // Thiết kế khung tiêu đề cho bảng hiển thị
            String header = 
                "----------------------------------------------------------------------\n" +
                "Code     | Customer Name        | Phone       | Email                 \n" +
                "----------------------------------------------------------------------";
            
            // Thiết kế dòng kẻ kết thúc bảng
            String footer = 
                "----------------------------------------------------------------------";

            // In tiêu đề bảng
            System.out.println(header);

            // Duyệt qua từng đối tượng khách hàng trong danh sách và in ra thông tin
            for (Customer cust : this) {
                System.out.println(cust);
            }

            // In chân bảng
            System.out.println(footer);
        }
    }// print()

    /*
     * Tìm khách hàng theo tên
        Input: Không
        Output: Danh sách các khách hàng có tên cần tìm sẽ được xuất ra màn hình
        Algorithm:
            Bắt đầu
            Nếu danh sách đang trống: Xuất "Danh sách trống"
            Ngược lại {
                Nhập searchName, không cần kiểm tra hợp lệ
                resultList = rỗng
                Với mỗi khách hàng (cust) trong danh sách này:
                    Nếu cust.name có chứa searchName thì đưa cust vào resultList;
        Xuất resultList
    }
     Kết thúc
    */

    public void searchName() {
        if (this.isEmpty()) System.out.println("Empty list");
        else{
            String searchName = ConsoleInputter.getStr("Searched name").trim();
            searchName = searchName.toUpperCase();
            CustList resultList = new CustList();
            for (Customer cust:this)
                if (cust.getName().toUpperCase().contains(searchName))
                    resultList.add(cust);
            resultList.print();
        }
    }//searchName()

    /* Sửa khách hàng
    Input: Không vì mã khách hàng nhập từ bàn phím
    Output: Khách hàng có mã đúng sẽ được cập nhật tên, số điện thoại, email
    Algorithm:
        Bắt đầu
        Nếu danh sách đang trống: Xuất "Danh sách trống"
        Ngược lại {
            Nhập mã khách hàng (custCode), không kiểm tra
            Vị trí (pos) = vị trí khách hàng có mã custCode
            Nếu (vị trí < 0): Thông báo "Không có khách hàng này"
            Ngược lại {
                Khách hàng (cust) = khách hàng ở vị trí pos
                Nhập lại tên (name), số điện thoại (phone), email cho khách hàng (có kiểm tra định dạng - pattern)
                Thông báo "Đã cập nhật"
            }
        }
        Ngừng
    */

    public void updateCust() {
        if (this.isEmpty()) System.out.println("Empty list");
        else {
            String custCode, newName, phone, email;
            custCode = ConsoleInputter.getStr("Searched code").trim().toUpperCase();
            int pos = this.indexOf(new Customer(custCode));
            if (pos < 0) System.out.println("This code does not exist.");
            else {
                Customer cust = this.get(pos);
                newName = ConsoleInputter.getStr("New name", NAME_PAT, 
                                                "Name has at least 2 chars.");
                cust.setName(newName);
                phone = ConsoleInputter.getStr("Phone no.", PHONE_PAT, 
                                                "VN Phone: 10 digits");
                cust.setPhone(phone);
                email = ConsoleInputter.getStr("Email", EMAIL_PAT, "Email: any@any.any");
                cust.setEmail(email);
                System.out.println("Updated.");
            }
        }
    } //updateCust()

    /* Đọc file nhị phân vào danh sách
    Input: Tên file
    Output: Không, các khách hàng (customer) trong file được đọc vào danh sách này trong bộ nhớ
    Algorithm:
        Bắt đầu
        Nếu file tồn tại {
            Mở file đối tượng
            Đọc từng đối tượng trong file rồi đưa vào danh sách này
            Đóng file
        }
        Ngừng
    */


    public void readFile(String fName) {
        File f = new File(fName);
        if (!f.exists()) {
            System.out.println("The file " + fName + " does not exist. Ignored.");
        }
        else {
            try {
                FileInputStream fis = new FileInputStream(f);
                ObjectInputStream ois = new ObjectInputStream(fis);
                Customer cust; // biến để đọc dữ liệu từ file ra
                while (true) { // khi hết file, ngoại lệ EOFException sẽ được phát ra
                    cust = (Customer)ois.readObject();
                    this.add(cust);
                }
            }
            catch(EOFException e) {
                System.out.println("All data in the file were read.");
            }
            catch (IOException e) {
                System.err.println(e.getMessage());
            }
            catch (ClassNotFoundException e) {
                System.err.println(e.getMessage());
            }
        }
    }// readFile()
    
    /* Ghi danh sách vào file nhị phân
    Input: Tên file
    Output: Không, Danh sách khách hàng (customer) được ghi lên object file
    lgorithm:
        Nếu danh sách trống: Thông báo "Danh sách trống" (Empty list)
        Ngược lại {
            Mở file đối tượng để ghi
            Lần lượt ghi từng đối tượng khách hàng (Customer) trong danh sách lên file
            Đóng file
        }
        Ngừng
    */

    public void writeFile(String fName) {
        if (this.isEmpty()) System.out.println("Emppty list");
        else{
            try{
                FileOutputStream fo = new FileOutputStream(fName);
                ObjectOutputStream os = new ObjectOutputStream(fo);
                for (Customer cust:this) os.writeObject(cust);
                os.close();
                fo.close();
                System.out.println("Data are saved to file.");
            }
            catch (Exception e) {
                System.err.println(e);
            }
        }
    }// Kết thúc lớp CustList


    // Tìm Customer theo mã
    // Sửa customer
}
