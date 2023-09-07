/**
 * MemberUpdateService.java
 */

package jp.co.tsys.common.service;

import jp.co.tsys.common.entity.Member;
import jp.co.tsys.common.exception.BusinessException;

/**
 *
 * @author FLM
 * @version 1.0.0
 */
public interface MemberUpdateService {

	// uodateMemberのメソッド
	public void updateMember(Member member) throws BusinessException;

}
