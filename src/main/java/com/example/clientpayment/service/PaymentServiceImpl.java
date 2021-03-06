package com.example.clientpayment.service;

import com.example.clientpayment.model.PaymentRequest;
import com.example.clientpayment.model.PaymentResponse;
import com.example.clientpayment.repository.PaymentEntity;
import com.example.clientpayment.repository.PaymentRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    SendService sendService;

    ObjectMapper objectMapper = new ObjectMapper();

    static ModelMapper modelMapper = new ModelMapper();

    static {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    @Override
    public PaymentResponse createPayment(PaymentRequest paymentRequest) {
        paymentRequest.setPaymentId(UUID.randomUUID().toString());

        PaymentEntity paymentEntity = modelMapper.map(paymentRequest, PaymentEntity.class);

        paymentEntity = paymentRepository.save(paymentEntity);

        return modelMapper.map(paymentEntity, PaymentResponse.class);
    }

    @Override
    public PaymentResponse updatePayment(PaymentRequest paymentRequest) {
//        paymentRequest.setClientId(UUID.randomUUID().toString());
        PaymentEntity dbEntity = paymentRepository.getPaymentEntityByPaymentId(paymentRequest.getPaymentId());
        PaymentEntity paymentEntity = modelMapper.map(paymentRequest, PaymentEntity.class);

        paymentEntity.setPaymentId(dbEntity.getPaymentId());

        paymentRepository.save(paymentEntity);

        return modelMapper.map(paymentEntity, PaymentResponse.class);

    }

    @Override
    public void deletePaymentById(String paymentId) {
        paymentRepository.deletePaymentEntityByPaymentId(paymentId);
    }

    @Override
    public void deletePaymentsByClientId(String clientId) {
        paymentRepository.deletePaymentEntitiesByClientId(clientId);
    }

    @Override
    public PaymentResponse getPaymentByPaymentId(String paymentId) {
        PaymentEntity paymentEntity = paymentRepository.getPaymentEntityByPaymentId(paymentId);
        return modelMapper.map(paymentEntity, PaymentResponse.class);
    }

    @Override
    public Page<PaymentResponse> getPaymentsByClientId(String clientId, Pageable pageable) {
        return paymentRepository.getPaymentEntitiesByClientId(clientId, pageable)
                .map(payment -> modelMapper.map(payment, PaymentResponse.class));
    }

    @Override
    public Page<PaymentResponse> getAllPayments(Pageable pageable) {
        return paymentRepository.getPaymentEntitiesBy(pageable)
                .map(payment -> modelMapper.map(payment, PaymentResponse.class));
    }

    @Override
    public PaymentResponse makePaymentByPaymentId(String paymentId) throws JsonProcessingException {
        PaymentEntity paymentEntity = paymentRepository.getPaymentEntityByPaymentId(paymentId);
        List<PaymentResponse> payments = new ArrayList<>();
        payments.add(modelMapper.map(paymentEntity, PaymentResponse.class));
        sendService.send(objectMapper.writeValueAsString(payments));
        paymentEntity.setPaid(true);
        paymentRepository.save(paymentEntity);
        return modelMapper.map(paymentEntity, PaymentResponse.class);
    }

    @Override
    public List<PaymentResponse> makeAllPaymentsByClientId(String clientId) throws JsonProcessingException {
        List<PaymentEntity> paymentEntities = paymentRepository.getPaymentEntitiesByClientId(clientId);
        List<PaymentResponse> paymentResponses = new ArrayList<>();
        List<PaymentResponse> payments = new ArrayList<>();
        for (PaymentEntity entity : paymentEntities) {
            if (entity.isPaid()) continue;
            payments.add(modelMapper.map(entity, PaymentResponse.class));
            entity.setPaid(true);
            paymentRepository.save(entity);
            paymentResponses.add(modelMapper.map(entity, PaymentResponse.class));
        }
        sendService.send(objectMapper.writeValueAsString(payments));
        return paymentResponses;
    }

    @Override
    public Integer totalOfAllPaymentsByClientId(String clientId) {
        List<PaymentEntity> paymentEntities = paymentRepository.getPaymentEntitiesByClientId(clientId);
        int total = 0;
        for (PaymentEntity entity : paymentEntities) {
            if (!entity.isPaid()) total += entity.getCost();
        }
        return total;
    }
}
