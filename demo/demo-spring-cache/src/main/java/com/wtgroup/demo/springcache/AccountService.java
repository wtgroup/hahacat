package com.wtgroup.demo.springcache;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author Nisus Liu
 * @version 0.0.1
 * @email liuhejun108@163.com
 * @date 2018/6/7 14:43
 */
@Service("accountService")
public class AccountService {
    @Cacheable(value = "accountCache")// 使用了一个缓存名叫 accountCache / 默认使用入参第一个值作为缓存的key
    public Account getAccountByName(String userName) {
        // 方法内部实现不考虑缓存逻辑，直接实现业务
        System.out.println("real query account." + userName);
        return getFromDB(userName);
    }


    //清空缓存
    @CacheEvict(value = "accountCache", key = "#account.getUserName()",allEntries = true)// 清空 accountCache 缓存 / SEL(Spring Expression Language)
    // allEntries = true, 即使指定了 key , 也会将其他key的缓存删除, 所以慎用.
    // 只对同一缓存名下的有效, 不会影响其他缓存名下缓存, 因为背后对应了不同的ConcurrentMapCacheFactoryBean
    public void evictCache(Account account) {
        System.out.println("清空缓存, key:" + account.getUserName());

    }




    //模拟从数据库取数
    private Account getFromDB(String acctName) {
        System.out.println("real querying db..." + acctName);
        Account a = new Account(acctName);
        a.setMoney(10002454.64);
        a.setPassword("123jun");


        return a;
    }
}
