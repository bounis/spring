package com.myspring.ioc.test;

import com.myspring.ioc.annotations.Autowired;
import com.myspring.ioc.annotations.PostConstruct;
import com.myspring.ioc.annotations.PreDestroy;
import com.myspring.ioc.annotations.Service;

@Service
public class TestServiceTwo {

    private TestServiceOne testServiceOne;

    @Autowired
    public TestServiceTwo(TestServiceOne testServiceOne) {
        this.testServiceOne = testServiceOne;
    }

    @PostConstruct
    private void onInit() {

    }

    @PreDestroy
    private void onPredestroy() {

    }
}
