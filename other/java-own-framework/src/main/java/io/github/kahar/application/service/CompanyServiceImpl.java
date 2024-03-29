package io.github.kahar.application.service;

import io.github.kahar.application.dao.CompanyDao;
import io.github.kahar.application.model.Company;
import io.github.kahar.framework.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

@Component(scope = Scope.SINGLETON)
public class CompanyServiceImpl implements CompanyService {

    private static final Logger logger = LoggerFactory.getLogger(CompanyServiceImpl.class);

    private final CompanyDao companyDao;

    @Autowired
    public CompanyServiceImpl(CompanyDao companyDao) {
        this.companyDao = companyDao;
    }

    @Override
    public void createCompany(Company company) {
        logger.info("SERVICE:   START - create company");
        createWithTransaction(company);
        logger.info("SERVICE:   END - create company");
    }

    @Override
    @Cacheable
    public String generateToken(Company company) {
        return UUID.randomUUID().toString();
    }

    @Override
    @Transactional
    public void createWithTransaction(Company company) {
        logger.info("SERVICE:   START - createWithTransaction");
        companyDao.createCompany(company);
        logger.info("SERVICE:   END - createWithTransaction");
    }

    @Override
    @Transactional
    public void updateCompany(Company company) {
        logger.info("SERVICE:   START - update company");
        companyDao.createCompany(company);
        logger.info("SERVICE:   END - update company");
    }
}