/**
 * SearchHotelItemController.java
 */

package jp.co.tsys.common.control;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import jp.co.tsys.common.exception.BusinessException;
import jp.co.tsys.common.form.HotelItemDetailForm;
import jp.co.tsys.common.form.RetrieveHotelItemForm;
import jp.co.tsys.common.service.SearchHotelItemService;

/**
 *
 * @author FLM
 * @version 1.0.0
 */
@SessionAttributes(names = "hotelItemForm")
@RequestMapping("/hotelItem/retrieve")
@Controller
public class SearchHotelItemController {

	// Service
	@Autowired
	private SearchHotelItemService service;

	@RequestMapping("entrysearch")
	// ホテル商品検索画面に遷移
	public String entryHotelItemSearch(Model model) {
		// 検索要素入力フォームをモデルに格納
		model.addAttribute("retrieveHotelItemForm",
				new RetrieveHotelItemForm());
		return "/hotelItem/retrieve/search_hotel";
	}

	@RequestMapping("searchlist") // 検索ボタン
	// ホテル商品検索画面に遷移しリストを表示する
	public String searchHotelItemList(Model model,
			@Validated RetrieveHotelItemForm form, BindingResult result) {
		// 入力チェック
		if (result.hasErrors()) {
			return "/hotelItem/retrieve/view";
		}
		// modelから検索フォームの取得、値の取得
		String itemCode = form.getItemCode();
		String hotelName = form.getHotelName();
		String date = form.getDate();
		// serviceの起動、上記の値の代入
		List<HotelItemDetailForm> hotelItemList = service
				.searchHotelItemList(itemCode, hotelName, date);
		// 取得したListをmodelに格納
		model.addAttribute("hotelItemList", hotelItemList);
		return "/hotelItem/retrieve/search_hotel";
	}

	@RequestMapping("detail/{itemCode}") // 一覧の商品リンク
	// ホテル商品詳細画面に遷移
	public String retrieveHotelItem(@PathVariable String itemCode,
			Model model) {
		// service起動、datailの取得SQL(引数にitemCode)
		HotelItemDetailForm hotelItem = service.retrieveHotelItem(itemCode);
		// 取得したdetailをsessionに格納→update, deleteで使う
		model.addAttribute("hotelItemForm", hotelItem);
		return "/hotelItem/retrieve/itemdetail";
	}
	// 例外処理（業務エラー）
	@ExceptionHandler(BusinessException.class)
	public String catchBizException(Model model, Exception e) {
		// エラーメッセージをキー名"message"でModelに格納
		model.addAttribute("message", e.getMessage());

		return "/hotelItem/retrieve/search_hotel";
	}
}