/**
 * MemberDeleteService.java
 */

package jp.co.tsys.common.service;

import jp.co.tsys.common.exception.BusinessException;

/**
 *
 * @author FLM
 * @version 1.0.0
 */
public interface MemberDeleteService {

	public boolean deleteMember(String memberCode) throws BusinessException;

}
