/**
 * HotelFindForm.java
 */

package jp.co.tsys.common.form;

import java.io.Serializable;

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
public class FindHotelForm implements Serializable {

	/** ホテルコード */
	@Size(max = 6)
	private String hotelCode;

	/** ホテル名 */
	@Size(max = 30)
	private String hotelName;
}
