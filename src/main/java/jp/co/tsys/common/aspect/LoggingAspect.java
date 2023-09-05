/**
 * LoggingAspect.java
 * All Rights Reserved, Copyright(c) Fujitsu Learning Media Limited
 */

package jp.co.tsys.common.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * AOPを利用したログ出力コンポーネント
 * @author FLM
 * @version 1.0 yyyy/mm/dd
 */
@Aspect
public class LoggingAspect {

	/** ロガー */
	private static final Logger LOGGER
			= LoggerFactory.getLogger(LoggingAspect.class);

	/**
	 * メソッド開始時にログを出力する
	 * @param point 織り込み箇所
	 */
	@Before(value = "execution(* jp.co.tsys.*.service.*ServiceImpl.*(..))")
	public void logBefore(JoinPoint point) {
		LOGGER.debug(getSignature(point) + " : START");
	}

	/**
	 * メソッド正常終了時にログを出力する
	 * @param point 織り込み箇所
	 * @param returnValue 戻り値
	 */
	@AfterReturning(
			value = "execution(* jp.co.tsys.*.service.*ServiceImpl.*(..))",
			returning = "returnValue")
	public void logAfterReturn(JoinPoint point, Object returnValue) {
		LOGGER.debug(getSignature(point) + " : END");
		LOGGER.debug(" ->> RETURNS : " + returnValue);
	}

	/**
	 * メソッドで例外発生時にログを出力する
	 * @param exception 例外
	 */
	@AfterThrowing(
			value = "execution(* jp.co.tsys.*.service.*ServiceImpl.*(..))",
			throwing = "exception")
	public void logAfterThrow(Exception exception) {
		LOGGER.error(exception.toString());
		StackTraceElement[] trace = exception.getStackTrace();
		for (StackTraceElement stackTraceElement : trace) {
			LOGGER.error(stackTraceElement.toString());
		}
	}

	/**
	 * メソッド終了時にログを出力する
	 * @param point 織り込み箇所
	 * @return 次画面名
	 * @throws Throwable 例外
	 */
	@After(value = "execution(* *..*Mapper.*(..))")
	public void logAfter(JoinPoint point) throws Throwable {
		String signature = getSignature(point);
		LOGGER.debug(signature + " : CALLED");
	}

	/**
	 * メソッド開始/終了時にログを出力する
	 * @param point 織り込み箇所
	 * @return 次画面名
	 * @throws Throwable 例外
	 */
	@Around(value = "within(jp.co.tsys.*.control.*)")
	public Object logAround(ProceedingJoinPoint point) throws Throwable {
		String signature = getSignature(point);
		LOGGER.debug(signature + " : START");
		Object returnValue = point.proceed();
		LOGGER.debug(signature + " : END");
		LOGGER.debug(" ->> RETURNS : " + returnValue);
		return returnValue;
	}

	/**
	 * 織り込み対象のシグニチャを取得する
	 * @param point 織り込み箇所
	 * @return 織り込み対象のシグニチャ
	 */
	private String getSignature(JoinPoint point) {
		return point.getSignature().getDeclaringType().getSimpleName() + "#"
				+ point.getSignature().getName();
	}
}
