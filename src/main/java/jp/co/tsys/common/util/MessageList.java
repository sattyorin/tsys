/**
 * MessageList.java
 * All Rights Reserved, Copyright(c) Fujitsu Learning Media Limited
 */

package jp.co.tsys.common.util;

/**
 * メッセージを管理するユーティリティクラス
 * @author FLM
 * @version 1.0 yyyy/mm/dd
 */
public final class MessageList {

	/**
	 * システムエラー時のエラーメッセージ
	 * <ul>
	 * <li>メッセージID：SYSERR000</li>
	 * <li>メッセージ本文：{@value}</li>
	 * </ul>
	 */
	public static final String SYSERR000 = "システムエラーです。システム管理者に連絡してください。";

	/**
	 * 業務エラー（セッション無効）時のエラーメッセージ
	 * <ul>
	 * <li>メッセージID：BIZERR000</li>
	 * <li>メッセージ本文：{@value}</li>
	 * </ul>
	 */
	public static final String BIZERR000 = "セッションが無効になりました。再度操作をやりなおしてください。";

	/** プライベート・コンストラクター */
	private MessageList() {}
}
