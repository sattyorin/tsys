/**
 * MemberUpdateServiceImpl.java
 */

package jp.co.tsys.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.tsys.common.entity.Member;
import jp.co.tsys.common.exception.BusinessException;
import jp.co.tsys.common.mapper.MemberMapper;
import jp.co.tsys.common.util.MessageList;

/**
 *
 * @author FLM
 * @version 1.0.0
 */

@Service
public class MemberUpdateServiceImpl implements MemberUpdateService {

	@Autowired
	private MemberMapper mapper;

	@Override
	public void updateMember(Member member) {

		// MapperのupdateMemberを呼び出し
		// 戻り値のbooleanを取得する

		boolean result = mapper.updateMember(member);

		// 変更出来なかった場合

		if (!result) {

			// 業務エラーを発生させる
			throw new BusinessException(MessageList.BIZERR001);
		}
	}

}
