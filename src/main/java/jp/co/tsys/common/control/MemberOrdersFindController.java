/**
 * MemberOrdersFindController.java
 */

package jp.co.tsys.common.control;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

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
	@Autowired
	private MemberFindService memberservice;
	private MemberOrdersFindService orderservice;
	private OrdersDeleteService deleteService;

	// 顧客情報検索画面に遷移する。
	@RequestMapping("order/find")
	public String sendFindMemberOrders(Model model) {
		return "/order/member_order_find";
	}

	// 顧客情報検索画面の検索ボタンに対応するメソッド
	// 受注一覧画面に遷移する。
	@RequestMapping("/history")
	public String findMemberOrders(@Validated MemberCodeForm memberCodeForm,
			BindingResult result, Model model) {
		// // 初期値
		Member member = new Member();
		// セッションから情報取得
		Member loginMember = (Member) model.getAttribute("loginmember");
		// member情報から従業員と顧客を条件分岐
		if ("Employee".equals(loginMember.getRole())) {
			// 入力チェック
			if (result.hasErrors()) {
				// 受注一覧画面
				return "/order/member_order_find";
			}
			//
			// // Service で各メソッドを呼び出し
			// // 戻り値Memberオブジェクトを取得する
			member = memberservice.findMember(memberCodeForm.getMemberCode());

			OrderHistoryForm orderHistoryForm = new OrderHistoryForm();

			List<Order> currentOrder = orderservice
					.findCurrentOrder(member.getMemberCode());

			List<Order> pastOrder = orderservice
					.findPastOrder(member.getMemberCode());

			// List<Order> -> List<Pair<Order, Boolean>>
			List<Pair<Order, Boolean>> currentOrderPairList = currentOrder
					.stream().map(order -> new Pair<Order, Boolean>(order,
							Boolean.FALSE))
					.collect(Collectors.toList());

			List<Pair<Order, Boolean>> pastOrderPairList = pastOrder.stream()
					.map(order -> new Pair<Order, Boolean>(order,
							Boolean.FALSE))
					.collect(Collectors.toList());

			orderHistoryForm.setCurrentOrders(currentOrderPairList);
			orderHistoryForm.setPastOrders(pastOrderPairList);
			orderHistoryForm.setMember(member);

			model.addAttribute("orderHistoryForm", orderHistoryForm);
			return "/order/order_history";

		} else {
			OrderHistoryForm orderHistoryForm = new OrderHistoryForm();

			List<Order> currentOrder = orderservice
					.findCurrentOrder(loginMember.getMemberCode());

			List<Order> pastOrder = orderservice
					.findPastOrder(loginMember.getMemberCode());
			// List<Order> -> List<Pair<Order, Boolean>>
			List<Pair<Order, Boolean>> currentOrderPairList = currentOrder
					.stream().map(order -> new Pair<Order, Boolean>(order,
							Boolean.FALSE))
					.collect(Collectors.toList());

			List<Pair<Order, Boolean>> pastOrderPairList = pastOrder.stream()
					.map(order -> new Pair<Order, Boolean>(order,
							Boolean.FALSE))
					.collect(Collectors.toList());

			orderHistoryForm.setCurrentOrders(currentOrderPairList);
			orderHistoryForm.setPastOrders(pastOrderPairList);
			orderHistoryForm.setMember(member);

			model.addAttribute("orderHistoryForm", orderHistoryForm);
			return "/order/order_history";

		}

		// } else {
		// // Service で各メソッドを呼び出し
		// // 戻り値Memberオブジェクトを取得する
		// member = service.findMember(loginMember.getMemberCode());
		// List<Integer> orderNolist = service
		// .findMemberOrder(member.getMemberCode());
		// for (Integer oneorderNo : orderNolist) {
		// listCurrentOrder = service.findCurrentOrder(oneorderNo);
		// listPastOrder = service.findPastOrder(oneorderNo);
		// }
		// }
		// // memberオブジェクトをキー名"member"でModelに格納
		// model.addAttribute("member", member);
		// // Listオブジェクトをキー名"listPastOrder"でModelに格納
		// model.addAttribute("listPastOrder", listPastOrder);
		// // Listオブジェクトをキー名"listCurrentOrder"でModelに格納
		// model.addAttribute("listCurrentOrder", listCurrentOrder);
	}

	// 受注履歴を削除して確認画面に遷移。
	@RequestMapping("/deletecomfirm")
	public String deleteCurrentOrder(
			@Validated OrderHistoryForm orderHistoryForm, BindingResult result,
			Model model) {
		boolean flag = false;

		for (Pair<Order, Boolean> currentOrder : orderHistoryForm
				.getCurrentOrders()) {
			List<HotelItem> hotelItemList = new ArrayList<>();
			if (currentOrder.getSecond().equals("true")) {

				HotelItem hotelItem = new HotelItem();
				hotelItem.setDate(
						currentOrder.getFirst().getHotelItem().getDate());
				hotelItem.getHotel().setName(currentOrder.getFirst()
						.getHotelItem().getHotel().getName());
				hotelItemList.add(hotelItem);

				deleteService.deleteCurrentOrder(
						currentOrder.getFirst().getOrderNo(),
						currentOrder.getFirst().getHotelItem().getItemCode(),
						currentOrder.getFirst().getQuantity(),
						currentOrder.getFirst().getHotelItem().getPrice());
				flag = true;

			}
			model.addAttribute("hotelItemList", hotelItemList);
		}
		if (flag == false) {
			throw new BusinessException(MessageList.BIZERR105);
		}

		return "/order/order_delete_comfirm";
	}
}
