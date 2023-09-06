/**
 * HotelItemDetailForm.java
 *
 * All Rights Reserved, Copyright(c) Fujitsu Learning Media Limited
 */

package jp.co.tsys.common.form;

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

	// TODO(kano): intに@NonNullは意味ないかな
	/** 料金 */
	private int price;

	// TODO(kano): intに@NonNullは意味ないかな
	/** 在庫 */
	private int stock;

}
