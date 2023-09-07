/**
 * SearchHotelItemServiceImpl.java
 *
 */

package jp.co.tsys.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.tsys.common.exception.BusinessException;
import jp.co.tsys.common.form.HotelItemDetailForm;
import jp.co.tsys.common.mapper.HotelItemMapper;

/**
 *
 * @author FLM
 * @version 1.0.0
 */
@Service
public class SearchHotelItemServiceImpl implements SearchHotelItemService {

	// Mapper
	@Autowired
	private HotelItemMapper mapper;

	public List<HotelItemDetailForm> searchHotelItemList(String itemCode,
			String hotelName, String date) {
		// MapperのfindHotelItemListメソッドを呼び出し
		// 戻り値のList<HotelItem>オブジェクトを取得する
		List<HotelItemDetailForm> hotelItemList = mapper
				.findHotelItemList(itemCode, hotelName, date);

		// 検索結果が存在しない場合
		if (hotelItemList.isEmpty()) {

			// 業務エラーを明示的に発生させる
			throw new BusinessException("エラーメッセージ");
		}
		return hotelItemList;
	}

	public HotelItemDetailForm retrieveHotelItem(String ItemCode) {
		// MapperのfindDetailメソッドを呼び出し
		// 戻り値のHotelItemオブジェクトを取得する
		HotelItemDetailForm hotelItem = mapper.findDetail(ItemCode);

		// 検索結果が存在しない場合
		if (hotelItem == null) {

			// 業務エラーを明示的に発生させる
			throw new BusinessException("エラーメッセージ");

		}
		return hotelItem;
	}

}
