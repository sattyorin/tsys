/**
 * MemberDeleteController.java
 */

package jp.co.tsys.common.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.SessionAttributes;

import jp.co.tsys.common.exception.BusinessException;
import jp.co.tsys.common.form.MemberForm;
import jp.co.tsys.common.service.MemberDeleteService;

/**
 *
 * @author FLM
 * @version 1.0.0
 */
@Controller
@RequestMapping("/member/delete")
@SessionAttributes(types = {MemberForm.class})
public class MemberDeleteController {

	@ModelAttribute("memberForm")
	MemberForm initMemberForm(Model model) {
		MemberForm memberForm = (MemberForm) model.getAttribute("memberForm");
		if (memberForm != null) {
			return memberForm;
		}
		return new MemberForm();
	}

	@Autowired
	private MemberDeleteService service;

	// 従業員がメンバーを削除する際の「確認」に対応するメソッド
	// 顧客が退会する際の「確認」に対応するメソッド
	@RequestMapping("/confirm")
	public String deleteMemberConfirm(String memberCode) {
		if (memberCode.isEmpty() || memberCode == null) {
			return "member/find/member_find";
		}
		// TODO(sara): if memberForm is empty
		return "member/delete/member_delete_confirm";

	}

	// 従業員がメンバーを削除する際の「確定」に対応するメソッド
	// 顧客が退会する際の「確定」に対応するメソッド
	@RequestMapping("/result")
	public String deleteMember(Model model) {
		// 削除が失敗した際のエラーをキャッチする
		try {
			MemberForm memberForm = (MemberForm) model
					.getAttribute("memberForm");
			service.deleteMember(memberForm.getMember().getMemberCode());
		} catch (BusinessException e) {
			// エラーメッセージをキー名"message"でModelに格納
			model.addAttribute("message", e.getMessage());
			return "member/delete/member_delete_final_confirm";
		}

		return "member/delete/member_delete_result";

	}

	// 顧客が退会する際の「最終確認」に対応するメソッド
	@RequestMapping("/finalconfirm")
	public String deleteCustomerFinalConfirm(String memberCode) {
		// TODO(sara): if memberForm is empty
		return "member/delete/member_delete_final_confirm";
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(BusinessException.class)
	public String catchBizException(Model model, Exception e) {
		// エラーメッセージをキー名"message"でModelに格納
		model.addAttribute("message", e.getMessage());

		return "member/delete/member_delete_confirm"; // ここ確認する
	}

}
