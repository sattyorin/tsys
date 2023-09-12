/**
 * HotelAllListViewController.java
 */

package jp.co.tsys.common.control;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
@SessionAttributes(names = {"HotelItemList", "form"})
@RequestMapping("/hotelitem/list")
@Controller
public class HotelAllListViewController {
	@Autowired
	private HotelAllListViewService service;

	// 商品管理メニューに遷移（一番上）
	@RequestMapping("/itemmgrmenu")
	public String itemMgrMenu(Model model) {
		return "/hotelitem/regist/item_menu";
	}

	/**
	 * 商品管理メニューで[ホテル商品一覧]ボタンを押下 マッピングするURL：/hotelItem/list/viewList
	 *
	 * @return /hotelItem/list/retrieveHotelItem
	 */
	@RequestMapping("/viewlist")
	public String viewList(Model model) {
		model.addAttribute("form", new HotelNameDateForm());
		return "/hotelitem/list/retrievehotelitem";
	}

	/**
	 * 検索ボタンを押下 マッピングするURL：/hotelItem/list/retrieveHotelItem
	 *
	 * @return /hotelItem/list/retrieveHotelItem
	 * @throws ParseException
	 */
	@RequestMapping("/retrievehotelitem")
	public String retrieveHotelItem(HotelNameDateForm form,
			BindingResult result, Model model) throws ParseException {

		model.addAttribute("form", form);
		// 入力チェック：検索条件が未入力の場合
		if (form.getName().equals("") && form.getStartdate().equals("")
				&& form.getEnddate().equals("")) {
			// エラーメッセージ[BIZERR304]をキー名"message"でModelに格納
			model.addAttribute("message", MessageList.BIZERR304);
			model.addAttribute("HotelItemList", null);
			return "/hotelitem/list/retrievehotelitem";
		}
		// 入力チェック：期間がStartdate,Enddateどちらかしか入力されていない場合
		if (form.getStartdate().equals("") ^ form.getEnddate().equals("")) {
			model.addAttribute("message", MessageList.BIZERR103);
			model.addAttribute("HotelItemList", null);
			if (form.getName().length() >= 31) {
				model.addAttribute("message",
						"ホテル名は30文字以内で入力してください。" + MessageList.BIZERR103);
			}
			return "/hotelitem/list/retrievehotelitem";
		}

		// 入力チェック：EnddateでStartdateより前の日付けが選択された場合
		if (!form.getStartdate().equals("") && !form.getEnddate().equals("")) {

			// <<準備>>form.getStartdate()をDate型に変換
			SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date startdate = sdFormat.parse(form.getStartdate());
			Date enddate = sdFormat.parse(form.getEnddate());

			// EnddateでStartdateより前の日付けが選択された場合
			if ((startdate.after(enddate) == true)) {
				model.addAttribute("message", MessageList.BIZERR103);
				model.addAttribute("HotelItemList", null);
				if (form.getName().length() >= 31) {
					model.addAttribute("message",
							"ホテル名は30文字以内で入力してください。" + MessageList.BIZERR103);
				}
				return "/hotelitem/list/retrievehotelitem";
			}
		}

		// ＜＜入力チェック＞＞ホテル商品に30文字以上入力された場合
		if (form.getName().length() >= 31) {
			model.addAttribute("message", "ホテル名は30文字以内で入力してください。");
			model.addAttribute("HotelItemList", null);
			return "/hotelitem/list/retrievehotelitem";
		}

		// service 検索実行
		List<HotelItemDetailForm> list = service.getHotelItem(form.getName(),
				form.getStartdate(), form.getEnddate());
		// List<HotelItemDetailForm> list = (List<HotelItemDetailForm>)

		//

		// // 該当する商品情報が存在しない場合
		if (list.isEmpty()) {
			// エラーメッセージ[BIZERR002]をキー名"message"でModelに格納
			model.addAttribute("message", MessageList.BIZERR002);
			model.addAttribute("HotelItemList", null);
			model.addAttribute("message", MessageList.BIZERR002);
			return "/hotelitem/list/retrievehotelitem";
		}

		// 次画面の準備
		model.addAttribute("HotelItemList", list);
		model.addAttribute("form", form);

		return "/hotelitem/list/retrievehotelitem";
	}

	/**
	 * マッピングするURL：
	 *
	 * @return /hotelItem/list/retrieveHotelItem
	 */
	@RequestMapping("HttpStatus.BAD_REQUEST")
	@ExceptionHandler(BusinessException.class)
	public String catchBizException(Model model, Exception e) {
		// エラーメッセージをキー名"message"でModelに格納
		model.addAttribute("message", e.getMessage());
		// フォームオブジェクトをキー名"HotelNameDateForm"でModelに格納
		model.addAttribute("HotelNameDateForm", new HotelNameDateForm());
		return "/hotelitem/list/retrievehotelitem";

	}

}
