/**
 * MemberOrdersFindController.java
 */

package jp.co.tsys.common.control;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import jp.co.tsys.common.entity.Hotel;
import jp.co.tsys.common.entity.HotelItem;
import jp.co.tsys.common.entity.Member;
import jp.co.tsys.common.entity.Order;
import jp.co.tsys.common.exception.BusinessException;
import jp.co.tsys.common.form.MemberCodeForm;
import jp.co.tsys.common.form.OrderHistoryForm;
import jp.co.tsys.common.service.MemberFindService;
import jp.co.tsys.common.service.MemberOrdersFindService;
import jp.co.tsys.common.service.OrdersDeleteService;
import jp.co.tsys.common.util.MessageList;
import jp.co.tsys.common.util.Pair;

/**
 *
 * @author FLM
 * @version 1.0.0
 */
@SessionAttributes(types = OrderHistoryForm.class)
@Controller
@RequestMapping("/order")
public class MemberOrdersFindController {
	@ModelAttribute("memberCodeForm")
	MemberCodeForm initMemberCodeForm() {
		return new MemberCodeForm();
	}

	@ModelAttribute("orderHistoryForm")
	OrderHistoryForm initOrderHistoryForm() {
		return new OrderHistoryForm();
	}

	@Autowired
	private MemberFindService memberFindService;
	@Autowired
	private MemberOrdersFindService memberOrdersFindService;
	@Autowired
	private OrdersDeleteService ordersDeleteService;

	// 顧客情報検索画面に遷移する。
	@RequestMapping("/find")
	public String sendFindMemberOrders(Model model) {
		return "/order/member_code_find";
	}

	// 顧客情報検索画面の検索ボタンに対応するメソッド
	// 受注一覧画面に遷移する。
	@RequestMapping("/history")
	public String findMemberOrders(@Validated MemberCodeForm memberCodeForm,
			BindingResult memberCodeResult, OrderHistoryForm orderHistoryForm,
			Model model, HttpSession session) {

		String memberCode = memberCodeForm.getMemberCode();
		if (memberCode != null && memberCodeResult.hasErrors()) {
			// TODO(sotarou, rintarou): Set error code;
			model.addAttribute("message", "メンバーコードを正しく入力してください");
			return "/order/member_code_find";
		}

		Member member = (Member) session.getAttribute("loginMember");
		if (memberCode != null) {
			try {
				member = memberFindService.findMember(memberCode);
			} catch (BusinessException e) {
				model.addAttribute("message", e);
				return "/order/member_code_find";
			}
		}

		List<Order> currentOrder = memberOrdersFindService
				.findCurrentOrder(member.getMemberCode());

		List<Order> pastOrder = memberOrdersFindService
				.findPastOrder(member.getMemberCode());

		// List<Order> -> List<Pair<Order, String>>
		List<Pair<Order, String>> currentOrderPairList = currentOrder.stream()
				.map(order -> new Pair<Order, String>(order, "false"))
				.collect(Collectors.toList());

		List<Pair<Order, String>> pastOrderPairList = pastOrder.stream()
				.map(order -> new Pair<Order, String>(order, "false"))
				.collect(Collectors.toList());

		orderHistoryForm.setCurrentOrders(currentOrderPairList);
		orderHistoryForm.setPastOrders(pastOrderPairList);
		orderHistoryForm.setMember(member);

		model.addAttribute("orderHistoryForm", orderHistoryForm);
		return "/order/order_history";
	}

	// / 受注取り消し画面の受注履歴ボタンに対応するメソッド
	// 受注一覧画面に遷移する。
	@RequestMapping("/backhistory")
	public String backHistory(Model model) {
		// // 初期値
		Member member = new Member();
		// セッションから情報取得
		OrderHistoryForm orderHistoryForm = (OrderHistoryForm) model
				.getAttribute("orderHistoryForm");
		// // Service で各メソッドを呼び出し
		// // // 戻り値Memberオブジェクトを取得する
		member = memberFindService
				.findMember(orderHistoryForm.getMember().getMemberCode());
		List<Order> currentOrder = memberOrdersFindService
				.findCurrentOrder(member.getMemberCode());
		List<Order> pastOrder = memberOrdersFindService
				.findPastOrder(member.getMemberCode());
		// List<Order> -> List<Pair<Order, String>>
		List<Pair<Order, String>> currentOrderPairList = currentOrder.stream()
				.map(order -> new Pair<Order, String>(order, "false"))
				.collect(Collectors.toList());
		List<Pair<Order, String>> pastOrderPairList = pastOrder.stream()
				.map(order -> new Pair<Order, String>(order, "false"))
				.collect(Collectors.toList());
		orderHistoryForm.setCurrentOrders(currentOrderPairList);
		orderHistoryForm.setPastOrders(pastOrderPairList);
		orderHistoryForm.setMember(member);
		model.addAttribute("orderHistoryForm", orderHistoryForm);
		return "/order/order_history";
	}

	// 受注履歴を削除して確認画面に遷移。
	@RequestMapping("/deletecomfirm")
	public String deleteCurrentOrder(
			@Validated OrderHistoryForm orderHistoryForm, BindingResult result,
			Model model) {

		boolean flag = false;
		List<HotelItem> hotelItemList = new ArrayList<>();
		for (Pair<Order, String> currentOrder : orderHistoryForm
				.getCurrentOrders()) {

			if ("True".equals(currentOrder.getSecond())) {
				HotelItem hotelItem = new HotelItem();
				Hotel hotel = new Hotel();
				hotel.setName(currentOrder.getFirst().getHotelItem().getHotel()
						.getName());
				hotelItem.setHotel(hotel);
				hotelItem.setDate(
						currentOrder.getFirst().getHotelItem().getDate());
				hotelItemList.add(hotelItem);

				ordersDeleteService.deleteCurrentOrder(
						currentOrder.getFirst().getOrderNo(),
						currentOrder.getFirst().getHotelItem().getItemCode(),
						currentOrder.getFirst().getQuantity(),
						currentOrder.getFirst().getHotelItem().getPrice());
				flag = true;

				ordersDeleteService.deleteOrderMaster();

			}
		}

		model.addAttribute("hotelItemList", hotelItemList);

		if (flag == false) {
			model.addAttribute("message", MessageList.BIZERR105);
			return "/order/order_history";
		}

		return "/order/order_delete_comfirm";
	}

	// @ExceptionHandler(BusinessException.class)
	// public String catchBizException(Model model, Exception e) {
	// model.addAttribute("message", e.getMessage());
	// return "";
	// }

}
