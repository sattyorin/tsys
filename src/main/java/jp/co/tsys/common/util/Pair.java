/**
 * 
 */
package jp.co.tsys.common.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author hrds23
 *
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Pair<A, B> {
	public A first;
	public B second;
}
