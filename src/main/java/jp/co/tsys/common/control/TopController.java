/**
 * TopController.java
 * All Rights Reserved, Copyright(c) Fujitsu Learning Media Limited
 */

package jp.co.tsys.common.control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.tsys.common.form.FindAllMemberForm;

/**
 * TOP画面表示Controller
 * 
 * @author FLM
 * @version 1.0 yyyy/mm/dd
 */
@Controller
public class TopController {

	/**
	 * TOP画面を表示するHandlerメソッド マッピングするURL： /(コンテキストルート)
	 * 
	 * @return トップ画面（/top）
	 */
	@RequestMapping("/")
	public String handler(Model model) {
		model.addAttribute("form", new FindAllMemberForm());
		return "/member/find/find_all_member";
	}
}
