/**
 * 
 */
package jp.co.tsys.common.control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.tsys.common.form.OrdersForm;
import jp.co.tsys.common.form.ShoppingCartForm;

@Controller
@RequestMapping("/payment")
public class PaymentController {
	@RequestMapping("/cart")
	public String inputCart(final ShoppingCartForm shoppingCartForm,
			BindingResult result, Model model) {
		return "shopping_cart";
	}

	@RequestMapping("/confirmation")
	public String confirmeResult(final OrdersForm ordersForm,
			BindingResult result, Model model) {
		return "order_confirmation";
	}

}
