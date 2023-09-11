/**
 * HotelRegistServiceImpl.java
 */

package jp.co.tsys.common.service;

import static jp.co.tsys.common.util.MessageList.BIZERR306;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import jp.co.tsys.common.entity.Hotel;
import jp.co.tsys.common.entity.HotelItem;
import jp.co.tsys.common.exception.BusinessException;
import jp.co.tsys.common.mapper.HotelItemMapper;
import jp.co.tsys.common.mapper.HotelMasterMapper;

/**
 *
 * @author FLM
 * @version 1.0.0
 */

@Service
public class RegistHotelServiceImpl implements RegistHotelService {

	@Autowired
	private HotelMasterMapper findmapper;
	@Autowired
	private HotelItemMapper registmapper;

	// ホテルコードまたはホテル名で、ホテルマスタを検索する
	@Override
	public List<Hotel> findHotel(String hotelCode, String hotelName) {
		List<Hotel> hotelList = findmapper.findHotel(hotelCode, hotelName);

		return hotelList;
	}

	@Override
	public void registHotelItem(HotelItem hotelitem) throws BusinessException {
		try {
			registmapper.registHotelItem(hotelitem.getItemCode(),
					hotelitem.getHotel().getHotelCode(), hotelitem.getDate(),
					hotelitem.getPrice(), hotelitem.getStock());
		} catch (DuplicateKeyException e) {
			throw new BusinessException(BIZERR306);
		}
	}

	@Override
	public String getLastHotelCode() {
		String lastHotelCode = registmapper.getLastHotelCode();

		return lastHotelCode;
	}

	// ホテルコードと宿泊日と料金が一致しているモノがDBに存在するかをカウントする。
	@Override
	public int countHotelItem(String hotelCode, String date, int price) {
		int count = registmapper.countHotelItem(hotelCode, date, price);

		return count;
	}

}
