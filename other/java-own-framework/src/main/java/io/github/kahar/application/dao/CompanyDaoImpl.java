package io.github.kahar.application.dao;

import io.github.kahar.application.model.Company;
import io.github.kahar.framework.annotation.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class CompanyDaoImpl implements CompanyDao {

    private static final Logger logger = LoggerFactory.getLogger(CompanyDaoImpl.class);

    @Override
    public void createCompany(Company company) {
        logger.info("DAO:   START - create company");

        logger.info("DAO:   END - create company");
    }

    @Override
    public void updateCompany(Company company) {
        logger.info("DAO:   START - update company");

        logger.info("DAO:   END - update company");
    }
}