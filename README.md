## FeastPrj – Mẫu project 1 LAB211

Đây là mã nguồn mẫu do thầy **Thân Văn Sử** cung cấp để sinh viên môn **LAB211 – OOP with Java** tham khảo cách thiết kế **Project 1 – Feast Order Management** (quản lý đặt tiệc). Mục tiêu chính của project là giúp sinh viên luyện tập:

- Thiết kế lớp (Customer, SetMenu, Order, các list quản lý tương ứng).
- Tổ chức project theo mô hình tách riêng lớp dữ liệu, lớp danh sách và lớp `main` điều phối.
- Làm việc với file văn bản (CSV) và file nhị phân (Object Stream).
- Sử dụng OOP cơ bản: kế thừa, đóng gói, ghi đè `equals`/`hashCode`, thao tác với `ArrayList`.

---

## Cấu trúc và dữ liệu chính

- Lớp `core.SetMenuList` đọc dữ liệu set menu từ file **FeastMenu.CSV** (text file).
- Lớp `core.CustList` quản lý danh sách khách hàng, đọc/ghi từ file **customers.dat** (file nhị phân).
- Lớp `core.OrderList` quản lý danh sách đơn đặt tiệc, đọc/ghi từ file **feast_order_sercice.dat** (file nhị phân).
- Lớp `newpackage.FeastMmg` là chương trình `main`, hiện menu và gọi các chức năng tương ứng.

Các file dữ liệu:

- `FeastMenu.CSV`: danh sách các set menu (món ăn, giá, nguyên liệu…).
- `customers.dat`: danh sách khách hàng đã đăng ký.
- `feast_order_sercice.dat`: danh sách đơn đặt tiệc đã lưu.

---

## Cách hoạt động của chương trình

1. Khi chạy `FeastMmg.main()`:
	- Chương trình đọc danh sách **Set Menu** từ `FeastMenu.CSV`.
	- Đọc danh sách **Customer** từ `customers.dat` (nếu có).
	- Đọc danh sách **Order** từ `feast_order_sercice.dat` (nếu có).
	- Nếu không đọc được set menu, chương trình thông báo và thoát (vì không thể đặt tiệc nếu chưa có thực đơn).

2. Sau khi dữ liệu sẵn sàng, chương trình hiển thị menu chính trên console:

	1. Đăng ký khách hàng mới (Register customers).
	2. Cập nhật thông tin khách hàng (Update customer information).
	3. Tìm kiếm khách hàng theo tên (Search for customer information by name).
	4. Hiển thị danh sách thực đơn (Display feast menus).
	5. Đặt tiệc mới (Place a feast order).
	6. Cập nhật thông tin đơn đặt tiệc (Update order information).
	7. Lưu dữ liệu khách hàng và đơn đặt tiệc xuống file.
	8. Hiển thị danh sách khách hàng.
	9. Hiển thị danh sách đơn đặt tiệc.
	10. Thoát chương trình (Quit, có hỏi lưu trước khi thoát nếu có thay đổi).

3. Các thao tác chính:

- **Đăng ký/Cập nhật khách hàng**: nhập mã, tên, điện thoại, email…; dữ liệu được lưu vào `CustList` và có thể ghi ra `customers.dat`.
- **Đặt tiệc (Order)**:
  - Chọn khách hàng theo mã.
  - Chọn set menu từ danh sách.
  - Nhập số bàn, ngày tổ chức (phải sau ngày hiện tại).
  - Chương trình tính tổng chi phí và in chi tiết đơn đặt tiệc, sau đó hỏi người dùng có lưu hay không.
- **Cập nhật Order**: chọn đơn theo mã số, chọn lại set menu, sửa số bàn, sửa ngày tổ chức rồi xác nhận lưu.
- **Lưu dữ liệu**: khi chọn chức năng lưu, hoặc khi thoát nếu người dùng đồng ý lưu, danh sách khách hàng và đơn đặt tiệc sẽ được ghi xuống các file nhị phân tương ứng.

---

## Mục đích học tập

Sinh viên có thể dùng project mẫu này để:

- Quan sát cách thầy thiết kế cấu trúc lớp, chia package `core` và `newpackage`.
- Học cách tách phần **xử lý nghiệp vụ** (các lớp List, lớp dữ liệu) khỏi phần **giao diện console** (`ConsoleInputter`, `System.out`).
- Tham khảo cách đọc/ghi file nhị phân đối tượng trong Java phục vụ cho Project 1.
- Làm nền tảng để tự thiết kế lại một project mới theo yêu cầu riêng của bài LAB211.

Project này chỉ mang tính **mẫu tham khảo**, sinh viên nên hiểu rõ luồng xử lý và tự triển khai lại cho bài làm của mình, không nên chỉ sao chép.
