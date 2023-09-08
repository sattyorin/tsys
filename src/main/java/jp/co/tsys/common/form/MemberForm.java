/**
 * NonRoleEmployeeMemberForm.java
 */

package jp.co.tsys.common.form;

import java.io.Serializable;

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

	private String memberCode;

	private String role;

	private String firstName;

	private String lastName;

	private String name;

	private String password;

	private String confirmPassword;

	private String mail;

	private String zipCode;

	private String prefecture;

	private String address;

	private String tel;
}
