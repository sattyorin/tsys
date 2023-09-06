/**
 * HotelItem.java
 * All Rights Reserved, Copyright(c) Fujitsu Learning Media Limited
 */

package jp.co.tsys.common.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ホテル情報を管理するエンティティクラス
 * 
 * @author FLM
 * @version 1.0 yyyy/mm/dd
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelItem implements Serializable {

	/** 商品コード */
	private String itemCode;

	/** ホテル */
	private Hotel hotel;

	/** 日付 */
	private String date;

	/** 料金 */
	private int price;

	/** 在庫 */
	private int stock;

}
