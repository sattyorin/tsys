// * PaymentServiceImpl.java

package jp.co.tsys.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.tsys.common.entity.Member;
import jp.co.tsys.common.entity.Order;
import jp.co.tsys.common.form.OrdersForm;
import jp.co.tsys.common.mapper.MemberMapper;
import jp.co.tsys.common.mapper.PaymentMapper;

/**
 * 注文情報登録Serviceの実装クラス
 *
 * @author
 * @version 1.0 yyyy/mm/dd
 */
@Service
public class PaymentServiceImpl implements PaymentService {

	/** Mapper */
	@Autowired
	private PaymentMapper payMapper;
	@Autowired
	private MemberMapper memberMapper;

	/**
	 * 登録に成功した場合に予約番号を返す
	 */

	@Override
	public Member findMember(String memberCode) {
		Member member = memberMapper.findMember(memberCode);

		return member;
	}

	@Override
	public void insertOrder(OrdersForm ordersForm) {
		// MapperのsaveOrderMasterメソッドを呼び出し
		payMapper.insertOrderMaster(ordersForm);
	}

	@Override
	public String getLastOrderNo() {
		// MapperのsaveOrderMasterメソッドを呼び出し
		// 予約番号を取得して、格納
		String orderNo = payMapper.getLastOrderNo();
		return orderNo;
	}

	@Override
	// MapperのsaveOrderMasterメソッドを呼び出し
	public void insertOrderDetail(List<Order> orders, String orderNo) {
		payMapper.insertOrderDetail(orders, orderNo);
	}

	@Override
	// MapperのsaveOrderMasterメソッドを呼び出し
	public void updateHotelStock(List<Order> orders) {
		for (Order order : orders) {
			payMapper.updateHotelStock(order);
		}
	}
}
