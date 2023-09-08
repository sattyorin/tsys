/**
 * FindAllMemberForm.java
 */

package jp.co.tsys.common.form;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

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

public class FindAllMemberForm implements Serializable {

	@NotBlank
	private String role;

	private String name;

	@Pattern(regexp = "0\\d{1,4}-\\d{1,4}-\\d{4}")
	private String tel;

	@Email
	private String mail;

}
