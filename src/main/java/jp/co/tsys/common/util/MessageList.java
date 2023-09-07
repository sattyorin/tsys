/**
 * MessageList.java
 * All Rights Reserved, Copyright(c) Fujitsu Learning Media Limited
 */

package jp.co.tsys.common.util;

/**
 * メッセージを管理するユーティリティクラス
 *
 * @author FLM
 * @version 1.0 yyyy/mm/dd
 */
public final class MessageList {
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!! 共通エラーメッセージ
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	/**
	 * システムエラー時のエラーメッセージ
	 * <ul>
	 * <li>メッセージID：SYSERR000</li>
	 * <li>メッセージ本文：{@value}</li>
	 * </ul>
	 */
	public static final String SYSERR000 = "こちらにお問い合わせください。";
	/**
	 * 業務エラー（セッション無効）時のエラーメッセージ
	 * <ul>
	 * <li>メッセージID：BIZERR000</li>
	 * <li>メッセージ本文：{@value}</li>
	 * </ul>
	 */
	public static final String BIZERR000 = "セッションが無効になりました。再度操作をやりなおしてください。";

	/**
	 * 業務エラー（入力したメンバーコードに対応するメンバーが見つからない）時のエラーメッセージ
	 * <ul>
	 * <li>メッセージID：BIZERR000</li>
	 * <li>メッセージ本文：{@value}</li>
	 * </ul>
	 */
	public static final String BIZERR001 = "入力したメンバーコードに対応するメンバーが見つかりません。";

	/**
	 * 業務エラー（該当するホテル商品がない）時のエラーメッセージ
	 * <ul>
	 * <li>メッセージID：BIZERR000</li>
	 * <li>メッセージ本文：{@value}</li>
	 * </ul>
	 */
	public static final String BIZERR002 = "該当するホテル商品が見つかりません。";

	/**
	 * 業務エラー（検索条件を満たすホテル商品が見つからない）時のエラーメッセージ
	 * <ul>
	 * <li>メッセージID：BIZERR000</li>
	 * <li>メッセージ本文：{@value}</li>
	 * </ul>
	 */

	// !!!!!!!!!!!!!!!!!!!!!!!!!!!! 商品販売エラーメッセージ
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	public static final String BIZERR101 = "検索条件を満たすホテル商品が見つかりません。";

	/**
	 * 業務エラー（予約部屋数が残室数を超えている）時のエラーメッセージ
	 * <ul>
	 * <li>メッセージID：BIZERR000</li>
	 * <li>メッセージ本文：{@value}</li>
	 * </ul>
	 */
	public static final String BIZERR102 = "予約部屋数が残室数を超えています。";

	/**
	 * 業務エラー（宿泊期間が不適切）時のエラーメッセージ
	 * <ul>
	 * <li>メッセージID：BIZERR000</li>
	 * <li>メッセージ本文：{@value}</li>
	 * </ul>
	 */
	public static final String BIZERR103 = "期間の範囲が不適切です。";

	/**
	 * 業務エラー（カートに商品が選択されていない）時のエラーメッセージ
	 * <ul>
	 * <li>メッセージID：BIZERR000</li>
	 * <li>メッセージ本文：{@value}</li>
	 * </ul>
	 */
	public static final String BIZERR104 = "カートに商品が選択されていません。";

	/**
	 * 業務エラー（チェックボックス未選択）時のエラーメッセージ
	 * <ul>
	 * <li>メッセージID：BIZERR000</li>
	 * <li>メッセージ本文：{@value}</li>
	 * </ul>
	 */
	public static final String BIZERR105 = "チェックボックスにチェックを入れてください。";

	public static final String BIZERR106 = "予約履歴はありません";

	public static final String BIZERR107 = "現在の予約履歴はありません。";

	public static final String BIZERR108 = "過去の予約履歴はありません。";


	// !!!!!!!!!!!!!!!!!!!!!!!!!!!! メンバー管理エラーメッセージ
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	/**
	 * 業務エラー（パスワードと確認用パスワードが不一致）時のエラーメッセージ
	 * <ul>
	 * <li>メッセージID：BIZERR000</li>
	 * <li>メッセージ本文：{@value}</li>
	 * </ul>
	 */
	public static final String BIZERR201 = "パスワードと確認用パスワードが一致していません。";

	/**
	 * 業務エラー（同じ電話番号が登録済み）時のエラーメッセージ
	 * <ul>
	 * <li>メッセージID：BIZERR000</li>
	 * <li>メッセージ本文：{@value}</li>
	 * </ul>
	 */
	public static final String BIZERR202 = "その電話番号はすでに登録されています。";

	/**
	 * 業務エラー（同じメールアドレスが登録済み）時のエラーメッセージ
	 * <ul>
	 * <li>メッセージID：BIZERR000</li>
	 * <li>メッセージ本文：{@value}</li>
	 * </ul>
	 */
	public static final String BIZERR203 = "そのメールアドレスはすでに登録されています。";

	/**
	 * 業務エラー（メンバーの絞り込み検索時に該当する情報がない）時のエラーメッセージ
	 * <ul>
	 * <li>メッセージID：BIZERR000</li>
	 * <li>メッセージ本文：{@value}</li>
	 * </ul>
	 */
	public static final String BIZERR204 = "該当するメンバー情報が見つかりません。";

	/**
	 * 業務エラー（既に退会されている）時のエラーメッセージ
	 * <ul>
	 * <li>メッセージID：BIZERR000</li>
	 * <li>メッセージ本文：{@value}</li>
	 * </ul>
	 */

	public static final String BIZERR205 = "すでに退会済みです。";

	/**
	 * 業務エラー（メンバーコードが重複している）時のエラーメッセージ
	 * <ul>
	 * <li>メッセージID：BIZERR000</li>
	 * <li>メッセージ本文：{@value}</li>
	 * </ul>
	 */

	public static final String BIZERR206 = "メンバーコードが重複しています。";

	// !!!!!!!!!!!!!!!!!!!!!!!!!!!! 商品管理エラーメッセージ
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	/**
	 * 業務エラー（ホテルコードとホテル名がともに未入力）時のエラーメッセージ
	 * <ul>
	 * <li>メッセージID：BIZERR000</li>
	 * <li>メッセージ本文：{@value}</li>
	 * </ul>
	 */
	public static final String BIZERR301 = "ホテルコードまたはホテル名を入力してください。";

	/**
	 * 業務エラー（該当するホテルがない）時のエラーメッセージ
	 * <ul>
	 * <li>メッセージID：BIZERR000</li>
	 * <li>メッセージ本文：{@value}</li>
	 * </ul>
	 */
	public static final String BIZERR302 = "該当するホテル情報が見つかりません。";

	/**
	 * 業務エラー（商品コード、ホテル名、宿泊日がともに未入力）時のエラーメッセージ
	 * <ul>
	 * <li>メッセージID：BIZERR000</li>
	 * <li>メッセージ本文：{@value}</li>
	 * </ul>
	 */
	public static final String BIZERR303 = "商品コード、ホテル名、宿泊日のうち1つ以上を入力してください。";

	/**
	 * 業務エラー（ホテル名、期間がともに未入力）時のエラーメッセージ
	 * <ul>
	 * <li>メッセージID：BIZERR000</li>
	 * <li>メッセージ本文：{@value}</li>
	 * </ul>
	 */
	public static final String BIZERR304 = "ホテル名、期間のうち1つ以上を入力してください。";

	/**
	 * 業務エラー（商品コードが重複している）時のエラーメッセージ
	 * <ul>
	 * <li>メッセージID：BIZERR000</li>
	 * <li>メッセージ本文：{@value}</li>
	 * </ul>
	 */
	public static final String BIZERR305 = "商品コードが重複しています。";

	/** プライベート・コンストラクター */
	private MessageList() {
	}
}
