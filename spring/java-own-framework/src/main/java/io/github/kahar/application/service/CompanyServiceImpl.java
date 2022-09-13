package io.github.kahar.application.service;

import io.github.kahar.application.dao.CompanyDao;
import io.github.kahar.application.model.Company;
import io.github.kahar.framework.annotation.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class CompanyServiceImpl implements CompanyService {

    private static final Logger logger = LoggerFactory.getLogger(CompanyServiceImpl.class);

    private final CompanyDao companyDao;

    public CompanyServiceImpl(CompanyDao companyDao) {
        this.companyDao = companyDao;
    }

    @Override
    public void createCompany(Company company) {
        try {
            beginTransaction();

            logger.info("SERVICE:   START - create company");
            companyDao.createCompany(company);
            logger.info("SERVICE:   END - create company");

            commitTransaction();
        } catch (Exception e) {
            rollbackTransaction();
        }
    }

    @Override
    public void updateCompany(Company company) {
        try {
            beginTransaction();

            logger.info("SERVICE:   START - update company");
            companyDao.createCompany(company);
            logger.info("SERVICE:   END - update company");

            commitTransaction();
        } catch (Exception e) {
            rollbackTransaction();
        }
    }

    private void beginTransaction() {
        logger.debug("BEGIN TRANSACTION");
    }

    private void commitTransaction() {
        logger.debug("COMMIT TRANSACTION");
    }

    private void rollbackTransaction() {
        logger.error("ROLLBACK TRANSACTION");
    }
}