/**
 * HotelRegistForm.java
 */

package jp.co.tsys.common.form;

import java.io.Serializable;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ホテル商品入力フォーム
 *
 * @author FLM
 * @version 1.0.0
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistHotelForm implements Serializable {

	/** ホテル商品コード */
	private String hotelItemCode;

	/** ホテルコード */
	private String hotelCode;

	/** ホテル名 */
	private String hotelName;

	/** 宿泊日 */
	@NotBlank
	private String date;

	/** 都市名 */
	private String city;

	/** グレード */
	private Integer grade;

	/** 料金 */
	@Max(999999999)
	@Min(1)
	@Digits(fraction = 0, integer = 10)
	private Integer price;

	/** 在庫数 */
	@Max(99999)
	@Min(1)
	@Digits(fraction = 0, integer = 6)
	private Integer stock;

}
