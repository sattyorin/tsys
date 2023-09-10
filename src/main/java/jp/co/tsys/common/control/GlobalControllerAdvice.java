package jp.co.tsys.common.control;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;

import jp.co.tsys.common.entity.Member;
import jp.co.tsys.common.exception.UserNameNotFoundException;
import jp.co.tsys.common.form.LoginForm;
import jp.co.tsys.common.service.RetrieveMemberService;

/**
 * @author sara
 */
@ControllerAdvice
public class GlobalControllerAdvice {
	@Autowired
	private RetrieveMemberService service;

	/**
	 * @param session
	 *            ControllerAdviceはModel非対応
	 * @return userName
	 */
	@ModelAttribute("userName")
	public Member userName(HttpSession session, LoginForm loginForm) {
		Member loginMember = (Member) session.getAttribute("loginMember");

		if (loginForm == null && loginMember == null) {
			// TODO(risa): set erorr code
			// TODO(sara): Separate errors when the session times out and when
			// the user has not yet logged in.
			throw new UserNameNotFoundException("ログインしてください。");
		}

		if (loginMember != null) {
			return loginMember;
		}

		loginMember = (Member) service.getMember(loginForm.getMemberCode(),
				loginForm.getPassword());
		session.setAttribute("loginMember", loginMember);
		return loginMember;
	}

	@ExceptionHandler(UserNameNotFoundException.class)
	public String nullError(HttpSession session, Model model,
			UserNameNotFoundException exception) {

		model.addAttribute("message", exception.getMessage());
		model.addAttribute("loginForm", new LoginForm());

		return "/login";
	}
}
