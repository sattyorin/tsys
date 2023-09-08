/**
 * TopMenuController.java
 */

package jp.co.tsys.common.control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * トップメニュー画面遷移Controller
 *
 * @author FLM
 * @version 1.0.0
 */

@Controller
@RequestMapping("/topmenu")
public class TopMenuController {
	/**
	 * 顧客用トップメニュー画面の【ホテル予約】に対応するHandlerメソッド マッピングするURL：/customerhotelreserve
	 * return 顧客ホテル予約画面(findHotel.html)
	 */
	@RequestMapping("/customerhotelreserve")
	public String hotelReserve() {
		return "/findHotel";
	}

	/**
	 * 顧客用トップメニュー画面の【予約履歴】に対応するHandlerメソッド マッピングするURL：/reserveHistory return
	 * 予約履歴画面(V0203_02OrderHistory.html)
	 */
	@RequestMapping("/reservehistory")
	public String reserveHistory() {
		return "/V0203_02OrderHistory";
	}

	/**
	 * 顧客用トップメニュー画面の【お客様情報 変更・退会】に対応するHandlerメソッド マッピングするURL：/changemember
	 * return お客様情報詳細画面(/member/find/retrive.html)
	 */
	@RequestMapping("/changemember")
	public String changeMember() {
		return "/member/find/retrive";
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
		return "/UC501_regist/M0004_001ItemMgrMenu";
	}

	/**
	 * 従業員用トップメニュー画面の【メンバー管理】に対応するHandlerメソッド マッピングするURL：/membermanage return
	 * 商品管理画面(/member_mgr)
	 */
	@RequestMapping("/member/regist/membermanage")
	public String memberManage() {
		return "/member_management";
	}
}
