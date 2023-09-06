// * PaymentServiceImpl.java

package jp.co.tsys.common.service;

import org.springframework.beans.factory.annotation.Autowired;

import jp.co.tsys.common.entity.Orders;
import jp.co.tsys.common.mapper.PaymentMapper;;

/**
 * 注文情報登録Serviceの実装クラス
 *
 * @author
 * @version 1.0 yyyy/mm/dd
 */
public class PaymentServiceImpl implements PaymentService {

	/** Mapper */
	@Autowired
	private PaymentMapper mapper;

	/**
	 * 登録に成功した場合に予約番号を返す
	 */
	@Override
	public String addProduct(Orders orders) {
		// MapperのsaveOrderMasterメソッドを呼び出し
		mapper.addOrderMaster(orders);

		// MapperのsaveOrderMasterメソッドを呼び出し
		// 予約番号を取得して、格納
		String orderNo = mapper.getLastOrderNo();

		// MapperのsaveOrderMasterメソッドを呼び出し
		mapper.addOrderDetail(orders.getOrders(), orderNo);

		return orderNo;

	}

}
