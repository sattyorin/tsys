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
 * 商品検索Serviceの実装クラス
 * @author FLM
 * @version 1.0 yyyy/mm/dd
 */
@Service
public class HotelItemDeleteServiceImpl
		implements HotelItemDeleteService {

	/** Mapper */
	@Autowired
	private HotelItemMapper mapper;

	/**
	 * @see HotelItemDeleteService#deleteHotelItem(String)
	 */
	@Override
	public int deleteHotelItem(String HotelItemCode) {
		// MapperのdeleteHotelItemメソッドを呼び出し
//		削除した件数を格納
		int delCount = mapper.deleteHotelItem(HotelItemCode);

		// 削除件数が一件でない(失敗した)場合
		if (delCount != 1) {

			// 業務エラーを明示的に発生させる
			throw new BusinessException("エラーメッセージ");
		}

		return delCount;
	}

}
