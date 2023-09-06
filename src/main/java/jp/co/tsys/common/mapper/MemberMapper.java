/**
 * MemberMapper.java
 *
 * All Rights Reserved, Copyright(c) Fujitsu Learning Media Limited
 */

package jp.co.tsys.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import jp.co.tsys.common.entity.Member;

/**
 *
 * @author FLM
 * @version 1.0.0
 */
@Mapper
public interface MemberMapper {

	/**
	 * テンプレ 上からメンバー登録・検索・更新・削除・一覧/ログインで並べたい
	 * 
	 * @param
	 * @return
	 */

	/**
	 * [登録]メンバー情報を登録する
	 * 
	 * @param member
	 * @return
	 */
	public void insertMember(Member member);

	/**
	 * 最新の顧客メンバーコードを取得する
	 *
	 *
	 */
	public String getLastCustomerCode();

	/**
	 * 最新の従業員メンバーコードを取得する
	 *
	 *
	 */
	public String getLastEmployeeCode();

	/**
	 * [検索]メンバー情報を検索する
	 * 
	 * @param memberCode
	 *            メンバーコード
	 * @return member オブジェクト
	 */
	public Member findMember(String memberCode);

	/**
	 * [更新]メンバー情報を更新する
	 * 
	 * @param member
	 *            メンバー
	 * @return
	 */
	public Member updateMember(Member member);

	/**
	 * [削除・退会]メンバー情報を削除・退会する
	 * 
	 * @param memberCode
	 *            メンバーコード
	 * @return boolean true or false
	 */
	public boolean deleteMember(String memberCode);

	/**
	 * [一覧]条件によって絞り込んだメンバー情報を取得する
	 * 
	 * @param role
	 *            権限 name 名前 tel 電話番号 mail メールアドレス
	 * @return
	 */
	public List<Member> findAllMember(@Param("role") String role,
			@Param("name") String name, @Param("tel") String tel,
			@Param("mail") String mail);

	/**
	 * [ログイン]メンバー情報を検索する
	 * 
	 * @param memberCode
	 *            メンバーコード password パスワード
	 * @return
	 */
	public Member findOne(@Param("memberCode") String memberCode,
			@Param("password") String password);

}
