package jp.co.tsys.common.control;

import static jp.co.tsys.common.util.MessageList.BIZERR000;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;

import jp.co.tsys.common.entity.Member;
import jp.co.tsys.common.exception.UserNotFoundException;
import jp.co.tsys.common.form.LoginForm;

/**
 * @author sara
 */
@ControllerAdvice
public class GlobalControllerAdvice {

	/**
	 * @param session
	 * @return loginMember
	 */
	@ModelAttribute("loginMember")
	public Member loginMember(HttpSession session, Model model,
			LoginForm loginForm) {
		Member loginMember = (Member) session.getAttribute("loginMember");

		// セッションが維持されている
		if (loginMember != null && loginMember.getMemberCode() != null) {
			session.setAttribute("loginMember", loginMember);
			return loginMember;
		}

		// login画面からの遷移
		if (loginForm != null) {
			// @retrievememberに遷移
			return new Member();
		}

		// login画面以外からの遷移
		// TODO(risa): Set erorr code.
		throw new UserNotFoundException(BIZERR000);
	}

	@ExceptionHandler(UserNotFoundException.class)
	public String nullError(HttpSession session, Model model,
			UserNotFoundException exception) {

		model.addAttribute("message", exception.getMessage());
		model.addAttribute("loginForm", new LoginForm());

		return "/login";
	}
}
