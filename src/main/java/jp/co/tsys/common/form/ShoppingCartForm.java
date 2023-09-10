package jp.co.tsys.common.form;

import java.io.Serializable;
import java.util.List;

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
public class ShoppingCartForm implements Serializable {

	// TODO(masa): ListってNotBlank?
	// @NotBlank
	private List<Order> orders;

	private int orderTotal;

}
