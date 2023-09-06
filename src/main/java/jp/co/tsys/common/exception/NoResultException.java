/**
 * NoResultException.java
 *
 * All Rights Reserved, Copyright(c) Fujitsu Learning Media Limited
 */

package jp.co.tsys.common.exception;

/**
 *
 * @author FLM
 * @version 1.0.0
 */
public class NoResultException extends RuntimeException {
	/**
	 * 業務例外を発生させる
	 * @param message エラーメッセージ
	 * @see RuntimeException#RuntimeException(String message)
	 */
	public NoResultException(String message) {
		super(message);
	}
}
