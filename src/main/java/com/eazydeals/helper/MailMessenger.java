
package com.eazydeals.helper;

public class MailMessenger {

	public static void successfullyRegister(String userName, String userEmail) {

		String subject = "Chào mừng đến với EazyDeals - Đăng ký thành công!";
		String body = "Xin chào " + userName
				+ ",<p>Xin chúc mừng và chào mừng nồng nhiệt đến với EazyDeals! Chúng tôi rất vui khi có bạn trở thành một phần trong cộng đồng đang phát triển của chúng tôi. Cảm ơn bạn đã chọn chúng tôi cho nhu cầu mua sắm trực tuyến của mình.</p>"
				+ "<p>Đăng ký của bạn đã thành công và chúng tôi vui mừng thông báo với bạn rằng bạn hiện là thành viên quan trọng trên nền tảng của chúng tôi. Với EazyDeals, bạn sẽ khám phá nhiều lựa chọn sản phẩm và ưu đãi thú vị phù hợp với sở thích và sở thích của bạn"
				+ "<p>Một lần nữa, chào mừng bạn lên máy bay! Chúng tôi mong được phục vụ bạn và mang lại trải nghiệm mua sắm thú vị và bổ ích cho bạn.</p>"
				+ "<p>Mua sắm vui vẻ!</p>";
		try {
			Mail.sendMail(userEmail, subject, body);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void successfullyOrderPlaced(String userName, String userEmail, String orderId, String OrderDate) {
		String subject = "Xác nhận đơn hàng - Sản phẩm của bạn đang được vận chuyển!";
		String body = "Xian chào " + userName
				+ ",<p>Chúng tôi vui mừng thông báo với bạn rằng đơn hàng của bạn đã được đặt thành công và hiện đang được xử lý. Cảm ơn bạn đã lựa chọn EazyDeals cho nhu cầu mua sắm của mình!</p>"
				+ "<p>Chi tiết đặt hàng: <br>" + "Đơn hàng số: " + orderId + "<br>Ngày đặt: " + OrderDate + "</p>"
				+ "<p>Xin lưu ý rằng đơn đặt hàng của bạn hiện đang được chuẩn bị để vận chuyển. Đội ngũ tận tâm của chúng tôi đang làm việc chăm chỉ để đảm bảo rằng sản phẩm của bạn được đóng gói an toàn và gửi đi sớm nhất.</p>"
				+ "<p>Sau khi đơn đặt hàng của bạn được chuyển đi, chúng tôi sẽ gửi cho bạn một email khác chứa thông tin theo dõi chi tiết, cho phép bạn theo dõi hành trình của đơn hàng cho đến khi hàng đến tận nhà bạn. Chúng tôi hiểu cảm giác thú vị như thế nào khi nhận được một gói hàng và chúng tôi sẽ cố gắng hết sức để giao nó cho bạn sớm nhất có thể.</p>"
				+ "<p>Cảm ơn đã đi mua sắm với chúng tôi! Sự tin tưởng của bạn vào <b>EazyDeals</b> có ý nghĩa rất lớn đối với chúng tôi và chúng tôi hứa sẽ mang đến cho bạn trải nghiệm mua sắm đặc biệt.</p>";
		try {
			Mail.sendMail(userEmail, subject, body);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void orderShipped(String userName, String userEmail, String orderId, String OrderDate) {
		String subject = "Đơn hàng của bạn sắp được giao!";
		String body = "Xin chào " + userName
				+ "<p>Lời chào từ <b>EazyDeals</b>! Chúng tôi vui mừng thông báo với bạn rằng đơn hàng của bạn đang trên đường đến tay bạn. Gói hàng của bạn đã được vận chuyển thành công và sẽ sớm đến trước cửa nhà bạn!</p>"
				+ "<p>Chi tiết đặt hàng: <br>" + "Đơn hàng số: " + orderId + "<br>Ngày đặt: " + OrderDate + "</p>"
				+ "<p>Đội ngũ tận tâm của chúng tôi đã xử lý và đóng gói cẩn thận đơn đặt hàng của bạn để đảm bảo đơn hàng đến tay bạn trong tình trạng hoàn hảo. Khi hàng được giao, đối tác giao hàng đáng tin cậy của chúng tôi cam kết mang gói hàng đến cho bạn nhanh nhất có thể.< /p>"
				+ "<p>Một lần nữa, chúng tôi đánh giá cao sự tin tưởng của bạn vào <b>EazyDeals</b> cho nhu cầu mua sắm của bạn. Chúng tôi mong muốn mang đến cho bạn trải nghiệm mua sắm vượt trội và sự hài lòng của bạn là ưu tiên hàng đầu của chúng tôi.</p>"
				+ "<p>Cảm ơn bạn đã chọn chúng tôi!</p>";
		try {
			Mail.sendMail(userEmail, subject, body);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void sendOtp(String userEmail, int code) {
		String subject = "Mã xác nhận thay đổi mật khẩu";
		String body = "Xin chào, " + "<p>Vui lòng sử dụng mã xác minh bên dưới để đặt lại mật khẩu của bạn!</p>"
				+ "<h3>" + code + "</h3>";
		try {
			Mail.sendMail(userEmail, subject, body);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
