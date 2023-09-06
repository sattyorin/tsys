/**
 * RetrieveMemberServiceImpl.java
 */

package jp.co.tsys.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.tsys.common.entity.Member;
import jp.co.tsys.common.mapper.MemberMapper;

/**
 * ログイン用Serviceの実装クラス
 * 
 * @author FLM
 * @version 1.0.0
 */
@Service
public class RetrieveMemberServiceImpl implements RetrieveMemberService {
	/** Mapper **/
	@Autowired
	private MemberMapper mapper;

	@Override
	public Member getMember(String memberCode, String password) {
		// MapperのfindOneメソッドを呼び出し
		// 戻り値のMemberオブジェクトを取得する
		Member member = mapper.findOne(memberCode, password);

		return member;
	}
}
