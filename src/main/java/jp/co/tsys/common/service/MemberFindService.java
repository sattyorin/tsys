/**
 * MemberFindService.java
 *
 * All Rights Reserved, Copyright(c) Fujitsu Learning Media Limited
 */

package jp.co.tsys.common.service;

import jp.co.tsys.common.entity.Member;
import jp.co.tsys.common.exception.BusinessException;

/**
 * メンバー検索Service
 * 
 * @author Yamaguchi
 * @version 1.0.0
 */
public interface MemberFindService {

	/**
	 * 検索するメンバーコードに一致したメンバー情報をデータベースから取得する。 一致したメンバーコードに対応するメンバー情報を返す。
	 *
	 * @param memberCode
	 *            メンバーコード
	 * @throws BusinessException
	 *             検索結果が存在しない場合
	 */
	public Member findMember(String memberCode) throws BusinessException;

}
