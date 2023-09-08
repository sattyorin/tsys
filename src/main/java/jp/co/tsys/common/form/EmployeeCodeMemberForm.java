/**
 * NonRoleEmployeeMemberForm.java
 */

package jp.co.tsys.common.form;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

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
public class EmployeeCodeMemberForm implements Serializable {

	@NotBlank
	private String memberCode;

	@NotBlank
	private String role;

	@NotBlank
	private String name;

	@NotBlank
	@Size(min = 8, max = 15)
	private String password;

	@NotBlank
	@Size(min = 8, max = 15)
	private String confirmPassword;

	@NotBlank
	@Email
	private String mail;
}
