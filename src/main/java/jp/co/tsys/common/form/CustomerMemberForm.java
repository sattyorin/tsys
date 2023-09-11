/**
 * NonRoleEmployeeMemberForm.java
 */

package jp.co.tsys.common.form;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
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
public class CustomerMemberForm implements Serializable {

	private String role;

	@NotBlank
	private String name;

	@NotBlank
	@Size(min = 3, max = 15)
	private String password;

	@NotBlank
	@Size(min = 3, max = 15)
	private String confirmPassword;

	@NotBlank
	@Email
	private String mail;

	@NotBlank
	@Pattern(regexp = "^\\d{3}\\-?\\d{4}$")
	private String zipCode;

	@NotBlank
	private String prefecture;

	@NotBlank
	private String address;

	@NotBlank
	@Pattern(regexp = "0\\d{1,2}-\\d{1,4}-\\d{4}")
	private String tel;
}
