/**
 *
 */
package jp.co.tsys.common.control;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import jp.co.tsys.common.entity.Member;
import jp.co.tsys.common.entity.Order;
import jp.co.tsys.common.exception.BusinessException;
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

	@ModelAttribute("hotelDetailForm")
	public HotelDetailForm initHotelDetailForm(HttpSession session) {
		HotelDetailForm hotelDetailForm = (HotelDetailForm) session
				.getAttribute("hotelDetailForm");
		if (hotelDetailForm != null) {
			return hotelDetailForm;
		}
		return new HotelDetailForm();
	}

	@ModelAttribute("shoppingCartForm")
	public ShoppingCartForm initShoppingCartForm(HttpSession session) {
		ShoppingCartForm shoppingCartForm = (ShoppingCartForm) session
				.getAttribute("shoppingCartForm");
		if (shoppingCartForm != null) {
			return shoppingCartForm;
		}
		return new ShoppingCartForm();
	}

	@Autowired
	private PaymentService paymentService;
	@Autowired
	private MemberFindService memberService;

	@RequestMapping("/cart")
	public String inputCart(
			@Validated @ModelAttribute("hotelDetailForm") HotelDetailForm hotelDetailForm,
			BindingResult hotelDetailResult,
			@Validated @ModelAttribute("shoppingCartForm") ShoppingCartForm shoppingCartForm,
			BindingResult shoppingCartResult, Model model) {

		// hotelDetailから飛んて来ているときだけチェック
		if (hotelDetailForm.getHotelItem() != null
				&& hotelDetailResult.hasErrors()) {
			return "/hotelsalses/find/hotel_detail";
		}

		if (shoppingCartResult.hasErrors()) {
			// ないはず
		}

		// TODO(masa): 在庫チェック
		// if (hotelDetailForm.getInputQuantity() > カートにあるホテルの残室数) {
		//
		// // エラーメッセージ[BIZERR102]をキー名"message"でModelに格納
		// model.addAttribute("message", BIZERR102);
		//
		// // ホテル詳細画面（/hotelfind/sendShoppingCart）を返却する
		// return "/hotelfind/sendShoppingCart";
		// }

		List<Order> orderList = new ArrayList<>();

		// shoppingCartFormの中
		if (shoppingCartForm.getOrders() != null) {
			orderList = shoppingCartForm.getOrders();
		}

		// hotelDetailFormの中
		System.out.println(hotelDetailForm);
		if (hotelDetailForm.getHotelItem() != null) {
			Order order = new Order();
			order.setHotelItem(hotelDetailForm.getHotelItem());
			order.setQuantity(hotelDetailForm.getInputQuantity());
			order.setSubTotal(
					order.getQuantity() * order.getHotelItem().getPrice());
			orderList.add(order);
		}

		// 合計を計算
		int sum = 0;
		for (Order item : orderList) {
			sum += item.getSubTotal();
		}

		shoppingCartForm.setOrders(orderList);
		shoppingCartForm.setOrderTotal(sum);

		// リロードでカートが増えないようにhotelDetailFormをリセット
		model.addAttribute("hotelDetailForm", new HotelDetailForm());
		model.addAttribute("shoppingCartForm", shoppingCartForm);

		return "/payment/shopping_cart";
	}

	// 予約部屋数の変更
	@RequestMapping("/changeQuantity")
	public String changeQuantity(
			@ModelAttribute("shoppingCartForm") ShoppingCartForm shoppingCartForm,
			BindingResult result, Model model) {

		if (result.hasErrors()) {
			return "/payment/shopping_cart";
		}

		List<Order> orderList = shoppingCartForm.getOrders();
		int sum = 0;

		for (Order item : orderList) {
			item.setSubTotal(
					item.getQuantity() * item.getHotelItem().getPrice());
			sum += item.getSubTotal();
		}

		shoppingCartForm.setOrders(orderList);
		shoppingCartForm.setOrderTotal(sum);
		model.addAttribute("shoppingCartForm", shoppingCartForm);
		return "/payment/shopping_cart";
	}

	@RequestMapping("/confirmation")
	public String confirmeResult(ShoppingCartForm shoppingCartForm,
			BindingResult result, Model model, HttpSession session) {
		// TODO(masa): Check this conditon.
		if (shoppingCartForm.getOrderTotal() <= 0) {
			// TODO(masa): Set error code.
			model.addAttribute("message", "カートに商品がありません");
			return "/payment/shopping_cart";
		}

		OrdersForm ordersForm = new OrdersForm();

		ordersForm.setOrders(shoppingCartForm.getOrders());
		ordersForm.setOrderTotal(shoppingCartForm.getOrderTotal());

		// 顧客と従業員の名前衝突回避用にインスタンス作成
		Member displayMemeber = new Member();

		Member loginMember = (Member) session.getAttribute("loginMember");
		System.out.println(loginMember.getRole());
		if (loginMember.getRole().equals("Customer")) {
			ordersForm.setMemberCode(loginMember.getMemberCode());
			displayMemeber.setName(loginMember.getName());
			displayMemeber.setZipCode(loginMember.getZipCode());
			displayMemeber.setPrefecture(loginMember.getPrefecture());
			displayMemeber.setAddress(loginMember.getAddress());
			displayMemeber.setTel(loginMember.getTel());
		}
		model.addAttribute("displayMemeber", displayMemeber);
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
		System.out.println(ordersForm.getMemberCode());
		paymentService.insertOrder(ordersForm);
		String lastOrderNo = paymentService.getLastOrderNo();
		ordersForm.setOrderNo(lastOrderNo);
		paymentService.insertOrderDetail(ordersForm.getOrders(),
				ordersForm.getOrderNo());

		// 在庫減らす
		paymentService.updateHotelStock(ordersForm.getOrders());

		System.out.println(ordersForm.getPayment());

		// カートの中身を破棄
		model.addAttribute("shoppingCartForm", new ShoppingCartForm());

		return "/payment/order_completion";
	}

	/**
	 * 業務例外のハンドリング
	 *
	 * @param model
	 *            Modelオブジェクト
	 * @param e
	 *            例外オブジェクト
	 */

	// 未完成メソッド
	@ExceptionHandler(BusinessException.class)
	public String caatchBizException(Model model, Exception e) {

		// エラーメッセージをキー名"message"でModelに格納
		model.addAttribute("message", e.getMessage());

		// フォームオブジェクトをキー名"hotelFindForm"でModelに格納
		model.addAttribute("shoppingCartForm", new ShoppingCartForm());
		return "/payment/shopping_cart";
	}

}
