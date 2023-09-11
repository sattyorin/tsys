/**
 * HotelItemDetailForm.java
 *
 * All Rights Reserved, Copyright(c) Fujitsu Learning Media Limited
 */

package jp.co.tsys.common.form;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 *
 * @author FLM
 * @version 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemUpdateForm {
	@NonNull
	/** 宿泊日 */
	private String date;

	@Max(999999999)
	@Min(1)
	/** 料金 */
	private int price;

	@Max(99999)
	@Min(1)
	/** 在庫 */
	private int stock;

}
