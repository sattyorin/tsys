/**
 * MemberUpdateContoroller.java
 */

package jp.co.tsys.common.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import jp.co.tsys.common.entity.Member;
import jp.co.tsys.common.form.CustomerMemberForm;
import jp.co.tsys.common.form.EmployeeMemberForm;
import jp.co.tsys.common.form.MemberForm;
import jp.co.tsys.common.service.MemberUpdateService;
import jp.co.tsys.common.util.MessageList;

/**
 *
 * @author FLM
 * @version 1.0.0
 */
@Controller
@SessionAttributes(types = {MemberForm.class,
		CustomerMemberForm.class}, names = {"memberForm", "employeeMemberForm"})
@RequestMapping("/member/update")
public class MemberUpdateContoroller {

	@ModelAttribute("memberForm")
	public MemberForm initMemberForm(Model model) {
		// TODO(sara) check
		MemberForm memberForm = (MemberForm) model.getAttribute("memberForm");
		if (memberForm != null) {
			return memberForm;
		}
		return new MemberForm();
	}

	@ModelAttribute("customerMemberForm")
	public CustomerMemberForm initCustomerMemberForm(Model model) {
		return new CustomerMemberForm();
	}
	@ModelAttribute("employeeMemberForm")
	public EmployeeMemberForm initEmployeeMemberForm(Model model) {
		return new EmployeeMemberForm();
	}

	@Autowired
	private MemberUpdateService service;

	// メンバー変更画面へ遷移するメソッド
	@RequestMapping("/entry")
	public String entryMemberUpdate(Model model) {

		// 入力値を取得するmodelを用意
		MemberForm memberForm = (MemberForm) model.getAttribute("memberForm");

		// セッションが切れた時など
		if (memberForm.getMember() == null) {
			return "/member/regist/member_management";
		}

		if (memberForm.getMember().getRole().equals("Customer")) {
			CustomerMemberForm customerMemberForm = new CustomerMemberForm();
			customerMemberForm.setName(memberForm.getMember().getName());
			customerMemberForm
					.setPassword(memberForm.getMember().getPassword());
			customerMemberForm
					.setConfirmPassword(memberForm.getConfirmPassword());
			customerMemberForm.setMail(memberForm.getMember().getMail());
			customerMemberForm.setZipCode(memberForm.getMember().getZipCode());
			customerMemberForm
					.setPrefecture(memberForm.getMember().getPrefecture());
			customerMemberForm.setAddress(memberForm.getMember().getAddress());
			customerMemberForm.setTel(memberForm.getMember().getTel());
			model.addAttribute("customerMemberForm", customerMemberForm);
		} else {
			EmployeeMemberForm employeeMemberForm = new EmployeeMemberForm();
			employeeMemberForm.setName(memberForm.getMember().getName());
			employeeMemberForm
					.setPassword(memberForm.getMember().getPassword());
			employeeMemberForm
					.setConfirmPassword(memberForm.getConfirmPassword());
			employeeMemberForm.setMail(memberForm.getMember().getMail());
			model.addAttribute("employeeMemberForm", employeeMemberForm);
		}

		// メンバー変更画面をreturnする
		return "/member/update/update_input";
	}

	// 顧客情報変更内容の確認に対応するHandlerメソッド
	@RequestMapping(value = "/customerupdate", method = RequestMethod.POST)
	public String customerUpdate(
			@ModelAttribute("customerMemberForm") @Validated CustomerMemberForm customerMemberForm,
			BindingResult result, Model model) {

		System.out.println(customerMemberForm);
		// 入力チェック
		if (result.hasErrors()) {
			// メンバー変更画面をreturnする
			// TODO(naoto): Set error code.
			return "/member/update/update_input";
		} else if (!customerMemberForm.getPassword()
				.equals(customerMemberForm.getConfirmPassword())) {
			model.addAttribute("message", MessageList.BIZERR201);
			return "/member/update/update_input";
		}

		// // フォームオブジェクトを"memberForm"でModelに格納
		// model.addAttribute("customerMemberForm", customerMemberForm);

		// 確認画面に遷移する
		return "/member/update/update_confirm";
	}

	// 従業員情報変更内容の確認に対応するHandlerメソッド
	@RequestMapping(value = "/employeeupdate", method = RequestMethod.POST)
	public String employeeUpdate(
			@ModelAttribute("employeeMemberForm") @Validated EmployeeMemberForm employeeMemberForm,
			BindingResult result, Model model) {
		// 入力チェック
		if (result.hasErrors()) {
			// メンバー変更画面をreturnする
			// TODO(naoto): Set error code.
			return "/member/update/update_input";
		} else if (!employeeMemberForm.getPassword()
				.equals(employeeMemberForm.getConfirmPassword())) {
			model.addAttribute("message", MessageList.BIZERR201);
			return "/member/update/update_input";
		}

		// 確認画面に遷移する
		return "/member/update/update_confirm";
	}

	// 顧客情報の確定更新に対応するHandlerメソッド
	@RequestMapping(value = "/customerresult", method = RequestMethod.POST)
	public String customerResult(Model model) {
		// フォームオブジェクトに格納された情報をMemberオブジェクトに設定する
		MemberForm memberForm = (MemberForm) model.getAttribute("memberForm");
		CustomerMemberForm customerMemberForm = (CustomerMemberForm) model
				.getAttribute("customerMemberForm");

		// もしセッションがきれたりしていたら
		if (memberForm == null || customerMemberForm == null) {
			// TODO(naoto, seiya): throw Exception.
			return "/member/find/member_detail";
		}

		Member member = new Member();
		member.setMemberCode(memberForm.getMember().getMemberCode());
		member.setRole("Customer");
		member.setName(customerMemberForm.getName());
		member.setPassword(customerMemberForm.getPassword());
		member.setZipCode(customerMemberForm.getZipCode());
		member.setPrefecture(customerMemberForm.getPrefecture());
		member.setAddress(customerMemberForm.getAddress());
		member.setTel(customerMemberForm.getTel());
		member.setMail(customerMemberForm.getMail());

		// ServiceのupdateMemberメソッドを呼び出す
		service.updateMember(member);

		// 結果確認画面に遷移する
		return "/member/update/update_complete";

	}

	// 従業員情報の確定更新に対応するHandlerメソッド
	@RequestMapping(value = "/employeeresult", method = RequestMethod.POST)
	public String employeeResult(Model model) {
		// フォームオブジェクトに格納された情報をMemberオブジェクトに設定する
		// TODO(sara): null check
		Member member = new Member();
		MemberForm memberForm = (MemberForm) model.getAttribute("memberForm");
		EmployeeMemberForm employeeMemberForm = (EmployeeMemberForm) model
				.getAttribute("employeeMemberForm");
		member.setMemberCode(memberForm.getMember().getMemberCode());
		member.setRole("Employee");
		member.setName(employeeMemberForm.getName());
		member.setPassword(employeeMemberForm.getPassword());
		member.setMail(employeeMemberForm.getMail());
		System.out.println(employeeMemberForm);
		System.out.println(member);

		// ServiceのupdateMemberメソッドを呼び出す
		service.updateMember(member);

		// 結果確認画面に遷移する
		return "/member/update/update_complete";

	}

	// 顧客情報の修正に対応するHandlerメソッド
	@RequestMapping("/customerreviseinput")
	public String customerReviseInput(Model model) {
		// メンバー変更画面をreturnする
		// CustomerMemberForm customerMemberForm = (CustomerMemberForm) model
		// .getAttribute("customerMemberForm");
		// MemberForm memberForm = (MemberForm)
		// model.getAttribute("memberForm");
		// memberForm.setName(customerMemberForm.getName());
		// memberForm.setPassword(customerMemberForm.getPassword());
		// memberForm.setConfirmPassword(customerMemberForm.getConfirmPassword());
		// memberForm.setZipCode(customerMemberForm.getZipCode());
		// memberForm.setPrefecture(customerMemberForm.getZipCode());
		// memberForm.setAddress(customerMemberForm.getAddress());
		// memberForm.setTel(customerMemberForm.getTel());
		// memberForm.setMail(customerMemberForm.getMail());
		// model.addAttribute("memberForm", memberForm);
		return "/member/update/update_input";
	}

	// 従業員情報の修正に対応するHandlerメソッド
	@RequestMapping("/employeereviseinput")
	public String employeereviseInput(Model model) {
		// メンバー変更画面をreturnする
		EmployeeMemberForm employeeMemberForm = (EmployeeMemberForm) model
				.getAttribute("employeeMemberForm");
		MemberForm memberForm = (MemberForm) model.getAttribute("memberForm");
		// memberForm.setName(employeeMemberForm.getName());
		// memberForm.setPassword(employeeMemberForm.getPassword());
		memberForm.setConfirmPassword(employeeMemberForm.getConfirmPassword());
		// memberForm.setMail(employeeMemberForm.getMail());
		model.addAttribute("memberForm", memberForm);
		return "/member/update/update_input";
	}
}
