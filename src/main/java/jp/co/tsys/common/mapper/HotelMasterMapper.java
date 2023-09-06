/**
 * HotelMasterMapper.java
 *
 * All Rights Reserved, Copyright(c) Fujitsu Learning Media Limited
 */

package jp.co.tsys.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import jp.co.tsys.common.entity.Hotel;

/**
 *
 * @author FLM
 * @version 1.0.0
 */
@Mapper
public interface HotelMasterMapper {
	public List<Hotel> findHotel(@Param("hotelCode") String hotelCode,
			@Param("hotelName") String hotelName);

}
