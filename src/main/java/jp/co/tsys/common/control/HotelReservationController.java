/**
 * HotelReservationController.java
 */

package jp.co.tsys.common.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.SessionAttributes;

import jp.co.tsys.common.entity.HotelItem;
import jp.co.tsys.common.exception.BusinessException;
import jp.co.tsys.common.exception.NoResultException;
import jp.co.tsys.common.form.HotelDetailForm;
import jp.co.tsys.common.form.HotelFindForm;
import jp.co.tsys.common.service.HotelReservationServiceImpl;

/**
 * ホテル1件検索を行うためのコントローラー
 *
 * @author 大久保
 * @version 1.0.0
 */
@Controller
@SessionAttributes(types = HotelDetailForm.class)
public class HotelReservationController {

	/** Service */
	@Autowired
	private HotelReservationServiceImpl service;

	/**
	 * 従業員用メニュー画面に遷移するHandlerメソッド
	 */
	@RequestMapping("/hotelreservation/sendemployeemenu")
	public String sendEmployeeMenu(Model model) {

		return "/hotelsalses/find/item_sales_menu";
	}

	/**
	 * ホテル検索画面の検索ボタンに対応するHandlerメソッド
	 */
	@RequestMapping("/hotelreservation/findhoteldetail/{itemCode}")
	public String findHotelDetail(@PathVariable String itemCode, Model model) {
		// itemCodeをもとにホテル商品を1件検索する
		HotelItem hotelItem = service.findHotelDetail(itemCode);
		// TODO(risa, yusaku): if hotelItem == null NoResultException?
		if (hotelItem == null) {
			// TODO(risa, yusaku):Set error code.
			throw new NoResultException("ホテルの詳細がありません");
		}

		// HotelDetailFormに検索したHotelItemを設定する
		HotelDetailForm hotelDetailForm = new HotelDetailForm();
		hotelDetailForm.setHotelItem(hotelItem);

		// 検索したホテル商品をキー名"hotelItem"でModelに格納する
		model.addAttribute("hotelDetailForm", hotelDetailForm);

		return "/hotelsalses/find/hotel_detail";
	}

	/**
	 * 業務例外のハンドリング
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(BusinessException.class)
	public String catchBizException(Model model, Exception e) {
		// エラーメッセージをキー名"message"でModelに格納
		model.addAttribute("message", e.getMessage());

		model.addAttribute("HotelFindForm", new HotelFindForm());

		return "/hotelfind/findhotel";
	}

	/**
	 * 検索結果がない場合のハンドリング
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(NoResultException.class)
	public String catchNoResultException(Model model, Exception e) {
		// エラーメッセージをキー名"message"でModelに格納
		model.addAttribute("message", e.getMessage());
		return "/hotelfind/findhotel";

	}
}
