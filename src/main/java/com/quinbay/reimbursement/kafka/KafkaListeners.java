package com.quinbay.reimbursement.kafka;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KafkaListeners {
    @Autowired
    ObjectMapper objectMapper;
//z
//    @KafkaListener(topics = "SEND.PRODUCT.INFO", groupId = "warehouse")
//    public void getProductDetail(ConsumerRecord<?,String> consumerRecord){
//        Product product = null;
//        try{
//            product = objectMapper.readValue(consumerRecord.value(), new TypeReference<Product>() {
//            });
//        }catch(Exception e){
//            System.out.print(e);
//        }
//        System.out.print("\n\n--------------------->"+product.getProName()+" "+product.getProId()+" "+product.getProName());
//    }
}
