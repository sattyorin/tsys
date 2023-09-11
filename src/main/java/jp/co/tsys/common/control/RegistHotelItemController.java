/**
 * HotelRegistControll.java
 *
 * All Rights Reserved, Copyright(c) Fujitsu Learning Media Limited
 */

package jp.co.tsys.common.control;

import static jp.co.tsys.common.util.MessageList.BIZERR301;
import static jp.co.tsys.common.util.MessageList.BIZERR302;
import static jp.co.tsys.common.util.MessageList.BIZERR305;

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
@RequestMapping("/hotelitem")
public class RegistHotelItemController {

	@Autowired
	private RegistHotelService service;

	// 商品管理メニューに遷移（一番上）
	// @RequestMapping("/register/itemmenu")
	// public String itemMgrMenu(Model model) {
	// return "/regist/item_menu";
	// }

	// 登録画面（検索画面）に遷移する
	@RequestMapping("/register/entryhotelitemregist")
	public String entryHotelItemRegist(Model model, SessionStatus status) {
		model.addAttribute("findHotelForm", new FindHotelForm());
		System.out.println("はじめ");
		return "/hotelitem/regist/hotel_find";
	}

	// 検索ボタン押されたとき、検索画面にもう一度遷移
	@RequestMapping("/register/findhotel")
	public String findHotel(@Validated FindHotelForm form, BindingResult result,
			Model model) {
		System.out.println("次");
		if (result.hasErrors()) {
			// TODO(ren): addAttribute error message
			// model.addAttribute("findHotelForm", new FindHotelForm());
			return "/hotelitem/regist/hotel_find";
		}

		// code,name共に空白の場合は、何も表示せずにまた検索画面へ、どちらか入力の場合は＊にして検索
		if (form.getHotelCode().isEmpty() && form.getHotelName().isEmpty()) {
			model.addAttribute("message", BIZERR301);
			return "/hotelitem/regist/hotel_find";
		} else {
			if (form.getHotelCode().isEmpty()) {
				form.setHotelCode("");
			}
			if (form.getHotelName().isEmpty()) {
				form.setHotelName("");
			}
		}

		// System.out.println("test***********");
		// System.out.println(form.getHotelName());

		List<Hotel> hotelList = service.findHotel(form.getHotelCode(),
				form.getHotelName());
		System.out.println(hotelList.size());

		if (hotelList.isEmpty()) {
			model.addAttribute("message", BIZERR302);
			return "/hotelitem/regist/hotel_find";
		}

		model.addAttribute("hotelList", hotelList);

		return "/hotelitem/regist/hotel_find";
	}

	// リンクを押されたとき、登録画面に遷移
	@RequestMapping("/register/confirmhotel/{hotelCode}")
	public String confirmHotel(@PathVariable String hotelCode, Model model) {
		List<Hotel> hotelList = service.findHotel(hotelCode, "");

		RegistHotelForm registForm = new RegistHotelForm();
		registForm.setHotelCode(hotelList.get(0).getHotelCode());
		registForm.setHotelName(hotelList.get(0).getName());
		registForm.setCity(hotelList.get(0).getCityName());
		registForm.setGrade(hotelList.get(0).getGrade());

		model.addAttribute("registHotelForm", registForm);

		// model.addAttribute("hotelList", hotelList);
		return "/hotelitem/regist/hotel_regist";
	}

	// 登録ボタンが押されたとき、登録確認画面に遷移
	@RequestMapping("/register/registhotelitem")
	public String registHotelItem(@Validated RegistHotelForm form,
			BindingResult result, Model model) {

		if (result.hasErrors()) {
			return "/hotelitem/regist/hotel_regist";
		}

		// ホテルコード、宿泊日、料金が一致している場合同じ画面に戻る
		int count = service.countHotelItem(form.getHotelCode(), form.getDate(),
				form.getPrice());
		if (count != 0) {
			// TODO(ren): use error code
			model.addAttribute("message", BIZERR305);
			return "/hotelitem/regist/hotel_regist";
		}

		// 自動でformがモデルにaddされるんだっけ？これいらん？最後のlasthotelcodenのaddはいるのか
		model.addAttribute("registHotelForm", form);

		// 商品コード自動付番
		String lastHotelCode = service.getLastHotelCode();

		// str1はItemCode３文字までの"HTL"
		String str1 = lastHotelCode.substring(0, 3);

		// str2はインデックス4からで"******"
		String str2 = lastHotelCode.substring(3, 9);

		// str2をint型に変換して+1する
		str2 = Integer.toString(Integer.parseInt(str2) + 1);

		str2 = String.format("%06d", Integer.parseInt(str2));

		// もう一度HTL******の形に戻す
		lastHotelCode = str1 + str2;

		model.addAttribute("lastHotelCode", lastHotelCode);
		form.setHotelItemCode(lastHotelCode);

		return "/hotelitem/regist/hotel_regist_confirm";
	}

	// 確定ボタンが押されたとき、登録結果画面に遷移
	@RequestMapping("/register/commithotelitem")
	public String commitHotelItem(RegistHotelForm form, Model model) {

		HotelItem hotelitem = new HotelItem();
		hotelitem.setItemCode((String) model.getAttribute("lastHotelCode"));
		List<Hotel> hotelList = service.findHotel(form.getHotelCode(), "");
		hotelitem.setHotel(hotelList.get(0));
		hotelitem.setDate(form.getDate());
		hotelitem.setPrice(form.getPrice());
		hotelitem.setStock(form.getStock());
		service.registHotelItem(hotelitem);

		model.addAttribute("registHotelForm", form);

		return "/hotelitem/regist/hotel_regist_result";
	}

	// bizinessexceptinをキャッチ
	// @ExceptionHandler(BusinessException.class)
	public String catchBizException(Model model, Exception e) {
		// エラーメッセージをキー名"message"でModelに格納
		model.addAttribute("message", e.getMessage());

		model.addAttribute("findHotelForm", new FindHotelForm());

		return "/hotelitem/regist/hotel_find";
	}

}
