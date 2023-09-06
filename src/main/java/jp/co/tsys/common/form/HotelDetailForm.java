/**
 * HotelDetailForm.java
 */

package jp.co.tsys.common.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import jp.co.tsys.common.entity.HotelItem;
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
public class HotelDetailForm {
	/** ホテル商品 */
	private HotelItem hotelItem;

	@NotBlank
	@Size(max = 2)
	/** 予約部屋数 */
	private String inputQuantity;
}
