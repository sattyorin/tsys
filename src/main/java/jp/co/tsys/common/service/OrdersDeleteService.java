/**
 * OrdersDeleteService.java
 */

package jp.co.tsys.common.service;

/**
 *
 * @author FLM
 * @version 1.0.0
 */
public interface OrdersDeleteService {
	public void deleteCurrentOrder(String orderNo, String itemCode);
}
