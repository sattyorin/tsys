/**
 * Orders.java
 * All Rights Reserved, Copyright(c) Fujitsu Learning Media Limited
 */

package jp.co.tsys.common.entity;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 注文情報を管理するエンティティクラス
 * 
 * @author FLM
 * @version 1.0 yyyy/mm/dd
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Orders implements Serializable {

	/** メンバーコード */
	private String memberCode;

	/** 注文番号 */
	private String orderNo;

	/** 注文日 */
	private String orderDate;

	/** 注文合計金額 */
	private int orderTotal;

	/** 決済方法 */
	private String payment;

	/** 注文商品リスト */
	private List<Order> orders;

}
