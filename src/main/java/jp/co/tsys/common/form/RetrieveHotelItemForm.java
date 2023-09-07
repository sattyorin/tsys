/**
 * SearchHotelItemForm.java
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
public class RetrieveHotelItemForm implements Serializable {

	// 商品コード
	@Size(max = 9)
	private String itemCode;

	// ホテル名
	@Size(max = 30)
	private String hotelName;

	// 日付
	private String date;
}
