package newpackage;

import core.SetMenuList;
import core.CustList;
import core.OrderList;
import tool.ConsoleInputter;
//Chương trình chính - Quản lý đặt tiệc
//Data: 
//  - Danh sách Set Menu
//  - Dánh sách Customer
//  - Dánh sách Order
//  - Tập tin
//      feastMemnu.csv: Tập tin text có chứ các set menu
//      customers.dat: Binary file chứa customer list
//      feast_order_serivice.dat: Binary file chứa order list

//Build a management system for traditional feast orders with the following functionalities:
//1. Register customers.
//2. Update customer information.
//3. Search for customer information by name.
//4. Display feast menus.
//5. Place a feast order.
//6. Update order information.
//7. Save data to file.
//8. Display Customer or Order lists.
//9. Display Order List
//10. Quit

public class FeastMmg {
    public static void main(String[] args) {
        // Tên các tệp tin của chương trình
        String setMnuFile = "FeastMenu.CSV";
        String custFile = "customers.dat";
        String orderFile= "feast_order_sercice.dat";

        // Chuẩn bị 3 danh sách cơ bản của chương trình được đọc từ tệp tin ra
        SetMenuList setMenuList = new SetMenuList();
        setMenuList.readFile(setMnuFile);
        if (setMenuList.isEmpty()) {
            System.out.println("Set menus are not ready. The program can not run.");
            ConsoleInputter.getStr("Enter for quit.");
            System.exit(0);
        }

        CustList custList = new CustList();
        custList.readFile(custFile);

        OrderList orderList = new OrderList(setMenuList, custList);
        orderList.readFile(orderFile);

        // Biến thực đơn (menu)
        Object[] mnuOptions = { /*1*/ "Register customers" ,
                                /*2*/ "Update customer information",
                                /*3*/ "Search for customer information by name",
                                /*4*/ "Display feast menus",
                                /*5*/ "Place a feast order",
                                /*6*/ "Update order information" ,
                                /*7*/ "Save data to file" ,
                                /*8*/ "Display Customer list" ,
                                /*9*/ "Display Order lists",
                                /*10*/"Quit" };
        int choice;

        // Biến quản lý sự thay đổi trong hai danh sách danh sách khách hàng (custList) và danh sách đơn hàng (orderList)
        boolean custChanged= false;
        boolean orderChanged = false;
        String title = "\nFEAST ORDER MANAGEMENT\n---------------------------";
        // Chạy chương trình
        do {
            System.out.println(title);
            choice = ConsoleInputter.intMenu(mnuOptions);
            switch (choice) {
                case 1: custList.addCustomer(); custChanged= true; break;
                case 2: custList.updateCust(); custChanged = true; break;
                case 3: custList.searchName(); break;
                case 4: setMenuList.print(); break;
                case 5: orderList.addOrder(); orderChanged=true; break;
                case 6: orderList.updateOrder(); orderChanged= true; break;
                case 7: if (custChanged) {
                            custList.writeFile(custFile);
                            custChanged=false;
                        }
                        if (orderChanged) {
                            orderList.writeFile(orderFile);
                            orderChanged = false;
                        }
                        break;
                case 8: custList.print(); break;
                case 9: orderList.print(); break;
                default:
                    if (custChanged || orderChanged) {
                        boolean resp = ConsoleInputter.getBoolean("Save changes?");
                        if (resp==true) {
                            if (custChanged) custList.writeFile(custFile);
                            if (orderChanged) orderList.writeFile(orderFile);
                            System.out.println("Saved. Good bye!");
                        }
                    }
            }
        }
        while (choice < mnuOptions.length);
    } // kết thúc phương thức main
} // kết thúc lớp FeastMng
  
