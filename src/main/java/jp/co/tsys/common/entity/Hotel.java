/**
 * Hotel.java
 * All Rights Reserved, Copyright(c) Fujitsu Learning Media Limited
 */

package jp.co.tsys.common.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ホテル基本情報を管理するエンティティクラス
 * @author FLM
 * @version 1.0 yyyy/mm/dd
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Hotel implements Serializable {

	/** ホテルコード */
	private String hotelCode;

	/** ホテル名 */
	private String name;

	/** 都市コード */
	private String cityCode;

	/** 都市名 */
	private String cityName;

	/** 等級 */
	private int grade;

	/** 基本料金 */
	private int basicPrice;

}
