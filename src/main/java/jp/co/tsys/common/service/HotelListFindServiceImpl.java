/**
 * HotelItemDeleteService.java
 */

package jp.co.tsys.common.service;

import static jp.co.tsys.common.util.MessageList.BIZERR101;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.tsys.common.entity.HotelItem;
import jp.co.tsys.common.exception.BusinessException;
import jp.co.tsys.common.mapper.HotelItemMapper;

/**
 * ホテル情報検索Serviceの実装クラス
 *
 * @author FLM
 * @version 1.0 yyyy/mm/dd
 */
@Service
public class HotelListFindServiceImpl implements HotelListFindService {

	/** Mapper */
	@Autowired
	private HotelItemMapper mapper;

	/**
	 * 都市名、期間、値段（任意）、グレード（任意）をもとにホテル商品情報を検索する
	 *
	 * @param inputCityName
	 *            都市名
	 * @param inDate
	 *            期間（チェックアウト）
	 * @param outDate
	 *            期間（チェックアウト）
	 * @param lowPrice
	 *            値段（下限）
	 * @param highPrice
	 *            値段（上限）
	 * @param grade
	 *            グレード
	 *
	 * @throws BusinessException
	 *             検索結果が存在しない場合
	 */
	@Override
	public List<HotelItem> findHotelList(String inputCityName, String inDate,
			String outDate, int lowProce, int highPrice, String grade)
			throws BusinessException {
		// MapperのfindHotelListメソッドを呼び出し
		// ホテル商品情報のリストを格納
		List<HotelItem> result = mapper.findHotelList(inputCityName, inDate,
				outDate, lowProce, highPrice, grade);

		// ホテル商品情報のリストが空の場合
		if (result.size() == 0) {

			// 業務エラーを明示的に発生させる
			throw new BusinessException(BIZERR101);
		}

		return result;

	}
}
