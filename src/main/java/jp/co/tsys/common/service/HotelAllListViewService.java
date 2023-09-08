/**
 * HotelAllListViewService.java
 */

package jp.co.tsys.common.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import jp.co.tsys.common.exception.BusinessException;
import jp.co.tsys.common.form.HotelItemDetailForm;

/**
 *
 * @author FLM
 * @version 1.0.0
 */
public interface HotelAllListViewService {
	/**
	 * ホテル名、期間をもとにホテル商品を検索する
	 * 
	 * @param name
	 *            ホテル名
	 * @param startdate
	 *            (start)
	 * @param enddate
	 *            期間(end)
	 * @return {@link List<HotelItemDetailForm>}オブジェクト
	 * @throws BusinessException
	 *             検索結果が存在しない場合
	 */

	public List<HotelItemDetailForm> getHotelItem(
			@Param("hotelName") String name,
			@Param("startdate") String startdate,
			@Param("enddate") String enddate) throws BusinessException;

}
