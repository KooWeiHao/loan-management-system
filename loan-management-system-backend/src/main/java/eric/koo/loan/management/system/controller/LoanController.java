package eric.koo.loan.management.system.controller;

import eric.koo.loan.management.system.controller.model.response.LoanResponseModel;
import eric.koo.loan.management.system.controller.model.resquest.LoanCreateRequestModel;
import eric.koo.loan.management.system.service.LoanService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/loan")
class LoanController {

    private final LoanService loanService;

    @Autowired
    LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping
    LoanResponseModel createLoan(@Valid @RequestBody LoanCreateRequestModel loanCreateRequestModel, Principal principal) {
        var newLoan = loanService.createLoan(loanCreateRequestModel.getAmount(), loanCreateRequestModel.getType(), loanCreateRequestModel.getPaymentType(), principal.getName());

        var loanResponse = new LoanResponseModel();
        BeanUtils.copyProperties(newLoan, loanResponse);
        loanResponse.setApplicantUsername(newLoan.getCreditFacility().getApplicant().getUsername());

        return loanResponse;
    }
}
