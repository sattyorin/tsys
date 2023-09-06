/**
 * FindAllMemberService.java
 *
 */

package jp.co.tsys.common.service;

import java.util.List;

import jp.co.tsys.common.entity.Member;

/**
 *
 * @author FLM
 * @version 1.0.0
 */
public interface FindAllMemberService {
	public List<Member> findAllMember(String role, String name, String tel,
			String mail);

}
