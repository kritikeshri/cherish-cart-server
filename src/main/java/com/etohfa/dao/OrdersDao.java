package com.etohfa.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.etohfa.entity.Orders;
import com.etohfa.entity.User;

@Repository
public interface OrdersDao extends JpaRepository<Orders, Integer> {

	List<Orders> findByOrderId(String orderId);

	List<Orders> findByOrderIdAndStatusIn(String orderId, List<String> status);

	List<Orders> findByUser(User user);

	List<Orders> findByUserAndStatusIn(User user, List<String> status);

	@Query("SELECT o FROM Orders o WHERE o.product.seller = :seller and status In (:status)")
	List<Orders> findAllOrdersBySellerAndStatusIn(@Param("seller") User seller, @Param("status") List<String> status);

	@Query("SELECT o FROM Orders o WHERE o.product.seller = :seller And status In (:status) AND o.orderTime BETWEEN :startDate AND :endDate")
	List<Orders> findBySellerAndStatusAndOrderTime(@Param("seller") User seller, @Param("status") List<String> status,
			@Param("startDate") String startDate, @Param("endDate") String endDate);

	@Query("SELECT o FROM Orders o WHERE status In (:status) AND o.orderTime BETWEEN :startDate AND :endDate")
	List<Orders> findByStatusAndOrderTime(@Param("status") List<String> status, @Param("startDate") String startDate,
			@Param("endDate") String endDate);
	
	@Query("SELECT o FROM Orders o WHERE status In (:status) AND o.deliveryPerson = :deliveryPerson")
	List<Orders> findByStatusAndDeliveryPerson(@Param("status") List<String> status, @Param("deliveryPerson") User deliveryPerson);

	@Query("SELECT o FROM Orders o WHERE o.user.id = :userId AND o.product.id = :productId AND status In (:status)")
	List<Orders> findByUserIdAndProductIdAndStatusIn(@Param("userId") int userId,
			@Param("productId") int productId, @Param("status") List<String> status);
}
