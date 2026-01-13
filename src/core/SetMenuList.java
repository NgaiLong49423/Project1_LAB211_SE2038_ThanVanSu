package core;

import java.util.ArrayList;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;

public class SetMenuList extends ArrayList<SetMenu> {
    /* Đọc các set menu từ text file
    Input: tên file
    Output: Không, Các set menu trong file được đọc và lưu vào danh sách này trong bộ nhớ
    Algorithm
        Bắt đầu
        Nếu file tồn tại {
            Mò file văn bản để đọc theo dòng
            Bỏ dòng đầu
            Khi còn đọc được 1 line từ file {
                Cắt line theo ký tự ',' để lấy từng thành phần data
                Tạo mới 1 set menu với data đã có
                Thêm set menu vào danh sách
            }
            Đóng file
        }
        Kết thúc
    */
    
    public void readFile (String fname) {
        File f = new File (fname);
        if (!f.exists()) {
            System.out.println("The file " + fname + " does not exist!");
            return;
        }
        try {
            FileReader fr = new FileReader (f);
            BufferedReader br = new BufferedReader (fr);
            br.readLine(); // bỏ qua dòng đầu chứa các tiêu đề
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) { // 1 line phải có đủ 4 thành phần
                    int price = Integer.parseInt(parts[2]);
                    parts[3] = parts[3].substring(1, parts[3].length()-2); //bỏ đi "" 
                    SetMenu m = new SetMenu(parts[0], parts[1], price, parts[3]);
                    this.add(m);
                }
            }
            br.close();
            fr.close();
        }
        catch (Exception e) {
            System.err.println(e);
        }
    }

    /* Xuất danh sách các set menu ra màn hình
       Đầu vào: Không
       Đầu ra: Không, chỉ xuất ra màn hình
       Thuật toán (Algorithm)
       Bắt đầu
           Nếu danh sách trống, thông báo danh sách trống
           Ngược lại {
               Xuất khung
               Với mỗi setMenu trong danh sách, xuất setMenu này
           }
       Kết thúc
    */

   public void print() {
        if (this.isEmpty()) {
            System.out.println("Set menu list is emppty!");
            return;
        }
        // Xuat khung
        String header = "--------------------------------------------------------\n" +
                        " List of set menus for ordering party:\n" +
                        "--------------------------------------------------------\n" ;

        System.out.println(header);
        // xuat cac set menu
        for (SetMenu m: this) System.out.println(m.toStringScreen());
    }// print()
}// ReadFile()
