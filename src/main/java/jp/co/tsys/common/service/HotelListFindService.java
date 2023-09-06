/**
 * HotelItemDeleteService.java
 */

package jp.co.tsys.common.service;

import java.util.List;

import jp.co.tsys.common.entity.HotelItem;
import jp.co.tsys.common.exception.BusinessException;;

/**
 * ホテル情報検索Service
 *
 * @author FLM
 * @version 1.0 yyyy/mm/dd
 */
public interface HotelListFindService {

	/**
	 * 都市名、期間、値段（任意）、グレード（任意）をもとにホテル情報を検索する
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
	public List<HotelItem> findHotelList(String inputCityName, String inDate,
			String outDate, int lowProce, int highPrice, String grade)
			throws BusinessException;

}
