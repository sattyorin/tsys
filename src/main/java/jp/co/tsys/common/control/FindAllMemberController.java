/**
 * FindAllMemberController.java
 */

package jp.co.tsys.common.control;

import static jp.co.tsys.common.util.MessageList.BIZERR204;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import jp.co.tsys.common.entity.Member;
import jp.co.tsys.common.form.FindAllMemberForm;
import jp.co.tsys.common.service.FindAllMemberService;

/**
 *
 * @author FLM
 * @version 1.0.0
 */
@SessionAttributes(types = FindAllMemberForm.class)
@Controller
@RequestMapping("/member/allview")
public class FindAllMemberController {

	@ModelAttribute("findAllMemberForm")
	public FindAllMemberForm initFindAllMemberForm() {
		return new FindAllMemberForm();
	}

	@ModelAttribute("memberList")
	public List<Member> initMemberList() {
		return new ArrayList<Member>();
	}

	@Autowired
	private FindAllMemberService service;

	/**
	 * メンバー一覧画面の[戻る]に対応するHandlerメソッド マッピングするURL：return
	 *
	 * @param itemDetail
	 *            商品情報フォームオブジェクト
	 * @return 商品詳細画面（/member/retrieve/detail）
	 */
	@RequestMapping("/return")
	public String quitUpdate() {

		return "/member/regist/member_management";
	}

	@RequestMapping("viewlist")
	public String FindAllMember(Model model) {

		// Member member = new Member();
		// List<Member> memberList = new ArrayList<>();
		// memberList.add(member);
		// model.addAttribute("memberList", memberList);

		return "/member/find/find_all_member";
	}

	/**
	 * 絞り込み検索結果を表示
	 */
	@RequestMapping("/search")
	public String FindAllMember(@Validated FindAllMemberForm findAllMemberForm,
			BindingResult result, Model model) {
		// TODO(shino): もしmailのチェックを入れたなら、ここにも!=""を追加
		if (result.hasErrors() && findAllMemberForm.getTel() != "") {
			return "/member/find/find_all_member";
		}

		List<Member> memberList = service.findAllMember(
				findAllMemberForm.getRole(), findAllMemberForm.getName(),
				findAllMemberForm.getTel(), findAllMemberForm.getMail());

		model.addAttribute("memberList", memberList);

		if (memberList.size() == 0) {
			// エラーメッセージ[BIZERR204]をキー名"message"でModelに格納
			model.addAttribute("message", BIZERR204);

			return "/member/find/find_all_member";
		}
		return "/member/find/find_all_member";
	}

	/**
	 * 存在しないメンバーが検索された場合の遷移ページ マッピングするURL：
	 *
	 * @return /member/list/retrievemember
	 */
	// @RequestMapping("HttpStatus.BAD_REQUEST")
	// @ExceptionHandler(BusinessException.class)
	// public String catchBizException(Model model, Exception e) {
	// // エラーメッセージをキー名”message”でModelに格納
	// model.addAttribute("message", e.getMessage());
	// // フォームオブジェクトをキー名”HotelNameDateForm”でModelに格納
	// model.addAttribute("FindAllMemberForm", new FindAllMemberForm());
	// return "error";
	// }

}
