/**
 * HotelRegistService.java
 */

package jp.co.tsys.common.service;

import java.util.List;

import jp.co.tsys.common.entity.Hotel;
import jp.co.tsys.common.entity.HotelItem;
import jp.co.tsys.common.exception.BusinessException;

/**
 *
 * @author FLM
 * @version 1.0.0
 */
public interface RegistHotelService {

	// ホテルコードまたはホテル名で、ホテルマスタを検索する
	public List<Hotel> findHotel(String hotelCode, String hotelName);

	// 入力した値をデータベースに登録する
	// @throws BussinessException
	public void registHotelItem(HotelItem hotelItem) throws BusinessException;

	// データベースの最後の商品コードを取得する
	public String getLastHotelCode();
}
