/**
 * MemeberFindController.java
 */

package jp.co.tsys.common.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import jp.co.tsys.common.entity.Member;
import jp.co.tsys.common.exception.BusinessException;
import jp.co.tsys.common.form.LoginForm;
import jp.co.tsys.common.form.MemberCodeForm;
import jp.co.tsys.common.form.MemberForm;
import jp.co.tsys.common.service.MemberFindService;

/**
 * メンバー検索のController
 * 
 * @author Yamaguchi
 * @version 1.0.0
 */
@SessionAttributes(types = MemberForm.class)
@Controller
@RequestMapping("/member/find")
public class MemeberFindController {

	/**
	 * Service
	 */
	@Autowired
	private MemberFindService service;

	/**
	 * 従業員画面のメンバー管理メニューの「メンバー変更・削除」ボタン押下→メンバー検索画面に推移
	 * マッピングするURL：/member/find/search
	 * 
	 * @param Model
	 *            model
	 * @return 商品検索画面（/V0904_01MemberFindView）
	 */
	@RequestMapping("/search")
	public String entryMemberFind(Model model) {
		model.addAttribute("memberCodeForm", new MemberCodeForm());

		return "/member_find";
	}

	/**
	 * メンバー検索画面の「検索」ボタン押下→メンバー詳細画面に遷移 マッピングするURL：/member/find/result
	 *
	 * @param @Validated
	 *            MemberCodeForm form, BindingResult result, Model model
	 * @return 商品検索画面（V0904_02_MemberDetailView） 見つからない場合、メンバー検索画面にもどる
	 */
	@RequestMapping("/result")
	public String fineMember(@Validated MemberCodeForm form,
			BindingResult result, Model model) {

		if (result.hasErrors()) {
			return "/member_find";
		}

		Member member = service.findMember(form.getMemberCode());
		model.addAttribute("memberForm", member);
		return "member_detail";
	}

	/**
	 * お客様画面の「お客様情報変更・削除」ボタン押下→メンバー詳細画面に遷移 マッピングするURL：/member/find/retrive
	 *
	 * @param @Validated
	 *            LoginForm form, BindingResult result, Model model
	 * @return 商品検索画面（V0904_02_MemberDetailView）
	 *         見つからない場合、お客様トップ画面topMenu.htmlにもどる
	 */
	@RequestMapping("/retrive")
	public String fineMember(@Validated LoginForm form, BindingResult result,
			Model model) {

		if (result.hasErrors()) {
			return "/topmenu";
		}

		Member member = service.findMember(form.getMemberCode());
		model.addAttribute("memberForm", member);
		return "member_detail";
	}

	/**
	 * BusinessExceptionハンドラ(メンバーが見つからない場合は、メンバー検索画面に戻る)
	 *
	 * @param model
	 * @param e
	 * @return
	 */
	// @ResponseStatus()
	@ExceptionHandler(BusinessException.class)
	public String catchBizException(Model model, Exception e) {
		model.addAttribute("message", e.getMessage());
		model.addAttribute("memberCodeForm", new MemberCodeForm());

		return "/member_find";
	}
	/**
	 * メンバー詳細画面にて「戻るボタン」を押された場合、メンバー検索画面に遷移する
	 *
	 * @param model
	 * @param e
	 * @return/member_find
	 */
	@RequestMapping("/return/memberfind")
	public String returnFindMember(Model model) {
		model.addAttribute("memberCodeForm", new MemberCodeForm());

		return "/member_find";
	}
	/**
	 * メンバー検索画面にて「戻るボタン」を押された場合、メンバー管理メニュー画面に遷移する
	 *
	 * @param model
	 * @param e
	 * @return/returnTopMenuEmp
	 */
	@RequestMapping("/return/findtopmenu/emp")
	public String returnMemberFind(Model model) {

		return "/member_mgr_menu.html"; // TODO(seiya):ここ絶対認識のすり合わせを行う
	}

	/**
	 * お客様の詳細画面にて「戻るボタン」を押された場合、お客様トップ画面に遷移する
	 *
	 * @param model
	 * @param e
	 * @return/returnTopMenu
	 */
	@RequestMapping("/return/findtopmenu/tmpemp")
	public String returnTopMenu(Model model) {

		return "/TopMenu"; // TODO(siya):ここ絶対認識のすり合わせを行う
	}

}
