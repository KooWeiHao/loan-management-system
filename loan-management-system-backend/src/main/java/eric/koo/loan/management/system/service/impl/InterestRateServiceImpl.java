package eric.koo.loan.management.system.service.impl;

import eric.koo.loan.management.system.entity.InterestRateEntity;
import eric.koo.loan.management.system.repository.InterestRateRepository;
import eric.koo.loan.management.system.service.InterestRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@Service
class InterestRateServiceImpl implements InterestRateService {

    @Value("${loan.management.system.default.full.payment.interest.rate}")
    private BigDecimal defaultFullPaymentInterestRate;

    @Value("${loan.management.system.default.partial.payment.one.year.interest.rate}")
    private BigDecimal defaultPartialPaymentOneYearInterestRate;

    @Value("${loan.management.system.default.partial.payment.two.years.interest.rate}")
    private BigDecimal defaultPartialPaymentTwoYearsInterestRate;

    private final InterestRateRepository interestRateRepository;

    @Autowired
    InterestRateServiceImpl(InterestRateRepository interestRateRepository) {
        this.interestRateRepository = interestRateRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public InterestRateEntity getLatestOrDefaultInterestRateByType(InterestRateEntity.Type type) {
        return interestRateRepository.getFirstByTypeAndInterestRateDateLessThanEqualOrderByInterestRateDateDescCreatedDateDesc(type, LocalDate.now())
                .orElseGet(() -> {
                    var defaultInterestRateMap = Map.of(
                            InterestRateEntity.Type.FULL_PAYMENT, defaultFullPaymentInterestRate,
                            InterestRateEntity.Type.PARTIAL_PAYMENT_ONE_YEAR, defaultPartialPaymentOneYearInterestRate,
                            InterestRateEntity.Type.PARTIAL_PAYMENT_TWO_YEARS, defaultPartialPaymentTwoYearsInterestRate
                    );

                    var interestRate = new InterestRateEntity();
                    interestRate.setInterestRate(defaultInterestRateMap.get(type));
                    interestRate.setInterestRateDate(LocalDate.now());
                    interestRate.setType(type);

                    return interestRate;
                });
    }

    @Transactional
    @Override
    public InterestRateEntity createInterestRate(BigDecimal interestRate, LocalDate interestRateDate, InterestRateEntity.Type type) {
        var newInterestRate = new InterestRateEntity();
        newInterestRate.setInterestRate(interestRate);
        newInterestRate.setInterestRateDate(interestRateDate);
        newInterestRate.setType(type);

        return interestRateRepository.save(newInterestRate);
    }
}
