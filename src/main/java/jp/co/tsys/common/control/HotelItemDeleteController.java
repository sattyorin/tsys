/**
 * HotelItemDeleteController.java
 */

package jp.co.tsys.common.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.HttpSessionRequiredException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.SessionAttributes;

import jp.co.tsys.common.exception.BusinessException;
import jp.co.tsys.common.form.HotelItemDetailForm;
import jp.co.tsys.common.service.HotelItemDeleteService;
import jp.co.tsys.common.util.MessageList;

/**
 * ホテル商品削除Controller
 *
 * @author FLM
 * @version 1.0 yyyy/mm/dd
 */
@SessionAttributes(names = "hotelItemForm")
@Controller
@RequestMapping("/hotelitem/delete")
public class HotelItemDeleteController {

	/** Service */
	@Autowired
	private HotelItemDeleteService service;

	/**
	 * 商品詳細画面から削除確認画面へ遷移するHandlerメソッド マッピングするURL： confirmdelete
	 *
	 * @param model
	 *            Modelオブジェクト
	 * @return 削除確認画面（/hotelItem/delete/confirmL）
	 */
	@RequestMapping("confirmdelete")
	public String confirmDelete(
			@ModelAttribute("hotelItemForm") HotelItemDetailForm form,
			Model model) {
		return "/hotelitem/delete/confirm";
	}

	/**
	 * 削除確認画面の[確認]に対応するHandlerメソッド マッピングするURL： /hotelItem/delete/commitdelete
	 * マッピングするHTTPメソッド： POST
	 *
	 * @param hotelItemCode
	 *            削除対象商品コード
	 * @param model
	 *            Modelオブジェクト
	 * @return 削除結果画面（/hotelItem/delete/result）
	 */
	@RequestMapping(value = "commitdelete")
	public String commitDelete(
			@ModelAttribute("hotelItemForm") HotelItemDetailForm itemDetail,
			Model model) {
		// フォームオブジェクトに格納されたホテル情報をresultオブジェクトに格納する。sessionからrequestに移行するため
		HotelItemDetailForm result = new HotelItemDetailForm(
				itemDetail.getItemCode(), itemDetail.getHotelCode(),
				itemDetail.getName(), itemDetail.getDate(),
				itemDetail.getCityName(), itemDetail.getGrade(),
				itemDetail.getPrice(), itemDetail.getStock());

		// serviceのdeleteメソッド呼び出し
		service.deleteHotelItem(itemDetail.getItemCode());

		// resultオブジェクトをキー名"hotelItemForm"でmodelに格納
		model.addAttribute("hotelItemResult", result);

		// セッションからフォームオブジェクトを削除(空欄で上書き)
		model.addAttribute("hotelItemForm", new HotelItemDetailForm());

		return "/hotelitem/delete/result";
	}

	/**
	 * 商品削除確認画面の[戻る]に対応するHandlerメソッド マッピングするURL： /hotelItem/delete/quitdelete
	 *
	 * @param itemDetail
	 *            商品情報フォームオブジェクト
	 * @return 商品詳細画面（/hotelItem/retrieve/detail-HTML）
	 */
	@RequestMapping("quitdelete")
	public String quitDelete(
			@ModelAttribute("hotelItemForm") HotelItemDetailForm itemDetail,
			Model model) {
		return "/hotelitem/retrieve/itemdetail";
	}

	/**
	 * 業務例外のハンドリング レスポンスステータスコード： HttpStatus.BAD_REQUEST ハンドリングする例外クラス：
	 * BusinessException.class
	 *
	 * @param model
	 *            Modelオブジェクト
	 * @param e
	 *            例外オブジェクト
	 * @return 商品詳細画面（/hotelItem/retrieve/itemdetail）
	 */
	// TODO(kano): check thi method
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(BusinessException.class)
	public String catchBizException(Model model, Exception e) {
		// エラーメッセージをキー名"message"でModelに格納
		model.addAttribute("message", e.getMessage());

		return "/hotelitem/retrieve/itemdetail";
	}

	/**
	 * セッションが無効になった場合のハンドリング レスポンスステータスコード： HttpStatus.BAD_GATEWAY
	 * ハンドリングする例外クラス： HttpSessionRequiredException.class
	 *
	 * @param model
	 *            Modelオブジェクト
	 * @return 商品詳細画面（/hotelItem/retrieve/itemdetail）
	 */
	// TODO(kano): check thi method
	@ResponseStatus(HttpStatus.BAD_GATEWAY)
	@ExceptionHandler(HttpSessionRequiredException.class)
	public String sessionExpired(Model model) {
		// エラーメッセージをキー名"message"でModelに格納
		model.addAttribute("message", MessageList.BIZERR000);

		return "/hotelitem/retrieve/itemdetail";
	}
}
