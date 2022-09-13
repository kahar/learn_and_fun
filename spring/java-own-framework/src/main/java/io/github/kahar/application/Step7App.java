package io.github.kahar.application;

import io.github.kahar.application.model.Company;
import io.github.kahar.application.service.CompanyService;
import io.github.kahar.framework.ApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Step7App {

    private static final Logger logger = LoggerFactory.getLogger(Step7App.class);

    public static void main(String[] args) {
        final ApplicationContext applicationContext = new ApplicationContext(Step7App.class.getPackage());
        final CompanyService companyServiceProxy = applicationContext.getBean(CompanyService.class);

        logger.info("======== Transactional ========");
        companyServiceProxy.createCompany(new Company());
        logger.info("===============================");

        logger.info("==== Transactional correct ====");
        companyServiceProxy.createWithTransaction(new Company());
        logger.info("===============================");


        logger.info("========== Cacheable ==========");
        final Company company1 = new Company();
        logger.info(companyServiceProxy.generateToken(company1));
        logger.info(companyServiceProxy.generateToken(company1));

        final Company company2 = new Company();
        logger.info(companyServiceProxy.generateToken(company2));
        logger.info("===============================");


        logger.info("============= Scope ===========");
        final CompanyService companyServiceProxy1 = applicationContext.getBean(CompanyService.class);
        final CompanyService companyServiceProxy2 = applicationContext.getBean(CompanyService.class);

        logger.info(String.valueOf(companyServiceProxy1 == companyServiceProxy2));
        logger.info("===============================");
    }
}
