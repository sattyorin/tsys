/**
 * MemberResistService.java
 */

package jp.co.tsys.common.service;

import jp.co.tsys.common.entity.Member;
import jp.co.tsys.common.exception.BusinessException;

/**
 *
 * @author FLM
 * @version 1.0.0
 */
public interface MemberResistService {

	/**
	 * @param menber
	 * @throws BusinessException
	 */

	public void resistMember(Member menber) throws BusinessException;

}
