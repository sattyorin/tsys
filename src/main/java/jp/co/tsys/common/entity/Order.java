/**
 * Order.java
 * All Rights Reserved, Copyright(c) Fujitsu Learning Media Limited
 */

package jp.co.tsys.common.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 注文商品情報を管理するエンティティクラス
 *
 * @author FLM
 * @version 1.0 yyyy/mm/dd
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order implements Serializable {

	private int orderNo;

	private String itemCode;

	private String orderDate;

	/** ホテル商品 */
	private HotelItem hotelItem;

	/** 部屋数 */
	private int quantity;

	/** 小計 */
	private int subTotal;

}
