/**
 * OrdersMapper.java
 */

package jp.co.tsys.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import jp.co.tsys.common.entity.Order;

/**
 *
 * @author FLM
 * @version 1.0.0
 */
@Mapper
public interface OrdersMapper {

	public List<Order> findCurrentOrderFromMemberCode(String memberCode,
			String date);

	public List<Order> findPastOrderFromMemberCode(String memberCode,
			String date);

	public String findOrderDateFromOrderNo(int oderNo);

	public int deleteOrderDetail(@Param("orderNo") int orderNo,
			@Param("itemCode") String itemCode);

	public int deleteOrderMaster(int orderNo);

	public int updateHotel(@Param("quantity") int quantity,
			@Param("itemCode") String itemCode);

	public int updateOrderMaster(@Param("orderNo") int orderNo,
			@Param("price") int price);

	// public List<Integer> findMemberOrder(String memberCode);

	// public List<Order> findCurrentOrder(int orderNo);

	// public List<Order> findPastOrder(int orderNo);
}
