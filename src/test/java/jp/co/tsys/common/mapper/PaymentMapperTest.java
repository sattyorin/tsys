/**
 */

package jp.co.tsys.common.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import jp.co.tsys.common.entity.Hotel;
import jp.co.tsys.common.entity.HotelItem;
import jp.co.tsys.common.entity.Order;

/**
 *
 * @author FLM
 * @version 1.0.0
 */
@SpringBootApplication
public class PaymentMapperTest {

	@Autowired // ★★ テスト対象のMapperを設定する
	private PaymentMapper mapper;

	/**
	 * テスト環境準備用のmainメソッド このメソッド内で実行用のexecute()メソッドを呼び出す
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		ConfigurableApplicationContext context = null;
		try {
			// ★★ 自クラスのインスタンスを生成：作成したテストクラス名に変更すること
			PaymentMapperTest test = new PaymentMapperTest();

			// ★★ run()には自クラスを指定すること
			context = SpringApplication.run(PaymentMapperTest.class, args);
			// Mapperのbeanを取得
			context.getAutowireCapableBeanFactory().autowireBeanProperties(test,
					AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE, false);
			// テストを実行する
			test.execute();
		} finally {
			if (context != null) {
				context.close();
			}
		}
	}

	/**
	 * テスト実行用のexecuteメソッド
	 */
	public void execute() {
		// ★★ mapperのメソッドに対応したテストコードを記述
		List<Order> orderList = new ArrayList<>();

		Hotel hotel = new Hotel();

		HotelItem hotelItem1 = new HotelItem();
		hotelItem1.setItemCode("C123");
		hotelItem1.setPrice(12000);
		hotelItem1.setHotel(hotel);

		HotelItem hotelItem2 = new HotelItem();
		hotelItem2.setItemCode("C124");
		hotelItem2.setPrice(12000);
		hotelItem2.setHotel(hotel);

		Order order1 = new Order();
		order1.setQuantity(2);
		order1.setHotelItem(hotelItem1);

		Order order2 = new Order();
		order2.setQuantity(1);
		order2.setHotelItem(hotelItem2);

		orderList.add(order1);
		orderList.add(order2);

		final String orderNo = "17";
		mapper.insertOrderDetail(orderList, orderNo);

		System.out.println("======= TEST01 =======");
		System.out.println("データベースを確認してください。");
	}

}
