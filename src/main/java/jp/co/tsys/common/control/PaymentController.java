/**
 *
 */
package jp.co.tsys.common.control;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import jp.co.tsys.common.entity.Member;
import jp.co.tsys.common.entity.Order;
import jp.co.tsys.common.form.HotelDetailForm;
import jp.co.tsys.common.form.OrdersForm;
import jp.co.tsys.common.form.ShoppingCartForm;
import jp.co.tsys.common.service.MemberFindService;
import jp.co.tsys.common.service.PaymentService;

@Controller
@SessionAttributes(types = {Member.class, HotelDetailForm.class,
		ShoppingCartForm.class, OrdersForm.class})
@RequestMapping("/payment")
public class PaymentController {

	@Autowired
	private PaymentService paymentService;
	@Autowired
	private MemberFindService memberService;

	// ホテルフォームとカートフォームがnullならエラーメッセージだす
	@RequestMapping("/cart")
	public String inputCart(
			@ModelAttribute("hotelDetailForm") HotelDetailForm hotelDetailForm,
			@ModelAttribute("shoppingCartForm") ShoppingCartForm shoppingCartForm,
			BindingResult result, Model model) {

		if (result.hasErrors()) {

			return "ホテル詳細画面";
		}

		//// 在庫チェック
		// if (hotelDetailForm.getInputQuantity() > カートにあるホテルの残室数) {
		//
		// // エラーメッセージ[BIZERR102]をキー名"message"でModelに格納
		// model.addAttribute("message", BIZERR102);
		//
		// // ホテル詳細画面（/hotelfind/sendShoppingCart）を返却する
		// return "/hotelfind/sendShoppingCart";
		// }

		List<Order> orderList = new ArrayList<>();

		if (shoppingCartForm.getOrders() != null) {
			orderList = shoppingCartForm.getOrders();
			// ＊shoppingCartFormがnullだと、例外でてしまう
		}

		// if (shoppingCartForm.getOrders() == null) {
		// orderList = new ArrayList<Order>();
		//// System.out.println("テスト");
		// } else {
		// orderList = shoppingCartForm.getOrders();
		//// System.out.println("テスト２");
		// }

		// System.out.println(hotelDetailForm.getInputQuantity());

		Order order = new Order();
		order.setHotelItem(hotelDetailForm.getHotelItem());
		order.setQuantity(Integer.parseInt(hotelDetailForm.getInputQuantity()));
		order.setSubTotal(
				order.getQuantity() * order.getHotelItem().getPrice());
		orderList.add(order);

		// 合計を計算
		int sum = 0;
		for (Order item : orderList) {
			sum += item.getSubTotal();
		}

		// *orderList.forEach(e -> {
		// sum += e.getSubTotal();
		// });
		// *map

		shoppingCartForm.setOrders(orderList);
		shoppingCartForm.setOrderTotal(sum);

		model.addAttribute("shoppingCartForm", shoppingCartForm);

		// for(Order item:shoppingCartForm.getOrders()){
		// System.out.println("s"+item.getQuantity());
		// }
		return "/payment/shopping_cart";
	}

	@RequestMapping("/confirmation")
	public String confirmeResult(
			@ModelAttribute("loginmember") Member loginmember,
			ShoppingCartForm shoppingCartForm, BindingResult result,
			Model model) {

		OrdersForm ordersForm = new OrdersForm();

		ordersForm.setOrders(shoppingCartForm.getOrders());
		ordersForm.setOrderTotal(shoppingCartForm.getOrderTotal());

		if (loginmember.getRole() == "Customer") {
			ordersForm.setMemberCode(loginmember.getMemberCode());

			// 顧客と従業員の名前衝突回避用にインスタンス作成
			Member displayMemeber = new Member();
			displayMemeber.setName(loginmember.getName());
			displayMemeber.setZipCode(loginmember.getZipCode());
			displayMemeber.setPrefecture(loginmember.getPrefecture());
			displayMemeber.setAddress(loginmember.getAddress());
			displayMemeber.setTel(loginmember.getTel());

			System.out.println(displayMemeber);
			model.addAttribute("displayMemeber", displayMemeber);
		}

		model.addAttribute("ordersForm", ordersForm);

		return "/payment/order_confirmation";
	}

	@RequestMapping("/findMember")
	public String findMember(@ModelAttribute OrdersForm ordersForm,
			@ModelAttribute("shoppingCartForm") ShoppingCartForm shoppingCartForm,
			BindingResult result, Model model) {

		// サービスにメンバーコード投げてメンバーオブジェクトを取得
		Member targetMember = memberService
				.findMember(ordersForm.getMemberCode());

		// 顧客と従業員の名前衝突回避用にインスタンス作成
		Member displayMemeber = new Member();

		ordersForm.setMemberCode(targetMember.getMemberCode());
		displayMemeber.setName(targetMember.getName());
		displayMemeber.setZipCode(targetMember.getZipCode());
		displayMemeber.setPrefecture(targetMember.getPrefecture());
		displayMemeber.setAddress(targetMember.getAddress());
		displayMemeber.setTel(targetMember.getTel());

		model.addAttribute("displayMemeber", displayMemeber);

		return "/payment/order_confirmation";
	}

	@RequestMapping("/completion")
	@Transactional
	public String commitResult(@ModelAttribute OrdersForm ordersForm,
			BindingResult result, Model model, SessionStatus status) {

		// 現在日時を取得する
		LocalDateTime date1 = LocalDateTime.now();
		DateTimeFormatter dtformat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String fdate1 = dtformat.format(date1);
		ordersForm.setOrderDate(fdate1);

		// データ登録
		paymentService.insertOrder(ordersForm);
		String lastOrderNo = paymentService.getLastOrderNo();
		ordersForm.setOrderNo(lastOrderNo);
		paymentService.insertOrderDetail(ordersForm.getOrders(),
				ordersForm.getOrderNo());

		// 在庫減らす
		paymentService.updateHotelStock(ordersForm.getOrders());

		// カートの中身を破棄
		model.addAttribute("shoppingCartForm", new ShoppingCartForm());

		return "/payment/order_completion";
	}

}
