package eric.koo.loan.management.system.controller;

import eric.koo.loan.management.system.controller.model.response.LoanPaymentResponseModel;
import eric.koo.loan.management.system.controller.model.resquest.LoanPaymentCreateRequestModel;
import eric.koo.loan.management.system.entity.LoanPaymentEntity;
import eric.koo.loan.management.system.security.RoleApplicant;
import eric.koo.loan.management.system.service.LoanPaymentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/loan-payment")
class LoanPaymentController {

    private final LoanPaymentService loanPaymentService;

    @Autowired
    LoanPaymentController(LoanPaymentService loanPaymentService) {
        this.loanPaymentService = loanPaymentService;
    }

    @RoleApplicant("createLoanPayment")
    @PostMapping
    LoanPaymentResponseModel createLoanPayment(@Valid @RequestBody LoanPaymentCreateRequestModel loanPaymentCreateRequestModel, Principal principal) {
        var loanPayment = loanPaymentService.createLoanPayment(loanPaymentCreateRequestModel.getLoanId(), loanPaymentCreateRequestModel.getAmount(), principal.getName());

        var loanPaymentResponse = new LoanPaymentResponseModel();
        BeanUtils.copyProperties(loanPayment, loanPaymentResponse);
        loanPaymentResponse.setLoanId(loanPayment.getLoan().getLoanId());

        return loanPaymentResponse;
    }

    // TODO: This endpoint is not secured
    @GetMapping
    List<LoanPaymentResponseModel> findLoanPaymentByLoanId(@RequestParam("loanId") Long loanId) {
        var loanPayments = loanPaymentService.findLoanPaymentByLoanId(loanId);

        return loanPayments.stream()
                .map(this::createLoanPaymentResponseModel)
                .collect(Collectors.toList());
    }

    private LoanPaymentResponseModel createLoanPaymentResponseModel(LoanPaymentEntity loanPayment) {
        var loanPaymentResponse = new LoanPaymentResponseModel();
        BeanUtils.copyProperties(loanPayment, loanPaymentResponse);
        loanPaymentResponse.setLoanId(loanPayment.getLoan().getLoanId());

        return loanPaymentResponse;
    }
}
