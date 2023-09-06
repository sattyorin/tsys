/**
 * HotelItemDeleteService.java
 */

package jp.co.tsys.common.service;

//import jp.co.tsys.common.entity.Hotelitem;
import jp.co.tsys.common.exception.BusinessException;;

/**
 * ホテル情報削除Service
 * 
 * @author FLM
 * @version 1.0 yyyy/mm/dd
 */
public interface HotelItemDeleteService {

	/**
	 * 商品コードをもとにホテル情報を削除する
	 * 
	 * @param hotelItemCode
	 *            商品コード
	 * @throws BusinessException
	 *             検索結果が存在しない場合
	 */
	public void deleteHotelItem(String hotelItemCode) throws BusinessException;

}
