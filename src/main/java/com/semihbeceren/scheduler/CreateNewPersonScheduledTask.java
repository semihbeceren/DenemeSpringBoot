package com.semihbeceren.scheduler;

import com.semihbeceren.model.Person;
import com.semihbeceren.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by Semih Beceren on 15.11.2016.
 */
@Profile("scheduler")
@Component
public class CreateNewPersonScheduledTask {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private static int number;

    @Autowired
    private PersonService personService;

    //30 saniyede bir cronJob metodu çağırılacaktır.
    @Scheduled(cron = "${scheduler.person.cron}")
    public void cronJob(){
        logger.info("-> Cron Job");
        logger.info("New person saved...");

        Person person = new Person();
        person.setName("Semih" + number);
        person.setSurname("Beceren");
        personService.create(person);
        number ++;


        simulateJob(5000);//5 saniye süren bir işi simule eder. Farkın görülebilmesi için konuldu.


        logger.info("<- Cron Job");
    }

    //Uygulama açıldıktan 10 saniye sonra fixedRateJobWithInitialDelay metodu çağırılır, sonrasında bu metod her çağırıldığı andan 15 saniye sonrasında tekrar çağırılır.
    @Scheduled(initialDelayString = "${scheduler.person.initialdelay}", fixedRateString = "${scheduler.person.fixedrate}")
    public void fixedRateJobWithInitialDelay(){
        logger.info("-> Fixed Rate Job With initial delay");
        simulateJob(5000);//5 saniye süren bir işi simule eder. Farkın görülebilmesi için konuldu.
        logger.info("<- Fixed Rate Job With initial delay");
    }

    //Uygulama açıldıktan 10 saniye sonra fixedRateJobWithInitialDelay metodu çağırılır, sonrasında bu metod her işlemini tamamladığı andan 15 saniye sonrasında tekrar çağırılır.
    @Scheduled(initialDelayString = "${scheduler.person.initialdelay}", fixedDelayString = "${scheduler.person.fixeddelay}")
    public void fixedDelayJobWithInitialDelay(){
        logger.info("-> Fixed Delay Job With initial delay");
        simulateJob(5000);//5 saniye süren bir işi simule eder. Farkın görülebilmesi için konuldu.
        logger.info("<- Fixed Delay Job With initial delay");
    }


    private void simulateJob(long wait){
        long start = System.currentTimeMillis();
        do{
            if(start + wait < System.currentTimeMillis()){
                break;
            }
        }while (true);
    }
}
