/**
 * LoginForm.java
 */

package jp.co.tsys.common.form;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ログイン情報入力フォーム
 * 
 * @author FLM
 * @version 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginForm implements Serializable {
	/** メンバーコード */
	@NotBlank
	private String memberCode;

	/** パスワード */
	@Size(min = 8, max = 15)
	@NotBlank
	private String password;
}
