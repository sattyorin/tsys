/**
 * MemberOrdersFindServiceImpl.java
 */

package jp.co.tsys.common.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import jp.co.tsys.common.entity.Member;
import jp.co.tsys.common.entity.Order;

/**
 *
 * @author FLM
 * @version 1.0.0
 */
@Service
public class MemberOrdersFindServiceImpl implements MemberOrdersFindService {

	// @Autowired
	// private OrdersMapper ordersMapper;
	// private MemberMapper memberMapper;

	@Override
	public Member findMember(String memberCode) {
		Member member = new Member();
		// TODO(soutarou): imple

		// Member member = memberMapper.findMember(memberCode);
		//
		// if (member == null) {
		// throw new BusinessException(BIZERR001);
		// }

		return member;
	}

	@Override
	public List<Order> findCurrentOrder(String memberCode) {
		List<Order> listOrder = new ArrayList<Order>();
		// TODO(soutarou): imple
		// List<Order> listOrder = ordersMapper.findCurrentOrder(memberCode);

		return listOrder;
	}

	@Override
	public List<Order> findPastOrder(String memberCode) {
		List<Order> listOrder = new ArrayList<Order>();
		// TODO(soutarou): imple
		// List<Order> listOrder = ordersMapper.findCurrentOrder(memberCode);

		return listOrder;
	}

}
