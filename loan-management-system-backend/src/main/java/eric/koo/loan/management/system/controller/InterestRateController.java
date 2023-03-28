package eric.koo.loan.management.system.controller;

import eric.koo.loan.management.system.controller.model.response.InterestRateResponseModel;
import eric.koo.loan.management.system.controller.model.resquest.InterestRateCreateRequestModel;
import eric.koo.loan.management.system.entity.InterestRateEntity;
import eric.koo.loan.management.system.service.InterestRateService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/interest-rate")
class InterestRateController {

    private final InterestRateService interestRateService;

    @Autowired
    InterestRateController(InterestRateService interestRateService) {
        this.interestRateService = interestRateService;
    }

    @PostMapping
    InterestRateResponseModel createInterestRate(@Valid @RequestBody InterestRateCreateRequestModel interestRateCreateRequestModel) {
        var interestRate = interestRateService.createInterestRate(interestRateCreateRequestModel.getInterestRate(), interestRateCreateRequestModel.getInterestRateDate(), interestRateCreateRequestModel.getType());

        return createInterestRateResponseModel(interestRate);
    }

    @GetMapping
    InterestRateResponseModel getLatestOrDefaultInterestRate(@RequestParam("type") InterestRateEntity.Type type) {
        var interestRate = interestRateService.getLatestOrDefaultInterestRateByType(type);

        return createInterestRateResponseModel(interestRate);
    }

    private InterestRateResponseModel createInterestRateResponseModel(InterestRateEntity interestRate) {
        var interestRateResponse = new InterestRateResponseModel();
        BeanUtils.copyProperties(interestRate, interestRateResponse);

        return interestRateResponse;
    }
}
