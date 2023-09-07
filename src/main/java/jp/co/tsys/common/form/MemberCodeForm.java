/**
 * MemberCodeForm.java
 */

package jp.co.tsys.common.form;

import java.io.Serializable;

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
public class MemberCodeForm implements Serializable {
	@NotBlank
	@Size(max = 6)
	private String memberCode;

}