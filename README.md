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