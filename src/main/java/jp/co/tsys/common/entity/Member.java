package jp.co.tsys.common.entity;

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
public class Member {

	private String memberCode;

	private String name;

	private String password;

	private String role;

	private String mail;

	private String zipCode;

	private String prefecture;

	private String address;

	private String tel;

}