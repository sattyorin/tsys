/**
 * SearchHotelItemService.java
 */

package jp.co.tsys.common.service;

import java.util.List;

import jp.co.tsys.common.form.HotelItemDetailForm;

/**
 *
 * @author FLM
 * @version 1.0.0
 */
public interface SearchHotelItemService {

	public List<HotelItemDetailForm> searchHotelItemList(String itemCode,
			String hotelName, String Date);

	public HotelItemDetailForm retrieveHotelItem(String itemCode);

}
