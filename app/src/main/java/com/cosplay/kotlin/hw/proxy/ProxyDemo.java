package com.cosplay.kotlin.hw.proxy;

/**
 * Author:wangzhiwei on 2019/1/28.
 * Email :wangzhiwei@moyi365.com
 * Description:
 */
public class ProxyDemo {
    public interface Ibuy {
        void buy();
    }
    public class BuyOwn implements Ibuy{
        @Override
        public void buy() {
        System.out.println("自己买的");
        }
    }
    public class BuyProxy implements Ibuy {
       people people1;

        public BuyProxy(people people1) {
            this.people1 = people1;
        }

        @Override
        public void buy() {
            System.out.println("代购的买的以前");
            people1.buy();
            System.out.println("代购的买完了");
        }
    }
    public class people implements Ibuy{
        @Override
        public void buy() {

        }
    }

}

