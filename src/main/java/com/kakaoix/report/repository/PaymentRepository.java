package com.kakaoix.report.repository;

import com.kakaoix.report.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by ds on 2018-08-30.
 */
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    Iterable<Payment> findByUserIdx(final int user_idx);
}
