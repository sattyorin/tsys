/**
 * HotelAllListViewController.java
 */

package jp.co.tsys.common.control;

/**
 *
 * @author FLM
 * @version 1.0.0
 */

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import jp.co.tsys.common.exception.BusinessException;
import jp.co.tsys.common.form.HotelItemDetailForm;
import jp.co.tsys.common.form.HotelNameDateForm;
import jp.co.tsys.common.service.HotelAllListViewService;
import jp.co.tsys.common.util.MessageList;

/**
 *
 * @author FLM
 * @version 1.0.0
 */
@SessionAttributes(names = "HotelItemList")
@RequestMapping("/hotelitem/list")
@Controller
public class HotelAllListViewController {
	@Autowired
	private HotelAllListViewService service;

	/**
	 * 商品管理メニューで[ホテル商品一覧]ボタンを押下 マッピングするURL：/hotelItem/list/viewList
	 * 
	 * @return /hotelItem/list/retrieveHotelItem
	 */
	@RequestMapping("/viewlist")
	public String viewList(Model model) {
		model.addAttribute("form", new HotelNameDateForm());
		return "/hotelItem/list/retrievehotelitem";
	}

	/**
	 * 検索ボタンを押下 マッピングするURL：/hotelItem/list/retrieveHotelItem
	 * 
	 * @return /hotelItem/list/retrieveHotelItem
	 */
	@RequestMapping("/retrievehotelitem")
	public String retrieveHotelItem(HotelNameDateForm form,
			BindingResult result, Model model) {
		System.out.println("form:" + form);
		// service
		List<HotelItemDetailForm> list = service.getHotelItem(form.getName(),
				form.getStartdate(), form.getEnddate());
		// List<HotelItemDetailForm> list = (List<HotelItemDetailForm>)

		System.out.println("list:" + list);
		model.addAttribute("HotelItemList", list);
		model.addAttribute("form", new HotelNameDateForm());
		System.out.println("enddate:" + form.getEnddate());

		// 検索条件が未入力の場合
		if (form.getName().equals("") && form.getStartdate().equals("")
				&& form.getEnddate().equals("")) {
			System.out.println("検索条件が未入力");
			// エラーメッセージ[BIZERR304]をキー名"message"でModelに格納
			model.addAttribute("message", MessageList.BIZERR304);
			model.addAttribute("HotelItemList", null);
		}

		else if (form.getStartdate().equals("")
				&& form.getEnddate().equals("*")) {
			model.addAttribute("message", MessageList.BIZERR103);
			model.addAttribute("HotelItemList", null);

		}

		// // 該当する商品情報が存在しない場合
		else if (list.isEmpty()) {
			System.out.println("該当するホテル商品がありません");
			// エラーメッセージ[BIZERR002]をキー名"message"でModelに格納
			model.addAttribute("message", MessageList.BIZERR002);
			System.out.println("該当するホテル商品がありません");
		} else {

		}

		return "/hotelItem/list/retrievehotelitem";
	}

	/**
	 * マッピングするURL：
	 * 
	 * @return /hotelItem/list/retrieveHotelItem
	 */
	@RequestMapping("HttpStatus.BAD_REQUEST")
	@ExceptionHandler(BusinessException.class)
	public String catchBizException(Model model, Exception e) {
		// エラーメッセージをキー名”message”でModelに格納
		model.addAttribute("message", e.getMessage());
		// フォームオブジェクトをキー名”HotelNameDateForm”でModelに格納
		model.addAttribute("HotelNameDateForm", new HotelNameDateForm());
		return "/hotelItem/list/retrievehotelitem";

	}

}
