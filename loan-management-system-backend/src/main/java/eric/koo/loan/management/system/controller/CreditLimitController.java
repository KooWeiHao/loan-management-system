package eric.koo.loan.management.system.controller;

import eric.koo.loan.management.system.controller.model.response.CreditLimitResponseModel;
import eric.koo.loan.management.system.controller.model.resquest.CreditLimitCreateRequestModel;
import eric.koo.loan.management.system.entity.CreditLimitEntity;
import eric.koo.loan.management.system.service.CreditLimitService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("credit-limit")
class CreditLimitController {

    private final CreditLimitService creditLimitService;

    @Autowired
    CreditLimitController(CreditLimitService creditLimitService) {
        this.creditLimitService = creditLimitService;
    }

    @PostMapping
    CreditLimitResponseModel createCreditLimit(@Valid @RequestBody CreditLimitCreateRequestModel creditLimitCreateRequestModel) {
        var creditLimit = creditLimitService.createCreditLimit(creditLimitCreateRequestModel.getCreditLimit(), creditLimitCreateRequestModel.getCreditLimitDate());

        return createCreditLimitResponseModel(creditLimit);
    }

    @GetMapping
    CreditLimitResponseModel getLatestOrDefaultCreditLimit() {
        var creditLimit = creditLimitService.getLatestOrDefaultCreditLimit();

        return createCreditLimitResponseModel(creditLimit);
    }

    private CreditLimitResponseModel createCreditLimitResponseModel(CreditLimitEntity creditLimit) {
        var creditLimitResponse = new CreditLimitResponseModel();
        BeanUtils.copyProperties(creditLimit, creditLimitResponse);

        return creditLimitResponse;
    }
}
