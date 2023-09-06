/**
 * OrdersDeleteServiceImpl.java
 */

package jp.co.tsys.common.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author FLM
 * @version 1.0.0
 */
@Transactional
@Service
public class OrdersDeleteServiceImpl implements OrdersDeleteService {

	// @Autowired
	// private OrdersMapper mapper;

	@Transactional
	@Override
	public void deleteCurrentOrder(String orderNo, String itemCode) {

		// TODO(soutarou): imple
		// 受注情報を削除する
		// mapper.deleteOrderDetail(@Param("orderNo") orderNo,@Param("itemCode")
		// itemCode);
		// mapper.deleteOrderMaster(orderNo);
		// mapper.updateHotel(@Param("quantity") int quantity,@Param("itemCode")
		// String itemCode);
		// mapper.updateOrderMaster(price);
	};
}
