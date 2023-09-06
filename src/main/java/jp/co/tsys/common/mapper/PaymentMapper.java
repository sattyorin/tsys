/**
 * PaymentMapper.java
 */

package jp.co.tsys.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import jp.co.tsys.common.entity.Order;
import jp.co.tsys.common.entity.Orders;

/**
 * @author FLM
 * @version 1.0 yyyy/mm/dd
 */
@Mapper
public interface PaymentMapper {

	/**
	 * 予約情報をOrderMasterテーブルに登録する
	 *
	 * @param orders
	 *            予約情報
	 */
	public void insertOrderMaster(Orders orders);

	/**
	 * 最新の予約番号を取得する
	 *
	 *
	 */
	public String getLastOrderNo();

	/**
	 * 予約情報をOrderDetailテーブルに登録する
	 *
	 * @param orders
	 *            予約情報
	 */
	public void insertOrderDetail(@Param("orders") List<Order> orders,
			@Param("orderNo") String orderNo);
}
