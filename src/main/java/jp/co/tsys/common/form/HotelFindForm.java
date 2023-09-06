/**
 * HotelItemDetailForm.java
 */

package jp.co.tsys.common.form;

import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
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
public class HotelFindForm {

	@NotNull
	@NotBlank
	/** 都市名 */
	private String inputCityName;

	@NotNull
	@NotBlank
	/** 期間（チェックイン） */
	private String inDate;

	@NotNull
	@NotBlank
	/** 期間（チェックアウト） */
	private String outDate;

	@Max(99999999)
	/** 値段（下限） */
	private int lowPrice;

	@Max(99999999)
	/** 値段（上限） */
	private int highPrice;

	/** グレード */
	private String grade;

	/** ホテル商品検索結果リスト */
	private List<HotelItem> result;

}
