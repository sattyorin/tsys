/**
 * NonRoleEmployeeMemberForm.java
 */

package jp.co.tsys.common.form;

import java.io.Serializable;

import jp.co.tsys.common.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author FLM
 * @version 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberForm implements Serializable {
	private Member member;
	private String confirmPassword;

}
