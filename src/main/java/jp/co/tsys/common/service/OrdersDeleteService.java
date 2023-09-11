package jp.co.tsys.common.service;

public interface OrdersDeleteService {
	public void deleteCurrentOrder(int orderNo, String itemCode, int quantity,
			int price);

	public void deleteOrderMaster();
}
