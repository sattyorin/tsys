/**
 */
package jp.co.tsys.common.control;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import jp.co.tsys.common.entity.Order;
import jp.co.tsys.common.form.HotelDetailForm;
import jp.co.tsys.common.form.ShoppingCartForm;
import jp.co.tsys.common.service.PaymentService;

@Controller
@SessionAttributes(types = ShoppingCartForm.class)
@RequestMapping("/payment")
public class PaymentController {

	@Autowired
	private PaymentService service;

	@RequestMapping("/cart")
	public String inputCart(@RequestParam HotelDetailForm hotelDetailForm,
			ShoppingCartForm shoppingCartForm, BindingResult result,
			Model model) {

		if (result.hasErrors()) {
			model.addAttribute("message", "error");
			return "/payment/shopping_cart";
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

		List<Order> orderList;

		if (shoppingCartForm == null) {
			orderList = new ArrayList<Order>();
		} else {
			orderList = shoppingCartForm.getOrders();
		}

		Order order = new Order();
		order.setHotelItem(hotelDetailForm.getHotelItem());
		order.setQuantity(Integer.parseInt(hotelDetailForm.getInputQuantity()));
		orderList.add(order);

		shoppingCartForm.setOrders(orderList);
		model.addAttribute("shoppingCartForm", shoppingCartForm);
		return "shopping_cart";
	}

	// @RequestMapping("/confirmation")
	// public String confirmeResult(@RequestParam ShoppingCartForm
	// shoppingCartForm,
	// BindingResult result, Model model) {
	// return "order_confirmation";
	// }
	//
	// @RequestMapping("/complition")
	// public String commitResult(final OrdersForm ordersForm,ShoppingCartForm
	// shoppingCartForm,
	// BindingResult result, Model model,SessionStatus status) {
	//
	// Orders orders
	// = new Orders(,shoppingCartForm.getOrders());
	//
	// // ServiceのregistMemberメソッドを呼び出す
	// service.registMember(member);
	//
	// // セッションからフォームオブジェクトを削除
	// status.setComplete();
	//
	// // 会員情報オブジェクトをキー名"member"でModelに格納
	// model.addAttribute("member", member);
	// return "order_commit";
	// }

}
