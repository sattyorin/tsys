/**
 * HotelNameDateForm.java
 */

package jp.co.tsys.common.form;

/**
 *
 * @author FLM
 * @version 1.0.0
 */

import java.io.Serializable;

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
public class HotelNameDateForm implements Serializable {
	/**
	 * ホテル名
	 */
	@Size(max = 30, message = "ホテル名は30文字以内で入力してください")
	private String name;
	/**
	 * 期間（startdate）
	 */
	@Size(min = 10, max = 10)
	@Pattern(regexp = "^[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$")
	private String startdate;
	/**
	 * 期間（enddate）
	 */
	@Size(min = 10, max = 10)
	@Pattern(regexp = "^[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$")
	private String enddate;
}
