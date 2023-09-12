/**
 * HotelDetailForm.java
 */

package jp.co.tsys.common.form;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

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

	@Min(1)
	@NotNull
	/** 予約部屋数 */
	private Integer inputQuantity;
}
