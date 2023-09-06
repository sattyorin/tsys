/**
 * HotelReservationServiceImpl.java
 */

package jp.co.tsys.common.service;

import static jp.co.tsys.common.util.MessageList.BIZERR101;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.tsys.common.entity.HotelItem;
import jp.co.tsys.common.exception.BusinessException;
import jp.co.tsys.common.mapper.HotelItemMapper;

/**
 *
 * ホテルの詳細情報を表示するためのService実装クラス
 * 
 * @author 大久保
 * @version 1.0.0
 */
@Service
public class HotelReservationServiceImpl {
	// Mapper
	@Autowired
	private HotelItemMapper mapper;

	// ItemCodeからホテル商品の1件検索をする
	public HotelItem findHotelDetail(String itemCode) {
		HotelItem hotelItem = mapper.findHotelDetail(itemCode);

		if (hotelItem == null) {
			throw new BusinessException(BIZERR101);
		}
		return hotelItem;
	}
}
