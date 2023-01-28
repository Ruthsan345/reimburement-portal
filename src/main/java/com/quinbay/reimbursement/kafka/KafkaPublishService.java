//package com.quinbay.reimbursement.kafka;
//
//import com.example.wholesaler.model.Bill;
//import com.example.wholesaler.model.CalFinace;
//import com.example.wholesaler.model.Finance;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Service;
//
//@Service
//public class KafkaPublishService {
//    @Autowired
//    ObjectMapper objectMapper;
//
//    @Autowired
//    KafkaTemplate kafkaTemplate;
//
//    public void sendBillingInformation( Bill bill){
//
//        try{
//            kafkaTemplate.send("SEND.BILLING.INFO", this.objectMapper.writeValueAsString(bill));
//            System.out.print("Published");
//        }catch(Exception e){
//            System.out.print(e);
//        }
//
//    }
//
//    public void sendFinanceInformation(Finance finance) {
//        try{
//            kafkaTemplate.send("SEND.FINANCE.INFO", this.objectMapper.writeValueAsString(finance));
//            System.out.print("Published");
//        }catch(Exception e){
//            System.out.print(e);
//        }
//    }
//
//    public void sendDueInformation(String billId, int amount) {
//        try{
//            CalFinace calFinace =  new CalFinace(billId,amount);
//            kafkaTemplate.send("SEND.DUE.INFO", this.objectMapper.writeValueAsString(calFinace));
//            System.out.print("Published");
//        }catch(Exception e){
//            System.out.print(e);
//        }
//
//    }
//}
