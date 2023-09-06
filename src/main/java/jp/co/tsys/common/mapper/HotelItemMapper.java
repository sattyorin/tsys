/**
 * HotelItemMapper.java
 */

package jp.co.tsys.common.mapper;

import java.util.List;

//import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import jp.co.tsys.common.entity.HotelItem;

//import jp.co.tsys.common.entity.Hotelitem;

/**
 * @author FLM
 * @version 1.0 yyyy/mm/dd
 */
@Mapper
public interface HotelItemMapper {

	/**
	 * ホテル情報のリストを更新する
	 *
	 * @param hotelItemCode
	 *            商品コード
	 * @param data
	 *            日付
	 * @param price
	 *            料金
	 * @param stock
	 *            在庫
	 */
	public int updateHotelItem(String hotelItemCode, String date, int price,
			int stock);

	/**
	 * ホテル情報のリストを一件削除する
	 *
	 * @param hotelItemCode
	 *            商品コード
	 */
	public int deleteHotelItem(String hotelItemCode);

	/**
	 * 商品コードからホテル商品の1件検索を行う
	 *
	 * @param hotelItemCode
	 *            商品コード
	 */
	public HotelItem findHotelDetail(String itemCode);

	/**
	 * 検索条件からホテル商品の全件検索を行う
	 *
	 * @param hotelItemCode
	 *            商品コード
	 */
	public List<HotelItem> findHotelList(String inputCityName, String inDate,
			String outDate, int lowProce, int highPrice, String grade);
}
