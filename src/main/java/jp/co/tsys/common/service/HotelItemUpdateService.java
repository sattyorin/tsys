/**
 * HotelItemDeleteService.java
 * All Rights Reserved, Copyright(c) Fujitsu Learning Media Limited
 */

package jp.co.tsys.common.service;

// import jp.co.tsys.common.entity.Hotelitem;
import jp.co.tsys.common.exception.BusinessException;;

/**
 * ホテル情報削除Service
 * @author FLM
 * @version 1.0 yyyy/mm/dd
 */
public interface HotelItemUpdateService {

	/**
	 * 商品コードをもとにホテル情報(日付、料金、在庫)を変更する
	 * @param itemCode 商品コード
	 * @param date
	 * @param price
	 * @param stock
	 * @throws BusinessException 検索結果が存在しない場合
	 */
	public void updateHotelItem(String itemCode, String date, int price,
			int stock)
			throws BusinessException;

}
