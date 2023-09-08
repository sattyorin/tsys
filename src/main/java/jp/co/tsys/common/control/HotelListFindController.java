/**
 */
package jp.co.tsys.common.control;

import static jp.co.tsys.common.util.MessageList.BIZERR101;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import jp.co.tsys.common.entity.HotelItem;
import jp.co.tsys.common.entity.Member;
import jp.co.tsys.common.exception.BusinessException;
import jp.co.tsys.common.form.HotelFindForm;
import jp.co.tsys.common.service.HotelListFindService;

@SessionAttributes(types = {Member.class, HotelFindForm.class})
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
	public String findHotel(@ModelAttribute("loginmember") Member loginmember,
			Model model) {

		// フォームオブジェクトをキー名"hotelFindForm"でModelに格納
		model.addAttribute("hotelFindForm", new HotelFindForm());

		System.out.println(loginmember.getRole());

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
	public String findHotelList(
			@ModelAttribute("hotelFindForm") @Validated HotelFindForm form,
			BindingResult result, Model model) {

		// 入力チェック
		if (result.hasErrors()) {
			// ホテル商品検索画面 (V0802_01HotelFindView.html)を返却する
			return "hotelsalses/find/hotel_find";
		}

		// 入力値を取得
		String inputCityName = form.getInputCityName();
		String inDate = form.getInDate();
		String outDate = form.getOutDate();

		// 入力値の補完
		// 値段（下限）の入力がなかった場合
		Integer lowPrice = form.getLowPrice();
		if (lowPrice == null) {
			// 最小値の0をセットする
			lowPrice = 0;
		}
		// 値段（上限）の入力がなかった場合
		Integer highPrice = form.getHighPrice();
		if (highPrice == null) {
			// 最大値の99999999をセットする
			highPrice = 99999999;
		}
		// グレードの入力がなかった場合
		String grade = form.getGrade();
		if (grade.equals("0")) {
			// 最小値の"1"をセットする
			grade = "1";
		}

		// ServiceのfingHotelListメソッドを呼び出し
		// 戻り値のList<HotelItem>オブジェクトを取得する
		List<HotelItem> hotelList = service.findHotelList(inputCityName, inDate,
				outDate, lowPrice, highPrice, grade);

		if (hotelList.size() == 0) {
			// エラーメッセージをキー名"message"でModelに格納
			model.addAttribute("message", BIZERR101);
		}

		// List<HotelItem>オブジェクトをキー名"result"でModelに格納する
		model.addAttribute("result", hotelList);

		return "hotelsalses/find/hotel_find";
	}

	/**
	 * 業務例外のハンドリング
	 *
	 * @param model
	 *            Modelオブジェクト
	 * @param e
	 *            例外オブジェクト
	 * @return ホテル商品検索画面 (V0802_01HotelFindView.html)
	 */
	@ExceptionHandler(BusinessException.class)
	public String caatchBizException(Model model, Exception e) {

		// エラーメッセージをキー名"message"でModelに格納
		model.addAttribute("message", e.getMessage());

		// フォームオブジェクトをキー名"hotelFindForm"でModelに格納
		model.addAttribute("hotelFindForm", new HotelFindForm());

		return "hotelsalses/find/hotel_find";
	}
}
