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

	/** ホテル商品 */
	private Hotelitem hotelItem;

	/** 部屋数 */
	private int quantity;

	/** チェックボックス （注文取消） */
	private boolean check;

}
