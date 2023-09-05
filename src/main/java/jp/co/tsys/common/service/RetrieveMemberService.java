/**
 * RetrieveMemberService.java
 */

package jp.co.tsys.common.service;

import jp.co.tsys.common.entity.Member;

/**
 * ログイン用Service
 * 
 * @author FLM
 * @version 1.0.0
 */
public interface RetrieveMemberService {
	/**
	 * メンバーコードとパスワードをもとにメンバー情報を検索する
	 * 
	 * @param memberCode
	 *            メンバーコード password パスワード
	 */
	public Member getMember(String memberCode, String password);
}
