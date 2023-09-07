
/**
 * MemberResistServiceImpl.java
 */

package jp.co.tsys.common.service;

import static jp.co.tsys.common.util.MessageList.BIZERR206;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;

import jp.co.tsys.common.entity.Member;
import jp.co.tsys.common.exception.BusinessException;
import jp.co.tsys.common.mapper.MemberMapper;

/**
 *
 * @author FLM
 * @version 1.0.0
 */
public class MemberResistServiceImpl implements MemberResistService {

	/** Mapper */
	@Autowired
	private MemberMapper mapper;

	/**
	 * 登録に成功した場合にメンバーコードを返す
	 */
	@Override
	public void resistMember(Member member) {

		// memberのroleを取得する
		String role = member.getRole();

		try {

			if (role == "customer") {

				// MapperのgetLastCustomerCodeメソッドを呼び出し
				// メンバーコード（顧客）を取得して、格納
				String customerCode = mapper.getLastCustomerCode();

				// 数字部分のみ切り取りSplit
				String CM = customerCode.substring(0, 2);

				String customernumberCode = customerCode.substring(2);

				// 数字変換perseInt
				int intcustomerCode = Integer.parseInt(customernumberCode);

				// ＋１
				int newcustomerCode = intcustomerCode + 1;

				// 合体
				String memberCode = CM + Integer.toString(newcustomerCode);

				// customerCodeをmemberのmemberCodeにいれる
				member.setMemberCode(memberCode);

			} else {

				// MapperのgetLastEmployeeCodeメソッドを呼び出し
				// メンバーコード（従業員）を取得して、格納
				String employeeCode = mapper.getLastEmployeeCode();

				// 数字変換perseInt
				int intemployeeCode = Integer.parseInt(employeeCode);

				// ＋１
				int newemployeeCode = intemployeeCode + 1;

				String memberCode = Integer.toString(newemployeeCode);

				member.setMemberCode(memberCode);

			}

			// MapperのinsertMemberメソッドを呼び出し
			mapper.insertMember(member);

		} catch (DuplicateKeyException e) {

			// 業務エラーを明示的に発生させる（エラーメッセージ[BIZERR206]）
			throw new BusinessException(BIZERR206, e);

		}
	}
}
