/**
 * MemberDeleteService.java
 *
 * All Rights Reserved, Copyright(c) Fujitsu Learning Media Limited
 */

package jp.co.tsys.common.service;


import jp.co.tsys.common.entity.Member;
import jp.co.tsys.common.exception.BusinessException;


/**
 *
 * @author FLM
 * @version 1.0.0
 */
public interface MemberDeleteService {

	public Member findMember(String memberCode) throws BusinessException;

	public boolean deleteMember(String memberCode) throws BusinessException;

}
