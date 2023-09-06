/**
 * MemberOrdersFindService.java
 */

package jp.co.tsys.common.service;

import java.util.List;

import jp.co.tsys.common.entity.Member;
import jp.co.tsys.common.entity.Order;
import jp.co.tsys.common.exception.BusinessException;

/**
 *
 * @author FLM
 * @version 1.0.0
 */
public interface MemberOrdersFindService {

	public Member findMember(String memberCode) throws BusinessException;

	public List<Order> findCurrentOrder(String memberCode)
			throws BusinessException;

	public List<Order> findPastOrder(String memberCode)
			throws BusinessException;
}
