/**
 * HotelItemDetailForm.java
 *
 * All Rights Reserved, Copyright(c) Fujitsu Learning Media Limited
 */

package jp.co.tsys.common.form;

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
public class HotelItemDetailForm {
	/** 商品コード */
	private String itemCode;

	/** ホテルコード */
	private String hotelCode;

	/** ホテル名 */
	private String name;

	/** 宿泊日 */
	private String date;

	/** 都市名 */
	private String cityName;

	/** 等級 */
	private int grade;

	/** 料金 */
	private int price;

	/** 在庫 */
	private int stock;

}
