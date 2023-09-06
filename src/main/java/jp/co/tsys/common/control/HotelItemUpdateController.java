/**
 * Lab3_UpdateMemberController.java
 */

package jp.co.tsys.common.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.HttpSessionRequiredException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import jp.co.tsys.common.exception.BusinessException;
import jp.co.tsys.common.form.HotelItemDetailForm;
import jp.co.tsys.common.form.ItemUpdateForm;
import jp.co.tsys.common.service.HotelItemUpdateService;

/**
 * 商品更新Controller
 * 
 * @author FLM
 * @version 1.0 yyyy/mm/dd
 */
@SessionAttributes(types = HotelItemDetailForm.class)
@Controller
@RequestMapping("/hotelitem/update")
public class HotelItemUpdateController {

	/** Service */
	@Autowired
	private HotelItemUpdateService service;

	/**
	 * 商品詳細画面から商品変更画面へ遷移するHandlerメソッド マッピングするURL： inputupdate
	 * 
	 * @param model
	 *            Modelオブジェクト
	 * @return 会員更新画面（/hotelItem/update/view）
	 */
	@RequestMapping("inputupdate")
	public String inputUpdate(Model model) {
		// sessionからdetailを取得
		HotelItemDetailForm itemDetail = (HotelItemDetailForm) model
				.getAttribute("hotelItemForm");
		// 変更用フォームの初期値設定
		ItemUpdateForm updateForm = new ItemUpdateForm(itemDetail.getDate(),
				itemDetail.getPrice(), itemDetail.getStock());

		// フォームオブジェクトをキー名"updateForm"でModelに格納
		model.addAttribute("updateForm", updateForm);

		return "/hotelItem/update/view";
	}

	/**
	 * 商品変更画面の[変更]に対応するHandlerメソッド マッピングするURL： confirmupdate マッピングするHTTPメソッド：
	 * POST
	 * 
	 * @param updateMember
	 *            会員更新情報入力フォームオブジェクト
	 * @param result
	 *            入力値検証結果オブジェクト
	 * @param model
	 *            Modelオブジェクト
	 * @return 会員更新確認画面（/hotelItem/update/confirm）
	 */
	@RequestMapping(value = "confirmupdate", method = RequestMethod.POST)
	public String confirmUpdate(@Validated ItemUpdateForm updateForm,
			BindingResult result, Model model) {
		// 入力チェック
		if (result.hasErrors()) {

			// 商品変更画面（/hotelItem/update/view-update-HTML）を返却する
			return "/hotelItem/update/view";
		}

		// フォームオブジェクトをキー名"updateForm"でModelに格納
		model.addAttribute("updateForm", updateForm);

		return "/hotelItem/update/confirm";
	}

	/**
	 * 商品変更確認画面の[確定]に対応するHandlerメソッド マッピングするURL： commitupdate マッピングするHTTPメソッド：
	 * POST
	 * 
	 * @param updateMember
	 *            会員更新情報入力フォームオブジェクト
	 * @param model
	 *            Modelオブジェクト
	 * @param status
	 *            セッションステータス
	 * @return 商品変更結果画面（/hotelItem/update/result）
	 */
	@RequestMapping(value = "commitupdate", method = RequestMethod.POST)
	public String commitUpdate(
			@ModelAttribute("updateForm") ItemUpdateForm updateForm,
			Model model, SessionStatus status) {
		// セッションに格納されているdetailFormの内容を取得する
		HotelItemDetailForm itemDetail = (HotelItemDetailForm) model
				.getAttribute("hotelItemForm");

		// フォームオブジェクトに格納されたホテル情報をresultオブジェクトに格納する。sessionからrequestに移行するため
		HotelItemDetailForm result = new HotelItemDetailForm(
				itemDetail.getItemCode(), itemDetail.getHotelCode(),
				itemDetail.getName(), updateForm.getDate(),
				itemDetail.getCityName(), itemDetail.getGrade(),
				updateForm.getPrice(), updateForm.getStock());

		// Serviceのupdateメソッドを呼び出す
		service.updateHotelItem(itemDetail.getItemCode(), updateForm.getDate(),
				updateForm.getPrice(), updateForm.getStock());

		// resultオブジェクトをキー名"hotelItemForm"でModelに格納
		model.addAttribute("hotelItemForm", result);

		// セッションからフォームオブジェクトを削除
		status.setComplete();

		return "/hotelItem/update/result";
	}

	/**
	 * 商品変更画面の[戻る]に対応するHandlerメソッド マッピングするURL：quitupdate
	 * 
	 * @param itemDetail
	 *            商品情報フォームオブジェクト
	 * @return 商品詳細画面（/hotelItem/retrieve/detail-HTML）
	 */
	@RequestMapping("quitupdate")
	public String quitUpdate(
			@ModelAttribute("hotelItemForm") HotelItemDetailForm itemDetail,
			Model model) {

		return "/hotelItem/retrieve/itemdetail";
	}

	/**
	 * 商品変更確認画面の[戻る]に対応するHandlerメソッド マッピングするURL： reviseupdate
	 * 
	 * @param updateMember
	 *            会員更新情報入力フォームオブジェクト
	 * @return 商品変更画面（/hotelItem/update/view）
	 */
	@RequestMapping("reviseupdate")
	public String reviseUpdate(
			@ModelAttribute("hotelItemForm") HotelItemDetailForm itemDetail,
			Model model) {
		return "/hotelItem/update/view";
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
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(BusinessException.class)
	public String catchBizException(Model model, Exception e) {
		// エラーメッセージをキー名"message"でModelに格納
		model.addAttribute("message", e.getMessage());

		return "/hotelItem/retrieve/itemdetail";
	}

	/**
	 * セッションが無効になった場合のハンドリング レスポンスステータスコード： HttpStatus.BAD_GATEWAY
	 * ハンドリングする例外クラス： HttpSessionRequiredException.class
	 * 
	 * @param model
	 *            Modelオブジェクト
	 * @return 商品詳細画面（/hotelItem/retrieve/itemdetail）
	 */
	@ResponseStatus(HttpStatus.BAD_GATEWAY)
	@ExceptionHandler(HttpSessionRequiredException.class)
	public String sessionExpired(Model model) {
		// エラーメッセージをキー名"message"でModelに格納
		model.addAttribute("message", "エラーメッセージ");

		return "/hotelItem/retrieve/itemdetail";
	}
}
