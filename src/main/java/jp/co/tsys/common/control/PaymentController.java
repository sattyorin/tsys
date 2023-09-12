/**
 *
 */
package jp.co.tsys.common.control;

import static jp.co.tsys.common.util.MessageList.BIZERR001;
import static jp.co.tsys.common.util.MessageList.BIZERR102;
import static jp.co.tsys.common.util.MessageList.BIZERR104;
import static jp.co.tsys.common.util.MessageList.BIZERR204;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import jp.co.tsys.common.form.MemberCodeForm;
import jp.co.tsys.common.form.OrdersForm;
import jp.co.tsys.common.form.ShoppingCartForm;
//import jp.co.tsys.common.service.MemberFindService;
import jp.co.tsys.common.service.PaymentService;

@Controller
@SessionAttributes(types = {Member.class, HotelDetailForm.class,
		ShoppingCartForm.class, OrdersForm.class, MemberCodeForm.class})
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
	// @Autowired
	// private MemberFindService memberService;

	@RequestMapping("/seeCart")
	public String seeCart(
			@ModelAttribute("shoppingCartForm") ShoppingCartForm shoppingCartForm,
			BindingResult shoppingCartResult, Model model) {

		if (shoppingCartResult.hasErrors()) {
			return "/hotelsalses/find/hotel_detail";
		}

		// リロードでカートが増えないようにhotelDetailFormをリセット
		model.addAttribute("hotelDetailForm", new HotelDetailForm());

		return "/payment/shopping_cart";
	}

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

		if (hotelDetailForm.getInputQuantity() != null) {
			// 在庫チェック
			if (hotelDetailForm.getInputQuantity() > hotelDetailForm
					.getHotelItem().getStock()) {
				// エラーメッセージ[BIZERR102]をキー名"message"でModelに格納
				model.addAttribute("message", BIZERR102);

				// ホテル詳細画面（/hotelfind/sendShoppingCart）を返却する
				return "/hotelsalses/find/hotel_detail";
			}
		}

		List<Order> orderList = new ArrayList<>();

		// shoppingCartFormの中
		if (shoppingCartForm.getOrders() != null) {
			orderList = shoppingCartForm.getOrders();
		}

		// hotelDetailFormの中
		if (hotelDetailForm.getHotelItem() != null) {
			Order order = new Order();
			order.setHotelItem(hotelDetailForm.getHotelItem());
			order.setQuantity(hotelDetailForm.getInputQuantity());
			order.setSubTotal(
					order.getQuantity() * order.getHotelItem().getPrice());
			orderList.add(order);
		}

		List<Order> setOrderList = new ArrayList<Order>();
		Set<String> idSet = new HashSet<>();
		for (Order order : orderList) {
			if (idSet.contains(order.getHotelItem().getItemCode())) {
				for (Order setOrder : setOrderList) {
					if (setOrder.getHotelItem().getItemCode()
							.equals(order.getHotelItem().getItemCode())) {
						setOrder.setQuantity(
								setOrder.getQuantity() + order.getQuantity());
						setOrder.setSubTotal(
								setOrder.getSubTotal() + order.getSubTotal());
					}
				}
			} else {
				idSet.add(order.getHotelItem().getItemCode());
				setOrderList.add(order);
			}
		}

		// 合計を計算
		int sum = 0;
		for (Order item : setOrderList) {
			sum += item.getSubTotal();
		}

		shoppingCartForm.setOrders(setOrderList);
		shoppingCartForm.setOrderTotal(sum);

		// リロードでカートが増えないようにhotelDetailFormをリセット
		model.addAttribute("hotelDetailForm", new HotelDetailForm());
		model.addAttribute("shoppingCartForm", shoppingCartForm);

		return "/payment/shopping_cart";
	}

	// 予約部屋数の変更
	@RequestMapping("/changeQuantity")
	public String changeQuantity(
			@ModelAttribute("shoppingCartForm") @Validated ShoppingCartForm shoppingCartForm,
			BindingResult result, Model model) {

		if (result.hasErrors()) {
			return "/payment/shopping_cart";
		}

		List<Order> orderList = shoppingCartForm.getOrders();
		int sum = 0;

		// 在庫チェック、小計と合計の更新
		for (Order item : orderList) {

			if (item.getQuantity() > item.getHotelItem().getStock()) {
				// エラーメッセージ[BIZERR102]をキー名"message"でModelに格納
				model.addAttribute("message", BIZERR102);
				// ホテル詳細画面（/hotelfind/sendShoppingCart）を返却する
				return "/payment/shopping_cart";
			}

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
			model.addAttribute("message", BIZERR104);
			return "/payment/shopping_cart";
		}

		// TODO(masa): 在庫チェック
		for (Order item : shoppingCartForm.getOrders()) {
			if (item.getQuantity() > item.getHotelItem().getStock()) {

				// エラーメッセージ[BIZERR102]をキー名"message"でModelに格納
				model.addAttribute("message", BIZERR102);

				// ホテル詳細画面（/hotelfind/sendShoppingCart）を返却する
				return "/payment/shopping_cart";
			}
		}

		OrdersForm ordersForm = new OrdersForm();

		ordersForm.setOrders(shoppingCartForm.getOrders());
		ordersForm.setOrderTotal(shoppingCartForm.getOrderTotal());

		// 顧客と従業員の名前衝突回避用にインスタンス作成
		Member displayMember = new Member();

		// validatino用
		MemberCodeForm memberCodeForm = new MemberCodeForm();

		Member loginMember = (Member) session.getAttribute("loginMember");
		if (loginMember.getRole().equals("Customer")) {
			memberCodeForm.setMemberCode(loginMember.getMemberCode());
			ordersForm.setMemberCode(loginMember.getMemberCode());
			displayMember.setName(loginMember.getName());
			displayMember.setZipCode(loginMember.getZipCode());
			displayMember.setPrefecture(loginMember.getPrefecture());
			displayMember.setAddress(loginMember.getAddress());
			displayMember.setTel(loginMember.getTel());
		}

		model.addAttribute("memberCodeForm", memberCodeForm);
		model.addAttribute("displayMember", displayMember);
		model.addAttribute("ordersForm", ordersForm);

		return "/payment/order_confirmation";
	}

	@RequestMapping("/findMember")
	public String findMember(
			@ModelAttribute("ordersForm") OrdersForm ordersForm,
			@Validated MemberCodeForm memberCodeForm, BindingResult result,
			Model model) {

		if (result.hasErrors()) {
			return "/payment/order_confirmation";
		}

		// サービスにメンバーコード投げてメンバーオブジェクトを取得
		Member targetMember = paymentService
				.findMember(memberCodeForm.getMemberCode());

		if (targetMember == null) {
			model.addAttribute("message", BIZERR001);
			return "/payment/order_confirmation";
		}

		// 顧客検索チェック
		if (targetMember.getRole().equals("Employee")) {
			model.addAttribute("message", BIZERR204);
			return "/payment/order_confirmation";
		}

		// 顧客と従業員の名前衝突回避用にインスタンス作成
		Member displayMember = new Member();

		ordersForm.setMemberCode(memberCodeForm.getMemberCode());
		displayMember.setName(targetMember.getName());
		displayMember.setZipCode(targetMember.getZipCode());
		displayMember.setPrefecture(targetMember.getPrefecture());
		displayMember.setAddress(targetMember.getAddress());
		displayMember.setTel(targetMember.getTel());

		model.addAttribute("displayMember", displayMember);

		return "/payment/order_confirmation";
	}

	@RequestMapping("/completion")
	@Transactional
	public String commitResult(@Validated MemberCodeForm memberCodeForm,
			BindingResult memberResult,
			@ModelAttribute("ordersForm") @Validated OrdersForm ordersForm,
			BindingResult orderResult, Boolean reloadCheck, Model model,
			SessionStatus status) {

		if (memberResult.hasErrors() || orderResult.hasErrors()) {
			return "/payment/order_confirmation";
		}

		// TODO(masa): 在庫チェック
		for (Order item : ordersForm.getOrders())
			if (item.getQuantity() > item.getHotelItem().getStock()) {

				// エラーメッセージ[BIZERR102]をキー名"message"でModelに格納
				model.addAttribute("message", BIZERR102);

				// ホテル詳細画面（/hotelfind/sendShoppingCart）を返却する
				return "/payment/order_confirmation";
			}

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

	/**
	 * 業務例外のハンドリング
	 *
	 * @param model
	 *            Modelオブジェクト
	 * @param e
	 *            例外オブジェクト
	 */

	@ExceptionHandler(BusinessException.class)
	public String caatchBizException(
			@ModelAttribute("ordersForm") OrdersForm ordersForm, Model model,
			Exception e) {

		// エラーメッセージをキー名"message"でModelに格納
		model.addAttribute("message", e.getMessage());

		// フォームオブジェクトをキー名"hotelFindForm"でModelに格納
		model.addAttribute("shoppingCartForm", new ShoppingCartForm());

		return "/payment/order_confirmation";
	}
}
