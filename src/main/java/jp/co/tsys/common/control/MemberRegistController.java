/**
 * Eg6_InsertEmpController.java
 * All Rights Reserved, Copyright(c) Fujitsu Learning Media Limited
 */

package jp.co.tsys.common.control;

import static jp.co.tsys.common.util.MessageList.BIZERR201;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import jp.co.tsys.common.entity.Member;
import jp.co.tsys.common.exception.BusinessException;
import jp.co.tsys.common.form.MemberForm;
import jp.co.tsys.common.service.MemberRegistService;

@SessionAttributes(types = MemberForm.class)
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
		// フォームオブジェクトをキー名"memberForm"でModelに格納
		MemberForm memberForm = new MemberForm();
		model.addAttribute("memberForm", memberForm);

		return "/member/regist/member_regist_role";
	}

	/**
	 * メンバー登録（権限）画面の[従業員登録]、[お客様登録]、またはログイン画面の[登録はこちら]に対応するHandlerメソッド
	 * マッピングするURL： /member/regist/employee/input
	 * 
	 * @param model
	 *            Modelオブジェクト
	 * @return メンバー登録画面（/member_regist）
	 */
	@RequestMapping("/input/{role}")
	public String inputRegist(@PathVariable String role,
			@ModelAttribute("memberForm") MemberForm form, Model model) {
		// roleの値（CustomerかEmployee）をformのroleに入れる
		form.setRole(role);
		model.addAttribute("memberForm", form);

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
	@RequestMapping(value = "/comfirm", method = RequestMethod.POST)
	public String confirmRegist(
			@ModelAttribute("memberForm") @Validated MemberForm form,
			BindingResult result, Model model) {
		// 入力チェック
		if (result.hasErrors()) {

			// メンバー登録画面（/member_regist）を返却する
			return "/member_regist";
		} else if (!form.getPassword().equals(form.getConfirmPassword())) {
			model.addAttribute("message", BIZERR201);
			return "/member_regist";
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
	@RequestMapping(value = "/commit", method = RequestMethod.POST)
	public String commitRegist(@ModelAttribute("memberForm") MemberForm form,
			Model model, SessionStatus status) {

		// フォームオブジェクトに格納された情報をEmployeeオブジェクトに設定する
		Member member = new Member("0", form.getName(), form.getPassword(),
				form.getRole(), form.getMail(), form.getZipCode(),
				form.getPrefecture(), form.getAddress(), form.getTel());

		// ServiceのregistEmployeeメソッドを呼び出す
		service.registMember(member);

		// Employeeオブジェクトをキー名"employee"でModelに格納
		model.addAttribute("member", member);

		// セッションからフォームオブジェクトを削除
		status.setComplete();

		return "/member/regist/member_regist_result";
	}

	/**
	 * メンバー登録（権限）画面の[戻る]に対応するHandlerメソッド メンバー管理メニュー画面へ戻る マッピングするURL：
	 * /member/regist/returnmenu
	 * 
	 * @param form
	 *            メンバー登録情報入力フォームオブジェクト
	 * @return メンバー管理メニュー画面（/member_mgr_menu）
	 */
	@RequestMapping("/returnmenu")
	public String returnMemberMenu(
			@ModelAttribute("memberForm") MemberForm form) {
		return "/member/regist/member_mgr_menu";
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
	public String returnRole(@ModelAttribute("memberForm") MemberForm form) {
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
	public String returnLogin(@ModelAttribute("memberForm") MemberForm form) {
		return "/member/regist/login";
	}

	/**
	 * メンバー登録確認画面の[修正]に対応するHandlerメソッド メンバー登録画面へ戻る マッピングするURL：
	 * /member/regist/returninput
	 * 
	 * @param form
	 *            メンバー登録情報入力フォームオブジェクト
	 * @return メンバー登録画面（/member_regist）
	 */
	@RequestMapping("/returninput")
	public String returnRegist(@ModelAttribute("memberForm") MemberForm form) {
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
		model.addAttribute("memberForm", new MemberForm());

		return "/member/regist/member_regist";
	}

}
