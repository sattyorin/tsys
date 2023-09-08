/**
 * MemberOrdersFindController.java
 */

package jp.co.tsys.common.control;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import jp.co.tsys.common.entity.Member;
import jp.co.tsys.common.entity.Order;
import jp.co.tsys.common.form.MemberCodeForm;
import jp.co.tsys.common.form.OrderHistoryForm;
import jp.co.tsys.common.service.MemberOrdersFindService;
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
	private MemberOrdersFindService service;

	// 顧客情報検索画面に遷移する。
	@RequestMapping("order/find")
	public String findMemberOrders(Model model) {
		return "V203_01MemberCodeFindView";
	}

	// 顧客情報検索画面の検索ボタンに対応するメソッド
	// 受注一覧画面に遷移する。
	@RequestMapping("/history")
	public String findMemberOrders(@Validated MemberCodeForm memberCodeForm,
			BindingResult result, Model model) {
		// // 初期値
		// Member member = null;
		// List<Order> listCurrentOrder = null;
		// List<Order> listPastOrder = null;
		// Orders orders = null;
		// // セッションから情報取得
		// Member loginMember = (Member) model.getAttribute("loginmember");
		// // member情報から従業員と顧客を条件分岐
		// if ("Employee".equals(loginMember.getRole())) {
		// // 入力チェック
		// if (result.hasErrors()) {
		// // 受注一覧画面
		// return "/order/member_order_find";
		// }
		//
		// // Service で各メソッドを呼び出し
		// // 戻り値Memberオブジェクトを取得する
		// member = service.findMember(memberCodeForm.getMemberCode());
		// List<Integer> orderNolist = service
		// .findMemberOrder(memberCodeForm.getMemberCode());
		//
		// for (Integer oneorderNo : orderNolist) {
		// listCurrentOrder = service.findCurrentOrder(oneorderNo);
		// listPastOrder = service.findPastOrder(oneorderNo);
		// }
		//
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

		// TODO(sara): Is orderDate necessary?
		String memberNo = "CM0002";
		Member member = new Member();
		OrderHistoryForm orderHistoryForm = new OrderHistoryForm();
		List<Order> currentOrder = service.findCurrentOrder(memberNo);
		// List<Order> -> List<Pair<Order, Boolean>>
		List<Pair<Order, Boolean>> currentOrderPairList = currentOrder.stream()
				.map(order -> new Pair<Order, Boolean>(order, Boolean.FALSE))
				.collect(Collectors.toList());
		orderHistoryForm.setCurrentOrders(currentOrderPairList);
		orderHistoryForm.setMember(member);
		model.addAttribute("orderHistoryForm", orderHistoryForm);
		return "/order/order_history";
	}

	// 受注履歴を削除して確認画面に遷移。
	@RequestMapping("/deletecomfirm")
	public String deleteCurrentOrder(
			@Validated OrderHistoryForm orderHistoryForm, BindingResult result,
			Model model) {
		for (Pair<Order, Boolean> currentOrder : orderHistoryForm
				.getCurrentOrders()) {
			System.out.println(currentOrder.getSecond());
		}
		// TODO(sotaro): set correct html
		return "/order/order_history";
	}
}
