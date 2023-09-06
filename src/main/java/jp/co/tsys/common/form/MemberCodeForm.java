/**
 * MemberCodeForm.java
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
public class MemberCodeForm implements Serializable {

	private String memberCode;

}