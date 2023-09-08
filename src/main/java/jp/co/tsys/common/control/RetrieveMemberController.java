/**
 * RetrieveMemberController.java
 */

package jp.co.tsys.common.control;

import static jp.co.tsys.common.util.MessageList.BIZERR001;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import jp.co.tsys.common.entity.Member;
import jp.co.tsys.common.form.LoginForm;
import jp.co.tsys.common.service.RetrieveMemberService;

/**
 * ログイン・ログアウトController
 */
@SessionAttributes(names = "loginmember")
@Controller
public class RetrieveMemberController {

	/** Service */
	@Autowired
	private RetrieveMemberService service;

	/**
	 * ログイン画面の【ログイン】に対応するHandlerメソッド マッピングするURL：/retrievemember
	 *
	 * @param memberCode
	 *            メンバーコード
	 * @param password
	 *            パスワード
	 * @param model
	 *            Modelオブジェクト return 従業員メニュー画面(top_menu_emp.html) or
	 *            トップメニュー(顧客)画面(top_menu.html)
	 */
	@RequestMapping("/retrievemember")
	public String retrieveMember(@Validated LoginForm loginform,
			BindingResult result, Model model) {
		// 入力チェック
		if (result.hasErrors()) {
			return "/login";
		}

		// ServiceのgetMemberメソッドを呼び出し
		// 戻り値のMemberオブジェクトを取得する
		Member loginmember = service.getMember(loginform.getMemberCode(),
				loginform.getPassword());

		// Memberオブジェクトをキー名"loginmember"でモデルに格納
		model.addAttribute("loginmember", loginmember);

		// 該当するメンバー情報が存在しない場合
		if (loginmember == null) {
			// エラーメッセージ[BIZERR001]をキー名"message"でModelに格納
			model.addAttribute("message", BIZERR001);

			// ログイン画面（/login）を返却する
			return "/login";
		}

		// 権限によって画面遷移先を振り分ける
		if (loginmember.getRole().equals("Employee")) {
			// RoleがEmployeeの場合の画面遷移先
			return "/top_menu_emp";
		} else {
			// RoleがCustomerの場合の画面遷移先
			return "/top_menu";
		}
	}

	/**
	 * ヘッダー画面の【ログアウト】に対応するHandlerメソッド マッピングするURL：/logout
	 *
	 * @param model
	 *            Modelオブジェクト return ログイン画面(login.html)
	 */
	@RequestMapping("/logout")
	public String logout(Model model, SessionStatus status) {
		model.addAttribute("loginForm", new LoginForm());
		status.setComplete();
		return "/login";
	}

	/**
	 * ヘッダー画面の【トップメニュー】に対応するHandlerメソッド マッピングするURL：/topmenu
	 *
	 * @param model
	 *            Modelオブジェクト return 従業員メニュー画面(top_menu_emp.html) or
	 *            トップメニュー(顧客)画面(top_menu.html)
	 */
	@RequestMapping("/topmenu")
	public String topMenu(@ModelAttribute("loginmember") Member loginmember,
			Model model) {

		// 権限によって画面遷移先を振り分ける
		if (loginmember.getRole().equals("Employee")) {
			// RoleがEmployeeの場合の画面遷移先
			return "/top_menu_emp";
		} else {
			// RoleがCustomerの場合の画面遷移先
			return "/top_menu";
		}
	}
}
