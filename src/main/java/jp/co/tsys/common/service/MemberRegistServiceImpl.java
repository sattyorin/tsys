/**
 * MemberRegistServiceImpl.java
 */

package jp.co.tsys.common.service;

import static jp.co.tsys.common.util.MessageList.BIZERR206;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
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
public class MemberRegistServiceImpl implements MemberRegistService {

	/** Mapper */
	@Autowired
	private MemberMapper mapper;

	/**
	 * 登録に成功した場合にメンバーコードを返す
	 */
	@Override
	public void registMember(Member member) {

		// memberのroleを取得する
		String role = member.getRole();

		try {

			if (role.equals("Customer")) {

				// MapperのgetLastCustomerCodeメソッドを呼び出し
				// メンバーコード（顧客）を取得して、格納
				String customerCode = mapper.getLastCustomerCode();

				// 数字部分のみ切り捨ててSplit
				String customerCodePrefix = customerCode.substring(0, 2);

				String customerCodeNumber = customerCode.substring(2);

				// 数字変換perseInt
				int intCustomerCodeNumber = Integer
						.parseInt(customerCodeNumber);

				// ＋１
				int newCustomerCodeNumber = intCustomerCodeNumber + 1;

				String newCustomerCode = String.format("%s%04d",
						customerCodePrefix, newCustomerCodeNumber);

				// customerCodeをmemberのmemberCodeにいれる
				member.setMemberCode(newCustomerCode);

			} else {

				// MapperのgetLastEmployeeCodeメソッドを呼び出し
				// メンバーコード（従業員）を取得して、格納
				String employeeCode = mapper.getLastEmployeeCode();

				// 数字変換perseInt
				int intEmployeeCode = Integer.parseInt(employeeCode);

				// ＋１
				int newIntEmployeeCode = intEmployeeCode + 1;

				String newEmployeeCode = Integer.toString(newIntEmployeeCode);

				member.setMemberCode(newEmployeeCode);

			}

			// MapperのinsertMemberメソッドを呼び出し
			mapper.insertMember(member);

		} catch (DuplicateKeyException e) {

			// 業務エラーを明示的に発生させる（エラーメッセージ[BIZERR206]）
			throw new BusinessException(BIZERR206, e);

		}
	}
}
