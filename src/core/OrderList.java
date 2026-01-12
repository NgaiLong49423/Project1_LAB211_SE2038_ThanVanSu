/* Lớp mô tả cho danh sách đơn đặt tiệc */
package core;
import tool.ConsoleInputter;
import java.util.ArrayList;
import java.io.File;          // các lớp giúp thao tác object file
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.EOFException;  // Các lớp mô tả lỗi khi truy xuất file
import java.io.IOException;
import java.text.DecimalFormat; // lớp giúp định dạng số
import java.util.Date;

public class OrderList extends ArrayList<Order> {
    public static final String DATE_PAT = "dd/MM/yyyy";
    public static final String ORDER_CODE_PAT = "^[cCgGkK][\\d]{4}$";
    private int newOrderCode = 1; // giúp tạo mã đơn hàng (orderCode) tự động

    SetMenuList setMnuList; // danh sách thực đơn (menu list) phải có trước
    CustList custList;      // danh sách khách hàng (cust list) phải có trước

    // Constructor với danh sách thực đơn và danh sách khách hàng đã có - NetBeans sinh ra
    public OrderList(SetMenuList setMnuList, CustList custList) {
        this.setMnuList = setMnuList;
        this.custList = custList;
    }

    // Chương trình quản lý có thao tác với tệp tin nên cần 2 hành vi đọc, ghi tệp tin
    /* Đọc tệp tin nhị phân vào danh sách
    Input: String fName, tên tệp tin
    Output: Các đơn hàng (order) trong tệp tin được đọc vào danh sách này trong bộ nhớ
    Algorithm
        Bắt đầu
        Nếu tệp tin này tồn tại {
            Mở tệp tin đối tượng với tham số là fName
            Đọc từng đơn hàng trong tệp tin rồi thêm vào danh sách hiện hành
            Đóng tệp tin
        }
        Ngừng
    */

    public void readFile(String fName) {
        File f = new File(fName);
        if (!f.exists()) {
            System.out.println("The file " + fName + " does not exist!");
        }
        else {
            try{
                FileInputStream fis = new FileInputStream(f);
                ObjectInputStream ois = new ObjectInputStream(fis);
                Order anOrder; // biến để đọc dữ liệu từ tệp tin ra
                while ((anOrder = (Order)ois.readObject()) != null) {
                    this.add(anOrder);
                    // cập nhật newOrderCode để tạo mã đơn hàng tự động phù hợp
                    // newOrderCode = mã đơn hàng lớn nhất + 1
                    if (newOrderCode <= anOrder.getOrderCode())
                        newOrderCode = anOrder.getOrderCode() + 1;
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
    } // readFile()

    /* Ghi danh sách vào tệp tin nhị phân
    Input: Tên tệp tin
    Output: Danh sách đơn hàng được ghi lên tệp tin
    Algorithm:
        Nếu danh sách trống: Thông báo "Danh sách trống" (Empty list)
        Ngược lại {
            Mở tệp tin đối tượng để ghi
            Lần lượt ghi từng đối tượng đơn hàng (order) trong danh sách lên tệp tin
            Đóng tệp tin
        }
        Ngừng
    */

    public void writeFile(String fName) {
        if (this.isEmpty()) System.out.println("Emppty list");
        else {
            try {
                FileOutputStream fo = new FileOutputStream(fName);
                ObjectOutputStream os = new ObjectOutputStream(fo);
                for (Order anOrder : this) os.writeObject(anOrder);
                os.close();
                fo.close();
                System.out.println("Data are saved to file.");
            }
            catch (Exception e) { // bẫy lỗi ở mức cao vì LƯỜI CODE
                System.err.println(e);
            }
        }
    } //writeFile()

    // Xuất thông tin đơn hàng (order) để người dùng biết trước khi lưu
    // dùng trong chức năng thêm đơn hàng (addOrder) và cập nhật đơn hàng (updateOrder)
    private void printOrder(int orderCode, Customer cust, SetMenu setMnu, 
                            int numTable, Date preferredDate) {
        int total = numTable * setMnu.getPrice(); // tổng chi phí đặt tiệc
        String frame = "----------------------------------------------------------\n" +
                    "Customer order Information [Order ID: " + orderCode + "]\n" +
                    "----------------------------------------------------------";
        System.out.println(frame); // xuất khung
        
        String custInfo;
        custInfo = "Code           : " + cust.getCode() + "\n" +
                "Customer name  : " + cust.getName() + "\n" +
                "Phone number   : " + cust.getPhone() + "\n" +
                "Email          : " + cust.getEmail() + "\n" +
                "----------------------------------------------------------";
        System.out.println(custInfo); // xuất thông tin khách hàng

        // xuất thực đơn (set menu)
        DecimalFormat dfCustom;
        dfCustom = new DecimalFormat("#,##0");
        String menuInfo = 
        "Code of set menu : " + setMnu.getCode() + "\n" +
        "Set menu name    : " + setMnu.getName() + "\n" +
        "Event date       : " + ConsoleInputter.dateStr(preferredDate, DATE_PAT) + "\n" +
        "Number of tables : " + numTable + "\n" +
        "Price            : " + dfCustom.format(setMnu.getPrice()) + " Vnd";
        System.out.println(menuInfo);

        // Xuất các thành phần (ingredients)
        String ingredientsInfo = "Ingredients:\n" ;
        String[] items = setMnu.getIngredients().split("#");
        ingredientsInfo += (items[0] + "\n" + items[1] + "\n" + items[2]) ;
        System.out.println(ingredientsInfo);
        System.out.println("----------------------------------------------------------");
        System.out.println("Total cost       " + dfCustom.format(total) + " Vnd");
        System.out.println("----------------------------------------------------------");
    } // printOrder()

    /* Thêm đơn hàng (order), dữ liệu lấy từ bàn phím
    Input: Không có vì bàn phím là thiết bị
    Output: Không vì danh sách đơn hàng (Order list) hiện hành có thêm 1 phần tử
    Giải thuật:
        Bắt đầu
        // Đã có mã đơn hàng mới trong biến newOrderCode
        Vòng lặp (Loop)
            Nhập mã khách hàng (KH) đúng định dạng (pattern) theo dạng chữ hoa;
            vị trí (pos) = vị trí có mã này trong danh sách khách hàng (custList);
            Nếu (vị trí < 0) Xuất "Mã khách hàng chưa tồn tại!";
        Trong khi (vị trí < 0);
        custCode = mã khách hàng ở vị trí pos trong custList
        setMenu = Dùng thực đơn (menu) để lấy thực đơn (setMenu) trong danh sách thực đơn (setMnuList)
        setMenuCode = setMenu.code
        Nhập số lượng bàn (numTable) (từ 1 đến 100)
        Vòng lặp (Loop) {
            Nhập ngày ưu tiên (preferred date) theo dạng dd/MM/yyyy
        } Khi ngày ưu tiên (preferred date) <= hiện tại (now)
        Tính tổng (total) = numTable * setMenu.getPrice()
        Xuất thông tin đặt tiệc
        Hỏi người dùng có lưu không?
        Nếu người dùng trả lời Y {
            Tạo đơn hàng mới (newOrder) với dữ liệu đã có
            Thêm newOrder vào danh sách (list) hiện hành
            Tăng newOrderCode thêm 1 đơn vị giúp sẵn sàng cho đơn hàng sau
            Thông báo "Đã thêm đơn hàng (order)"
        }
        Ngừng
    */

    public void addOrder() {
        // khai báo các biến để chứa dữ liệu nhập
        String custCode; // mã khách hàng đặt tiệc
        String setMenuCode;
        int numTable; // số bàn được đặt
        Date preferredDate; // ngày đặt tiệc
        int pos; // vị trí tìm kiếm trong danh sách
        Customer cust; // khách hàng đặt tiệc
        SetMenu setMnu; // thực đơn (set menu) được đặt
        do {
            custCode = ConsoleInputter.getStr("Cust. code", ORDER_CODE_PAT, 
                                            "Code: [(C/G/K)+4 digits.]").toUpperCase();
            pos = custList.indexOf(new Customer(custCode));
            cust = custList.get(pos);
            if (pos < 0) System.out.println("The customer does notr exist!");
        }
        while (pos < 0);
        setMnu = (SetMenu) ConsoleInputter.objMenu(this.setMnuList);
        setMenuCode = setMnu.getCode();
        numTable = ConsoleInputter.getInt("Number of table:", 1, 100);
        boolean before = true;
        do {
            preferredDate = ConsoleInputter.getDate("Prefered date-d/m/y", DATE_PAT);
            before = preferredDate.before(new Date());
            if (before == true) System.out.println("Prefered date must be after today");
        } while (before == true);

        // Xuất thông tin đơn hàng trước khi lưu
        printOrder(newOrderCode, cust, setMnu, numTable, preferredDate);
        // Hỏi người dùng có muốn lưu hay không
        boolean response = ConsoleInputter.getBoolean("Save order? Y/N");
        if (response == true) {
            Order newOrder = new Order(newOrderCode, custCode, setMenuCode, 
                                       numTable, preferredDate);
            this.add(newOrder);
            newOrderCode++;
            System.out.println("New order was added.");
        }
    } // addOrder()

    /* Cập nhật đơn hàng
    Input: Không vì lấy dữ liệu từ bàn phím
    Output: Không, một đơn hàng trong bộ nhớ được cập nhật
    Algorithm:
        Bắt đầu
        Nhập mã đơn hàng (orderCode) cần cập nhật (không cần kiểm tra định dạng - pattern)
        Tìm đơn hàng này
        Nếu không tìm thấy, thông báo "Không có đơn hàng này"
        Ngược lại { // tương tự như thêm đơn hàng (add order)
            Chọn lại mã thực đơn (setMenuCode)
            Nhập lại số lượng bàn (numTable)
            Nhập lại ngày ưu tiên (preferredDate)
        }
        Ngừng
    */

    public void updateOrder() {
        int orderCode;
        String custCode; // mã khách hàng đặt tiệc
        String setMenuCode;
        int numTable; // số bàn được đặt
        Date preferredDate; // ngày đặt tiệc
        int pos; // vị trí tìm kiếm trong danh sách
        Customer cust; // khách hàng đặt tiệc
        SetMenu setMnu; // thực đơn (set menu) được đặt
        Order order; // đơn hàng
        orderCode = Integer.parseInt(ConsoleInputter.getStr("Updated order code"));
        pos = this.indexOf(new Order(orderCode));
        if (pos < 0) System.out.println("This order dóe not exist!");
        else {
            order = this.get(pos); // đơn hàng cần cập nhật
            custCode = order.getCustCode();
            pos = custList.indexOf(new Customer(custCode));
            cust = custList.get(pos);
            // Chọn lại thực đơn (setMenu), nhập lại số bàn (numTable) và ngày ưu tiên (preferredDate)
            setMnu = (SetMenu) ConsoleInputter.objMenu(this.setMnuList);
            setMenuCode = setMnu.getCode();
            numTable = ConsoleInputter.getInt("Number of table:", 1, 100);
            boolean before = true;
            do {
                preferredDate = ConsoleInputter.getDate("Preferred date", DATE_PAT);
                before = preferredDate.before(new Date());
                if (before == true) System.out.println("preferred date must be after today");
            } while (before == true);

            // Xuất thông tin đơn hàng trước khi lưu
            printOrder(orderCode, cust, setMnu, numTable, preferredDate);
            // Hỏi người dùng có muốn lưu hay không
            boolean response = ConsoleInputter.getBoolean("Save order? Y/N");
            if (response == true) {
                order.setSetMenuCode(setMenuCode);
                order.setNumTable(numTable);
                order.setPreferedDate(preferredDate);
                System.out.println("The order " + orderCode + " was updated.");
            }
        }
    } //updateOrder()

    // Xuất danh sách các đơn đặt tiệc
    public void print() {
        DecimalFormat dfCustom;
        dfCustom = new DecimalFormat("#,##0");
        if (this.isEmpty()) System.out.println("The order list is emppty!");
        else {
            // Xuất khung tiêu đề
            String header;
            header = "----------------------------------------------------------------------------\n" +
                     " ID     | Event Date | Customer ID| Set Menu |   Price | Tables |   Cost |\n" +
                     "----------------------------------------------------------------------------";
            System.out.println(header);
            String footer;
            footer = "----------------------------------------------------------------------------";
            // Xuất danh sách các đơn đặt tiệc (order)
            String setMenuCode;
            int pos;
            SetMenu setMnu;
            int total;
            for (Order order : this) {
                setMenuCode = order.getSetMenuCode(); // Lấy thông tin thực đơn (set menu)
                pos = setMnuList.indexOf(new SetMenu(setMenuCode));
                setMnu = setMnuList.get(pos);
                total = order.getNumTable() * setMnu.getPrice();
                Date preferredDate = order.getPreferedDate();
                String line = String.format("%-7s| %-10s | %-11s | %-8s|%10s|%7s|%11s|",
                        order.getOrderCode(),
                        ConsoleInputter.dateStr(preferredDate, DATE_PAT),
                        order.getCustCode(),
                        order.getSetMenuCode(),
                        dfCustom.format(setMnu.getPrice()),
                        order.getNumTable(),
                        dfCustom.format(total));
                System.out.println(line);
            }
            // Xuất đáy khung (footer)
            System.out.println(footer);
        }
    }
} // Kết thúc lớp OrderList
