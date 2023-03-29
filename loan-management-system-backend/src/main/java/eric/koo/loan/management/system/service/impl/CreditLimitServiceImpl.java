package eric.koo.loan.management.system.service.impl;

import eric.koo.loan.management.system.entity.CreditLimitEntity;
import eric.koo.loan.management.system.repository.CreditLimitRepository;
import eric.koo.loan.management.system.service.CreditLimitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
class CreditLimitServiceImpl implements CreditLimitService {

    @Value("${loan.management.system.default.credit.limit}")
    private BigDecimal defaultCreditLimit;

    private final CreditLimitRepository creditLimitRepository;

    @Autowired
    CreditLimitServiceImpl(CreditLimitRepository creditLimitRepository) {
        this.creditLimitRepository = creditLimitRepository;
    }

    @Override
    public CreditLimitEntity getDefaultCreditLimit() {
        var creditLimit = new CreditLimitEntity();
        creditLimit.setCreditLimitDate(LocalDate.now());
        creditLimit.setCreditLimit(defaultCreditLimit);

        return creditLimit;
    }

    @Transactional(readOnly = true)
    @Override
    public CreditLimitEntity getLatestOrDefaultCreditLimit() {
        return creditLimitRepository.getFirstByCreditLimitDateLessThanEqualOrderByCreditLimitDateDescCreatedDateDesc(LocalDate.now())
                .orElse(getDefaultCreditLimit());
    }

    @Override
    public List<CreditLimitEntity> findAllCreditLimit() {
        return creditLimitRepository.findAllByOrderByCreditLimitDateDescCreatedDateDesc();
    }

    @Transactional
    @Override
    public CreditLimitEntity createCreditLimit(BigDecimal creditLimit, LocalDate creditLimitDate) {
        var newCreditLimit = new CreditLimitEntity();
        newCreditLimit.setCreditLimit(creditLimit);
        newCreditLimit.setCreditLimitDate(creditLimitDate);

        return creditLimitRepository.save(newCreditLimit);
    }
}
