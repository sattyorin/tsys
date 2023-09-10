/**
 * HotelDetailForm.java
 */

package jp.co.tsys.common.form;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

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

	@Max(20)
	@Min(1)
	// TODO(risa, yusaku): String? Integer?
	// TODO(risa, yusaku): set max value
	/** 予約部屋数 */
	private int inputQuantity;
}
