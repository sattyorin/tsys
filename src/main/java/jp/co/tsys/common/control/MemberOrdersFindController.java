/**
 * MemberOrdersFindController.java
 *
 * All Rights Reserved, Copyright(c) Fujitsu Learning Media Limited
 */

package jp.co.tsys.common.control;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.tsys.common.entity.Member;
import jp.co.tsys.common.entity.Order;
import jp.co.tsys.common.form.MemberCodeForm;
import jp.co.tsys.common.service.MemberOrdersFindService;

/**
 *
 * @author FLM
 * @version 1.0.0
 */
@Controller
@RequestMapping("/order")
public class MemberOrdersFindController {
	@Autowired
	private MemberOrdersFindService service;

	// 顧客情報検索画面に遷移する。
	@RequestMapping("/find")
	public String findMemberOrders(Model model) {
		return "V203_01MemberCodeFindView";
	}

	// 顧客情報検索画面の検索ボタンに対応するメソッド
	// 受注一覧画面に遷移する。
	@RequestMapping("/history")
	public String findMemberOrders(@Validated MemberCodeForm form, BindingResult result, Model model) {
		// 入力チェック
		if (result.hasErrors()) {
			// 受注一覧画面
			return "V203_01MemberCodeFindView";
		}

		// Service で各メソッドを呼び出し
		// 戻り値Memberオブジェクトを取得する
		Member member = service.findMember(form.getMemberCode());
		List<Order> listCurrentOrder = service.findCurrentOrder(form.getMemberCode());
		List<Order> listPastOrder = service.findPastOrder(form.getMemberCode());


		// memberオブジェクトをキー名"member"でModelに格納
		model.addAttribute("member", member);
		// Listオブジェクトをキー名"listPastOrder"でModelに格納
		model.addAttribute("listPastOrder", listPastOrder);
		// Listオブジェクトをキー名"listCurrentOrder"でModelに格納
		model.addAttribute("listCurrentOrder", listCurrentOrder);

		return "V203_02OrderHistory";
	}

	// 受注履歴を削除して確認画面に遷移。
	@RequestMapping("/deleteComfirm")
	public String deleteCurrentOrder() {
		return "V202_01OrderDeleteConfilm";
	}
}
