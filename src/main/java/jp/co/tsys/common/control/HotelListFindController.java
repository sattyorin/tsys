/**
 */
package jp.co.tsys.common.control;

import static jp.co.tsys.common.util.MessageList.BIZERR101;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

// Store these in the session for when you reload
@SessionAttributes(types = {Member.class, HotelFindForm.class}, names = {
		"locations", "grades"})
@Controller
@RequestMapping("/hotelfind")
public class HotelListFindController {
	@Value("${locations}")
	private String locations;

	@Value("${grades}")
	private String grades;

	/** Service */
	@Autowired
	private HotelListFindService service;

	@ModelAttribute("locations")
	public List<String> initLocations() {
		return Arrays.asList(locations.split(","));
	}

	@ModelAttribute("grades")
	public List<String> initGrades() {
		return Arrays.asList(grades.split(","));
	}

	@ModelAttribute("hotelFindForm")
	public HotelFindForm initHotelFindForm() {
		return new HotelFindForm();
	}

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
			@ModelAttribute("hotelFindForm") @Validated HotelFindForm hotelFindForm,
			BindingResult result, Model model) {

		if (result.hasErrors()) {
			return "hotelsalses/find/hotel_find";
		}

		// 入力値を取得
		Integer lowPrice = hotelFindForm.getLowPrice();
		Integer highPrice = hotelFindForm.getHighPrice();
		String grade = hotelFindForm.getGrade();
		String inputCityName = hotelFindForm.getInputCityName();
		String inDate = hotelFindForm.getInDate();
		String outDate = hotelFindForm.getOutDate();

		// 値段（下限）の入力がなかった場合
		if (lowPrice == null) {
			lowPrice = 0; // 最小値をセットする
		}
		// 値段（上限）の入力がなかった場合
		if (highPrice == null) {
			highPrice = 99999999; // 最大値をセットする
		}
		// グレードの入力がなかった場合
		if (grade.equals("0")) {
			grade = "1"; // 最小値をセットする
		}

		// ServiceのfingHotelListメソッドを呼び出し
		List<HotelItem> hotelList = service.findHotelList(inputCityName, inDate,
				outDate, lowPrice, highPrice, grade);

		if (hotelList.size() == 0) {
			model.addAttribute("message", BIZERR101);
		}

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
