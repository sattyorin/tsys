/**
 * MemberOrdersFindServiceImpl.java
 */

package jp.co.tsys.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.tsys.common.entity.Member;
import jp.co.tsys.common.entity.Order;
import jp.co.tsys.common.exception.BusinessException;
import jp.co.tsys.common.mapper.MemberMapper;
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
	private MemberMapper memberMapper;

	@Override
	public Member findMember(String memberCode) {
		Member member = memberMapper.findMember(memberCode);

		if (member == null) {
			throw new BusinessException("BIZERR001");
		}

		return member;
	}

	@Override
	public List<Integer> findMemberOrder(String memberCode) {
		List<Integer> orderNoList = ordersMapper.findMemberOrder(memberCode);

		if (orderNoList.size() == 0) {
			throw new BusinessException("BIZERR106");
		}

		return orderNoList;
	}

	@Override
	public List<Order> findCurrentOrder(String memberCode) {
		// TODO: get date
		String date = "2023-8-12";
		List<Order> listCurrentOrder = ordersMapper
				.findCurrentOrderFromMemberCode(memberCode, date);
		if (listCurrentOrder.size() == 0) {
			throw new BusinessException("BIZERR107");
		}

		return listCurrentOrder;
	}

	@Override
	public List<Order> findPastOrder(int orderNo) {
		List<Order> listPastOrder = ordersMapper.findCurrentOrder(orderNo);

		if (listPastOrder.size() == 0) {
			throw new BusinessException("BIZERR108");
		}

		return listPastOrder;
	}

}
