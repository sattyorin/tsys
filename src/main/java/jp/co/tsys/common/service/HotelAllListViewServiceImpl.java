/**
 * HotelAllListViewServiceImpl.java
 */

package jp.co.tsys.common.service;

/**
 *
 * @author FLM
 * @version 1.0.0
 */
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.tsys.common.exception.BusinessException;
import jp.co.tsys.common.form.HotelItemDetailForm;
import jp.co.tsys.common.mapper.HotelItemMapper;
import jp.co.tsys.common.util.MessageList;

/**
 * ホテル商品検索Serviceの実装クラス
 * 
 * @author FLM
 * @version 1.0.0
 */
@Service
public class HotelAllListViewServiceImpl implements HotelAllListViewService {
	@Autowired
	private HotelItemMapper mapper;

	@Override
	public List<HotelItemDetailForm> getHotelItem(
			@Param("hotelName") String name,
			@Param("startdate") String startdate,
			@Param("enddate") String enddate) throws BusinessException {
		List<HotelItemDetailForm> hotelitemList = mapper.findHotelItemList(name,
				startdate, enddate);

		if (hotelitemList == null) {
			System.out.println("hotelitemList == nullです");
			throw new BusinessException(MessageList.BIZERR002);

		}
		System.out.println("serv--------------------------------------");
		return hotelitemList;
	}

}
