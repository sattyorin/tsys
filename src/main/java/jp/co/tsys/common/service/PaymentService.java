// * PaymentService.java

package jp.co.tsys.common.service;

import jp.co.tsys.common.entity.Orders;
import jp.co.tsys.common.exception.BusinessException;;

/**
 * 注文情報登録Service
 *
 * @author
 * @version 1.0 yyyy/mm/dd
 */
public interface PaymentService {

	/**
	 * 注文情報をデータベースに登録する 登録に成功した場合に予約番号を返す
	 *
	 * @param orders
	 *            注文情報
	 * @throws BusinessException
	 *             検索結果が存在しない場合
	 */
	public String addProduct(Orders orders) throws BusinessException;

}
