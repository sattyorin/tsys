/**
 * FindAllMemberServiceImpl.java
 *
 */

package jp.co.tsys.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.tsys.common.entity.Member;
import jp.co.tsys.common.mapper.MemberMapper;

/**
 *
 * @author FLM
 * @version 1.0.0
 */
@Service
public class FindAllMemberServiceImpl implements FindAllMemberService {

	@Autowired
	private MemberMapper mapper;

	@Override
	public List<Member> findAllMember(String role, String name, String tel,
			String mail) {

		List<Member> memberList = mapper.findAllMember(role, name, tel, mail);

		return memberList;
	}
}
