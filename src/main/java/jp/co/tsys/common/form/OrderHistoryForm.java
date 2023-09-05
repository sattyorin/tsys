package jp.co.tsys.common.form;

import java.io.Serializable;
import java.lang.reflect.Member;
import java.util.List;

import jp.co.tsys.common.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * OrderHistoryForm.java
 *
 * All Rights Reserved, Copyright(c) Fujitsu Learning Media Limited
 */

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

	private List<Order> currentOrders;

	private List<Order> pastOrders;

}
