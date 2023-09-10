package jp.co.tsys.common.form;

import java.io.Serializable;
import java.util.List;

import jp.co.tsys.common.entity.Member;
import jp.co.tsys.common.entity.Order;
import jp.co.tsys.common.util.Pair;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author FLM
 * @version 1.0.0
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderHistoryForm implements Serializable {
	private Member member;

	private List<Pair<Order, String>> currentOrders;

	private List<Pair<Order, String>> pastOrders;

}
