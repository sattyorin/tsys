/**
 * MemberRegistService.java
 */

package jp.co.tsys.common.service;

import jp.co.tsys.common.entity.Member;
import jp.co.tsys.common.exception.BusinessException;

/**
 *
 * @author FLM
 * @version 1.0.0
 */
public interface MemberRegistService {

	/**
	 * @param menber
	 * @throws BusinessException
	 */
	public void registMember(Member member) throws BusinessException;


	public int countMail(String mail);

}
