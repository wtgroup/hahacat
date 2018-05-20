package com.wtgroup.demo.neo4j.sdn;

import com.wtgroup.demo.neo4j.Application;
import com.wtgroup.demo.neo4j.domain.Person;
import com.wtgroup.demo.neo4j.repository.PersonRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

/**
 * @author Nisus-Liu
 * @version 1.0.0
 * @email liuhejun108@163.com
 * @date 2018-04-21-17:45
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class x01TestSDN {

    @Autowired
    private PersonRepository personRepository;
    @Test
    public void fun01(){
        Long count = personRepository.countWhereNameLike("Lilly.*");
        System.out.println(count);
    }

    @Test
    public void fun02(){
        Iterable<Person> people = personRepository.diyQueryByIdIsNot(99);
        people.forEach((r)->{
            System.out.println(r.getId());
            System.out.println(r.getName());
        });
    }


    //No matching PlatformTransactionManager bean found for qualifier 'transactionManager'
    @Test
    public void fun03(){
        Person p = new Person();
        p.setName("李白");
        p.setCreate(new Date());
        personRepository.save(p);

        Iterable<Person> res = personRepository.findByNameLike("李白");
        for (Person re : res) {
            System.out.println(re.getId());
            System.out.println(re.getName());
        }
    }



}
