package eric.koo.loan.management.system.controller;

import eric.koo.loan.management.system.controller.model.response.LoanPaymentResponseModel;
import eric.koo.loan.management.system.controller.model.resquest.LoanPaymentCreateRequestModel;
import eric.koo.loan.management.system.service.LoanPaymentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/loan-payment")
class LoanPaymentController {

    private final LoanPaymentService loanPaymentService;

    @Autowired
    LoanPaymentController(LoanPaymentService loanPaymentService) {
        this.loanPaymentService = loanPaymentService;
    }

    @PostMapping
    LoanPaymentResponseModel createLoanPayment(@Valid @RequestBody LoanPaymentCreateRequestModel loanPaymentCreateRequestModel, Principal principal) {
        var loanPayment = loanPaymentService.createLoanPayment(loanPaymentCreateRequestModel.getLoanId(), loanPaymentCreateRequestModel.getAmount(), principal.getName());

        var loanPaymentResponse = new LoanPaymentResponseModel();
        BeanUtils.copyProperties(loanPayment, loanPaymentResponse);
        loanPaymentResponse.setLoanId(loanPayment.getLoan().getLoanId());

        return loanPaymentResponse;
    }
}
