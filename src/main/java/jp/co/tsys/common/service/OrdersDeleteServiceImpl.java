package jp.co.tsys.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.tsys.common.mapper.OrdersMapper;

/**
 *
 * @author FLM
 * @version 1.0.0
 */
@Transactional
@Service
public class OrdersDeleteServiceImpl implements OrdersDeleteService {

	@Autowired
	private OrdersMapper mapper;

	@Transactional
	@Override
	public void deleteCurrentOrder(int orderNo, String itemCode, int quantity,
			int price) {

		mapper.deleteOrderDetail(orderNo, itemCode);
		mapper.updateHotel(quantity, itemCode);
		mapper.updateOrderMaster(orderNo, price, quantity);
	};

	@Transactional
	@Override
	public void deleteOrderMaster() {
		mapper.deleteOrderMaster();
	}
}
