# Tài liệu hướng dẫn môn JAVA CARD 
## I. Chức năng ghi lại lỗi vào thẻ! 
1. Trong thẻ sẽ có chức năng ghi lỗi, block thẻ và unblock thẻ

2. Chức năng ghi lỗi sẽ bao gồm 5 lỗi và mỗi lỗi sẽ được đếm ghi vi phạm. 3 lỗi sẽ khoá thẻ: 
* Vượt đèn đỏ
* Đi sai làn 
* Đi ngược chiều 
* đi quá tốc độ 
* uống rượu bia lái xe. 

Luồng thực hiện chức năng: 
* 5 lỗi này sẽ được ghi lại và lưu trên code giao diện java không được lưu trong thẻ. 
* Nếu vi phạm 4 lỗi đầu tiên sẽ được gọi đến hàm error() để được đếm lỗi. Có biến đếm lỗi ở trong thẻ mỗi
lần gọi tới sẽ được cộng thêm 1 lỗi 
* Nếu vi phạm lỗi rượu bia thì gọi đến hàm block() để block luôn thẻ đó. Có 1 biến đếm block thẻ chỉ có 0 hoặc là 1. 
* Trước khi đăng nhập sẽ gọi đến hàm checkBlock(). Xem thẻ bị block hay chưa. hàm trả về là 0 thì là chưa cho phép đăng nhập nếu 1 là đã bị block và thông báo thẻ đã bị block. 
* Sau mỗi một lần cộng thêm lỗi ở hàm error() sẽ gọi đến hàm checkerror() để kiểm tra xem đã vi phạm 3 lỗi hay chưa nếu 3 lỗi rồi thì gọi đến hàm block() để block thẻ lại.

## II. Kịch bản chức năng ghi thông tin và in thông tin ra bên ngoài

1. Thông tin sẽ được gửi vào theo một dạng cố định và với thứ tự INS như sau
* Đầu tiên phải gọi tới INS_COUNTINSERT để có thể đếm độ dài của từng trường một
* Sau khi đếm được độ dài từng array rồi thì đến INS_INSERT để có thể lưu được thông tin vào card 
* Muốn gọi thông tin ra bên ngoài thì gọi đến INS_THONGTIN
* lưu ý khi sử dụng 2 INS đầu tiên thì dữ liệu gửi vào phải giống nhau và có cú pháp như sau

```
Họ tên!Ngày sinh!CMND!GPLX!Phương tiện!
```