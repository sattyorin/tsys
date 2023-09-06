/**
 * HotelRegistForm.java
 */

package jp.co.tsys.common.form;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

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
	private String grade;

	/** 料金 */
	@NotBlank
	@Size(max = 10)
	private int price;

	/** 在庫数 */
	@NotBlank
	@Size(max = 5)
	private int stock;

}
