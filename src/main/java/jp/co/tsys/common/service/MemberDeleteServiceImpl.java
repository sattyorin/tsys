/**
 * MemeberDeleteServiceImpl.java
 */

package jp.co.tsys.common.service;

import static jp.co.tsys.common.util.MessageList.BIZERR204;
import static jp.co.tsys.common.util.MessageList.BIZERR205;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.tsys.common.entity.Member;
import jp.co.tsys.common.exception.BusinessException;
import jp.co.tsys.common.mapper.MemberMapper;

/**
 *
 * @author FLM
 * @version 1.0.0
 */

@Service
public class MemberDeleteServiceImpl implements MemberDeleteService {

	/** Mapper */
	@Autowired
	private MemberMapper mapper;

	@Override
	public Member findMember(String memberCode) {

		Member member = mapper.findMember(memberCode);

		// 検索結果が存在しない場合
		if (member == null) {

			// 業務エラーを明示的に発生させる（エラーメッセージ[BIZERR204]）
			throw new BusinessException(BIZERR204);
		}

		return member;
	}

	// 削除する・退会する
	@Override
	public boolean deleteMember(String memberCode) {

		boolean result = mapper.deleteMember(memberCode);

		// 削除できなかった場合
		if (!result) {

			// 業務エラーを明示的に発生させる（エラーメッセージ[BIZERR205]）
			throw new BusinessException(BIZERR205);
		}
		return result;
	}

}
