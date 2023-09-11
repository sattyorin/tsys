/**
 * MemberOrdersFindServiceImpl.java
 */

package jp.co.tsys.common.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.tsys.common.entity.Order;
import jp.co.tsys.common.mapper.OrdersMapper;

/**
 *
 * @author FLM
 * @version 1.0.0
 */
@Service
public class MemberOrdersFindServiceImpl implements MemberOrdersFindService {

	@Autowired
	private OrdersMapper ordersMapper;
	private String strDate;

	// public List<Integer> findMemberOrder(String memberCode) {
	// List<Integer> orderNoList = ordersMapper.findMemberOrder(memberCode);
	//
	// if (orderNoList.size() == 0) {
	// throw new BusinessException("BIZERR106");
	// }
	//
	// return orderNoList;
	// }

	public MemberOrdersFindServiceImpl() {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		this.strDate = dateFormat.format(date);
	}

	@Override
	public List<Order> findCurrentOrder(String memberCode) {

		List<Order> listCurrentOrder = ordersMapper
				.findCurrentOrderFromMemberCode(memberCode, strDate);
		// if (listCurrentOrder.size() == 0) {
		// throw new BusinessException("BIZERR107");
		// }

		return listCurrentOrder;
	}

	@Override
	public List<Order> findPastOrder(String memberCode) {
		List<Order> listPastOrder = ordersMapper
				.findPastOrderFromMemberCode(memberCode, strDate);

		// if (listPastOrder.size() == 0) {
		// throw new BusinessException("BIZERR108");
		// }

		return listPastOrder;
	}

}
