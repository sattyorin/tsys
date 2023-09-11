package jp.co.tsys.common.form;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import jp.co.tsys.common.entity.Order;
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
public class OrdersForm implements Serializable {

	@NotBlank
	@Size(max = 6)
	private String memberCode;

	private String orderNo;

	private String orderDate;

	private int orderTotal;

	@NotBlank
	private String payment;

	private List<Order> orders;

}
