/**
 * HotelRegistServiceImpl.java
 */

package jp.co.tsys.common.service;

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
	// ホテルコードまたはホテル名で、ホテルマスタを検索する
	@Override
	public List<Hotel> findHotel(String hotelCode, String hotelName) {

		List<Hotel> hotelList = findmapper.findHotel(hotelCode, hotelName);

		return hotelList;
	}

	@Autowired
	private HotelItemMapper hotelItemMapper;
	// 登録
	public void registHotelItem(HotelItem hotelitem) throws BusinessException {
		try {
			hotelItemMapper.registHotelItem(hotelitem.getItemCode(),
					hotelitem.getHotel().getHotelCode(), hotelitem.getDate(),
					hotelitem.getPrice(), hotelitem.getStock());
		} catch (DuplicateKeyException e) {
			throw new BusinessException("BIZERR305");
		}

	}

	public String getLastHotelCode() {
		String lastHotelCode = hotelItemMapper.getLastHotelCode();
		return lastHotelCode;

	}
}
