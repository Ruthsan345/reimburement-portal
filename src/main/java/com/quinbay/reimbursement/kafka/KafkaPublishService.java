package com.quinbay.reimbursement.kafka;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.quinbay.reimbursement.model.Mailer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KafkaPublishService {
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    KafkaTemplate kafkaTemplate;

    public void sendMailingInformation( Mailer mailer){
        try{
            kafkaTemplate.send("SEND.MAILING.INFO", this.objectMapper.writeValueAsString(mailer));
            System.out.print("Published");
        }catch(Exception e){
            System.out.print(e);
        }
    }

    public void sendScheduledMail(List<Mailer> mailerList) {
        try{
            Object[] mails = mailerList.toArray();
            kafkaTemplate.send("SEND.SCHEDULER.INFO", this.objectMapper.writeValueAsString(mails));
            System.out.print("Published");
        }catch(Exception e){
            System.out.print(e);
        }
    }

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
}
