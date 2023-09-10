package jp.co.tsys.common.control;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;

import jp.co.tsys.common.exception.UserNameNotFoundException;
import jp.co.tsys.common.form.LoginForm;

/**
 * @author sara
 *
 */
@ControllerAdvice
public class GlobalControllerAdvice {
	/**
	 * @param session
	 *            ControllerAdviceはModel非対応
	 * @return userName
	 */
	@ModelAttribute("userName")
	public String userName(HttpSession session, LoginForm loginForm) {
		String userName = (String) session.getAttribute("userName");

		if (loginForm == null && userName == null) {
			// TODO(risa): set erorr code
			// TODO(sara): Separate errors when the session times out and when
			// the user has not yet logged in.
			throw new UserNameNotFoundException("ログインしてください。");
		}
		// TODO(sara): get userName insted of MemberCode
		session.setAttribute("userName", loginForm.getMemberCode());
		return userName;
	}

	@ExceptionHandler(UserNameNotFoundException.class)
	public String nullError(HttpSession session, Model model,
			UserNameNotFoundException exception) {

		model.addAttribute("message", exception.getMessage());
		model.addAttribute("loginForm", new LoginForm());

		return "/login";
	}
}
