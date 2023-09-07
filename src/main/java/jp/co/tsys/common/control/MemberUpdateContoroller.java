/**
 * MemberUpdateContoroller.java
 */

package jp.co.tsys.common.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import jp.co.tsys.common.entity.Member;
import jp.co.tsys.common.exception.BusinessException;
import jp.co.tsys.common.form.MemberForm;
import jp.co.tsys.common.service.MemberUpdateService;

/**
 *
 * @author FLM
 * @version 1.0.0
 */
@Controller
@SessionAttributes(types = MemberForm.class)
@RequestMapping("/member/update")
public class MemberUpdateContoroller {

	@Autowired
	private MemberUpdateService service;

	// メンバー変更画面へ遷移するメソッド
	@RequestMapping("/entry")
	public String entryMemberUpdate(
			@ModelAttribute("memberForm") MemberForm memberForm, Model model) {
		// 更新前の情報をmemberオブジェクトに格納する
		MemberForm member = new MemberForm();
		member.setMemberCode(memberForm.getMemberCode());
		member.setName(memberForm.getName());
		member.setPassword(memberForm.getPassword());
		member.setConfirmpassword(memberForm.getConfirmpassword());
		member.setZipCode(memberForm.getZipCode());
		member.setPrefecture(memberForm.getPrefecture());
		member.setAddress(memberForm.getAddress());
		member.setTel(memberForm.getTel());
		member.setMail(memberForm.getMail());

		model.addAttribute("memberForm", member);
		// メンバー変更画面をreturnする
		return "/member/update-input";
	}

	// 変更内容の確認に対応するHandlerメソッド
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String updateMember(
			@ModelAttribute("memberForm") @Validated MemberForm memberForm,
			BindingResult result, Model model) {
		// 入力チェック
		if (result.hasErrors()) {
			// メンバー変更画面をreturnする
			return "/Member/update-input";
		}

		// フォームオブジェクトを"memberForm"でModelに格納
		model.addAttribute("memberForm", memberForm);

		// 確認画面に遷移する
		return "/member/update-confirm";
	}

	// メンバー情報の確定更新に対応するHandlerメソッド
	@RequestMapping(value = "/result", method = RequestMethod.POST)
	public String entryUpdateMember(
			@ModelAttribute("memberForm") MemberForm memberForm, Model model,
			SessionStatus status) {
		// フォームオブジェクトに格納された情報をMemberオブジェクトに設定する
		Member member = new Member();
		String name = memberForm.getLastName() + memberForm.getFirstName();
		member.setName(name);
		// memberCodeのセッションから取得したい
		member.setMemberCode("AAAAAAAAA");
		member.setPassword(memberForm.getPassword());
		member.setRole(memberForm.getRole());
		member.setMail(memberForm.getMail());
		member.setZipCode(memberForm.getZipCode());
		member.setPrefecture(memberForm.getPrefecture());
		member.setAddress(memberForm.getAddress());
		member.setTel(memberForm.getTel());

		// ServiceのupdateMemberメソッドを呼び出す
		service.updateMember(member);

		// Memberオブジェクトをキー名"member"でModelに格納
		model.addAttribute("member", member);

		// 結果確認画面に遷移する
		return "/member/update-complete";

	}

	// メンバー情報の修正に対応するHandlerメソッド
	@RequestMapping("/reviseinput")
	public String reviseInput(
			@ModelAttribute("memberForm") MemberForm memberForm) {
		// メンバー変更画面をreturnする
		return "/member/update-input";
	}

	// 業務例外に対応するHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(BusinessException.class)
	public String catchBizException(Model model, Exception e,
			@ModelAttribute("memberForm") MemberForm memberForm) {
		// エラーメッセージをキー名"message"でModelに格納
		model.addAttribute("message", e.getMessage());

		// 更新前の情報をmemberオブジェクトに格納する
		MemberForm member = new MemberForm();
		member.setMemberCode(memberForm.getMemberCode());
		member.setName(memberForm.getName());
		member.setPassword(memberForm.getPassword());
		member.setConfirmpassword(memberForm.getConfirmpassword());
		member.setZipCode(memberForm.getZipCode());
		member.setPrefecture(memberForm.getPrefecture());
		member.setAddress(memberForm.getAddress());
		member.setTel(memberForm.getTel());
		member.setMail(memberForm.getMail());

		model.addAttribute("memberForm", member);
		// メンバー変更画面をreturnする
		return "/member/update-input";

	}

}
