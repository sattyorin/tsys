/**
 * MemberFindServiceImpl.java
 *
 * All Rights Reserved, Copyright(c) Fujitsu Learning Media Limited
 */

package jp.co.tsys.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.tsys.common.entity.Member;
import jp.co.tsys.common.exception.BusinessException;
import jp.co.tsys.common.mapper.MemberMapper;
import jp.co.tsys.common.util.MessageList;

/**
 * メンバー検索のService
 * 
 * @author Yamaguchi
 * @version 1.0.0
 */
@Service
public class MemberFindServiceImpl implements MemberFindService {
	/** Mapper */
	@Autowired
	private MemberMapper mapper;

	/**
	 * 検索したメンバーコードに対応するメンバー情報を返すメソッド(Mapper)を呼び出す
	 * if>検索したメンバーが存在しない場合、BUsinessExceptionをスルー
	 */
	@Override
	public Member findMember(String memberCode) {
		Member member = mapper.findMember(memberCode);

		if (member == null) {
			throw new BusinessException(MessageList.BIZERR001);
		}

		return member;
	}
}
