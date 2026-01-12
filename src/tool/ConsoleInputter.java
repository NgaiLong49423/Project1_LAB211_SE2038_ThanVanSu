package tool; //gói tool chứa các lớp tiện ích dùng chung

import java.util.Scanner; //nhập dữ liệu từ bàn phím
import java.util.Date;  //làm việc với ngày tháng
import java.text.DateFormat; // định dạng ngày tháng
import java.text.SimpleDateFormat;// định dạng ngày tháng theo mẫu (SimpleDateFormat)
import java.text.ParseException; //xử lý lỗi khi chuyển chuỗi thành ngày tháng 
import java.util.Calendar; //làm việc với các thành phần của ngày tháng 
import java.util.GregorianCalendar; //làm việc với các thành phần của ngày tháng 
import java.util.List; //làm việc với danh sách

public class ConsoleInputter {

    public static Scanner sc = new Scanner(System.in);  //Khai báo đối tượng Scanner dùng chung

    public static boolean getBoolean(String prompt) { // Nhập kiểu boolean 
        System.out.println(prompt + " (Y/N, T/F, 1/0)?: "); //gợi ý nhập 
        String data = sc.nextLine().trim().toLowerCase();  //đọc chuỗi nhập vào
        char c = data.charAt(0); //lấy ký tự đầu tiên
        return c == 'Y' || c == 'T' || c == '1'; //trả về true/false
    }

    public static int getInt(String prompt, int min, int max) { // Nhập số nguyên trong khoảng min..max
        int result = 0;
        do {
            System.out.print(prompt + "[" + min + " - " + max + "]: ");
            result = Integer.parseInt(sc.nextLine().trim());
        } while (result < min || result > max);
        return result;
    }

    public static double getDouble(String prompt, double min, double max) {
        double result = 0;
        do {
            System.out.print(prompt + "[" + min + " - " + max + "]: ");
            result = Double.parseDouble(sc.nextLine().trim());
            if (result < min || result > max) {
                System.out.println("Value range: " + "[" + min + ", " + max + "]");
            }
        } while (result < min || result > max);
        return result;

    }

    public static String getStr(String prompt) {
        System.out.print(prompt + ": ");
        return sc.nextLine().trim();
    }

    public static String getStr(String prompt, String pattern, String errorMsg) {
        String data;
        boolean valid;
        do {
            System.out.println(prompt + ": ");
            data = sc.nextLine().trim();
            valid = data.matches(pattern);
            if (!valid) {
                System.out.println(errorMsg);
            }
        } while (!valid);
        return data;
    }

    public static Date getDate(String prompt, String dataFormat) {
        String dateStr;
        Date d;

        DateFormat formatter = new SimpleDateFormat(dataFormat);
        do {
            System.out.println(prompt + ": ");
            dateStr = sc.nextLine().trim();
            try {
                d = formatter.parse(dateStr);
            } catch (ParseException e) {
                System.out.println("Date format should be " + dataFormat + ".");
                d = null;
            }
        } while (d == null);
        return d;
    }

    public static String dateStr(Date date, String dateFormat) {
        if (date == null) {
            return null;
        }
        DateFormat formatter = new SimpleDateFormat(dateFormat);
        return formatter.format(date);
    }

    /* Lấy 1 thành phần trong Date. Lớp Calender giúp truy xuất các thành phần
   Cách dùng: int year = getPart(aDate, Calendar.YEAR);
   Chú ý: Thành phần tháng cho kết quả : Tháng 1 --> 0
     */
    public static int getPart(Date d, int calendarPart) {
        GregorianCalendar cal = new GregorianCalendar(); // tạo calandar
        cal.setTime(d); // cho calendar mang ngày d
        return cal.get(calendarPart); // lấy ra thành phần thời gian này
    }

// Lấy tuổi với ngày sinh đã biết
    public int getAge(Date birthDate) {
        int currentYear = getPart(new Date(), Calendar.YEAR);
        int birthYear = getPart(birthDate, Calendar.YEAR);
        return currentYear - birthYear;
    }

    /* Thay vì xây dựng lớp cho Menu, hàm menu được xây dựng. Tham số của
   các hàm menu này có thể là một mảng tĩnh, mảng động hoặc một danh sách
   Cú pháp tham số là mảng động (tham số với số phần tử thay đổi tùy ý)
   DataType... Hàm có số đối số thay đổi). Trình biên dịch cư xử với
   tham số có số lượng thay đổi như mảng 1 chiều. */

 /*Menu trả về số int mà người dùng chọn.
 * Cách dùng: int choice = intMenu("Add", "Search", "Remove");
     */
    
    public static int intMenu (Object... options){
        //int choice;
        int n = options.length ; //Số mục trong menu
        for (int i = 0; i < n; i++)
            System.out.println((i+1) + "-" + options[i]);
        return getInt("Choose ", 1, n); //User bị buộc nhập số phù hợp 1..n
    }
    
    public static int intMenu(List options) {
        // int choice;
        int n = options.size(); // số mục trong menu
        for (int i = 0; i < n; i++) // xuất các options     
            System.out.println((i + 1) + "-" + options.get(i));
        return getInt("Choose ", 1, n); // User bị buộc nhập số phù hợp 1..n
    }

    /*Menu trả về object mà người dùng chọn
Cách dùng: String objChoice = (String)objMenu("Add", "Search", "Remove");
     */
    public static Object objMenu(Object... options) {
        int choice = intMenu(options);
        return options[choice - 1];
    }

    public static Object objMenu(List options) {
        int choice = intMenu(options);
        return options.get(choice - 1);
    }

// sinh key tự động dựa trên ngày tháng theo mẫu yyyyMMddhhmmss
    public static String dateKeyGen() {
        Date now = new Date(); // lấy ngày hiện tại
        // chuyển thành dạng chuỗi theo mẫu
        SimpleDateFormat f = new SimpleDateFormat("yyyyMMddhhmmss");
        return f.format(now);
    }

// TESTS
    public static void main(String[] args) {
        // Test nhập boolean
        boolean b = ConsoleInputter.getBoolean("Gender");
        System.out.println("Data input: " + b);
        // Test nhập số
        int age = ConsoleInputter.getInt("Age", 18, 60);
        System.out.println("Age inputted: " + age);
        /* Nhập số int lớn hơn 0. Các kiểu dữ liệu số đã định nghĩa sẵn
       tầm trị bằng 2 hằng MIN_VLUE, MAX_VALUE */
        int nItem = ConsoleInputter.getInt("Number of Item", 1, Integer.MAX_VALUE);
        System.out.println("Number of items: " + nItem);
        // Nhập lương là số thực ít nhất là 200
        double salary = ConsoleInputter.getDouble("Sal", 200, Double.MAX_VALUE);
        System.out.println("Salaray inputted: " + salary);
        // Test nhập 1 String bất kỳ
        String str;
        str = ConsoleInputter.getStr("Input a string");
        System.out.println("Data input: " + str);
        // Test nhập số phone theo mẫu: 9 số hoặc 11 số có chỉ định tính từ
        // đầu chuỗi nhập (chỉ định bằng ^) đến cuối chuỗi nhập ($)
        str = ConsoleInputter.getStr("Phone 1", "^[\\d]{9}|[\\d]{11}$", "9/11 digits!");
        System.out.println("Phone 1 input: " + str);
        str = ConsoleInputter.getStr("Phone 2", "[\\d]{9}|[\\d]{11}", "9/11 digits!");
        System.out.println("Phone 2 input: " + str);
        // Test xuất Date, truy xuất thành phần của Date
        Date d = new Date(); //Lấy ngày hiện hành trong máy tính
        System.out.println(d);
        System.out.println("MM-dd-yyyy: " + dateStr(d, "MM-dd-yyyy"));
        System.out.println("dd-MM-yyyy: " + dateStr(d, "dd-MM-yyyy"));
        System.out.println("yyyy-MM: " + dateStr(d, "yyyy-MM"));
        System.out.println("Year: " + getPart(d, Calendar.YEAR));
        // Month bắt đầu từ 0 nên phải cộng thêm 1
        System.out.println("Month: " + getPart(d, Calendar.MONTH + 1));
        System.out.println("Date: " + getPart(d, Calendar.DATE));
        // Test nhập và xuất Date
        d = ConsoleInputter.getDate("Date of birth dd-MM-yyyy", "dd-MM-yyyy");
        System.out.println(d);
        System.out.println("dd-MM-yyyy: " + dateStr(d, "dd-MM-yyyy"));
        //Test các menu
        int choice = intMenu("Ađd", "Search", "Remove", "Update", "Print");
        System.out.println("User choice (int): " + choice);
        String objChoice = (String)objMenu("Add", "Search", "Remove", "Update", "Print");
        System.out.println("User choice (obj): " + objChoice);
        //test sinh mã tự động
        String code = dateKeyGen();
        System.out.println("Code: " + code);
    }//main()   
}//class ConsoleInputter
