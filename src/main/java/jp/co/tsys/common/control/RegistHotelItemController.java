/**
 * HotelRegistControll.java
 */

package jp.co.tsys.common.control;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import jp.co.tsys.common.entity.Hotel;
import jp.co.tsys.common.entity.HotelItem;
import jp.co.tsys.common.form.FindHotelForm;
import jp.co.tsys.common.form.RegistHotelForm;
import jp.co.tsys.common.service.RegistHotelService;

/**
 *
 * @author FLM
 * @version 1.0.0
 */

@SessionAttributes(names = "lastHotelCode")
@Controller
@RequestMapping("hotelItem")
public class RegistHotelItemController {

	@Autowired
	private RegistHotelService service;

	// 登録画面（検索画面）に遷移する
	@RequestMapping("/hotelItem/register/entryHotelItemRegist")
	public String entryHotelItemRegist(Model model, SessionStatus status) {

		model.addAttribute("hotelFindForm", new FindHotelForm());

		status.setComplete();
		return "V0501_01HotelFindView";
		// return null;

	}

	//
	//
	// //商品管理メニューに遷移（一番上）
	// @RequestMapping("/hotelItem/register/itemMgrMenu")
	// public String itemMgrMenu(Model model) {
	//
	// return null;
	// }

	// 検索ボタン押されたとき、検索画面にもう一度遷移
	@RequestMapping("/hotelItem/register/findHotel")
	public String findHotel(@Validated FindHotelForm form, BindingResult result,
			Model model) {

		if (result.hasErrors()) {

			// エラーメッセージは？？？

			return "V0501_01HotelFindView";
		}

		// code,name共に空白の場合は、何も表示せずにまた検索画面へ、どちらか入力の場合は＊にして検索
		if (form.getHotelCode() == null && form.getHotelName() == null) {

			model.addAttribute("message", "どちらか入力！！！！");
			return "V0501_01HotelFindView";

		} else {
			if (form.getHotelCode() == null) {
				form.setHotelCode("*");
			}
			if (form.getHotelName() == null) {
				form.setHotelName("*");
			}
		}

		List<Hotel> hotelList = service.findHotel(form.getHotelCode(),
				form.getHotelName());

		model.addAttribute("hotelList", hotelList);

		return "V0501_01HotelFindView";
	}

	// リンクを押されたとき、登録画面に遷移
	@RequestMapping("/hotelItem/register/confirmHotel/{hotelCode}")
	public String confirmHotel(@PathVariable String hotelCode, Model model) {

		List<Hotel> hotelList = service.findHotel(hotelCode, "*");

		model.addAttribute("hotelList", hotelList);
		return "V0501_02HotelRegistView";
	}

	// 登録ボタンが押されたとき、登録確認画面に遷移
	@RequestMapping("/hotelItem/register/registHotelItem")
	public String registHotelItem(@Validated RegistHotelForm form,
			BindingResult result, Model model) {

		if (result.hasErrors()) {
			return "V0501_02HotelRegistView";
		}

		model.addAttribute("registHotelForm", form);

		// 商品コード自動付番
		String lastHotelCode = service.getLastHotelCode();

		// str1はItemCode３文字までの"HTL"
		String str1 = lastHotelCode.substring(0, 3);

		// str2はインデックス4からで"******"
		String str2 = lastHotelCode.substring(3, 9);

		// str2をint型に変換して+1する
		str2 = Integer.toString(Integer.parseInt(str2) + 1);

		// もう一度HTL******の形に戻す
		lastHotelCode = str1 + str2;

		model.addAttribute("lastHotelCode", lastHotelCode);

		return "V0501_03HotelRegistConfirmView.html";
	}

	// 確定ボタンが押されたとき、登録結果画面に遷移
	@RequestMapping("/hotelItem/register/commitHotelItem")
	public String commitHotelItem(RegistHotelForm form, Model model) {

		HotelItem hotelitem = new HotelItem();
		hotelitem.setItemCode((String) model.getAttribute("lastHotelCode"));

		List<Hotel> hotelList = service.findHotel(form.getHotelCode(), "*");
		hotelitem.setHotel(hotelList.get(0));

		hotelitem.setDate(form.getDate());
		hotelitem.setPrice(form.getPrice());
		hotelitem.setStock(form.getStock());

		service.registHotelItem(hotelitem);

		model.addAttribute("registHotelForm", form);

		return "V0501_04HotelRegistResultView";
	}

	// bizinessexceptinをキャッチ
	// @ExceptionHandler(BusinessException.class)
	public String catchBizException(Model model, Exception e) {
		// エラーメッセージをキー名"message"でModelに格納
		model.addAttribute("message", e.getMessage());

		model.addAttribute("hotelFindForm", new FindHotelForm());

		return "V0501_01HotelFindView";
	}

}
