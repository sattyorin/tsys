/**
 * MemeberFindController.java
 */

package jp.co.tsys.common.control;

import static jp.co.tsys.common.util.MessageList.BIZERR001;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import jp.co.tsys.common.entity.Member;
import jp.co.tsys.common.exception.BusinessException;
import jp.co.tsys.common.form.MemberCodeForm;
import jp.co.tsys.common.form.MemberForm;
import jp.co.tsys.common.service.MemberFindService;

/**
 * メンバー検索のController
 *
 * @author Yamaguchi
 * @version 2.0.0
 */
@SessionAttributes(types = {MemberForm.class, MemberCodeForm.class})
@Controller
@RequestMapping("/member/find")
public class MemeberFindController {

	@ModelAttribute("memberCodeForm")
	MemberCodeForm initMemberCodeForm() {
		return new MemberCodeForm();
	}

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

		// Member loginmember = new Member();//ここから5行テスト用
		// loginmember.setMemberCode("CM0002");
		// loginmember.setRole("Employee");
		// loginmember.setPassword("鈴木イチロウ");
		// loginmember.setMail("1low@mephone.org");
		// loginmember.setZipCode("108-6112");
		// loginmember.setPrefecture("東京都");
		// loginmember.setTel("港区港南");
		// loginmember.setAddress("03-3334-4455");

		return "/member/find/member_find";
	}

	/**
	 * メンバー検索画面の「検索」ボタン押下→メンバー詳細画面に遷移 マッピングするURL：/member/find/result
	 *
	 * @param @Validated
	 *            MemberCodeForm form, BindingResult result, Model model
	 * @return 商品検索画面（V0904_02_MemberDetailView） 見つからない場合、メンバー検索画面にもどる
	 */
	@RequestMapping("/result")
	public String findMember(@Validated MemberCodeForm form,
			BindingResult result, Model model) {

		if (result.hasErrors()) {
			return "/member/find/member_find";
		}

		Member member = service.findMember(form.getMemberCode());// メンバーコードに一致したメンバー情報を取得

		if (member == null) {
			model.addAttribute("message", BIZERR001);
		}

		MemberForm memberForm = new MemberForm();
		memberForm.setMember(member);
		// memberForm.setMemberCode(member.getMemberCode());
		// memberForm.setName(member.getName());
		// memberForm.setRole(member.getRole());
		// memberForm.setPassword(member.getPassword());
		// memberForm.setZipCode(member.getZipCode());
		// memberForm.setPrefecture(member.getPrefecture());
		// memberForm.setAddress(member.getAddress());
		// memberForm.setTel(member.getTel());
		// memberForm.setMail(member.getMail());// new
		// MemberFormにメンバー情報をセット

		model.addAttribute("memberForm", memberForm);

		return "/member/find/member_detail";
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
	public String retriveMember(Model model, HttpSession session) {

		MemberForm memberForm = new MemberForm();
		memberForm.setMember((Member) session.getAttribute("loginMember"));

		// Member loginmember = new Member();//ここから(A)までテスト用
		// loginmember.setMemberCode("CM0005");
		// loginmember.setRole("Customer");
		// loginmember.setName("高橋亜紀");
		// loginmember.setPassword("pass");
		// loginmember.setMail("takaaki@aaa.com");
		// loginmember.setZipCode("146-0083");
		// loginmember.setPrefecture("東京都");
		// loginmember.setTel("大田区千鳥");
		// loginmember.setAddress("090-1111-2222");
		//
		// model.addAttribute("loginmember", loginmember);//ログインメンバーにログインする役職を設定
		// //(A)テスト終了

		model.addAttribute("memberForm", memberForm);
		return "/member/find/member_detail";
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

		return "/member/find/member_find";
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

		return "/member/find/member_find";
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

		return "/member/regist/member_management";
	}

	/**
	 * お客様の詳細画面にて「戻るボタン」を押された場合、お客様トップ画面に遷移する
	 *
	 * @param model
	 * @param e
	 * @return/returnTopMenu
	 */
	@RequestMapping("/return/findtopmenu/cust")
	public String returnTopMenu(Model model) {

		return "/top_menu";
	}
}
