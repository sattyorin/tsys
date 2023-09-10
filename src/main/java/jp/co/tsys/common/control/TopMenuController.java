/**
 * TopMenuController.java
 */

package jp.co.tsys.common.control;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import jp.co.tsys.common.entity.Member;
import jp.co.tsys.common.form.HotelFindForm;
import jp.co.tsys.common.form.MemberForm;

/**
 * トップメニュー画面遷移Controller
 *
 * @author FLM
 * @version 1.0.0
 */

@SessionAttributes(types = {HotelFindForm.class}, names = {"locations",
		"grades"})
@Controller
@RequestMapping("/topmenu")
public class TopMenuController {

	@Value("${locations}")
	private String locations;

	@Value("${grades}")
	private String grades;

	@ModelAttribute("locations")
	public List<String> initLocations() {
		return Arrays.asList(locations.split(","));
	}

	@ModelAttribute("grades")
	public List<String> initGrades() {
		return Arrays.asList(grades.split(","));
	}

	@ModelAttribute("hotelFindForm")
	public HotelFindForm initHotelFindForm() {
		return new HotelFindForm();
	}

	/**
	 * 顧客用トップメニュー画面の【ホテル予約】に対応するHandlerメソッド マッピングするURL：/customerhotelreserve
	 * return 顧客ホテル予約画面(findHotel.html)
	 */
	@RequestMapping("/customerhotelreserve")
	public String hotelReserve(Model model) {

		return "/hotelsalses/find/hotel_find";
	}

	/**
	 * 顧客用トップメニュー画面の【予約履歴】に対応するHandlerメソッド マッピングするURL：/reserveHistory return
	 * 予約履歴画面(V0203_02OrderHistory.html)
	 */
	@RequestMapping("/reservehistory")
	public String reserveHistory() {
		return "/order/order_history";
	}

	/**
	 * 顧客用トップメニュー画面の【お客様情報 変更・退会】に対応するHandlerメソッド マッピングするURL：/changemember
	 * return お客様情報詳細画面(/member/find/retrive.html)
	 */
	@RequestMapping("/changemember")
	public String changeMember(Model model, HttpSession session) {
		MemberForm memberForm = new MemberForm();
		memberForm.setMember((Member) session.getAttribute("loginMember"));
		model.addAttribute("memberForm", memberForm);
		return "/member/find/member_detail";
	}

	/**
	 * 従業員用トップメニュー画面の【ホテル予約】に対応するHandlerメソッド マッピングするURL：/emphotelreserve return
	 * 従業員ホテル予約画面(/hotelreservation/sendemployeemenu)
	 */
	@RequestMapping("/emphotelreserve")
	public String empHotelReserve() {
		return "/hotelsalses/find/item_sales_menu";
	}

	/**
	 * 従業員用トップメニュー画面の【商品管理】に対応するHandlerメソッド マッピングするURL：/productmanage return
	 * 商品管理画面(/UC501_regist/M0004_001ItemMgrMenu)
	 */
	@RequestMapping("/productmanage")
	public String productManage() {
		return "/hotelitem/regist/item_menu";
	}

	/**
	 * 従業員用トップメニュー画面の【メンバー管理】に対応するHandlerメソッド マッピングするURL：/membermanage return
	 * 商品管理画面(/member_mgr)
	 */
	@RequestMapping("/member/regist/membermanage")
	public String memberManage() {
		return "/member/regist/member_management";
	}
}
