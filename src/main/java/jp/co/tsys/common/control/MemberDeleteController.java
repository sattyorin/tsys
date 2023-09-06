/**
 * MemberDeleteController.java
 */

package jp.co.tsys.common.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import jp.co.tsys.common.entity.Member;
import jp.co.tsys.common.exception.BusinessException;
import jp.co.tsys.common.form.MemberCodeForm;
import jp.co.tsys.common.service.MemberDeleteService;

/**
 *
 * @author FLM
 * @version 1.0.0
 */
@Controller
@RequestMapping("/member/delete")
public class MemberDeleteController {

	@Autowired
	private MemberDeleteService service;

	@RequestMapping("/find")
	public String findMember(@Validated MemberCodeForm form,
			BindingResult result, Model model) {
		// 入力チェック
		if (result.hasErrors()) {

			// メンバー検索画面（/member/find/search）を返却する
			return "/delete/MemberFindView";
		}

		// ServiceのfindMemberメソッドを呼び出し
		// 戻り値のmemberオブジェクトを取得する
		Member member = service.findMember(form.getMemberCode());

		// Memberオブジェクトをキー名"member"でModelに格納
		model.addAttribute("member", member);

		return "/delete/MemberDetailView";
	}

	// 従業員がメンバーを削除する際の「確認」に対応するメソッド
	// 顧客が退会する際の「確認」に対応するメソッド
	@RequestMapping("/confirm")
	public String deleteMemberConfirm(String memberCode) {

		return "/delete/V902_01MemberDeleteConfirmView";

	}

	// 従業員がメンバーを削除する際の「確定」に対応するメソッド
	// 顧客が退会する際の「確定」に対応するメソッド
	@RequestMapping("/member/delete/result")
	public String deleteMember(String memberCode) {

		return "/delete/V902_02MemberDeleteResultView";

	}

	// 顧客が退会する際の「最終確認」に対応するメソッド
	@RequestMapping("finalconfirm")
	public String deleteCustomerFinalConfirm(String memberCode) {

		return "/delete/V902_03MemberDeleteFinalConfirmView";

	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(BusinessException.class)
	public String catchBizException(Model model, Exception e) {
		// エラーメッセージをキー名"message"でModelに格納
		model.addAttribute("message", e.getMessage());

		// フォームオブジェクトをキー名"MemberCodeForm"でModelに格納
		// model.addAttribute("MemberCodeForm", new MemberCodeForm());

		return "/delete/V902_01MemberDeleteConfirmView"; // ここ確認する
	}

}
