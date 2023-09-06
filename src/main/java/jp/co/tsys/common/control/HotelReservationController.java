/**
 * HotelReservationController.java
 */

package jp.co.tsys.common.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import jp.co.tsys.common.entity.HotelItem;
import jp.co.tsys.common.exception.BusinessException;
import jp.co.tsys.common.exception.NoResultException;
import jp.co.tsys.common.form.HotelFindForm;
import jp.co.tsys.common.service.HotelReservationServiceImpl;

/**
 * ホテル1件検索を行うためのコントローラー
 * 
 * @author 大久保
 * @version 1.0.0
 */
@Controller
public class HotelReservationController {

	/** Service */
	@Autowired
	private HotelReservationServiceImpl service;

	/**
	 * ホテル検索画面の検索ボタンに対応するHandlerメソッド
	 */
	@RequestMapping("/hotelReservation/findHotelDetail")
	public String findHotelDetail(@RequestParam String itemCode, Model model) {
		// itemCodeをもとにホテル商品を1件検索する
		HotelItem hotelItem = service.findHotelDetail(itemCode);

		// 検索したホテル商品をキー名"hotelItem"でModelに格納する
		model.addAttribute("hotelItem", hotelItem);

		return "V0801_01HotelDetailView";
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

		return "V0802_01HotelFindView";
	}

	/**
	 * 検索結果がない場合のハンドリング
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(NoResultException.class)
	public String catchNoResultException(Model model, Exception e) {
		// エラーメッセージをキー名"message"でModelに格納
		model.addAttribute("message", e.getMessage());

		return "V0802_01HotelFindView";
	}
}
