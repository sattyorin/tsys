// * PaymentService.java

package jp.co.tsys.common.service;

import java.util.List;

import jp.co.tsys.common.entity.Member;
import jp.co.tsys.common.entity.Order;
import jp.co.tsys.common.exception.BusinessException;
import jp.co.tsys.common.form.OrdersForm;;

/**
 * 注文情報登録Service
 *
 * @author
 * @version 1.0 yyyy/mm/dd
 */
public interface PaymentService {

	/**
	 * 注文情報をデータベースに登録する 登録に成功した場合に予約番号を返す
	 *
	 * @param orders
	 *            注文情報
	 * @throws BusinessException
	 *             検索結果が存在しない場合
	 */

	public Member findMember(String memberCode) throws BusinessException;

	public void insertOrder(OrdersForm ordersForm) throws BusinessException;

	public String getLastOrderNo() throws BusinessException;

	public void insertOrderDetail(List<Order> orders, String orderNo)
			throws BusinessException;

	public void updateHotelStock(List<Order> orders) throws BusinessException;

}
