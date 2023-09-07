/**
 */
package jp.co.tsys.common.control;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.tsys.common.entity.HotelItem;
import jp.co.tsys.common.exception.BusinessException;
import jp.co.tsys.common.form.HotelFindForm;
import jp.co.tsys.common.service.HotelListFindService;

@Controller
@RequestMapping("/hotelfind")
public class HotelListFindController {

	/** Service */
	@Autowired
	private HotelListFindService service;

	/**
	 * 従業員商品販売メニュー画面の[ホテル予約]orトップメニュー画面の[ホテル予約]に対応するHandlerメソッド
	 *
	 * @param model
	 *            Modelオブジェクト
	 * @return ホテル商品検索画面 (V0802_01HotelFindView.html)
	 */
	@RequestMapping("/findhotel")
	public String findHotel(Model model) {

		// フォームオブジェクトをキー名"hotelFindForm"でModelに格納
		model.addAttribute("hotelFindForm", new HotelFindForm());

		return "hotelsalses/find/hotel_find";
	}

	/**
	 * ホテル商品検索画面の[検索]に対応するHandlerメソッド()
	 *
	 * @param form
	 *            ホテル商品検索オブジェクト
	 * @param result
	 *            入力値検証結果オブジェクト
	 * @param model
	 *            Modelオブジェクト
	 * @return ホテル商品検索画面 (V0802_01HotelFindView.html)
	 */
	@RequestMapping("/findhotellist")
	public String findHotelList(HotelFindForm form, BindingResult result,
			Model model) {

		// 入力チェック
		if (result.hasErrors()) {
			// ホテル商品検索画面 (V0802_01HotelFindView.html)を返却する
			return "hotelsalses/find/hotel_find";
		}

		// ServiceのfingHotelListメソッドを呼び出し
		// 戻り値のList<HotelItem>オブジェクトを取得する
		List<HotelItem> hotelList = service.findHotelList(
				form.getInputCityName(), form.getInDate(), form.getOutDate(),
				form.getLowPrice(), form.getHighPrice(), form.getGrade());

		// List<HotelItem>オブジェクトをキー名"result"でModelに格納する
		model.addAttribute("result", hotelList);

		return "V0802_01HotelFindView.html";
	}

	/**
	 * 業務例外（検索結果が存在しない場合）のハンドリング
	 *
	 * @param model
	 *            Modelオブジェクト
	 * @param e
	 *            例外オブジェクト
	 * @return ホテル商品検索画面 (V0802_01HotelFindView.html)
	 */
	@ExceptionHandler(BusinessException.class)
	public String caatchBizException(Model model, Exception e) {

		// memberのroleを取得する
		model.addAttribute("message", e.getMessage());

		// フォームオブジェクトをキー名"hotelFindForm"でModelに格納
		model.addAttribute("hotelFindForm", new HotelFindForm());

		return "hotelsalses/find/hotel_find";
	}
}
