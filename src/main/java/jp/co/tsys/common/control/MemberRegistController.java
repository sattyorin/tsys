/**
 * MemberRegistController.java
 */

package jp.co.tsys.common.control;

import static jp.co.tsys.common.util.MessageList.BIZERR201;
import static jp.co.tsys.common.util.MessageList.BIZERR203;

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

import jp.co.tsys.common.entity.Member;
import jp.co.tsys.common.exception.BusinessException;
import jp.co.tsys.common.form.CustomerMemberForm;
import jp.co.tsys.common.form.EmployeeMemberForm;
import jp.co.tsys.common.form.LoginForm;
import jp.co.tsys.common.form.MemberForm;
import jp.co.tsys.common.service.MemberRegistService;

@SessionAttributes(names = {"memberForm", "loginmember", "customerMemberForm",
		"employeeMemberForm"})
@Controller
@RequestMapping("/member/regist")
public class MemberRegistController {

	/** Service */
	@Autowired
	private MemberRegistService service;

	/**
	 * メンバー管理メニュー画面の[メンバー登録]に対応するHandlerメソッド マッピングするURL：
	 * /member/regist/chooserole
	 *
	 * @param model
	 *            Modelオブジェクト
	 * @return メンバー登録（権限）画面（/member_regist_role）
	 */
	@RequestMapping("/chooserole")
	public String chooseRole(Model model) {

		MemberForm memberForm = new MemberForm();
		model.addAttribute("memberForm", memberForm);

		CustomerMemberForm customerMemberForm = new CustomerMemberForm();
		model.addAttribute("customerMemberForm", customerMemberForm);

		EmployeeMemberForm employeeMemberForm = new EmployeeMemberForm();
		model.addAttribute("employeeMemberForm", employeeMemberForm);

		return "/member/regist/member_regist_role";
	}

	/**
	 * メンバー登録（権限）画面の[従業員登録]、[お客様登録]、またはログイン画面の[登録はこちら]に対応するHandlerメソッド
	 * マッピングするURL： /member/regist/employee/input
	 *
	 * @param model
	 *            Modelオブジェクト
	 * @param memberForm
	 * @return メンバー登録画面（/member_regist）
	 */
	// メンバー登録（権限）画面の[お客様登録]に対応
	@RequestMapping("/customerinput")
	public String customerInputRegist(Model model) {

		// MemberForm memberForm = new MemberForm();

		// EmployeeMemberForm employeeMemberForm = new EmployeeMemberForm();

		// 入力値を取得するmodelを用意
		CustomerMemberForm customerMemberForm = new CustomerMemberForm();

		// Customerをformのroleに入れる
		customerMemberForm.setRole("Customer");

		model.addAttribute("customerMemberForm", customerMemberForm);

		return "/member/regist/member_regist";
	}

	// メンバー登録（権限）画面の[従業員登録]に対応
	@RequestMapping("/employeeinput")
	public String employeeInputRegist(Model model) {

		// MemberForm memberForm = new MemberForm();

		// CustomerMemberForm customerMemberForm = new CustomerMemberForm();

		EmployeeMemberForm employeeMemberForm = new EmployeeMemberForm();

		// Employeeをformのroleに入れる
		employeeMemberForm.setRole("Employee");

		model.addAttribute("employeeMemberForm", employeeMemberForm);

		return "/member/regist/member_regist";
	}

	// ログイン画面の[登録はこちら]に対応
	@RequestMapping("/customercinput")
	public String inputRegist(Model model) {

		// MemberForm memberForm = new MemberForm();

		// EmployeeMemberForm employeeMemberForm = new EmployeeMemberForm();

		// 入力値を取得するmodelを用意
		CustomerMemberForm customerMemberForm = new CustomerMemberForm();

		// Customerをformのroleに入れる
		customerMemberForm.setRole("CustomerC");

		model.addAttribute("customerMemberForm", customerMemberForm);

		return "/member/regist/member_regist";
	}

	/**
	 * メンバー登録画面の[確認]に対応するHandlerメソッド マッピングするURL： /member/regist/comfirm
	 * マッピングするHTTPメソッド： POST
	 *
	 * @param form
	 *            メンバー情報入力フォームオブジェクト
	 * @param result
	 *            入力値検証結果オブジェクト
	 * @return 従業員登録確認画面（/member_regist_confirm）
	 */
	// メンバー登録画面（お客様登録）の[確認]に対応
	@RequestMapping(value = "/customercomfirm", method = RequestMethod.POST)
	public String customerConfirmRegist(
			@ModelAttribute("customerMemberForm") @Validated CustomerMemberForm customerMemberForm,
			BindingResult result, Model model) {

		int count = service.countMail(customerMemberForm.getMail());

		// 入力チェック
		if (result.hasErrors()) {
			// メンバー登録画面（/member_regist）を返却する
			return "/member/regist/member_regist";

		} else if (!customerMemberForm.getPassword()
				.equals(customerMemberForm.getConfirmPassword())) {
			model.addAttribute("message", BIZERR201);
			return "/member/regist/member_regist";

		} else if (count != 0) {
			model.addAttribute("message", BIZERR203);
			return "/member/regist/member_regist";
		}

		return "/member/regist/member_regist_confirm";
	}

	// メンバー登録画面（従業員登録）の[確認]に対応
	@RequestMapping(value = "/employeecomfirm", method = RequestMethod.POST)
	public String employeeConfirmRegist(
			@ModelAttribute("employeeMemberForm") @Validated EmployeeMemberForm employeeMemberForm,
			BindingResult result, Model model) {

		int count = service.countMail(employeeMemberForm.getMail());

		// 入力チェック
		if (result.hasErrors()) {
			// メンバー登録画面（/member_regist）を返却する
			return "/member/regist/member_regist";

		} else if (!employeeMemberForm.getPassword()
				.equals(employeeMemberForm.getConfirmPassword())) {
			model.addAttribute("message", BIZERR201);
			return "/member/regist/member_regist";

		} else if (count != 0) {
			model.addAttribute("message", BIZERR203);
			return "/member/regist/member_regist";
		}

		return "/member/regist/member_regist_confirm";
	}

	/**
	 * メンバー登録確認画面の[確定]に対応するHandlerメソッド マッピングするURL： /member/regist/commit
	 * マッピングするHTTPメソッド： POST
	 *
	 * @param form
	 *            メンバー情報入力フォームオブジェクト
	 * @param model
	 *            Modelオブジェクト
	 * @param status
	 *            セッションステータス
	 * @return メンバー登録確定画面（/member_regist_result）
	 */

	// メンバー登録確認画面（お客様登録）の[確定]に対応
	@RequestMapping(value = "/customercommit", method = RequestMethod.POST)
	public String customerCommitRegist(Model model) {

		// フォームオブジェクトに格納された情報をEmployeeオブジェクトに設定する

		CustomerMemberForm customerMemberForm = (CustomerMemberForm) model
				.getAttribute("customerMemberForm");

		Member member = new Member("0", customerMemberForm.getName(),
				customerMemberForm.getPassword(), "Customer",
				customerMemberForm.getMail(), customerMemberForm.getZipCode(),
				customerMemberForm.getPrefecture(),
				customerMemberForm.getAddress(), customerMemberForm.getTel());

		// ServiceのregistEmployeeメソッドを呼び出す
		service.registMember(member);

		// Employeeオブジェクトをキー名"employee"でModelに格納
		model.addAttribute("member", member);

		// // セッションからフォームオブジェクトを削除
		// status.setComplete();

		return "/member/regist/member_regist_result";
	}

	// メンバー登録確認画面（従業員登録）の[確定]に対応
	@RequestMapping(value = "/employeecommit", method = RequestMethod.POST)
	public String employeeCommitRegist(Model model) {

		EmployeeMemberForm employeeMemberForm = (EmployeeMemberForm) model
				.getAttribute("employeeMemberForm");

		// フォームオブジェクトに格納された情報をEmployeeオブジェクトに設定する
		Member member = new Member("0", employeeMemberForm.getName(),
				employeeMemberForm.getPassword(), employeeMemberForm.getRole(),
				employeeMemberForm.getMail(), null, null, null, null);

		// ServiceのregistEmployeeメソッドを呼び出す
		service.registMember(member);

		// Employeeオブジェクトをキー名"employee"でModelに格納
		model.addAttribute("member", member);

		// // セッションからフォームオブジェクトを削除
		// status.setComplete();

		return "/member/regist/member_regist_result";
	}

	/**
	 * メンバー登録（権限）画面の[戻る]に対応するHandlerメソッド メンバー管理メニュー画面へ戻る マッピングするURL：
	 * /member/regist/returnmenu
	 *
	 * @param form
	 *            メンバー登録情報入力フォームオブジェクト
	 * @return メンバー管理メニュー画面（/member_management）
	 */
	@RequestMapping("/returnmenu")
	public String returnMemberMenu() {

		return "/member/regist/member_management";
	}

	/**
	 * 従業員が登録するメンバー登録画面の[戻る]に対応するHandlerメソッド メンバー登録（権限）画面へ戻る マッピングするURL：
	 * /member/regist/returnrole
	 *
	 * @param form
	 *            メンバー登録情報入力フォームオブジェクト
	 * @return メンバー登録（権限）画面（/member_regist_role）
	 */
	@RequestMapping("/returnrole")
	public String returnRole(Model model) {

		MemberForm memberForm = new MemberForm();
		model.addAttribute("memberForm", memberForm);

		CustomerMemberForm customerMemberForm = new CustomerMemberForm();
		model.addAttribute("customerMemberForm", customerMemberForm);

		EmployeeMemberForm employeeMemberForm = new EmployeeMemberForm();
		model.addAttribute("employeeMemberForm", employeeMemberForm);

		return "/member/regist/member_regist_role";
	}

	/**
	 * 顧客が登録する場合、メンバー登録画面の[戻る]、メンバー登録確定画面の[ログイン画面へ]に対応するHandlerメソッド ログイン画面へ戻る
	 * マッピングするURL： /member/regist/returnlogin
	 *
	 * @param form
	 *            メンバー登録情報入力フォームオブジェクト
	 * @return ログイン画面（/）
	 */
	@RequestMapping("/returnlogin")
	public String returnLogin(Model model) {
		model.addAttribute("loginForm", new LoginForm());
		return "login";
	}

	/**
	 * メンバー登録確認画面の[修正]に対応するHandlerメソッド メンバー登録画面へ戻る マッピングするURL：
	 * /member/regist/returninput
	 *
	 * @param form
	 *            メンバー登録情報入力フォームオブジェクト
	 * @return メンバー登録画面（/member_regist）
	 */
	@RequestMapping("/returncustomerinput")
	public String returnCustomerRegist(
			@ModelAttribute("customerMemberForm") CustomerMemberForm customerMemberForm) {
		return "/member/regist/member_regist";
	}

	@RequestMapping("/returnemployeeinput")
	public String returnEmployeeRegist(
			@ModelAttribute("employeeMemberForm") EmployeeMemberForm employeeMemberForm) {
		return "/member/regist/member_regist";
	}

	@RequestMapping("/returncustomercinput")
	public String returnCustomerCRegist(
			@ModelAttribute("customerMemberForm") CustomerMemberForm customerMemberForm) {
		return "/member/regist/member_regist";
	}

	/**
	 * 業務例外（主キー一意制約違反の場合）のハンドリング レスポンスステータスコード： HttpStatus.CONFLICT
	 * ハンドリングする例外クラス： BusinessException.class
	 *
	 * @param model
	 *            Modelオブジェクト
	 * @param e
	 *            例外オブジェクト
	 * @return 従業員登録画面（/eg6/insert-input）
	 */
	@ResponseStatus(HttpStatus.CONFLICT)
	@ExceptionHandler(BusinessException.class)
	public String catchBizException(Model model, Exception e) {
		// エラーメッセージをキー名"message"でModelに格納
		model.addAttribute("message", e.getMessage());

		// フォームオブジェクトをキー名"employeeForm"でModelに格納
		model.addAttribute("memberForm", new CustomerMemberForm());

		return "/member/regist/member_regist";
	}

}
