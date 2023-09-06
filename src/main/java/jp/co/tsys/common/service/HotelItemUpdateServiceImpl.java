/**
 * HotelItemDeleteServiceImpl.java
 * All Rights Reserved, Copyright(c) Fujitsu Learning Media Limited
 */

package jp.co.tsys.common.service;

import static jp.co.tsys.common.util.MessageList.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import jp.co.tsys.common.entity.Hotelitem;
import jp.co.tsys.common.exception.BusinessException;
//import jp.co.tsys.common.entity.Hotel;
import jp.co.tsys.common.mapper.HotelItemMapper;

/**
 * 商品削除Serviceの実装クラス
 * @author FLM
 * @version 1.0 yyyy/mm/dd
 */
@Service
public class HotelItemUpdateServiceImpl
		implements HotelItemUpdateService {

	/** Mapper */
	@Autowired
	private HotelItemMapper mapper;

	/**
	 * @see HotelItemUpdateService#updateHotelItem(String, String, int, int)
	 */
	@Override
	public void updateHotelItem(String itemCode, String date, int price,
			int stock) {
		// MapperのupdateHotelItemメソッドを呼び出し
//		更新した件数を格納
		int updateCount = mapper.updateHotelItem(itemCode, date, price, stock);

		// 更新件数が一件でない(失敗した)場合
		if (updateCount != 1) {

			// 業務エラーを明示的に発生させる
			throw new BusinessException("エラーメッセージ");
		}

//		return void;
	}

}
