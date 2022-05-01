package com.example.clientpayment.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PaymentRepository extends ElasticsearchRepository<PaymentEntity, String> {
    Page<PaymentEntity> getPaymentEntitiesByClientId(String clientId, Pageable pageable);

    List<PaymentEntity> getPaymentEntitiesByClientId(String clientId);

//    Page<PaymentEntity> countPaymentEntitiesByClientId(String clientId, Pageable pageable);

    Page<PaymentEntity> getPaymentEntitiesBy(Pageable pageable);

    PaymentEntity getPaymentEntityByPaymentId(String paymentId);

    @Transactional
    void deletePaymentEntityByPaymentId(String paymentId);

    @Transactional
    void deletePaymentEntitiesByClientId(String clientId);
}
