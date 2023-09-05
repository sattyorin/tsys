/**
 * MemberMapper.java
 *
 * All Rights Reserved, Copyright(c) Fujitsu Learning Media Limited
 */

package jp.co.tsys.common.mapper;


import jp.co.tsys.common.entity.Member;

/**
 *
 * @author FLM
 * @version 1.0.0
 */
public interface MemberMapper {



	/**テンプレ　上からメンバー登録・検索・変更・削除・一覧/ログインで並べたい
	 * []あああ
	 * @param
	 * @return
	 */

	/**
	 * [検索]メンバー情報を検索する
	 * @param memberCode メンバーコード
	 * @return member オブジェクト
	 */
	public Member findMember(String memberCode);

	/**
	 * [更新]メンバー情報を更新する
	 * @param member メンバー
	 * @return
	 */
	public Member updateMember(Member member);



}
